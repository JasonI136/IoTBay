package iotbay.servlets.admin_servlet.routes;

import iotbay.database.DatabaseManager;
import iotbay.models.httpResponses.GenericApiResponse;
import iotbay.servlets.interfaces.Route;
import iotbay.util.CustomHttpServletRequest;
import iotbay.util.CustomHttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class AdminUserMetricsRegPerMonthGetRoute implements Route {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                CustomHttpServletRequest req = (CustomHttpServletRequest) request;
        CustomHttpServletResponse resp = (CustomHttpServletResponse) response;

        DatabaseManager db = (DatabaseManager) req.getServletContext().getAttribute("db");

        try {
            resp.sendJsonResponse(GenericApiResponse.<Map<String, Integer>>builder()
                    .statusCode(200)
                    .message("OK")
                    .error(false)
                    .data(db.getUsers().getMetrics().getRegisteredUsersPerMonth())
                    .build()
            );
        } catch (SQLException e) {
            resp.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(500)
                    .message("Internal Server Error")
                    .error(true)
                    .data(e.getMessage())
                    .build()
            );
        }
    }
}
