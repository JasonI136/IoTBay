/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package iotbay.servlets;

import iotbay.database.DatabaseManager;
import iotbay.models.Category;
import iotbay.models.Product;
import iotbay.models.httpResponses.GenericApiResponse;
import iotbay.models.httpResponses.ProductResponse;
import iotbay.util.CustomHttpServletResponse;
import iotbay.util.PaginationHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

/**
 * @author cmesina
 */
public class ShopServlet extends HttpServlet {

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
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CustomHttpServletResponse res = new CustomHttpServletResponse(response);
        int limit;
        int page;

        String searchNameParam = request.getParameter("searchName");

        // If the search name is empty, redirect to the shop page.
        if (searchNameParam != null) {
            if (searchNameParam.trim().equals("")) {
                response.sendRedirect(request.getContextPath() + "/shop");
                return;
            }
        }

        try {
            limit = request.getParameter("limit") != null ? Integer.parseInt(request.getParameter("limit")) : 10;
        } catch (NumberFormatException e) {
            if (request.getContentType() == null) {
                request.getSession().setAttribute("message", "Invalid limit parameter.");
                response.sendError(400);
            } else if (request.getContentType().equals("application/json")) {
                res.sendJsonResponse(GenericApiResponse.<String>builder()
                        .statusCode(400)
                        .message("Bad request")
                        .error(true)
                        .data("Invalid limit parameter.")
                        .build());
            }
            return;
        }

        try {
            page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        } catch (NumberFormatException e) {
            if (request.getContentType() == null) {
                request.getSession().setAttribute("message", "Invalid page parameter.");
                response.sendError(400);
            } else if (request.getContentType().equals("application/json")) {
                res.sendJsonResponse(GenericApiResponse.<String>builder()
                        .statusCode(400)
                        .message("Bad request")
                        .error(true)
                        .data("Invalid page parameter.")
                        .build());
            }
            return;
        }

        PaginationHandler<Product> paginationHandler = new PaginationHandler<>(this.db.getProducts(), page, limit);

        try {
            if (searchNameParam != null) {
                paginationHandler.loadItems(searchNameParam);
            } else {
                paginationHandler.loadItems();
            }
        } catch (SQLException e) {
            if (request.getContentType() == null) {
                request.getSession().setAttribute("message", "Server Error");
                response.sendError(500);
            } else if (request.getContentType().equals("application/json")) {
                res.sendJsonResponse(GenericApiResponse.<String>builder()
                        .statusCode(500)
                        .message("Server Error")
                        .error(true)
                        .data(e.getMessage())
                        .build());
            }
            return;
        }


        List<Category> categories;
        try {
            categories = this.db.getCategories().getCategories();
        } catch (Exception e) {
            throw new ServletException("Failed to query database: " + e.getMessage());
        }

        if (request.getContentType() == null) {
            request.setAttribute("paginationHandler", paginationHandler);
            request.setAttribute("categories", categories);
            request.setAttribute("products", paginationHandler.getItems());
            request.getRequestDispatcher("/WEB-INF/jsp/products.jsp").forward(request, response);
        } else if (request.getContentType().equals("application/json")) {
            res.sendJsonResponse(GenericApiResponse.<PaginationHandler<Product>>builder()
                    .statusCode(200)
                    .message("OK")
                    .error(false)
                    .data(paginationHandler)
                    .build());
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
