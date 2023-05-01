package iotbay.servlets.admin_servlet.routes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import iotbay.database.DatabaseManager;
import iotbay.models.Product;
import iotbay.models.httpResponses.GenericApiResponse;
import iotbay.servlets.interfaces.Route;
import iotbay.util.CustomHttpServletRequest;
import iotbay.util.CustomHttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class AdminProductUpdateRoute implements Route {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomHttpServletRequest req = new CustomHttpServletRequest(request);
        CustomHttpServletResponse res = new CustomHttpServletResponse(response);
        DatabaseManager db = (DatabaseManager) request.getServletContext().getAttribute("db");

        JsonObject json = req.getJsonBody();

        String path = req.getPathInfo();
        String[] pathParts = path.split("/");
        if (pathParts.length != 3) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .message("Invalid product id.")
                    .data("Invalid product id or product id not provided.")
                    .error(true)
                    .build());
            return;
        }

        String id = pathParts[2];

        int productId;
        try {
            productId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .message("Invalid product id.")
                    .data("Invalid product id.")
                    .error(true)
                    .build());
            return;
        }

        Product product;
        try {
            product = db.getProducts().getProduct(productId);
        } catch (Exception e) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(500)
                    .message("Failed to retrieve product.")
                    .data(e.getMessage())
                    .error(true)
                    .build());
            return;
        }

        if (product == null) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(404)
                    .message("Product not found.")
                    .data("Product not found.")
                    .error(true)
                    .build());
            return;
        }

        JsonElement name = json.get("name");
        if (name != null) {
            product.setName(name.getAsString());
        }

        JsonElement price = json.get("price");
        if (price != null) {
            try {
                product.setPrice(price.getAsDouble());
            } catch (NumberFormatException e) {
                res.sendJsonResponse(GenericApiResponse.<String>builder()
                        .statusCode(400)
                        .message("Invalid price.")
                        .data("Invalid price.")
                        .error(true)
                        .build());
                return;
            }
        }

        JsonElement description = json.get("description");
        if (description != null) {
            product.setDescription(description.getAsString());
        }

        JsonElement stock = json.get("quantity");
        if (stock != null) {
            try {
                int stockParsed = stock.getAsInt();
                if (stockParsed < 0) {
                    res.sendJsonResponse(GenericApiResponse.<String>builder()
                            .statusCode(400)
                            .message("Invalid stock.")
                            .data("Invalid stock.")
                            .error(true)
                            .build());
                    return;
                }
                product.setQuantity(stockParsed);
            } catch (NumberFormatException e) {
                res.sendJsonResponse(GenericApiResponse.<String>builder()
                        .statusCode(400)
                        .message("Invalid stock.")
                        .data("Invalid stock.")
                        .error(true)
                        .build());
                return;
            }
        }

        JsonElement imageURL = json.get("imageURL");
        if (imageURL != null) {
            product.setImageURL(imageURL.getAsString());
        }

        JsonElement categoryId = json.get("categoryId");
        if (categoryId != null) {
            int categoryIdParsed;
            try {
                categoryIdParsed = categoryId.getAsInt();
            } catch (NumberFormatException e) {
                res.sendJsonResponse(GenericApiResponse.<String>builder()
                        .statusCode(400)
                        .message("Invalid category id.")
                        .data("Invalid category id.")
                        .error(true)
                        .build());
                return;
            }

            product.setCategoryId(categoryIdParsed);
        }

        try {
            db.getProducts().updateProduct(product);
        } catch (SQLException e) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(500)
                    .message("Failed to update product.")
                    .data(e.getMessage())
                    .error(true)
                    .build());
            return;
        }


        res.sendJsonResponse(GenericApiResponse.<String>builder()
                .statusCode(200)
                .message("Product updated.")
                .data("Product updated.")
                .build());
    }
}
