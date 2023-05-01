package iotbay.servlets.admin_servlet.routes;

import iotbay.database.DatabaseManager;
import iotbay.exceptions.ProductInOrderException;
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

public class AdminProductDeleteRoute implements Route {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomHttpServletRequest req = new CustomHttpServletRequest(request);
        CustomHttpServletResponse res = new CustomHttpServletResponse(response);
        DatabaseManager db = (DatabaseManager) request.getServletContext().getAttribute("db");

        //get the product id from the url. The url is in the format of /product/delete/{id}
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

        try {
            db.getProducts().deleteProduct(product);
        } catch (SQLException e) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(500)
                    .message("Failed to delete product.")
                    .data(e.getMessage())
                    .error(true)
                    .build());
            return;
        } catch (ProductInOrderException e) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .message("Error")
                    .data(e.getMessage())
                    .error(true)
                    .build());
            return;
        }

        res.sendJsonResponse(GenericApiResponse.<String>builder()
                .statusCode(200)
                .message("Product deleted.")
                .data("Product deleted.")
                .build());
    }
}
