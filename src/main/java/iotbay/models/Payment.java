package iotbay.models;

import lombok.Data;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Data
public class Payment implements Serializable {
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
//            
//        }

    /**
     * The payment's unique id.
     * <br>
     * <br>
     * <b>TABLE:</b> PAYMENT.id
     */
    private int id;

    /**
     * The payment's invoice id.
     * <br>
     * <br>
     * <b>TABLE:</b> PAYMENT.invoice_id
     */
    private int invoiceId;

    /**
     * The payment's date.
     * <br>
     * <br>
     * <b>TABLE:</b> PAYMENT.date
     */
    private Timestamp date;

    /**
     * The payment's payment method id.
     * <br>
     * <br>
     * <b>TABLE:</b> PAYMENT.payment_method_id
     */
    private int paymentMethodId;

    /**
     * The payment's amount.
     * <br>
     * <br>
     * <b>TABLE:</b> PAYMENT.amount
     */
    private float amount;

    /**
     * Default constructor.
     */
    public Payment() {}

    /**
     * Inintialise a new payment from a result set.
     * @param rs The result set.
     * @throws SQLException If the result set is invalid.
     */
    public Payment(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.invoiceId = rs.getInt("invoice_id");
        this.date = rs.getTimestamp("date");
        this.paymentMethodId = rs.getInt("payment_method_id");
        this.amount = rs.getFloat("amount");
    }


}
