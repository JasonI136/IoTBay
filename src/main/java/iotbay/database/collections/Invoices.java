package iotbay.database.collections;

import iotbay.database.DatabaseManager;
import iotbay.models.Invoice;

import java.sql.*;

/**
 * Represents a collection of invoices
 */
public class Invoices {

    /**
     * An instance of the database manager
     */
    DatabaseManager db;

    /**
     * Initializes the invoices collection with the database manager
     * @param db an instance of the database manager
     */
    public Invoices(DatabaseManager db) {
        this.db = db;
    }

    /**
     * Adds an invoice to the database
     * @param orderId the order id
     * @param invoiceDate the invoice date
     * @param amount the amount
     * @return the invoice
     * @throws SQLException if there is an error adding the invoice
     */
    public Invoice addInvoice(int orderId, Timestamp invoiceDate, float amount) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setOrderId(orderId);
        invoice.setInvoiceDate(invoiceDate);
        invoice.setAmount(amount);

        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO INVOICE (order_id, invoice_date, amount) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {

                stmt.setInt(1, invoice.getOrderId());
                stmt.setTimestamp(2, invoice.getInvoiceDate());
                stmt.setFloat(3, invoice.getAmount());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating order failed, no rows affected.");
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        invoice.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating order failed, no ID obtained.");
                    }
                }

            }
        }

        return invoice;
    }

    /**
     * Gets an invoice by id
     * @param id the invoice id
     * @return the invoice, or null if not found
     * @throws SQLException if there is an error getting the invoice
     */
    public Invoice getInvoice(int id) throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM INVOICE WHERE id = ?")) {

                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new Invoice(rs);
                    }
                }
            }
        }

        return null;

    }

    /**
     * Gets an invoice by order id
     * @param orderId the order id
     * @return the invoice, or null if not found
     * @throws SQLException if there is an error getting the invoice
     */
    public Invoice getInvoiceByOrderId(int orderId) throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM INVOICE WHERE order_id = ?")) {

                stmt.setInt(1, orderId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new Invoice(rs);
                    }
                }
            }
        }

        return null;

    }

    /**
     * Updates an invoice
     * @param id the invoice id
     * @param orderId the order id
     * @param invoiceDate the invoice date
     * @param amount the amount
     * @throws SQLException if there is an error updating the invoice
     */
    public void updateInvoice(int id, int orderId, Timestamp invoiceDate, float amount) throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM INVOICE WHERE id = ?")) {

                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("Updating invoice failed, no rows affected.");
                    }
                }
            }
        }
    }

    /**
     * Deletes an invoice
     * @param id the invoice id
     * @throws SQLException if there is an error deleting the invoice
     */
    public void deleteInvoice(int id) throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM INVOICE WHERE id = ?")) {

                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("Deleting invoice failed, no rows affected.");
                    }
                }
            }
        }
    }

    /**
     * Deletes an invoice by order id
     * @param orderId the order id
     * @throws SQLException if there is an error deleting the invoice
     */
    public void deleteInvoiceByOrderId(int orderId) throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM INVOICE WHERE order_id = ?")) {

                stmt.setInt(1, orderId);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    throw new SQLException("Deleting invoice failed, no rows affected.");
                }
            }
        }
    }


}
