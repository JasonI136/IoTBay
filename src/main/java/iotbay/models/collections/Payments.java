package iotbay.models.collections;

import iotbay.database.DatabaseManager;
import iotbay.exceptions.UserExistsException;
import iotbay.models.entities.Payment;

import java.sql.*;

/**
 * The payments collection
 */
public class Payments {
    DatabaseManager db;

    public Payments(DatabaseManager db) {
        this.db = db;
    }

    public Payment addPayment(int invoiceId, Timestamp date, int paymentMethodId, float amount) throws Exception {
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
                    throw new Exception("Creating payment failed, no rows affected.");
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

    public Payment getPayment(int id) throws Exception {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM PAYMENT WHERE id = ?")) {

                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        new Payment(rs);
                    } else {
                        throw new Exception("Getting payment failed, no rows affected.");
                    }
                }

            }
        }

        return null;
    }

    public void updatePayment(int id, int invoiceId, Timestamp date, int paymentMethodId, float amount) throws Exception {
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
                    throw new Exception("Updating payment failed, no rows affected.");
                }
            }
        }

    }

    public void deletePayment(int id) throws Exception {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM PAYMENT WHERE id = ?")) {

                stmt.setInt(1, id);
                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new Exception("Deleting payment failed, no rows affected.");
                }
            }
        }
    }

}
