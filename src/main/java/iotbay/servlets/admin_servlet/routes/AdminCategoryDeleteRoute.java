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

public class AdminCategoryDeleteRoute implements Route {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomHttpServletRequest req = new CustomHttpServletRequest(request);
        CustomHttpServletResponse res = new CustomHttpServletResponse(response);
        DatabaseManager db = (DatabaseManager) request.getServletContext().getAttribute("db");

        String path = req.getPathInfo();
        String[] pathParts = path.split("/");
        // path is in the format of /category/{id}
        if (pathParts.length != 3) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .message("Invalid category id.")
                    .data("Invalid category id or category id not provided.")
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
                    .message("Invalid category id.")
                    .data("Invalid category id or category id not provided.")
                    .error(true)
                    .build());
            return;
        }


        try {
            db.getCategories().deleteCategory(id);
        } catch (SQLException e) {
            if (e.getMessage().contains("violation of foreign key constraint")) {
                res.sendJsonResponse(GenericApiResponse.<String>builder()
                        .statusCode(400)
                        .message("Error")
                        .data("Category is in use. Please delete or unassign all products in this category before deleting this category.")
                        .error(true)
                        .build());
                return;
            }

            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(500)
                    .message("Internal server error.")
                    .data(e.getMessage())
                    .error(true)
                    .build());
            return;
        }

        res.sendJsonResponse(GenericApiResponse.<String>builder()
                .statusCode(200)
                .message("Category deleted successfully.")
                .data("Category deleted successfully.")
                .error(false)
                .build());
    }
}
