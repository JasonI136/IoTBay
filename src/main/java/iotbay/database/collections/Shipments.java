package iotbay.database.collections;

import iotbay.database.DatabaseManager;
import iotbay.models.Shipment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents a collection of shipments
 */
public class Shipments {

    /**
     * An instance of the database manager
     */
    private final DatabaseManager db;

    /**
     * Initializes the shipments collection with the database manager
     * @param db an instance of the database manager
     */
    public Shipments(DatabaseManager db) {
        this.db = db;
    }

    /**
     * Adds a shipment to the database
     * @param shipment the shipment to add
     * @throws SQLException if there is an error adding the shipment
     */
    public void addShipment(Shipment shipment) throws SQLException {
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

    /**
     * Updates a shipment in the database
     * @param shipment the shipment to update
     * @throws SQLException if there is an error updating the shipment
     */
    public void updateShipment(Shipment shipment) throws SQLException {
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

    /**
     * Deletes a shipment from the database
     * @param id the id of the shipment to delete
     * @throws SQLException if there is an error deleting the shipment
     */
    public void deleteShipment(int id) throws SQLException {
        try (Connection conn = db.getDbConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM SHIPMENT WHERE id = ?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        }
    }

    /**
     * Gets a shipment from the database
     * @param id the id of the shipment to get
     * @return the shipment, or null if it doesn't exist
     * @throws SQLException if there is an error getting the shipment
     */
    public Shipment getShipment(int id) throws SQLException {
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
