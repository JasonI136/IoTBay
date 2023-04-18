package iotbay.database.collections;

import iotbay.database.DatabaseManager;
import iotbay.models.Payment;

import java.sql.*;

/**
 * The payments collection
 */
public class Payments {

    /**
     * An instance of the database manager
     */
    DatabaseManager db;

    /**
     * Initializes the payments collection with the database manager
     * @param db
     */
    public Payments(DatabaseManager db) {
        this.db = db;
    }

    /**
     * Adds a payment to the database
     * @param invoiceId the invoice id
     * @param date the date
     * @param paymentMethodId the payment method id
     * @param amount the amount
     * @return a {@link iotbay.models.Payment} object
     * @throws SQLException if there is an error adding the payment
     */
    public Payment addPayment(int invoiceId, Timestamp date, int paymentMethodId, float amount) throws SQLException {
        Payment payment = new Payment();
        payment.setInvoiceId(invoiceId);
        payment.setDate(date);
        payment.setPaymentMethodId(paymentMethodId);
        payment.setAmount(amount);

        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO PAYMENT (invoice_id, date, payment_method_id, amount) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {

                stmt.setInt(1, payment.getInvoiceId());
                stmt.setTimestamp(2, payment.getDate());
                stmt.setInt(3, payment.getPaymentMethodId());
                stmt.setFloat(4, payment.getAmount());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating payment failed, no rows affected.");
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        payment.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating payment failed, no ID obtained.");
                    }
                }

            }
        }

        return payment;
    }

    /**
     * Gets a payment from the database
     * @param id the payment id
     * @return a {@link iotbay.models.Payment} object, or null if the payment does not exist.
     * @throws SQLException if there is an error getting the payment
     */
    public Payment getPayment(int id) throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM PAYMENT WHERE id = ?")) {

                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        new Payment(rs);
                    }
                }

            }
        }

        return null;
    }

    /**
     * Updates a payment in the database
     * @param id the payment id
     * @param invoiceId the invoice id
     * @param date the date
     * @param paymentMethodId the payment method id
     * @param amount the amount
     * @throws SQLException if there is an error updating the payment
     */
    public void updatePayment(int id, int invoiceId, Timestamp date, int paymentMethodId, float amount) throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE PAYMENT SET invoice_id = ?, date = ?, payment_method_id = ?, amount = ? WHERE id = ?")) {

                stmt.setInt(1, invoiceId);
                stmt.setTimestamp(2, date);
                stmt.setInt(3, paymentMethodId);
                stmt.setFloat(4, amount);
                stmt.setInt(5, id);
                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Updating payment failed, no rows affected.");
                }
            }
        }

    }

    /**
     * Deletes a payment from the database
     * @param id the payment id
     * @throws SQLException if there is an error deleting the payment
     */
    public void deletePayment(int id) throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM PAYMENT WHERE id = ?")) {

                stmt.setInt(1, id);
                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Deleting payment failed, no rows affected.");
                }
            }
        }
    }

}
