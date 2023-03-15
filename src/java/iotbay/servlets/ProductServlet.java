/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package iotbay.servlets;

import iotbay.database.DatabaseManager;
import iotbay.exceptions.ProductNotFoundException;
import iotbay.models.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cmesina
 */
public class ProductServlet extends HttpServlet {
    
    DatabaseManager db;

    @Override
    public void init() throws ServletException {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        
        this.db = (DatabaseManager) getServletContext().getAttribute("db");
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
            out.println("<title>Servlet ProductServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductServlet at " + request.getContextPath() + "</h1>");
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
        
        this.initShoppingCart(request);
        
        List<Product> products;
        try {
            products = db.getProducts(10, 0);
        } catch (SQLException e) {
            throw new ServletException("Failed to query database: " + e.getMessage());
        }
        
        
        request.setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/jsp/products.jsp").forward(request, response);
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
        
        if (pathInfo != null && pathInfo.endsWith("/addtocart")) {
            
            
            if (request.getParameter("productId") != null) {
                try {
                    this.initShoppingCart(request);
                    List<Product> userShoppingCart = (ArrayList<Product>) request.getSession().getAttribute("shoppingCart");
                     userShoppingCart.add(this.db.getProduct(Integer.parseInt(request.getParameter("productId"))));
                } catch (Exception e) {
                    throw new ServletException(e.getMessage());
                }
            }
            response.sendRedirect(request.getContextPath() + "/shop");
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
    
    private void initShoppingCart(HttpServletRequest request) {
        List<Product> userShoppingCart = (ArrayList<Product>) request.getSession().getAttribute("shoppingCart");
        if (userShoppingCart == null) {
            userShoppingCart = new ArrayList<Product>();
            request.getSession().setAttribute("shoppingCart", userShoppingCart);
        }
    }

}
