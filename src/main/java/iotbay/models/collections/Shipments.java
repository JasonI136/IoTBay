package iotbay.models.collections;

import iotbay.database.DatabaseManager;
import iotbay.models.entities.Shipment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Shipments {
//    "CREATE TABLE SHIPMENT ("
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
    private final DatabaseManager db;

    public Shipments(DatabaseManager db) {
        this.db = db;
    }

    public void addShipment(Shipment shipment) throws Exception {
        try (Connection conn = db.getDbConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO SHIPMENT (order_id, dispatch_date, delivery_date, courier_name, tracking_number, status) VALUES (?, ?, ?, ?, ?, ?)")) {
                ps.setInt(1, shipment.getOrderId());
                ps.setString(2, shipment.getDispatchDate());
                ps.setString(3, shipment.getDeliveryDate());
                ps.setString(4, shipment.getCourierName());
                ps.setString(5, shipment.getTrackingNumber());
                ps.setString(6, shipment.getStatus());
                ps.executeUpdate();
            }
        }
    }

    public void updateShipment(Shipment shipment) throws Exception {
        try (Connection conn = db.getDbConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE SHIPMENT SET order_id = ?, dispatch_date = ?, delivery_date = ?, courier_name = ?, tracking_number = ?, status = ? WHERE id = ?")) {
                ps.setInt(1, shipment.getOrderId());
                ps.setString(2, shipment.getDispatchDate());
                ps.setString(3, shipment.getDeliveryDate());
                ps.setString(4, shipment.getCourierName());
                ps.setString(5, shipment.getTrackingNumber());
                ps.setString(6, shipment.getStatus());
                ps.setInt(7, shipment.getId());
                ps.executeUpdate();
            }
        }
    }

    public void deleteShipment(int id) throws Exception {
        try (Connection conn = db.getDbConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM SHIPMENT WHERE id = ?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        }
    }

    public Shipment getShipment(int id) throws Exception {
        try (Connection conn = db.getDbConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM SHIPMENT WHERE id = ?")) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new Shipment(rs, db);
                    }
                }
            }
        }
        return null;
    }




}
