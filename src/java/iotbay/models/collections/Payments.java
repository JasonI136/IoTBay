package iotbay.models.collections;

import iotbay.database.DatabaseManager;
import iotbay.exceptions.UserExistsException;

import java.sql.PreparedStatement;

/**
 * The payments collection
 */
public class Payments {
    //    if (!this.tableExists("PAYMENT")) {
//            String createTableQuery =
//                            "CREATE TABLE PAYMENT ("
//                            + "id                               INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
//                            + "invoice_id                       INT,"
//                            + "date                             DATE,"
//                            + "payment_method_id                INT,"
//                            + "amount                           FLOAT,"
//                            + "PRIMARY KEY (id),"
//                            + "CONSTRAINT payment_invoice_id_ref FOREIGN KEY (invoice_id) REFERENCES INVOICE(id),"
//                            + "CONSTRAINT payment_payment_method_id_ref FOREIGN KEY (payment_method_id) REFERENCES PAYMENT_METHOD(id)"
//                            + ")";
//
//            Statement stmt = this.conn.createStatement();
//            stmt.execute(createTableQuery);
//            conn.commit();
//        }
    DatabaseManager db;

    public Payments(DatabaseManager db) {
        this.db = db;
    }

    public void addPayment(int invoiceId, String date, int paymentMethodId, float amount) throws Exception {
        try (PreparedStatement pstmt = db.prepareStatement(
                "INSERT INTO PAYMENT (invoice_id, date, payment_method_id, amount) VALUES (?, ?, ?, ?)",
                invoiceId,
                date,
                paymentMethodId,
                amount
        )) {
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new Exception("Creating payment failed, no rows affected.");
            }
        }
    }

    public void getPayment(int id) throws Exception {
        try (PreparedStatement pstmt = db.prepareStatement(
                "SELECT * FROM PAYMENT WHERE id = ?",
                id
        )) {
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new Exception("Getting payment failed, no rows affected.");
            }
        }
    }

    public void updatePayment(int id, int invoiceId, String date, int paymentMethodId, float amount) throws Exception {
        try (PreparedStatement pstmt = db.prepareStatement(
                "UPDATE PAYMENT SET invoice_id = ?, date = ?, payment_method_id = ?, amount = ? WHERE id = ?",
                invoiceId,
                date,
                paymentMethodId,
                amount,
                id
        )) {
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new Exception("Updating payment failed, no rows affected.");
            }
        }
    }

    public void deletePayment(int id) throws Exception {
        try (PreparedStatement pstmt = db.prepareStatement(
                "DELETE FROM PAYMENT WHERE id = ?",
                id
        )) {
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new Exception("Deleting payment failed, no rows affected.");
            }
        }
    }

}
