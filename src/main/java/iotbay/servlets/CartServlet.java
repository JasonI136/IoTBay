/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package iotbay.servlets;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import iotbay.database.DatabaseManager;
import iotbay.exceptions.ProductStockException;
import iotbay.models.Cart;
import iotbay.models.CartItem;
import iotbay.models.Product;
import iotbay.models.User;
import iotbay.models.httpResponses.GenericApiResponse;
import iotbay.util.CheckoutSession;
import iotbay.util.CustomHttpServletRequest;
import iotbay.util.CustomHttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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
                response.sendError(404);
            }
        } else {
            response.sendError(400);
        }
    }

    private void checkOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomHttpServletResponse res = (CustomHttpServletResponse) response;
        CustomHttpServletRequest req = (CustomHttpServletRequest) request;
        try {
            this.initShoppingCart(request);
            Cart userShoppingCart = (Cart) request.getSession().getAttribute("shoppingCart");
            User user = (User) request.getSession().getAttribute("user");

            CheckoutSession checkoutSession = new CheckoutSession(userShoppingCart, user, this.db);
            PaymentIntent paymentIntent = checkoutSession.initiateCheckout();

            // send payment intent to client
            res.sendJsonResponse(
                    GenericApiResponse.<PaymentIntent>builder()
                            .statusCode(200)
                            .message("Payment intent created")
                            .data(paymentIntent)
                            .build());
        } catch (SQLException | StripeException | IOException e) {
            res.sendJsonResponse(
                    GenericApiResponse.<String>builder()
                            .statusCode(500)
                            .message("Error")
                            .data("There was an error processing your request. Please try again later.")
                            .error(true)
                            .build());
        } catch (ProductStockException e) {
            // only send product id and name. Use a map to store the product id and name
            Map<Integer, String> outOfStockProducts = e.outOfStockProducts.stream().collect(Collectors.toMap(Product::getId, Product::getName));

            res.sendJsonResponse(
                    GenericApiResponse.<Map<Integer, String>>builder()
                            .statusCode(400)
                            .message("Product(s) out of stock")
                            .data(outOfStockProducts)
                            .error(true)
                            .build());

        }
    }

    private void addCartItem(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        CustomHttpServletResponse res = (CustomHttpServletResponse) response;
        CustomHttpServletRequest req = (CustomHttpServletRequest) request;
        try {
            if (request.getParameter("productId") != null) {

                try {
                    Product product = this.db.getProducts().getProduct(Integer.parseInt(request.getParameter("productId")));
                    // check if product exists
                    if (product == null) {
                        res.sendJsonResponse(
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
                        res.sendJsonResponse(
                                GenericApiResponse.<String>builder()
                                        .statusCode(400)
                                        .message("Invalid quantity")
                                        .data("The quantity you have entered is invalid. Please try again.")
                                        .error(true)
                                        .build()
                        );
                        return;
                    }

                    // check if product is in stock
                    if (!productIsInStock(product, quantity)) {
                        res.sendJsonResponse(
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

                    res.sendJsonResponse(
                            GenericApiResponse.<String>builder()
                                    .statusCode(200)
                                    .message("Success")
                                    .data("Item added to cart")
                                    .error(false)
                                    .build()
                    );

                } catch (NumberFormatException e) {
                    res.sendJsonResponse(
                            GenericApiResponse.<String>builder()
                                    .statusCode(400)
                                    .message("Invalid product ID")
                                    .data("Invalid product ID")
                                    .error(true)
                                    .build()
                    );
                } catch (ProductStockException e) {
                    res.sendJsonResponse(
                            GenericApiResponse.<String>builder()
                                    .statusCode(400)
                                    .message("Product not in stock")
                                    .data("The amount of this item in your cart exceeds the amount in stock. Please try again later.")
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

    private void clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomHttpServletResponse res = (CustomHttpServletResponse) response;
        CustomHttpServletRequest req = (CustomHttpServletRequest) request;

        request.getSession().removeAttribute("shoppingCart");
        this.initShoppingCart(request);

        res.sendJsonResponse(
                GenericApiResponse.<String>builder()
                        .statusCode(200)
                        .message("Success")
                        .data("Cart cleared")
                        .error(false)
                        .build()
        );
    }

    private void updateCart(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        CustomHttpServletResponse res = (CustomHttpServletResponse) response;
        CustomHttpServletRequest req = (CustomHttpServletRequest) request;
        try {
            JsonObject payload = req.getJsonBody();

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
                res.sendJsonResponse(
                        GenericApiResponse.<String>builder()
                                .statusCode(400)
                                .message("Products not in stock")
                                .data("The following products are not in stock or the quantity you have requested is not available: " + productsNotInStockString + ". However, your cart has been updated with the items that are in stock.")
                                .error(true)
                                .build()
                );
                return;
            }

            res.sendJsonResponse(
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
