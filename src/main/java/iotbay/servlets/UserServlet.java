/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package iotbay.servlets;

import com.stripe.model.PaymentMethod;
import com.stripe.model.SetupIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import iotbay.database.DatabaseManager;
import iotbay.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * @author cmesina
 */
public class UserServlet extends HttpServlet {

    DatabaseManager db;

    private static final Logger logger = LogManager.getLogger(UserServlet.class);
    private static final Logger iotbayLogger = LogManager.getLogger("iotbayLogger");

    @Override
    public void init() throws ServletException {
        super.init();
        this.db = (DatabaseManager) getServletContext().getAttribute("db");
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo() == null ? "/" : request.getPathInfo();
        // refresh the user
        //if (Misc.refreshUser(request, response, this.db.getUsers())) return;

        switch (path) {
            case "/payments/add/success":
                addPaymentMethodSuccess(request, response);
                return;
            case "/payments/add/cancel":
                response.sendRedirect( getServletContext().getContextPath() + "/user");
                return;
            case "/":
                request.getRequestDispatcher("/WEB-INF/jsp/user.jsp").forward(request, response);
                return;
            default:
                response.sendError(404);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();

        if (path != null) {
            switch (request.getPathInfo()) {
                case "/payments/add":
                    addPaymentMethod(request, response);
                    return;
                case "/payments/remove":
                    removePaymentMethod(request, response);
                    return;
                default:
                    response.sendError(404);
            }
        }


    }

    private void removePaymentMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        // detach payment method from stripe customer and remove from database
        int paymentMethodId = Integer.parseInt(request.getParameter("paymentMethodId"));
        User user = (User) request.getSession().getAttribute("user");

        iotbay.models.PaymentMethod paymentMethod;

        try {
            paymentMethod = user.getPaymentMethod(paymentMethodId);
        } catch (Exception e) {
            throw new ServletException(e);
        }


        // the payment method not found
        if (paymentMethod == null) {
            try {
                response.sendError(404);
                return;
            } catch (IOException e) {
                throw new ServletException(e);
            }
        }

        try {
            PaymentMethod stripePaymentMethod = PaymentMethod.retrieve(paymentMethod.getStripePaymentMethodId());
            stripePaymentMethod.detach();
            user.deletePaymentMethod(paymentMethod);

            logger.info("User " + user.getId() + " removed a payment method");
            iotbayLogger.info("User " + user.getId() + " removed a payment method");

            response.sendRedirect(request.getContextPath() + "/user");

        } catch (Exception e) {
            throw new ServletException(e);
        }

    }

    private static void addPaymentMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                        .setMode(SessionCreateParams.Mode.SETUP)
                        .setCustomer(((User) request.getSession().getAttribute("user")).getStripeCustomerId())
                        .setSuccessUrl("http://localhost:8080/IoTBay/user/payments/add/success?session_id={CHECKOUT_SESSION_ID}")
                        .setCancelUrl("http://localhost:8080/IoTBay/user/payments/add/cancel")
                        .build();

        try {
            Session session = Session.create(params);
            response.sendRedirect(session.getUrl());
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void addPaymentMethodSuccess(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String sessionId = request.getParameter("session_id");
        try {
            Session session = Session.retrieve(sessionId);
            SetupIntent setupIntent = SetupIntent.retrieve(session.getSetupIntent());
            try {
                User user = (User) request.getSession().getAttribute("user");
                iotbay.models.PaymentMethod paymentMethod = new iotbay.models.PaymentMethod();
                paymentMethod.setStripePaymentMethodId(setupIntent.getPaymentMethod());
                paymentMethod.setUserId(user.getId());

                // retrieve the payment method from stripe
                PaymentMethod stripePaymentMethod = PaymentMethod.retrieve(setupIntent.getPaymentMethod());

                paymentMethod.setPaymentMethodType(stripePaymentMethod.getCard().getBrand());
                paymentMethod.setCardLast4(Integer.parseInt(stripePaymentMethod.getCard().getLast4()));
                user.addPaymentMethod(paymentMethod);

                logger.info("User " + user.getId() + " added a new payment method");
                iotbayLogger.info("User " + user.getId() + " added a new payment method");

                response.sendRedirect(request.getContextPath() + "/user");
            } catch (Exception e) {
                throw new ServletException(e);
            }
        } catch (Exception e) {
            throw new ServletException(e);
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
