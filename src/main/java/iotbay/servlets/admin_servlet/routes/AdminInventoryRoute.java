package iotbay.servlets.admin_servlet.routes;

import iotbay.database.DatabaseManager;
import iotbay.models.Category;
import iotbay.models.Product;
import iotbay.servlets.interfaces.Route;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminInventoryRoute implements Route {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DatabaseManager db = (DatabaseManager) request.getServletContext().getAttribute("db");

        List<Product> products;
        try {
            products = db.getProducts().getProducts(100, 0, false);
        } catch (SQLException e) {
            throw new ServletException("Failed to query database: " + e.getMessage());
        }

        List<Category> categories;
        try {
            categories = db.getCategories().getCategories();
        } catch (Exception e) {
            throw new ServletException("Failed to query database: " + e.getMessage());
        }

        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/jsp/new-admin/inventory.jsp").forward(request, response);
    }

}
