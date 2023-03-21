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
import iotbay.models.collections.Products;
import iotbay.models.entities.Cart;
import iotbay.models.entities.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jasonmba
 */
public class CartServlet extends HttpServlet {

    DatabaseManager db;

    Products products;

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
        try {
            this.initShoppingCart(request);
            Cart userShoppingCart = (Cart) request.getSession().getAttribute("shoppingCart");
            User user = (User) request.getSession().getAttribute("user");

            // create stripe payment intent
            Map<String, Object> params = new HashMap<>();
            params.put("amount", (int) userShoppingCart.getTotalPrice() * 100);
            params.put("currency", "aud");
            params.put("payment_method",user.getPaymentMethod(9).getStripePaymentMethodId());
            params.put("customer", user.getStripeCustomerId());

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            // send payment intent to client
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new Gson().toJson(paymentIntent));
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
    }

    private void addCartItem(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        if (request.getParameter("productId") != null) {
            try {
                this.initShoppingCart(request);
                Cart userShoppingCart = (Cart) request.getSession().getAttribute("shoppingCart");
                userShoppingCart.addCartItem(this.products.getProduct(Integer.parseInt(request.getParameter("productId"))), Integer.parseInt(request.getParameter("quantity")));
                response.setStatus(200);
            } catch (Exception e) {
                throw new ServletException(e.getMessage());
            }
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
