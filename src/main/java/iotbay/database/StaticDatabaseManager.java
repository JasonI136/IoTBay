package iotbay.database;

import java.sql.Connection;

/**
 * A static class that provides a static method to get a database connection.
 * This is only used for Log4j2 to log to the database.
 * This should not be used anywhere else.
 */
public class StaticDatabaseManager {

    /**
     * The instance of the database manager
     */
    private static DatabaseManager instance;

    /**
     * Sets the instance of the database manager
     * @param instance the instance of the database manager
     */
    public static void setInstance(DatabaseManager instance) {
        StaticDatabaseManager.instance = instance;
    }

    /**
     * Gets a database connection
     * @return a database connection
     */
    public static Connection getConnection() {
        return instance.getDbConnection();
    }
}
