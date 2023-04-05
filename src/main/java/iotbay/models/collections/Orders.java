package iotbay.models.collections;

import iotbay.database.DatabaseManager;
import iotbay.models.entities.Order;
import iotbay.models.enums.OrderStatus;

import java.io.Serializable;
import java.sql.*;

public class Orders {

    DatabaseManager db;

    private int id;

    private int userId;

    private Date orderDate;

    private String orderStatus;

    public Orders(DatabaseManager db) {
        this.db = db;
    }

    public Order addOrder(int userId, Timestamp orderDate, String orderStatus) throws Exception {
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderDate(orderDate);
        order.setOrderStatus(OrderStatus.valueOf(orderStatus));

        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO CUSTOMER_ORDER (user_id, order_date, order_status) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {

                stmt.setInt(1, order.getUserId());
                stmt.setTimestamp(2, order.getOrderDate());
                stmt.setString(3, order.getOrderStatus().toString());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new Exception("Creating order failed, no rows affected.");
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

    public void updateOrder(int id, int userId, Timestamp orderDate, String orderStatus) throws Exception {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE CUSTOMER_ORDER SET user_id = ?, order_date = ?, order_status = ? WHERE id = ?")) {

                stmt.setInt(1, userId);
                stmt.setTimestamp(2, orderDate);
                stmt.setString(3, orderStatus);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new Exception("Updating order failed, no rows affected.");
                }
            }
        }
    }

    public Order getOrder(int id) throws Exception {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM CUSTOMER_ORDER WHERE id = ?")) {

                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new Order(rs);
                    } else {
                        return null;
                    }
                }
            }
        }

    }

    public void deleteOrder(int id) throws Exception {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM CUSTOMER_ORDER WHERE id = ?")) {

                stmt.setInt(1, id);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new Exception("Deleting order failed, no rows affected.");
                }
            }
        }
    }

}