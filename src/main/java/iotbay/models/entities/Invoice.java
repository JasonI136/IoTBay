package iotbay.models.entities;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

@Data
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
//            
//        }


    private int id;
    private int orderId;
    private Timestamp invoiceDate;
    private float amount;

    public Invoice() {};

    public Invoice(ResultSet rs) throws Exception {
        this.id = rs.getInt("id");
        this.orderId = rs.getInt("order_id");
        this.invoiceDate = rs.getTimestamp("invoice_date");
        this.amount = rs.getFloat("amount");
    }

}
