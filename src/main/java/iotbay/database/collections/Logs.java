package iotbay.database.collections;

import iotbay.database.DatabaseManager;
import iotbay.models.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of logs
 */
public class Logs {

    /**
     * An instance of the database manager
     */
    DatabaseManager db;

    /**
     * Initializes the logs collection with the database manager
     * @param db
     */
    public Logs(DatabaseManager db) {
        this.db = db;
    }

    /**
     * Gets all the logs from the database
     * @param limit the number of logs to retrieve
     * @param offset the offset to start retrieving logs from
     * @return a list of {@link iotbay.models.Log} objects
     * @throws SQLException if there is an error retrieving the logs
     */
    public List<Log> getLogs(int limit, int offset) throws SQLException {
        List<Log> logs = new ArrayList<>();
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM EVENT_LOG OFFSET ? ROWS FETCH NEXT ? ROWS ONLY")) {

                stmt.setInt(1, offset);
                stmt.setInt(2, limit);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        logs.add(new Log(rs));
                    }
                }
            }
        }

        return logs;
    }

    /**
     * Gets all the logs from the database
     * @param limit the number of logs to retrieve
     * @param offset the offset to start retrieving logs from
     * @param desc whether to order the logs in descending order
     * @return a list of {@link iotbay.models.Log} objects
     * @throws SQLException if there is an error retrieving the logs
     */
    public List<Log> getLogs(int limit, int offset, boolean desc) throws SQLException {
        List<Log> logs = new ArrayList<>();
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM EVENT_LOG ORDER BY ID " + (desc ? "DESC" : "ASC") + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY")) {

                stmt.setInt(1, offset);
                stmt.setInt(2, limit);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        logs.add(new Log(rs));
                    }
                }
            }
        }

        return logs;
    }
}
