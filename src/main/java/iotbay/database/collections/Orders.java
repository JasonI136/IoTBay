package iotbay.database.collections;

import iotbay.database.DatabaseManager;
import iotbay.database.collections.metrics.OrderMetrics;
import iotbay.enums.OrderStatus;
import iotbay.models.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of orders
 */
public class Orders implements ModelDAO<Order> {

    /**
     * An instance of the database manager
     */
    DatabaseManager db;

    OrderMetrics metrics;

    /**
     * Initializes the orders collection with the database manager
     * @param db
     */
    public Orders(DatabaseManager db) {
        this.db = db;
        this.metrics = new OrderMetrics(this);
    }

    /**
     * Adds an order to the database.
     * @param userId the id of the user
     * @param orderDate the date of the order
     * @param orderStatus the status of the order
     * @return an instance of the {@link iotbay.models.Order} object
     * @throws SQLException if there is an error adding the order
     */
    public Order addOrder(int userId, Timestamp orderDate, OrderStatus orderStatus) throws SQLException {
        Order order = new Order(this.db);
        order.setUserId(userId);
        order.setOrderDate(orderDate);
        order.setOrderStatus(orderStatus);

        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO CUSTOMER_ORDER (user_id, order_date, order_status) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {

                stmt.setInt(1, order.getUserId());
                stmt.setTimestamp(2, order.getOrderDate());
                stmt.setString(3, order.getOrderStatus().toString());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating order failed, no rows affected.");
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        order.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating order failed, no ID obtained.");
                    }
                }

            }
        }

        return order;
    }

    /**
     * Updates an order in the database.
     * @param id the id of the order
     * @param userId the id of the user
     * @param orderDate the date of the order
     * @param orderStatus the status of the order
     * @throws SQLException if there is an error updating the order
     */
    public void updateOrder(int id, int userId, Timestamp orderDate, String orderStatus) throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE CUSTOMER_ORDER SET user_id = ?, order_date = ?, order_status = ? WHERE id = ?")) {

                stmt.setInt(1, userId);
                stmt.setTimestamp(2, orderDate);
                stmt.setString(3, orderStatus);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Updating order failed, no rows affected.");
                }
            }
        }
    }

    /**
     * Gets an order from the database.
     * @param id the id of the order
     * @return an instance of the {@link iotbay.models.Order} object
     * @throws SQLException if there is an error getting the order
     */
    public Order getOrder(int id) throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM CUSTOMER_ORDER WHERE id = ?")) {

                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new Order(rs, this.db);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gets all orders from the database.
     * @return a list of {@link iotbay.models.Order} objects
     * @throws SQLException if there is an error getting the orders
     */
    public List<Order> getOrders() throws SQLException {
        List<Order> orderList = new ArrayList<>();

        String query;

        query = "SELECT * FROM CUSTOMER_ORDER";

        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Order order = new Order(rs, this.db);
                        orderList.add(order);
                    }
                }
            }
        }
        return orderList;
    }

    public List<Order> get(int offset, int limit) throws SQLException {
        List<Order> orderList = new ArrayList<>();

        String query;

        query = "SELECT * FROM CUSTOMER_ORDER OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, offset);
                stmt.setInt(2, limit);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Order order = new Order(rs, this.db);
                        orderList.add(order);
                    }
                }
            }
        }
        return orderList;
    }

    @Override
    public List<Order> get(int offset, int limit, String searchTerm) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Gets all orders from the database with a specific status.
     * @param status the status of the order
     * @return a list of {@link iotbay.models.Order} objects
     * @throws SQLException if there is an error getting the orders
     */
    public List<Order> getOrders(OrderStatus status) throws SQLException {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM CUSTOMER_ORDER WHERE order_status = ?")) {

                stmt.setString(1, status.toString());

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        orders.add(new Order(rs, this.db));
                    }
                }
            }
        }

        return orders;
    }

    /**
     * Gets all orders from the database for a specific user.
     * @param userId the id of the user
     * @return a list of {@link iotbay.models.Order} objects
     * @throws SQLException if there is an error getting the orders
     */
    public List<Order> getOrders(int userId) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();

        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CUSTOMER_ORDER WHERE user_id = ?")) {
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    orders.add(new Order(rs, this.db));
                }
            }
        }

        return orders;
    }

    /**
     * Deletes an order from the database.
     * @param id the id of the order
     * @throws SQLException if there is an error deleting the order
     */
    public void deleteOrder(int id) throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM CUSTOMER_ORDER WHERE id = ?")) {

                stmt.setInt(1, id);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Deleting order failed, no rows affected.");
                }
            }
        }
    }

    /**
     * Gets the number of orders in the database.
     * @return the number of orders
     * @throws SQLException if there is an error getting the number of orders
     */
    public int count() throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT COUNT(*) FROM CUSTOMER_ORDER")) {

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    } else {
                        return 0;
                    }
                }
            }
        }
    }

    @Override
    public int count(String searchTerm) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public DatabaseManager getDb() {
        return db;
    }

    public OrderMetrics getMetrics() {
        return metrics;
    }

}

	