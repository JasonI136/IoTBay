package iotbay.servlets.admin_servlet.routes;

import iotbay.database.DatabaseManager;
import iotbay.models.Order;
import iotbay.models.httpResponses.GenericApiResponse;
import iotbay.servlets.interfaces.Route;
import iotbay.util.CustomHttpServletRequest;
import iotbay.util.CustomHttpServletResponse;
import iotbay.util.PaginationHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class AdminOrdersRoute implements Route {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomHttpServletResponse res = new CustomHttpServletResponse(response);
        CustomHttpServletRequest req = new CustomHttpServletRequest(request);

        if (req.getContentType() == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/new-admin/orders.jsp").forward(request, response);
        } else if (req.getContentType().equals("application/json")) {
            DatabaseManager db = (DatabaseManager) request.getServletContext().getAttribute("db");
            int limit = request.getParameter("limit") != null ? Integer.parseInt(request.getParameter("limit")) : 10;
            int pageParameter;

            try {
                pageParameter = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
            } catch (NumberFormatException e) {
                res.sendJsonResponse(GenericApiResponse.<String>builder()
                        .statusCode(400)
                        .message("Invalid page parameter")
                        .data(e.getMessage())
                        .error(true)
                        .build());
                return;
            }

            PaginationHandler<Order> paginationHandler = new PaginationHandler<>(db.getOrders(), pageParameter, limit);

            try {
                paginationHandler.loadItems();
            } catch (SQLException e) {
                res.sendJsonResponse(GenericApiResponse.<String>builder()
                        .statusCode(500)
                        .message("Internal server error")
                        .data(e.getMessage())
                        .error(true)
                        .build());
                return;
            }

            res.sendJsonResponse(GenericApiResponse.<PaginationHandler<Order>>builder()
                    .statusCode(200)
                    .message("Success")
                    .data(paginationHandler)
                    .error(false)
                    .build());
        }

    }
}
