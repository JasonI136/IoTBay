package iotbay.models.collections;

import iotbay.database.DatabaseManager;
import iotbay.models.entities.Invoice;

import java.sql.*;

public class Invoices {
    DatabaseManager db;

    public Invoices(DatabaseManager db) {
        this.db = db;
    }

    public Invoice addInvoice(int orderId, Timestamp invoiceDate, float amount) throws Exception {
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
                    throw new Exception("Creating invoice failed, no rows affected.");
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

    public Invoice getInvoice(int id) throws Exception {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM INVOICE WHERE id = ?")) {

                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new Invoice(rs);
                    } else {
                        return null;
                    }
                }
            }
        }
        
    }

    public void updateInvoice(int id, int orderId, Timestamp invoiceDate, float amount) throws Exception {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM INVOICE WHERE id = ?")) {

                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        throw new Exception("Updating invoice failed, no rows affected.");
                    }
                }
            }
        }
    }

    public void deleteInvoice(int id) throws Exception {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM INVOICE WHERE id = ?")) {

                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        throw new Exception("Deleting invoice failed, no rows affected.");
                    }
                }
            }
        }
    }

    public void deleteInvoiceByOrderId(int orderId) throws Exception {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM INVOICE WHERE order_id = ?")) {

                stmt.setInt(1, orderId);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new Exception("Deleting invoice failed, no rows affected.");
                }
            }
        }
    }


}
