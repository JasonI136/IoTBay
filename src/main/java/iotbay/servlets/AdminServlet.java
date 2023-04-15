package iotbay.servlets;

import iotbay.database.DatabaseManager;
import iotbay.models.entities.Category;
import iotbay.models.entities.Order;
import iotbay.models.entities.Product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminServlet extends HttpServlet {
    DatabaseManager db;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo() == null ? "/" : request.getPathInfo();

        switch (path) {
            case "/":
                adminIndex(request, response);
                return;
            case "/users":
                adminUsers(request, response);
                return;
            case "/inventory":
                adminInventory(request, response);
                return;
            case "/orders":
                adminOrders(request, response);
                return;
            default:
                request.getSession().setAttribute("message", "Page not found");
                response.sendError(404);
                return;
        }
    }

    private void adminIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderCount = 0;
        int userCount = 0;
        int productCount = 0;
        try {
            orderCount = db.getOrders().getOrderCount();
            userCount = db.getUsers().getUserCount();
            productCount = db.getProducts().getProductCount();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("orderCount", orderCount);
        request.setAttribute("userCount", userCount);
        request.setAttribute("productCount", productCount);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/admin-index.jsp").forward(request, response);
    }

    private void adminUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/admin/admin-users.jsp").forward(request, response);
    }

    private void adminInventory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products;
        try {
            products = this.db.getProducts().getProducts(100, 0, false);
        } catch (SQLException e) {
            throw new ServletException("Failed to query database: " + e.getMessage());
        }

        List<Category> categories;
        try {
            categories = this.db.getCategories().getCategories();
        } catch (Exception e) {
            throw new ServletException("Failed to query database: " + e.getMessage());
        }

        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/admin-inventory.jsp").forward(request, response);
    }

    private void adminOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Order> orders;
        try {
            orders = this.db.getOrders().getOrders();
        } catch (Exception e) {
            throw new ServletException("Failed to query database: " + e.getMessage());
        }

        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/admin-orders.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void init() throws ServletException {
        super.init();
        this.db = (DatabaseManager) getServletContext().getAttribute("db");
    }
}
