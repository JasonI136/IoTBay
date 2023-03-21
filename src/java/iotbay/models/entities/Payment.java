package iotbay.models.entities;

import java.io.Serializable;
import java.sql.Statement;

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
//            conn.commit();
//        }

    private int id;
    private int invoiceId;
    private String date;
    private int paymentMethodId;
    private float amount;

    public Payment() {};

    public Payment(int id, int invoiceId, String date, int paymentMethodId, float amount) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.date = date;
        this.paymentMethodId = paymentMethodId;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
