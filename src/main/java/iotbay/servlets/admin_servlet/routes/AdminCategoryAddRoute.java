package iotbay.servlets.admin_servlet.routes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import iotbay.database.DatabaseManager;
import iotbay.models.Category;
import iotbay.models.httpResponses.GenericApiResponse;
import iotbay.servlets.interfaces.Route;
import iotbay.util.CustomHttpServletRequest;
import iotbay.util.CustomHttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class AdminCategoryAddRoute implements Route {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomHttpServletRequest req = new CustomHttpServletRequest(request);
        CustomHttpServletResponse res = new CustomHttpServletResponse(response);
        DatabaseManager db = (DatabaseManager) request.getServletContext().getAttribute("db");
        JsonObject json = req.getJsonBody();

        JsonElement name = json.get("name");

        if (name == null) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .message("Invalid category name.")
                    .data("Invalid category name or category name not provided.")
                    .error(true)
                    .build());
            return;
        }

        // check if category name already exists
        try {
            if (db.getCategories().getCategory(name.getAsString()) != null) {
                res.sendJsonResponse(GenericApiResponse.<String>builder()
                        .statusCode(400)
                        .message("Category name already exists.")
                        .data("Category name already exists.")
                        .error(true)
                        .build());
                return;
            }
        } catch (SQLException e) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(500)
                    .message("Internal server error.")
                    .data(e.getMessage())
                    .error(true)
                    .build());
            return;
        }

        Category category = new Category();
        category.setCategoryName(name.getAsString());

        try {
            db.getCategories().addCategory(category);
        } catch (SQLException e) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(500)
                    .message("Internal server error.")
                    .data(e.getMessage())
                    .error(true)
                    .build());
        }

        res.sendJsonResponse(GenericApiResponse.<String>builder()
                .statusCode(200)
                .message("Category added successfully.")
                .data("Category added successfully.")
                .error(false)
                .build());
    }
}
