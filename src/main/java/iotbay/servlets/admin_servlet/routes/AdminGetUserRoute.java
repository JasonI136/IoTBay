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

public class AdminGetUserRoute implements Route {
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


        User user;
        try {
            user = db.getUsers().getUser(id);
        } catch (SQLException e) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(500)
                    .message("Internal server error.")
                    .data(e.getMessage())
                    .error(true)
                    .build());
            return;
        }

        if (user == null) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(404)
                    .message("User not found.")
                    .data("User not found.")
                    .error(true)
                    .build());
            return;
        }

        res.sendJsonResponse(GenericApiResponse.<User>builder()
                .statusCode(200)
                .message("OK")
                .data(user)
                .error(false)
                .build());
    }
}
