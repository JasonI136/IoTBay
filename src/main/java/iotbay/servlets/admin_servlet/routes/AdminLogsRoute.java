package iotbay.servlets.admin_servlet.routes;

import iotbay.database.DatabaseManager;
import iotbay.models.Log;
import iotbay.servlets.interfaces.Route;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class AdminLogsRoute implements Route {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DatabaseManager db = (DatabaseManager) request.getServletContext().getAttribute("db");
        List<Log> logs;
        try {
            logs = db.getLogs().getLogs(100, 0, true);
        } catch (Exception e) {
            throw new ServletException("Failed to query database: " + e.getMessage());
        }

        request.setAttribute("logs", logs);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/admin-logs.jsp").forward(request, response);
    }
}
