package iotbay.models;

import lombok.Data;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
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


    /**
     * The invoice's unique id.
     * <br>
     * <br>
     * <b>TABLE:</b> INVOICE.id
     */
    private int id;

    /**
     * The invoice's order id.
     * <br>
     * <br>
     * <b>TABLE:</b> INVOICE.order_id
     */
    private int orderId;

    /**
     * The invoice's date.
     * <br>
     * <br>
     * <b>TABLE:</b> INVOICE.invoice_date
     */
    private Timestamp invoiceDate;

    /**
     * The invoice's amount.
     * <br>
     * <br>
     * <b>TABLE:</b> INVOICE.amount
     */
    private float amount;

    /**
     * Default constructor
     */
    public Invoice() {}

    /**
     * Initialises the invoice with the given result set.
     * @param rs The result set.
     * @throws SQLException If there is an error with the SQL query.
     */
    public Invoice(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.orderId = rs.getInt("order_id");
        this.invoiceDate = rs.getTimestamp("invoice_date");
        this.amount = rs.getFloat("amount");
    }

}
