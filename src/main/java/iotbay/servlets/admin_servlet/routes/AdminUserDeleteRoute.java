package iotbay.servlets.admin_servlet.routes;

import iotbay.database.DatabaseManager;
import iotbay.models.User;
import iotbay.models.httpResponses.GenericApiResponse;
import iotbay.servlets.interfaces.Route;
import iotbay.util.CustomHttpServletRequest;
import iotbay.util.CustomHttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class AdminUserDeleteRoute implements Route {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomHttpServletRequest req = (CustomHttpServletRequest) request;
        CustomHttpServletResponse res = (CustomHttpServletResponse) response;
        DatabaseManager db = (DatabaseManager) request.getServletContext().getAttribute("db");

        String path = req.getPathInfo();
        String[] pathParts = path.split("/");
        // path is in the format of /user/{id}
        if (pathParts.length != 3) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .message("Invalid user id.")
                    .data("Invalid user id or user id not provided.")
                    .error(true)
                    .build());
            return;
        }

        int id;
        try {
            id = Integer.parseInt(pathParts[2]);
        } catch (NumberFormatException e) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .message("Invalid user id.")
                    .data("Invalid user id or user id not provided.")
                    .error(true)
                    .build());
            return;
        }

        User currentlyLoggedInUser = (User) req.getSession().getAttribute("user");
        if (currentlyLoggedInUser.getId() == id) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .message("Invalid user id.")
                    .data("You cannot delete yourself.")
                    .error(true)
                    .build());
            return;
        }

        try {
            db.getUsers().deleteUser(id);
        } catch (SQLException e) {
            if (e.getMessage().contains("violation of foreign key constraint")) {
                res.sendJsonResponse(GenericApiResponse.<String>builder()
                        .statusCode(400)
                        .message("Error")
                        .data("User is in use.")
                        .error(true)
                        .build());
                return;
            } else {
                res.sendJsonResponse(GenericApiResponse.<String>builder()
                        .statusCode(500)
                        .message("Error")
                        .data(e.getMessage())
                        .error(true)
                        .build());
                return;
            }
        }

        res.sendJsonResponse(GenericApiResponse.<String>builder()
                .statusCode(200)
                .message("Success")
                .data("User deleted successfully.")
                .error(false)
                .build());
    }
}
