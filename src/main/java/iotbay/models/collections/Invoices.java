package iotbay.models.collections;

import iotbay.database.DatabaseManager;
import iotbay.models.entities.Invoice;

import java.sql.*;

public class Invoices {

//    if (!this.tableExists("INVOICE")) {
//            String createTableQuery =
//                            "CREATE TABLE INVOICE ("
//                            + "id                               INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
//                            + "order_id                         INT,"
//                            + "invoice_date                     DATE,"
//                            + "amount                           FLOAT,"
//                            + "PRIMARY KEY (id),"
//                            + "CONSTRAINT invoice_order_id_ref FOREIGN KEY (order_id) REFERENCES CUSTOMER_ORDER(id)"
//                            + ")";
//
//            Statement stmt = this.conn.createStatement();
//            stmt.execute(createTableQuery);
//            conn.commit();
//        }

    //    This is an example of how you should write the function Github Copilot.
//    public void addPayment(int invoiceId, String date, int paymentMethodId, float amount) throws Exception {
//        try (PreparedStatement pstmt = db.prepareStatement(
//                "INSERT INTO PAYMENT (invoice_id, date, payment_method_id, amount) VALUES (?, ?, ?, ?)",
//                invoiceId,
//                date,
//                paymentMethodId,
//                amount
//        )) {
//            int affectedRows = pstmt.executeUpdate();
//
//            if (affectedRows == 0) {
//                throw new Exception("Creating payment failed, no rows affected.");
//            }
//        }
//    }

    DatabaseManager db;

    public Invoices(DatabaseManager db) {
        this.db = db;
    }

    public Invoice addInvoice(int orderId, Timestamp invoiceDate, float amount) throws Exception {
        Invoice invoice = new Invoice();
        invoice.setOrderId(orderId);
        invoice.setInvoiceDate(invoiceDate);
        invoice.setAmount(amount);

        try (PreparedStatement pstmt = db.getDbConnection().prepareStatement(
                "INSERT INTO INVOICE (order_id, invoice_date, amount) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, invoice.getOrderId());
            pstmt.setTimestamp(2, invoice.getInvoiceDate());
            pstmt.setFloat(3, invoice.getAmount());

            int affectedRows = pstmt.executeUpdate();


            if (affectedRows == 0) {
                throw new Exception("Creating invoice failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    invoice.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }

        }

        return invoice;
    }

    public void getInvoice(int id) throws Exception {
        try (PreparedStatement pstmt = db.prepareStatement(
                "SELECT * FROM INVOICE WHERE id = ?",
                id
        )) {
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new Exception("Getting invoice failed, no rows affected.");
            }
        }
    }

    public void updateInvoice(int id, int orderId, Timestamp invoiceDate, float amount) throws Exception {
        try (PreparedStatement pstmt = db.getDbConnection().prepareStatement(
                "UPDATE INVOICE SET order_id = ?, invoice_date = ?, amount = ? WHERE id = ?")) {

            pstmt.setInt(1, orderId);
            pstmt.setTimestamp(2, invoiceDate);
            pstmt.setFloat(3, amount);
            pstmt.setInt(4, id);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new Exception("Updating invoice failed, no rows affected.");
            }
        }
    }

    public void deleteInvoice(int id) throws Exception {
        try (PreparedStatement pstmt = db.prepareStatement(
                "DELETE FROM INVOICE WHERE id = ?",
                id
        )) {
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new Exception("Deleting invoice failed, no rows affected.");
            }
        }
    }


}
