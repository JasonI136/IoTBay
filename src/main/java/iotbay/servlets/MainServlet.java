/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package iotbay.servlets;

import iotbay.database.DatabaseManager;
import iotbay.models.Category;
import iotbay.models.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author cmesina
 */
public class MainServlet extends HttpServlet {

    DatabaseManager db;

    private static final Logger logger = LogManager.getLogger(MainServlet.class);

    @Override
    public void init() throws ServletException {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
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
        String path = request.getRequestURI().substring(request.getContextPath().length());
        if (path.startsWith("/public/")) {
            // Serve a local file
            String filePath = request.getServletContext().getRealPath(path);

            if (filePath == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            File file = new File(filePath);
            if (file.exists()) {
                response.setContentType(getServletContext().getMimeType(filePath));
                FileInputStream input = new FileInputStream(file);
                OutputStream output = response.getOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                input.close();
                output.flush();
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else if (path.isEmpty() || "/".equals(path)) {
            // Show index.jsp if no path is specified
            List<Category> categories;
            List<Product> products;
            try {
                products = this.db.getProducts().getProducts(100, 0, false);
                categories = this.db.getCategories().getCategories();
            } catch (Exception e) {
                throw new ServletException("Failed to query database: " + e.getMessage());
            }



            request.setAttribute("products", products);
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
        } else {
            // Send a 404 response for any other path
            request.setAttribute("message", String.format("The page '%s' was not found.", path));
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
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
