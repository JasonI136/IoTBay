package iotbay.models;

import iotbay.database.DatabaseManager;
import iotbay.enums.OrderStatus;
import lombok.Data;

import java.io.Serializable;
import java.sql.*;
import java.util.List;

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

    /**
     * The constructor for the Order class.
     * @param db The database manager.
     */
    public Order(DatabaseManager db) {
        this.db = db;
    }

    /**
     * The constructor for the Order class.
     * @param rs The result set.
     * @param db The database manager.
     * @throws SQLException If there is an error with the SQL query.
     */
    public Order(ResultSet rs, DatabaseManager db) throws SQLException {
        this.id = rs.getInt("id");
        this.userId = rs.getInt("user_id");
        this.orderDate = rs.getTimestamp("order_date");
        this.orderStatus = OrderStatus.valueOf(rs.getString("order_status"));
        this.stripePaymentIntentId = rs.getString("stripe_payment_intent_id");
        this.db = db;
    }

    /**
     * Update the order in the database
     * @throws SQLException If there is an error with the SQL query.
     */
    public void update() throws SQLException {
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
                    throw new SQLException("Updating order failed, no rows affected.");
                }

            }
        }
    }

    public void delete() throws SQLException {
        Invoice invoice = this.db.getInvoices().getInvoiceByOrderId(this.getId());
        if (invoice != null) {
            this.db.getInvoices().deleteInvoice(invoice.getId());
        }
        this.db.getOrderLineItems().deleteOrderLineItems(this.getId());
        this.db.getOrders().deleteOrder(this.getId());

    }

    public Invoice getInvoice() throws SQLException {
        return this.db.getInvoices().getInvoiceByOrderId(this.getId());
    }
}
