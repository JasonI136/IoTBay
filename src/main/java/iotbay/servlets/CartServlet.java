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
import iotbay.enums.OrderStatus;
import iotbay.exceptions.ProductStockException;
import iotbay.models.*;
import iotbay.models.httpResponses.GenericApiResponse;
import iotbay.util.Misc;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jasonmba
 */
public class CartServlet extends HttpServlet {

    DatabaseManager db;

    private static final Logger logger = LogManager.getLogger(CartServlet.class);
    private static final Logger iotbayLogger = LogManager.getLogger("iotbayLogger");

    /**
     * Initalises the servlet. Gets the database manager from the servlet context.
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        this.db = (DatabaseManager) getServletContext().getAttribute("db");
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
            if (path.equals("/checkout")) {
                request.setAttribute("stripe_pk", ((Properties) getServletContext().getAttribute("secrets")).getProperty("stripe.api.publishable.key"));
                request.getRequestDispatcher("/WEB-INF/jsp/checkout.jsp").forward(request, response);
            } else {
                response.sendError(404);
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

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        if (path != null) {
            if (path.equals("/clear")) {
                this.clearCart(request, response);
            } else {
                response.sendError(400);
            }
        } else {
            response.sendError(400);
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
            newOrder = this.db.getOrders().addOrder(user.getId(), new Timestamp(System.currentTimeMillis()), OrderStatus.PENDING);

            if (newOrder == null) {
                logger.error("Failed to create new order for user " + user.getId() + ".");
                Misc.sendJsonResponse(response,
                        GenericApiResponse.<String>builder()
                                .statusCode(500)
                                .message("Internal server error")
                                .data("There was an issue creating your order. You have not been charged.")
                                .error(true)
                                .build()
                );
                return;
            }

            Map<String, String> metadata = new HashMap<>();
            metadata.put("order_id", Integer.toString(newOrder.getId()));

            // create the order line items
            Cart cart = (Cart) request.getSession().getAttribute("shoppingCart");

            List<CartItem> cartItemsNotInStock = new ArrayList<>();
            for (CartItem cartItem : cart.getCartItems()) {
                try {
                    this.db.getOrderLineItems().addOrderLineItem(newOrder.getId(), cartItem.getProduct().getId(), cartItem.getCartQuantity(), cartItem.getTotalPrice());
                } catch (ProductStockException e) {
                    cartItemsNotInStock.add(cartItem);
                } catch (SQLException e) {
                    logger.error("Failed to create new order line items for order " + newOrder.getId() + ".");
                    Misc.sendJsonResponse(response,
                            GenericApiResponse.<String>builder()
                                    .statusCode(500)
                                    .message("Internal server error")
                                    .data("There was an issue creating your order. You have not been charged.")
                                    .error(true)
                                    .build()
                    );
                    this.db.getOrders().deleteOrder(newOrder.getId());
                    return;
                }
            }

            if (cartItemsNotInStock.size() > 0) {
                String cartItemsNotInStockString = cartItemsNotInStock.stream().map(CartItem::getProduct).map(Product::getName).collect(Collectors.joining(", "));
                Misc.sendJsonResponse(response,
                        GenericApiResponse.<String>builder()
                                .statusCode(400)
                                .message("Product(s) out of stock")
                                .data("The following items are no longer in stock: " + cartItemsNotInStockString)
                                .error(true)
                                .build()
                );
                // delete the order
                logger.warn("Failed to create new order line items for order " + newOrder.getId() + " due to product(s) out of stock.");
                iotbayLogger.warn("Failed to create new order line items for order " + newOrder.getId() + " due to product(s) out of stock.");
                this.db.getOrders().deleteOrder(newOrder.getId());
                return;
            }

            Invoice invoice;
            try {
                invoice = this.db.getInvoices().addInvoice(newOrder.getId(), date, (float) userShoppingCart.getTotalPrice() * 100);
            } catch (Exception e) {
                logger.error("Failed to create new invoice for order " + newOrder.getId() + ".");
                Misc.sendJsonResponse(response,
                        GenericApiResponse.<String>builder()
                                .statusCode(500)
                                .message("Internal server error")
                                .data("There was an issue creating your order. You have not been charged.")
                                .error(true)
                                .build()
                );
                this.db.getOrders().deleteOrder(newOrder.getId());
                return;
            }

            metadata.put("invoice_id", Integer.toString(invoice.getId()));
            params.put("metadata", metadata);

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            newOrder.setStripePaymentIntentId(paymentIntent.getId());
            newOrder.update();

            iotbayLogger.info("User " + user.getId() + " has initiated payment for new order " + newOrder.getId() + ".");
            logger.info("User " + user.getId() + " has initiated payment for new order " + newOrder.getId() + ".");

            // decrement product stock
            for (CartItem cartItem : cart.getCartItems()) {
                try {
                    // get the product but not from the cartItem as it may be out of stock. So get it from the database for the latest stock.
                    Product product = db.getProducts().getProduct(cartItem.getProduct().getId());
                    product.setQuantity(product.getQuantity() - cartItem.getCartQuantity());
                    this.db.getProducts().updateProduct(product);
                } catch (SQLException e) {
                    logger.error("Failed to decrement product stock for product " + cartItem.getProduct().getId() + ".");
                    Misc.sendJsonResponse(response,
                            GenericApiResponse.<String>builder()
                                    .statusCode(500)
                                    .message("Error")
                                    .data("There was an issue creating your order. You have not been charged.")
                                    .error(true)
                                    .build()
                    );
                    // rollback

                    // delete the invoice
                    this.db.getInvoices().deleteInvoice(invoice.getId());

                    // delete the order line items
                    this.db.getOrderLineItems().deleteOrderLineItems(newOrder.getId());

                    // delete the order
                    this.db.getOrders().deleteOrder(newOrder.getId());

                    return;
                }
            }

            // send payment intent to client
            Misc.sendJsonResponse(response,
                    GenericApiResponse.<PaymentIntent>builder()
                            .statusCode(200)
                            .message("Payment intent created")
                            .data(paymentIntent)
                            .build());
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
    }

    private void addCartItem(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            if (request.getParameter("productId") != null) {

                try {
                    Product product = this.db.getProducts().getProduct(Integer.parseInt(request.getParameter("productId")));
                    // check if product exists
                    if (product == null) {
                        Misc.sendJsonResponse(response,
                                GenericApiResponse.<String>builder()
                                        .statusCode(400)
                                        .message("Product not found")
                                        .data("This product does not exist. Please try again later.")
                                        .error(true)
                                        .build());
                        return;
                    }


                    int quantity = Integer.parseInt(request.getParameter("quantity"));

                    // check if quantity is valid
                    if (quantity < 1) {
                        response.sendError(400, "Invalid quantity");
                        return;
                    }

                    // check if product is in stock
                    if (!productIsInStock(product, quantity)) {
                        Misc.sendJsonResponse(response,
                                GenericApiResponse.<String>builder()
                                        .statusCode(400)
                                        .message("Product not in stock")
                                        .data("This product is not in stock or the quantity you have requested is not available.")
                                        .error(true)
                                        .build());
                        return;
                    }

                    this.initShoppingCart(request);
                    Cart userShoppingCart = (Cart) request.getSession().getAttribute("shoppingCart");
                    userShoppingCart.addCartItem(product, quantity);

                    Misc.sendJsonResponse(response,
                            GenericApiResponse.<String>builder()
                                    .statusCode(200)
                                    .message("Success")
                                    .data("Item added to cart")
                                    .error(false)
                                    .build()
                    );

                } catch (NumberFormatException e) {
                    Misc.sendJsonResponse(response,
                            GenericApiResponse.<String>builder()
                                    .statusCode(400)
                                    .message("Invalid product ID")
                                    .data("Invalid product ID")
                                    .error(true)
                                    .build()
                    );
                } catch (IOException e) {
                    throw new ServletException(e.getMessage());
                }
            }
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }

    }

    private void clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        request.getSession().removeAttribute("shoppingCart");
        this.initShoppingCart(request);

        try {
            Misc.sendJsonResponse(response,
                    GenericApiResponse.<String>builder()
                            .statusCode(200)
                            .message("Success")
                            .data("Cart cleared")
                            .error(false)
                            .build()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateCart(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            Gson gson = new Gson();
            String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            JsonObject payload = gson.fromJson(json, JsonObject.class);

            this.initShoppingCart(request);
            Cart userShoppingCart = (Cart) request.getSession().getAttribute("shoppingCart");

            List<Product> productsNotInStock = new ArrayList<>();
            for (Map.Entry<String, JsonElement> cartItem : payload.entrySet()) {
                int productId = Integer.parseInt(cartItem.getKey());
                int quantity = cartItem.getValue().getAsInt();

                Product product = this.db.getProducts().getProduct(productId);

                // check if product is in stock
                if (!productIsInStock(product, quantity) && quantity > 0) {
                    productsNotInStock.add(product);
                    continue;
                }

                userShoppingCart.updateCartItem(product, quantity);
            }

            if (productsNotInStock.size() > 0) {
                String productsNotInStockString = productsNotInStock.stream().map(Product::getName).collect(Collectors.joining(", "));
                Misc.sendJsonResponse(response,
                        GenericApiResponse.<String>builder()
                                .statusCode(400)
                                .message("Products not in stock")
                                .data("The following products are not in stock or the quantity you have requested is not available: " + productsNotInStockString + ". However, your cart has been updated with the items that are in stock.")
                                .error(true)
                                .build()
                );
                return;
            }

            Misc.sendJsonResponse(response,
                    GenericApiResponse.<String>builder()
                            .statusCode(200)
                            .message("Success")
                            .data("Cart updated")
                            .error(false)
                            .build()
            );
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

    private boolean productIsInStock(Product product, int desiredQuantity) {
        return product.getQuantity() > 0 && desiredQuantity <= product.getQuantity();
    }

}
