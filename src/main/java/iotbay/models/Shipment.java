package iotbay.models;

import iotbay.database.DatabaseManager;
import lombok.Data;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class Shipment implements Serializable {
//"CREATE TABLE SHIPMENT ("
//                            + "id                               INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
//                            + "order_id                         INT,"
//                            + "dispatch_date                    DATE,"
//                            + "delivery_date                    DATE,"
//                            + "courier_name                     VARCHAR(256),"
//                            + "tracking_number                  VARCHAR(256),"
//                            + "status                           VARCHAR(256),"
//                            + "PRIMARY KEY (id),"
//                            + "CONSTRAINT shipment_order_id_ref FOREIGN KEY (order_id) REFERENCES CUSTOMER_ORDER(id)"
//                            + ")";

    /**
     * The database manager.
     */
    private transient final DatabaseManager db;

    /**
     * The Shipment's unique id.
     * <br>
     * <br>
     * <b>TABLE:</b> SHIPMENT.id
     */
    private int id;

    /**
     * The Shipment's order id.
     * <br>
     * <br>
     * <b>TABLE:</b> SHIPMENT.order_id
     */
    private int orderId;

    /**
     * The Shipment's dispatch date.
     * <br>
     * <br>
     * <b>TABLE:</b> SHIPMENT.dispatch_date
     */
    private String dispatchDate;

    /**
     * The Shipment's delivery date.
     * <br>
     * <br>
     * <b>TABLE:</b> SHIPMENT.delivery_date
     */
    private String deliveryDate;

    /**
     * The Shipment's courier name.
     * <br>
     * <br>
     * <b>TABLE:</b> SHIPMENT.courier_name
     */
    private String courierName;

    /**
     * The Shipment's tracking number.
     * <br>
     * <br>
     * <b>TABLE:</b> SHIPMENT.tracking_number
     */
    private String trackingNumber;

    /**
     * The Shipment's status.
     * <br>
     * <br>
     * <b>TABLE:</b> SHIPMENT.status
     */
    private String status;

    /**
     * Creates a new shipment.
     * @param db The database manager.
     */
    public Shipment(DatabaseManager db) {
        this.db = db;
    }

    /**
     * Creates a new shipment from a result set.
     * @param rs The result set.
     * @param db The database manager.
     * @throws SQLException If there is an error.
     **/
    public Shipment(ResultSet rs, DatabaseManager db) throws SQLException {
        this.id = rs.getInt("id");
        this.orderId = rs.getInt("order_id");
        this.dispatchDate = rs.getString("dispatch_date");
        this.deliveryDate = rs.getString("delivery_date");
        this.courierName = rs.getString("courier_name");
        this.trackingNumber = rs.getString("tracking_number");
        this.status = rs.getString("status");
        this.db = db;
    }

    /**
     * Updates the shipment in the database.
     * @throws SQLException If there is an error.
     */
    public void update() throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("UPDATE SHIPMENT SET order_id = ?, dispatch_date = ?, delivery_date = ?, courier_name = ?, tracking_number = ?, status = ? WHERE id = ?")) {
                stmt.setInt(1, this.orderId);
                stmt.setString(2, this.dispatchDate);
                stmt.setString(3, this.deliveryDate);
                stmt.setString(4, this.courierName);
                stmt.setString(5, this.trackingNumber);
                stmt.setString(6, this.status);
                stmt.setInt(7, this.id);
                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Updating shipment failed, no rows affected.");
                }
            }
        }
    }
}
