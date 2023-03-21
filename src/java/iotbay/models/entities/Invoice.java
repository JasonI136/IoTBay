package iotbay.models.entities;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Statement;

public class Invoice implements Serializable {

//     if (!this.tableExists("INVOICE")) {
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


    private int id;
    private int orderId;
    private String invoiceDate;
    private float amount;

    public Invoice() {};

    public Invoice(ResultSet rs) throws Exception {
        this.id = rs.getInt("id");
        this.orderId = rs.getInt("order_id");
        this.invoiceDate = rs.getString("invoice_date");
        this.amount = rs.getFloat("amount");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
