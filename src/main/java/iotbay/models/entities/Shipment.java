package iotbay.models.entities;

import iotbay.database.DatabaseManager;
import lombok.*;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

    private transient final DatabaseManager db;

    private int id;
    private int orderId;
    private String dispatchDate;
    private String deliveryDate;
    private String courierName;
    private String trackingNumber;
    private String status;

    public Shipment(DatabaseManager db) {
        this.db = db;
    }

    public Shipment(ResultSet rs, DatabaseManager db) throws Exception {
        this.id = rs.getInt("id");
        this.orderId = rs.getInt("order_id");
        this.dispatchDate = rs.getString("dispatch_date");
        this.deliveryDate = rs.getString("delivery_date");
        this.courierName = rs.getString("courier_name");
        this.trackingNumber = rs.getString("tracking_number");
        this.status = rs.getString("status");
        this.db = db;
    }

    public void update() throws Exception {
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
                    throw new Exception("Updating shipment failed, no rows affected.");
                }
            }
        }
    }
}
