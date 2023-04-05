/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package iotbay.servlets;

import com.google.gson.Gson;
import iotbay.database.DatabaseManager;
import iotbay.models.entities.Product;
import iotbay.models.collections.Products;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author cmesina
 */
public class ProductServlet extends HttpServlet {

    DatabaseManager db;

    Products products;


    @Override
    public void init() throws ServletException {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody

        this.db = (DatabaseManager) getServletContext().getAttribute("db");
        this.products = (Products) getServletContext().getAttribute("products");
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
            out.println("<title>Servlet productModal</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet productModal at " + request.getContextPath() + "</h1>");
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

        String pathInfo = request.getPathInfo();

        if (pathInfo == null) {
            response.setStatus(400);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print("{\"error\": \"No product ID provided.\"}");
        } else {
            try {
                int productId = Integer.parseInt(pathInfo.substring(1));
                try {
                    Product product = this.products.getProduct(productId);

                    // Use Gson to serialize the product to JSON
                    Gson gson = new Gson();
                    String json = gson.toJson(product);

                    // Set the response content type to JSON
                    response.setContentType("application/json");

                    // Write the JSON to the response output stream
                    PrintWriter out = response.getWriter();
                    out.print(json);
                    out.flush();
                } catch (Exception e) {

                    throw new ServletException("Failed to get product: " + e.getMessage());
                }
            } catch (NumberFormatException e) {
                response.setStatus(400);
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.print("{\"error\": \"Invalid product ID\"}");
            }
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
        processRequest(request, response);
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