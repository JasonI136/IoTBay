package iotbay.servlets.admin_servlet.routes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import iotbay.database.DatabaseManager;
import iotbay.enums.OrderStatus;
import iotbay.models.Order;
import iotbay.models.httpResponses.GenericApiResponse;
import iotbay.servlets.interfaces.Route;
import iotbay.util.CustomHttpServletRequest;
import iotbay.util.CustomHttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class AdminOrderUpdateRoute implements Route {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomHttpServletResponse res = new CustomHttpServletResponse(response);
        CustomHttpServletRequest req = new CustomHttpServletRequest(request);
        DatabaseManager db = (DatabaseManager) request.getServletContext().getAttribute("db");

        JsonObject json = req.getJsonBody();

        //get the product id from the url. The url is in the format of /product/delete/{id}
        String path = req.getPathInfo();
        String[] pathParts = path.split("/");
        if (pathParts.length != 3) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .message("Invalid order id.")
                    .data("Invalid order id or order id not provided.")
                    .error(true)
                    .build());
            return;
        }

        int orderId;

        try {
            orderId = Integer.parseInt(pathParts[2]);
        } catch (NumberFormatException e) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .message("Invalid order id.")
                    .data("Invalid order id or order id not provided.")
                    .error(true)
                    .build());
            return;
        }

        Order order;
        try {
            order = db.getOrders().getOrder(orderId);
        } catch (SQLException e) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(500)
                    .message("Internal server error.")
                    .data(e.getMessage())
                    .error(true)
                    .build());
            return;
        }

        if (order == null) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(404)
                    .message("Order not found.")
                    .data("Order not found.")
                    .error(true)
                    .build());
            return;
        }

        JsonElement orderStatusElement = json.get("orderStatus");
        if (orderStatusElement != null) {
            order.setOrderStatus(OrderStatus.valueOf(orderStatusElement.getAsString()));
        }

        try {
            order.update();
        } catch (SQLException e) {
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
                .message("Order updated successfully.")
                .data("Order updated successfully.")
                .error(false)
                .build());
    }
}
