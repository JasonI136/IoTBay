/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package iotbay.servlets;

import iotbay.database.DatabaseManager;
import iotbay.models.Order;
import iotbay.models.OrderLineItem;
import iotbay.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


/**
 * @author jasonmba
 */
public class OrderTrackingServlet extends HttpServlet {

    DatabaseManager db;

    @Override
    public void init() throws ServletException {
        super.init();
        this.db = (DatabaseManager) getServletContext().getAttribute("db");
    }


    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet orderTrackingServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet orderTrackingServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

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
        request.getRequestDispatcher("/WEB-INF/jsp/order-tracking.jsp").forward(request, response);
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

        String orderIDString = request.getParameter("orderid");
        String orderLastName = request.getParameter("lastname");
        if (orderIDString.isEmpty()) {
            request.setAttribute("error", "Please enter an order ID.");
            request.getRequestDispatcher("/WEB-INF/jsp/order-tracking.jsp").forward(request, response);
            return;
        }

        int orderID = Integer.parseInt(orderIDString);

        try {
            Order order = this.db.getOrders().getOrder(orderID);
            if (order != null) {
                User orderUser = order.getUser();

                if (orderUser == null) {
                    request.setAttribute("error_title", "Error");
                    request.setAttribute("error_msg", "Order could not be found.");
                    request.getRequestDispatcher("/WEB-INF/jsp/order-tracking.jsp").forward(request, response);
                    return;
                }

                if (!orderUser.getLastName().equals(orderLastName)) {
                    request.setAttribute("error_title", "Error");
                    request.setAttribute("error_msg", "Order could not be found.");
                    request.getRequestDispatcher("/WEB-INF/jsp/order-tracking.jsp").forward(request, response);
                    return;
                }

                request.setAttribute("order", order);
                List<OrderLineItem> orderLineItemsList = this.db.getOrderLineItems().getOrderLineItems(orderID);
                request.setAttribute("orderLineItemsList", orderLineItemsList);
                request.getRequestDispatcher("/WEB-INF/jsp/order.jsp").forward(request, response);
            } else {
                request.setAttribute("error_title", "Error");
                request.setAttribute("error_msg", "Order could not be found.");
                request.getRequestDispatcher("/WEB-INF/jsp/order-tracking.jsp").forward(request, response);
            }

        } catch (Exception e) {
            throw new ServletException(e.getMessage());
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
