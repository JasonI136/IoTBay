package iotbay.models.collections;

import iotbay.database.DatabaseManager;
import iotbay.models.entities.OrderLineItem;

import java.sql.*;
import java.util.*;

public class OrderLineItems {

    DatabaseManager db;

    Orders orders;

    Products products;

    public OrderLineItems(DatabaseManager db, Orders orders, Products products) {
        this.db = db;
        this.orders = orders;
        this.products = products;
    }

    public OrderLineItem addOrderLineItem(int orderId, int productId, int quantity, double price) throws Exception {
        OrderLineItem orderLineItem = new OrderLineItem(this.db);
        orderLineItem.setOrder(orders.getOrder(orderId));
        orderLineItem.setProduct(products.getProduct(productId));
        orderLineItem.setQuantity(quantity);
        orderLineItem.setPrice(price);
        
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO ORDER_LINE_ITEM (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {

                stmt.setInt(1, orderLineItem.getOrder().getId());
                stmt.setInt(2, orderLineItem.getProduct().getId());
                stmt.setInt(3, orderLineItem.getQuantity());
                stmt.setDouble(4, orderLineItem.getPrice());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new Exception("Creating order line item failed, no rows affected.");
                }

            }
        }
        
        return orderLineItem;
    }

    public void getOrderLineItem(int orderId, int productId) throws Exception {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM ORDER_LINE_ITEM WHERE order_id = ? AND product_id = ?")) {

                stmt.setInt(1, orderId);
                stmt.setInt(2, productId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        new OrderLineItem(rs, this.db);
                    } else {
                        throw new SQLException("Getting order line item failed, no ID obtained.");
                    }
                }

            }
        }
    }

    public ArrayList<OrderLineItem> getOrderLineItems(int orderId) throws Exception {
    ArrayList<OrderLineItem> orderLineItems = new ArrayList<>();

    try (Connection conn = this.db.getDbConnection()) {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT ORDER_LINE_ITEM.ORDER_ID, ORDER_LINE_ITEM.PRODUCT_ID, ORDER_LINE_ITEM.QUANTITY, ORDER_LINE_ITEM.PRICE, P.NAME AS \"PRODUCT_NAME\", P.IMAGE_URL AS \"PRODUCT_IMAGE\", P.PRICE AS \"PRODUCT_PRICE\", CO.USER_ID, CO.ORDER_DATE, CO.ORDER_STATUS  FROM ORDER_LINE_ITEM\n" +
                        "INNER JOIN CUSTOMER_ORDER CO on ORDER_LINE_ITEM.ORDER_ID = CO.ID\n" +
                        "INNER JOIN PRODUCT P on ORDER_LINE_ITEM.PRODUCT_ID = P.ID\n" +
                        "WHERE ORDER_LINE_ITEM.ORDER_ID = ?")) {

            stmt.setInt(1, orderId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    try {
                        OrderLineItem orderLineItem = new OrderLineItem(rs, this.db);
                        orderLineItems.add(orderLineItem);
                    } catch (Exception e) {
                        throw new Exception("Getting order failed, no rows affected. Error message: " + e.getMessage() + "");
                    }
                }
            }

        }
    }

    return orderLineItems;
}

    public void updateOrderLineItem(int orderId, int productId, int quantity) throws Exception {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE ORDER_LINE_ITEM SET quantity = ? WHERE order_id = ? AND product_id = ?")) {

                stmt.setInt(1, quantity);
                stmt.setInt(2, orderId);
                stmt.setInt(3, productId);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new Exception("Updating order line item failed, no rows affected.");
                }
            }
        }

    }

    public void deleteOrderLineItem(int orderId, int productId) throws Exception {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM ORDER_LINE_ITEM WHERE order_id = ? AND product_id = ?")) {

                stmt.setInt(1, orderId);
                stmt.setInt(2, productId);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new Exception("Deleting order line item failed, no rows affected.");
                }
            }
        }
    }

    public void deleteOrderLineItems(int orderId) throws Exception {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM ORDER_LINE_ITEM WHERE order_id = ?")) {

                stmt.setInt(1, orderId);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new Exception("Deleting order line items failed, no rows affected.");
                }
            }
        }
    }
}
