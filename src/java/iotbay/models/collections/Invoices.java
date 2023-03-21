package iotbay.models.collections;

import iotbay.database.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.Statement;

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

    public void addInvoice(int orderId, String invoiceDate, float amount) throws Exception {
        try (PreparedStatement pstmt = db.prepareStatement(
                "INSERT INTO INVOICE (order_id, invoice_date, amount) VALUES (?, ?, ?)",
                orderId,
                invoiceDate,
                amount
        )) {
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new Exception("Creating invoice failed, no rows affected.");
            }
        }
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

    public void updateInvoice(int id, int orderId, String invoiceDate, float amount) throws Exception {
        try (PreparedStatement pstmt = db.prepareStatement(
                "UPDATE INVOICE SET order_id = ?, invoice_date = ?, amount = ? WHERE id = ?",
                orderId,
                invoiceDate,
                amount,
                id
        )) {
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
