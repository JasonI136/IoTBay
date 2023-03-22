package iotbay.models.collections;

import iotbay.database.DatabaseManager;
import iotbay.exceptions.UserExistsException;
import iotbay.models.entities.Payment;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

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

    public Payment addPayment(int invoiceId, Timestamp date, int paymentMethodId, float amount) throws Exception {
        Payment payment = new Payment();
        payment.setInvoiceId(invoiceId);
        payment.setDate(date);
        payment.setPaymentMethodId(paymentMethodId);
        payment.setAmount(amount);

        try (PreparedStatement pstmt = db.getDbConnection().prepareStatement(
                "INSERT INTO PAYMENT (invoice_id, date, payment_method_id, amount) VALUES (?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, invoiceId);
            pstmt.setTimestamp(2, date);
            pstmt.setInt(3, paymentMethodId);
            pstmt.setFloat(4, amount);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new Exception("Creating payment failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    payment.setId(generatedKeys.getInt(1));
                } else {
                    throw new Exception("Creating payment failed, no ID obtained.");
                }
            }
        }

        return payment;
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

    public void updatePayment(int id, int invoiceId, Timestamp date, int paymentMethodId, float amount) throws Exception {
        try (PreparedStatement pstmt = db.getDbConnection().prepareStatement(
                "UPDATE PAYMENT SET invoice_id = ?, date = ?, payment_method_id = ?, amount = ? WHERE id = ?")) {

            pstmt.setInt(1, invoiceId);
            pstmt.setTimestamp(2, date);
            pstmt.setInt(3, paymentMethodId);
            pstmt.setFloat(4, amount);
            pstmt.setInt(5, id);
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
