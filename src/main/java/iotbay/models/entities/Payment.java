package iotbay.models.entities;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
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

    private int id;
    private int invoiceId;
    private Timestamp date;
    private int paymentMethodId;
    private float amount;

    public Payment() {}

    public Payment(ResultSet rs) throws Exception {
        this.id = rs.getInt("id");
        this.invoiceId = rs.getInt("invoice_id");
        this.date = rs.getTimestamp("date");
        this.paymentMethodId = rs.getInt("payment_method_id");
        this.amount = rs.getFloat("amount");
    }

    public Payment(int id, int invoiceId, Timestamp date, int paymentMethodId, float amount) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.date = date;
        this.paymentMethodId = paymentMethodId;
        this.amount = amount;
    }

}
