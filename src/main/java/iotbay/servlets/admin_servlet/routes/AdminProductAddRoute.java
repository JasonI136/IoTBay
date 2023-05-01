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

public class AdminProductAddRoute implements Route {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomHttpServletRequest req = new CustomHttpServletRequest(request);
        CustomHttpServletResponse res = new CustomHttpServletResponse(response);
        DatabaseManager db = (DatabaseManager) request.getServletContext().getAttribute("db");
        JsonObject json = req.getJsonBody();

        JsonElement name = json.get("name");
        JsonElement description = json.get("description");
        JsonElement price = json.get("price");
        JsonElement quantity = json.get("quantity");
        JsonElement categoryId = json.get("categoryId");
        JsonElement imageURL = json.get("imageURL");

        if (name == null || description == null || price == null || quantity == null || categoryId == null) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .message("Invalid product data.")
                    .data("Invalid product data or product data not provided.")
                    .error(true)
                    .build());
            return;
        }


        Product product = new Product();
        product.setName(name.getAsString());
        product.setDescription(description.getAsString());
        try {
            product.setPrice(price.getAsDouble());
            product.setQuantity(quantity.getAsInt());
            product.setCategoryId(categoryId.getAsInt());
        } catch (NumberFormatException e) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .message("Invalid product data.")
                    .data("Invalid product data or product data not provided.")
                    .error(true)
                    .build());
            return;
        }

        product.setImageURL((imageURL.getAsString() == null || imageURL.getAsString().isEmpty()) ? "https://placehold.co/600x400" : imageURL.getAsString());

        try {
            db.getProducts().addProduct(product);
        } catch (SQLException e) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(500)
                    .message("Internal server error.")
                    .data(e.getMessage())
                    .error(true)
                    .build());
            return;
        }

        res.sendJsonResponse(GenericApiResponse.<Product>builder()
                .statusCode(200)
                .message("Product added successfully.")
                .data(product)
                .error(false)
                .build());

    }
}
