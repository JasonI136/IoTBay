package iotbay.database;

import java.sql.Connection;

public class StaticDatabaseManager {
    private static DatabaseManager instance;

    public static void setInstance(DatabaseManager instance) {
        StaticDatabaseManager.instance = instance;
    }

    public static Connection getConnection() {
        return instance.getDbConnection();
    }
}
