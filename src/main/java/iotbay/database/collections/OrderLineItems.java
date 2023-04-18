package iotbay.database.collections;

import iotbay.database.DatabaseManager;
import iotbay.models.OrderLineItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of order line items
 */
public class OrderLineItems {

    /**
     * An instance of the database manager
     */
    DatabaseManager db;

    /**
     * An instance of the orders collection
     */
    Orders orders;

    /**
     * An instance of the products collection
     */
    Products products;

    /**
     * Initializes the order line items collection with the database manager, orders collection and products collection.
     * @param db an instance of the database manager
     * @param orders an instance of the orders collection
     * @param products an instance of the products collection
     */
    public OrderLineItems(DatabaseManager db, Orders orders, Products products) {
        this.db = db;
        this.orders = orders;
        this.products = products;
    }

    /**
     * Adds an order line item to the database.
     * @param orderId the id of the order
     * @param productId the id of the product
     * @param quantity the quantity of the product
     * @param price the price of the product
     * @return an instance of the {@link iotbay.models.OrderLineItem} object
     * @throws SQLException if there is an error adding the order line item
     */
    public OrderLineItem addOrderLineItem(int orderId, int productId, int quantity, double price) throws SQLException {
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
                    throw new SQLException("Creating order line item failed, no rows affected.");
                }

            }
        }

        return orderLineItem;
    }

    /**
     * Gets an order line item from the database.
     * @param orderId the id of the order
     * @param productId the id of the product
     * @return an instance of the {@link iotbay.models.OrderLineItem} object
     * @throws SQLException if there is an error getting the order line item
     */
    public OrderLineItem getOrderLineItem(int orderId, int productId) throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM ORDER_LINE_ITEM WHERE order_id = ? AND product_id = ?")) {

                stmt.setInt(1, orderId);
                stmt.setInt(2, productId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new OrderLineItem(rs, this.db);
                    }
                }

            }
        }

        return null;
    }

    /**
     * Gets all the order line items for an order.
     * @param orderId the id of the order
     * @return a list of {@link iotbay.models.OrderLineItem} objects
     * @throws SQLException if there is an error getting the order line items
     */
    public List<OrderLineItem> getOrderLineItems(int orderId) throws SQLException {
        List<OrderLineItem> orderLineItems = new ArrayList<>();

        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT ORDER_LINE_ITEM.ORDER_ID, ORDER_LINE_ITEM.PRODUCT_ID, ORDER_LINE_ITEM.QUANTITY, ORDER_LINE_ITEM.PRICE, P.NAME AS \"PRODUCT_NAME\", P.IMAGE_URL AS \"PRODUCT_IMAGE\", P.PRICE AS \"PRODUCT_PRICE\", CO.USER_ID, CO.ORDER_DATE, CO.ORDER_STATUS  FROM ORDER_LINE_ITEM\n" +
                            "INNER JOIN CUSTOMER_ORDER CO on ORDER_LINE_ITEM.ORDER_ID = CO.ID\n" +
                            "INNER JOIN PRODUCT P on ORDER_LINE_ITEM.PRODUCT_ID = P.ID\n" +
                            "WHERE ORDER_LINE_ITEM.ORDER_ID = ?")) {

                stmt.setInt(1, orderId);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        OrderLineItem orderLineItem = new OrderLineItem(rs, this.db);
                        orderLineItems.add(orderLineItem);
                    }
                }

            }
        }

        return orderLineItems;
    }

    /**
     * Updates an order line item in the database.
     * @param orderId the id of the order
     * @param productId the id of the product
     * @param quantity the quantity of the product
     * @throws SQLException if there is an error updating the order line item
     */
    public void updateOrderLineItem(int orderId, int productId, int quantity) throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE ORDER_LINE_ITEM SET quantity = ? WHERE order_id = ? AND product_id = ?")) {

                stmt.setInt(1, quantity);
                stmt.setInt(2, orderId);
                stmt.setInt(3, productId);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Updating order line item failed, no rows affected.");
                }
            }
        }

    }

    /**
     * Deletes an order line item from the database.
     * @param orderId the id of the order
     * @param productId the id of the product
     * @throws SQLException if there is an error deleting the order line item
     */
    public void deleteOrderLineItem(int orderId, int productId) throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM ORDER_LINE_ITEM WHERE order_id = ? AND product_id = ?")) {

                stmt.setInt(1, orderId);
                stmt.setInt(2, productId);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Deleting order line item failed, no rows affected.");
                }
            }
        }
    }

    /**
     * Deletes all the order line items for an order.
     * @param orderId the id of the order
     * @throws SQLException if there is an error deleting the order line items
     */
    public void deleteOrderLineItems(int orderId) throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM ORDER_LINE_ITEM WHERE order_id = ?")) {

                stmt.setInt(1, orderId);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Deleting order line items failed, no rows affected.");
                }
            }
        }
    }
}
