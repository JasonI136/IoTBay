package iotbay.servlets;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import iotbay.database.DatabaseManager;
import iotbay.enums.OrderStatus;
import iotbay.exceptions.ProductInOrderException;
import iotbay.models.*;
import iotbay.models.httpResponses.GenericApiResponse;
import iotbay.util.CustomHttpServletRequest;
import iotbay.util.CustomHttpServletResponse;
import iotbay.util.PaginationHandler;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class AdminServlet extends HttpServlet {

    DatabaseManager db;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo() == null ? "/" : request.getPathInfo();

        switch (path) {
            case "/":
                adminIndex(request, response);
                return;
            case "/users":
                adminUsers(request, response);
                return;
            case "/users/get":
                adminUsersGet(request, response);
                return;
            case "/inventory/add":
                adminInventoryAdd(request, response);
                return;
            case "/inventory":
                adminInventory(request, response);
                return;
            case "/orders":
                adminOrders(request, response);
                return;
            case "/orders/get":
                adminOrdersGet(request, response);
                return;
            case "/logs":
                adminLogs(request, response);
                return;
            default:
                request.getSession().setAttribute("message", "Page not found");
                response.sendError(404);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo() == null ? "/" : request.getPathInfo();

        if (path.startsWith("/product/update/") || path.startsWith("/product/update")) {
            adminProductsUpdate(request, response);
            return;
        } else if (path.startsWith("/orders/update") || path.startsWith("/orders/update/")) {
            adminOrdersUpdate(request, response);
            return;
        } else if (path.startsWith("/categories/add")) {
            adminCategoriesAdd(request, response);
            return;
        } else if (path.startsWith("/products/add")) {
            adminProductAdd(request, response);
            return;
        } else {
            request.getSession().setAttribute("message", "Page not found");
            response.sendError(404);
            return;
        }

    }

    private void adminProductAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CustomHttpServletRequest req = new CustomHttpServletRequest(request);
        CustomHttpServletResponse res = new CustomHttpServletResponse(response);
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
        product.setPrice(price.getAsDouble());
        product.setQuantity(quantity.getAsInt());
        product.setCategoryId(categoryId.getAsInt());
        product.setImageURL(imageURL.getAsString() == null ? "https://placehold.co/600x400" : imageURL.getAsString());

        try {
            this.db.getProducts().addProduct(product);
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
                .message("Product added successfully.")
                .data("Product added successfully.")
                .error(false)
                .build());

    }

    private void adminCategoriesAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CustomHttpServletRequest req = new CustomHttpServletRequest(request);
        CustomHttpServletResponse res = new CustomHttpServletResponse(response);
        JsonObject json  = req.getJsonBody();

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
            if (this.db.getCategories().getCategory(name.getAsString()) != null) {
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
            this.db.getCategories().addCategory(category);
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

    private void adminOrdersUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CustomHttpServletResponse res = new CustomHttpServletResponse(response);
        CustomHttpServletRequest req = new CustomHttpServletRequest(request);

        JsonObject json = req.getJsonBody();

        //get the product id from the url. The url is in the format of /product/delete/{id}
        String path = req.getPathInfo();
        String[] pathParts = path.split("/");
        if (pathParts.length != 4) {
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
            orderId = Integer.parseInt(pathParts[3]);
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
            order = this.db.getOrders().getOrder(orderId);
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

    private void adminOrdersGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CustomHttpServletResponse res = new CustomHttpServletResponse(response);
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

        PaginationHandler<Order> paginationHandler = new PaginationHandler<>(this.db.getOrders(), pageParameter, limit);

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

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo() == null ? "/" : request.getPathInfo();

        if (path.startsWith("/product/delete/") || path.startsWith("/product/delete")) {
            adminProductsDelete(request, response);
            return;
        } else if (path.startsWith("/categories") || path.startsWith("/categories/")) {
            adminCategoriesDelete(request, response);
            return;
        } else {
            request.getSession().setAttribute("message", "Page not found");
            response.sendError(404);
        }
    }

    private void adminCategoriesDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CustomHttpServletRequest req = new CustomHttpServletRequest(request);
        CustomHttpServletResponse res = new CustomHttpServletResponse(response);

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
            this.db.getCategories().deleteCategory(id);
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

    private void adminProductsDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CustomHttpServletRequest req = new CustomHttpServletRequest(request);
        CustomHttpServletResponse res = new CustomHttpServletResponse(response);


        //get the product id from the url. The url is in the format of /product/delete/{id}
        String path = req.getPathInfo();
        String[] pathParts = path.split("/");
        if (pathParts.length != 4) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .message("Invalid product id.")
                    .data("Invalid product id or product id not provided.")
                    .error(true)
                    .build());
            return;
        }

        String id = pathParts[3];

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
            product = this.db.getProducts().getProduct(productId);
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
            this.db.getProducts().deleteProduct(product);
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

    private void adminProductsUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CustomHttpServletRequest req = new CustomHttpServletRequest(request);
        CustomHttpServletResponse res = new CustomHttpServletResponse(response);
        JsonObject json = req.getJsonBody();

        String path = req.getPathInfo();
        String[] pathParts = path.split("/");
        if (pathParts.length != 4) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .message("Invalid product id.")
                    .data("Invalid product id or product id not provided.")
                    .error(true)
                    .build());
            return;
        }

        String id = pathParts[3];

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
            product = this.db.getProducts().getProduct(productId);
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
            this.db.getProducts().updateProduct(product);
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

    private void adminUsersGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomHttpServletResponse res = new CustomHttpServletResponse(response);

        List<User> users;
        try {
            users = this.db.getUsers().getUsers(100, 0);
        } catch (SQLException e) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(500)
                    .message("Failed to retrieve users.")
                    .data(e.getMessage())
                    .error(true)
                    .build()
            );
            return;
        }

        res.sendJsonResponse(GenericApiResponse.<List<User>>builder()
                .statusCode(200)
                .message("OK")
                .data(users)
                .build()
        );

    }

    private void adminLogs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Log> logs;
        try {
            logs = this.db.getLogs().getLogs(100, 0, true);
        } catch (Exception e) {
            throw new ServletException("Failed to query database: " + e.getMessage());
        }

        request.setAttribute("logs", logs);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/admin-logs.jsp").forward(request, response);
    }

    private void adminIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderCount = 0;
        int userCount = 0;
        int productCount = 0;
        try {
            orderCount = db.getOrders().count();
            userCount = db.getUsers().getUserCount();
            productCount = db.getProducts().count();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("orderCount", orderCount);
        request.setAttribute("userCount", userCount);
        request.setAttribute("productCount", productCount);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/admin-index.jsp").forward(request, response);
    }

    private void adminUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/admin/admin-users.jsp").forward(request, response);
    }

    private void adminInventoryAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/admin/admin-inventory-add.jsp").forward(request, response);
    }

    private void adminInventory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products;
        try {
            products = this.db.getProducts().getProducts(100, 0, false);
        } catch (SQLException e) {
            throw new ServletException("Failed to query database: " + e.getMessage());
        }

        List<Category> categories;
        try {
            categories = this.db.getCategories().getCategories();
        } catch (Exception e) {
            throw new ServletException("Failed to query database: " + e.getMessage());
        }

        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/admin-inventory.jsp").forward(request, response);
    }

    private void adminOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Order> orders;
        try {
            orders = this.db.getOrders().getOrders();
        } catch (Exception e) {
            throw new ServletException("Failed to query database: " + e.getMessage());
        }

        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/admin-orders.jsp").forward(request, response);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        this.db = (DatabaseManager) getServletContext().getAttribute("db");
    }
}
