package iotbay.models.entities;

import iotbay.database.DatabaseManager;
import iotbay.models.enums.OrderStatus;
import lombok.*;

import java.io.Serializable;
import java.sql.*;

/**
 * The order entity
 */
@Data
public class Order implements Serializable {

    private final transient DatabaseManager db;

    /**
     * The order id
     */
    private int id;

    /**
     * The user id
     */
    private int userId;

    /**
     * The order date
     */
    private Timestamp orderDate;

    /**
     * The order status
     */
    private OrderStatus orderStatus;

    /**
     * The stripe payment method id.
     */
    private String stripePaymentIntentId;

    public Order(DatabaseManager db) {
        this.db = db;
    }

    public Order(ResultSet rs, DatabaseManager db) throws Exception {
        this.id = rs.getInt("id");
        this.userId = rs.getInt("user_id");
        this.orderDate = rs.getTimestamp("order_date");
        this.orderStatus = OrderStatus.valueOf(rs.getString("order_status"));
        this.stripePaymentIntentId = rs.getString("stripe_payment_intent_id");
        this.db = db;
    }

    /**
     * Update the order in the database
     * @throws Exception
     */
    public void update() throws Exception {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE CUSTOMER_ORDER SET user_id = ?, order_date = ?, order_status = ?, stripe_payment_intent_id = ? WHERE id = ?")) {

                stmt.setInt(1, this.getUserId());
                stmt.setTimestamp(2, this.getOrderDate());
                stmt.setString(3, this.getOrderStatus().toString());
                stmt.setString(4, this.getStripePaymentIntentId());
                stmt.setInt(5, this.getId());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new Exception("Updating order failed, no rows affected.");
                }

            }
        }
    }
}
