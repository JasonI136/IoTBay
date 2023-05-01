package iotbay.servlets.admin_servlet.routes;

import iotbay.database.DatabaseManager;
import iotbay.models.User;
import iotbay.models.httpResponses.GenericApiResponse;
import iotbay.servlets.interfaces.Route;
import iotbay.util.CustomHttpServletResponse;
import iotbay.util.PaginationHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminUsersRoute implements Route {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomHttpServletResponse res = new CustomHttpServletResponse(response);
        int limit;
        int page;

        if (request.getContentType() == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/admin/admin-users.jsp").forward(request, response);
        } else if (request.getContentType().equals("application/json")) {
            DatabaseManager db = (DatabaseManager) request.getServletContext().getAttribute("db");

            String searchNameParam = request.getParameter("searchName");

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

            PaginationHandler<User> paginationHandler = new PaginationHandler<>(db.getUsers(), page, limit);

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

            res.sendJsonResponse(GenericApiResponse.<PaginationHandler<User>>builder()
                    .statusCode(200)
                    .message("OK")
                    .data(paginationHandler)
                    .build()
            );
        }
    }
}
