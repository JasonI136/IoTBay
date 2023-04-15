package iotbay.models.collections;

import iotbay.database.DatabaseManager;
import iotbay.models.entities.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Logs {
    DatabaseManager db;

    public Logs(DatabaseManager db) {
        this.db = db;
    }

    public List<Log> getLogs(int limit, int offset) throws Exception {
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
}
