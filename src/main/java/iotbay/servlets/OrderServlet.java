/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package iotbay.servlets;

import com.stripe.model.PaymentIntent;
import iotbay.models.collections.Invoices;
import iotbay.models.collections.OrderLineItems;
import iotbay.models.collections.Orders;
import iotbay.models.collections.Payments;
import iotbay.models.entities.Cart;
import iotbay.models.entities.CartItem;
import iotbay.models.entities.Order;
import iotbay.models.entities.User;
import iotbay.models.entities.Invoice;
import iotbay.models.enums.OrderStatus;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jasonmba
 */
public class OrderServlet extends HttpServlet {

    Orders orders;

    Payments payments;

    Invoices invoices;

    OrderLineItems orderLineItems;

    @Override
    public void init() throws ServletException {
        super.init();

        orders = (Orders) getServletContext().getAttribute("orders");
        payments = (Payments) getServletContext().getAttribute("payments");
        invoices = (Invoices) getServletContext().getAttribute("invoices");
        orderLineItems = (OrderLineItems) getServletContext().getAttribute("orderLineItems");
    }


    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet OrderServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/order.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();

        if (path != null) {
            switch(path) {
                case "/confirm":
                    confirmOrder(request, response);
                    return;
                default:
                    response.sendError(404);
                    return;
            }
        }

        response.sendError(404);
    }

    private void confirmOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get the stripe payment intent id from the request parameter and verify it with the stripe api.
// if the payment intent is valid, then create the order and redirect to the order confirmation page.
// if the payment intent is invalid, then redirect to the order page with an error message.

        String paymentIntentId = request.getParameter("paymentIntentId");
        Timestamp date = new Timestamp(new java.util.Date().getTime());
        PaymentIntent paymentIntent;

        try {
            paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        } catch (Exception e) {response.sendError(500);
            throw new ServletException(e);
        }

        String orderSessionId = (String) request.getSession().getAttribute("orderSessionId");

        if (paymentIntent.getMetadata().get("orderSessionId") == null || !paymentIntent.getMetadata().get("orderSessionId").equals(orderSessionId)) {
            response.sendError(400);
            return;
        }

        if (paymentIntent.getStatus().equals("succeeded")) {
            User user = (User) request.getSession().getAttribute("user");
            // create the order
            Order order;
            try {
                order = this.orders.addOrder(user.getId(), date, OrderStatus.PROCESSING.getValue());
            } catch (Exception e) {
                throw new ServletException(e);
            }
            // create the order line items
            Cart cart = (Cart) request.getSession().getAttribute("shoppingCart");

            for (CartItem cartItem: cart.getCartItems()) {
                try {
                    this.orderLineItems.addOrderLineItem(order.getId(), cartItem.getProduct().getId(), cartItem.getCartQuantity());
                } catch (Exception e) {
                    throw new ServletException(e);
                }
            }

            // make the invoice
            Invoice invoice;

            try {
                invoice = this.invoices.addInvoice(order.getId(), date, paymentIntent.getAmount().floatValue());
            } catch (Exception e) {
                throw new ServletException(e);
            }

            // make the payment
            try {
                this.payments.addPayment(invoice.getId(), date, user.getPaymentMethodByStripeId(paymentIntent.getPaymentMethod()).getId(), paymentIntent.getAmount().floatValue());
            } catch (Exception e) {
                throw new ServletException(e);
            }

            // clear the cart
            request.getSession().setAttribute("shoppingCart", new Cart());
            request.getSession().setAttribute("orderSessionId", null);

            response.setStatus(200);
            // redirect to the order confirmation page
        } else {
            // redirect to the order page with an error message
            response.sendError(400);
        }




    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
