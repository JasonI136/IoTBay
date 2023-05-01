package iotbay.servlets.admin_servlet.routes;

import iotbay.database.DatabaseManager;
import iotbay.servlets.interfaces.Route;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AdminIndexRoute implements Route {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DatabaseManager db = (DatabaseManager) request.getServletContext().getAttribute("db");

        int orderCount = 0;
        int userCount = 0;
        int productCount = 0;
        try {
            orderCount = db.getOrders().count();
            userCount = db.getUsers().count();
            productCount = db.getProducts().count();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("orderCount", orderCount);
        request.setAttribute("userCount", userCount);
        request.setAttribute("productCount", productCount);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/admin-index.jsp").forward(request, response);
    }

}
