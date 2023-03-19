/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package iotbay.servlets;

import com.stripe.model.SetupIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import iotbay.database.DatabaseManager;
import iotbay.models.User;
import iotbay.models.Users;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 *
 * @author cmesina
 */
public class UserServlet extends HttpServlet {

    DatabaseManager db;

    Users users;

    @Override
    public void init() throws ServletException {
        super.init();
        this.db = (DatabaseManager) getServletContext().getAttribute("db");
        this.users = (Users) getServletContext().getAttribute("users");
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
            out.println("<title>Servlet UserServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserServlet at " + request.getContextPath() + "</h1>");
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
        String pathInfo = request.getPathInfo();

                //refresh the user
        try {
            request.getSession().setAttribute("user", this.users.getUser(((User) request.getSession().getAttribute("user")).getUsername()));
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }


        if (pathInfo != null) {
            if (pathInfo.startsWith("/addpaymentmethod/success")) {
            String sessionId = request.getParameter("session_id");
            try {
                Session session = Session.retrieve(sessionId);
                SetupIntent setupIntent = SetupIntent.retrieve(session.getSetupIntent());
                try {
                    User user = (User) request.getSession().getAttribute("user");
                    user.addPaymentMethod(setupIntent.getPaymentMethod());

                    // refresh user as payment methods have changed
                    request.getSession().setAttribute("user", this.users.getUser(((User) request.getSession().getAttribute("user")).getUsername()));

                    response.sendRedirect(request.getContextPath() + "/user");
                    return;
                } catch (Exception e) {
                    throw new ServletException(e);
                }
            } catch (Exception e) {
                throw new ServletException(e);
            }
        }
        }


        request.getRequestDispatcher("/WEB-INF/jsp/user.jsp").forward(request, response);
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
        String pathInfo = request.getPathInfo();

        if (pathInfo.startsWith("/addpaymentmethod")) {
            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                            .setMode(SessionCreateParams.Mode.SETUP)
                            .setCustomer(((User) request.getSession().getAttribute("user")).getStripeCustomerId())
                            .setSuccessUrl("http://localhost:8080/IoTBay/user/addpaymentmethod/success?session_id={CHECKOUT_SESSION_ID}")
                            .setCancelUrl("http://localhost:8080/IoTBay/user/addpaymentmethod/cancel")
                            .build();

            try{
                Session session = Session.create(params);
                response.sendRedirect(session.getUrl());
            } catch (Exception e) {
                throw new ServletException(e);
            }
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
