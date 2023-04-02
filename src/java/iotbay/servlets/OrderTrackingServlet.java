/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package iotbay.servlets;
import iotbay.database.DatabaseManager;
import iotbay.models.collections.Orders;
import iotbay.models.entities.OrderLineItem;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import iotbay.models.collections.OrderLineItems;
import iotbay.models.entities.Order;
import iotbay.models.entities.Product;
import iotbay.models.collections.Products;
import java.util.*;


/**
 *
 * @author jasonmba
 */
public class OrderTrackingServlet extends HttpServlet {

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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/order-tracking.jsp").forward(request, response);
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

        Orders orders = new Orders((DatabaseManager) getServletContext().getAttribute("db"));
        OrderLineItems orderlineitems = new OrderLineItems((DatabaseManager) getServletContext().getAttribute("db"));
        Products products = new Products((DatabaseManager) getServletContext().getAttribute("db"));
        LinkedList<OrderLineItem> orderlineitemslist = new LinkedList<OrderLineItem>();
        LinkedList<Product> productlist = new LinkedList<Product>();
        //String lastname = request.getParameter("lastname");
        
        String orderIDString = request.getParameter("orderid");
        int orderID = Integer.parseInt(orderIDString);
        
        try {
            Order order = orders.getOrder(orderID);
            if (order != null) {
                request.setAttribute("order_status", order.getOrderStatus().toString());
                request.setAttribute("order_date", order.getOrderDate().toString());
                request.setAttribute("user_id", order.getUserId());
                orderlineitemslist = orderlineitems.getOrderLineItems(orderID);
                for (OrderLineItem lineitem : orderlineitemslist) {
                    int productID = lineitem.getProductId();
                    productlist.add(products.getProduct(productID));
                }
                request.setAttribute("product_list", productlist);
                request.setAttribute("order_id", orderIDString);
                request.getRequestDispatcher("/WEB-INF/jsp/order.jsp").forward(request, response);
            } else {
                request.setAttribute("found_id", "No ID found");
                request.getRequestDispatcher("/WEB-INF/jsp/order-tracking.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
