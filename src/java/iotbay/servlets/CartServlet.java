/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package iotbay.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import iotbay.database.DatabaseManager;
import iotbay.models.Products;
import iotbay.models.cart.Cart;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author jasonmba
 */
public class CartServlet extends HttpServlet {

    DatabaseManager db;

    Products products;

    /**
     * Initalises the servlet. Gets the database manager from the servlet context.
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/cart.jsp").forward(request, response);
    }

    /**
     * This post method is used to add or update items in the cart.
     * / -> adds the item to the cart.
     * /updateCart -> updates the cart.
     *
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

        if (path != null && path.equals("/updateCart")) {
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
        } else {
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
            //response.sendRedirect(request.getHeader("referer"));
        }

    }

    /**
     * Initialises the shopping cart if it does not exist.
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
