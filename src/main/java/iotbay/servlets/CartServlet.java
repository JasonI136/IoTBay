/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package iotbay.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.stripe.model.PaymentIntent;
import iotbay.database.DatabaseManager;
import iotbay.exceptions.ProductNotFoundException;
import iotbay.exceptions.UserNotFoundException;
import iotbay.exceptions.UserNotLoggedInException;
import iotbay.models.collections.*;
import iotbay.models.entities.*;
import iotbay.models.enums.OrderStatus;
import iotbay.util.Misc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author jasonmba
 */
public class CartServlet extends HttpServlet {

    DatabaseManager db;

    Products products;

    Orders orders;

    OrderLineItems orderLineItems;

    Invoices invoices;

    Users users;

    /**
     * Initalises the servlet. Gets the database manager from the servlet context.
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        this.db = (DatabaseManager) getServletContext().getAttribute("db");
        this.products = (Products) getServletContext().getAttribute("products");
        this.orders = (Orders) getServletContext().getAttribute("orders");
        this.orderLineItems = (OrderLineItems) getServletContext().getAttribute("orderLineItems");
        this.invoices = (Invoices) getServletContext().getAttribute("invoices");
        this.users = (Users) getServletContext().getAttribute("users");
    }


    /**
     * HTTP GET /cart
     * Displays the cart page.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();

        if (path != null) {
            switch (path) {
                case "/checkout":
                    try {
                        Misc.refreshUser(request, users);
                    } catch (UserNotLoggedInException | UserNotFoundException e) {
                        request.getSession().setAttribute("redirect", "/cart/checkout");
                        response.sendRedirect(getServletContext().getContextPath() + "/login");
                        return;
                    } catch (Exception e) {
                        throw new ServletException(e);
                    }
                    request.setAttribute("stripe_pk", ((Properties) getServletContext().getAttribute("secrets")).getProperty("stripe.api.publishable.key"));
                    request.getRequestDispatcher("/WEB-INF/jsp/checkout.jsp").forward(request, response);
                    break;
                default:
                    response.sendError(404);
                    break;
            }
        } else {
            request.getRequestDispatcher("/WEB-INF/jsp/cart.jsp").forward(request, response);
        }


    }

    /**
     * This post method is used to add or update items in the cart.
     * / -> adds the item to the cart.
     * /updateCart -> updates the cart.
     * <p>
     * METHOD: POST
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();

        if (path != null) {
            switch (path) {
                case "/update":
                    this.updateCart(request, response);
                    break;
                case "/checkout":
                    this.checkOut(request, response);
                    break;
                default:
                    response.sendError(400);
                    break;
            }
        } else {
            this.addCartItem(request, response);
        }

    }

    private void checkOut(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        Order newOrder = null;
        try {
            this.initShoppingCart(request);
            Cart userShoppingCart = (Cart) request.getSession().getAttribute("shoppingCart");
            User user = (User) request.getSession().getAttribute("user");

            // create stripe payment intent
            Map<String, Object> params = new HashMap<>();
            params.put("amount", (int) userShoppingCart.getTotalPrice() * 100);
            params.put("currency", "aud");
            params.put("customer", user.getStripeCustomerId());
            // add metadata

            // create a new order
            newOrder = this.orders.addOrder(user.getId(), new Timestamp(System.currentTimeMillis()), OrderStatus.PENDING);

            if (newOrder == null) {
                throw new Exception("Failed to create order");
            }

            Map<String, String> metadata = new HashMap<>();
            metadata.put("order_id", Integer.toString(newOrder.getId()));

            // create the order line items
            Cart cart = (Cart) request.getSession().getAttribute("shoppingCart");

            for (CartItem cartItem : cart.getCartItems()) {
                try {
                    this.orderLineItems.addOrderLineItem(newOrder.getId(), cartItem.getProduct().getId(), cartItem.getCartQuantity(), cartItem.getTotalPrice());
                } catch (Exception e) {
                    throw new ServletException(e);
                }
            }

            Invoice invoice;
            try {
                invoice = this.invoices.addInvoice(newOrder.getId(), date, (float) userShoppingCart.getTotalPrice() * 100);
            } catch (Exception e) {
                throw new ServletException(e);
            }

            metadata.put("invoice_id", Integer.toString(invoice.getId()));
            params.put("metadata", metadata);

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            newOrder.setStripePaymentIntentId(paymentIntent.getId());
            newOrder.update();

            // send payment intent to client
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new Gson().toJson(paymentIntent));
        } catch (Exception e) {
            // if there is an error, delete the order
            if (newOrder != null) {
                try {
                    this.orders.deleteOrder(newOrder.getId());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new ServletException(e.getMessage());
        }
    }

    private void addCartItem(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            if (request.getParameter("productId") != null) {

                try {
                    Product product = this.products.getProduct(Integer.parseInt(request.getParameter("productId")));
                    if (product == null) {
                        response.sendError(404, "Product ID not found");
                        return;
                    }

                    int quantity = Integer.parseInt(request.getParameter("quantity"));

                    if (quantity < 1) {
                        response.sendError(400, "Invalid quantity");
                        return;
                    }
                    try {
                        this.initShoppingCart(request);
                        Cart userShoppingCart = (Cart) request.getSession().getAttribute("shoppingCart");
                        userShoppingCart.addCartItem(product, quantity);
                        response.setStatus(200);
                    } catch (Exception e) {
                        response.sendError(400, "Invalid product id");
                        return;
                    }

                } catch (NumberFormatException e) {
                    response.sendError(400, "Invalid product id");
                    return;
                } catch (ProductNotFoundException e) {
                    response.sendError(404, "Product ID not found");
                    return;
                }
            }
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }

    }

    private void updateCart(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            Gson gson = new Gson();
            String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            JsonObject payload = gson.fromJson(json, JsonObject.class);

            this.initShoppingCart(request);
            Cart userShoppingCart = (Cart) request.getSession().getAttribute("shoppingCart");

            for (Map.Entry<String, JsonElement> cartItem : payload.entrySet()) {
                int productId = Integer.parseInt(cartItem.getKey());
                int quantity = cartItem.getValue().getAsInt();
                userShoppingCart.updateCartItem(this.products.getProduct(productId), quantity);
            }

            response.setStatus(200);
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
    }

    /**
     * Initialises the shopping cart if it does not exist.
     *
     * @param request
     */
    private void initShoppingCart(HttpServletRequest request) {
        Cart userShoppingCart = (Cart) request.getSession().getAttribute("shoppingCart");
        if (userShoppingCart == null) {
            userShoppingCart = new Cart();
            request.getSession().setAttribute("shoppingCart", userShoppingCart);
        }
    }

}
