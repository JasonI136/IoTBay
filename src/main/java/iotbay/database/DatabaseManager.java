/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iotbay.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * @author cmesina
 */
public class DatabaseManager {


    protected HikariDataSource dataSource;

    private static final Logger logger = LogManager.getLogger(DatabaseManager.class);


    /**
     * Creates a new instance of DatabaseManager
     *
     * @param dbUrl  the database url
     * @param dbUser the database user
     * @param dbPass the database password
     * @param dbName the database name
     * @throws ClassNotFoundException if the database driver is not found
     * @throws SQLException           if there is an error connecting to the database
     */
    public DatabaseManager(String dbUrl, String dbUser, String dbPass, String dbName) throws ClassNotFoundException, SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl + dbName);
        config.setUsername(dbUser);
        config.setPassword(dbPass);
        config.setMaximumPoolSize(100);
        config.setLeakDetectionThreshold(10000);

        this.dataSource = new HikariDataSource(config);


        this.initDb();
    }

    /**
     * Creates the database tables if they do not exist and performs any other database initialization tasks.
     *
     * @throws SQLException if there is an error creating the tables
     */
    private void initDb() throws SQLException {
        // create the user table
        if (!this.tableExists("USER_ACCOUNT")) {
            logger.warn("Creating USER_ACCOUNT table");
            String createTableQuery =
                    "CREATE TABLE USER_ACCOUNT ("
                            + "id                               INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                            + "username                         VARCHAR(256) UNIQUE,"
                            + "password                         VARCHAR(256),"
                            + "password_salt                    VARCHAR(256),"
                            + "first_name                       VARCHAR(256),"
                            + "last_name                        VARCHAR(256),"
                            + "email                            VARCHAR(256) UNIQUE,"
                            + "address                          VARCHAR(256),"
                            + "phone_number                     INT,"
                            + "is_staff                         BOOLEAN,"
                            + "stripe_customer_id               VARCHAR(256),"
                            + "PRIMARY KEY (id)"
                            + ")";

            try (Connection conn = this.getDbConnection()) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(createTableQuery);
                }
            }

        }

        // create the payment method table
        if (!this.tableExists("PAYMENT_METHOD")) {
            logger.warn("Creating PAYMENT_METHOD table");
            String createTableQuery =
                    "CREATE TABLE PAYMENT_METHOD ("
                            + "id                               INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                            + "user_id                          INT,"
                            + "stripe_payment_method_id         VARCHAR(256),"
                            + "payment_method_type              VARCHAR(256),"
                            + "card_last_4                      INT,"
                            + "PRIMARY KEY (id),"
                            + "CONSTRAINT user_id_ref FOREIGN KEY (user_id) REFERENCES USER_ACCOUNT(id)"
                            + ")";

            try (Connection conn = this.getDbConnection()) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(createTableQuery);
                }
            }

        }

        // create the category table
        if (!this.tableExists("CATEGORY")) {
            logger.warn("Creating CATEGORY table");
            String createTableQuery =
                    "CREATE TABLE CATEGORY ("
                            + "id                               INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                            + "name                             VARCHAR(256),"
                            + "PRIMARY KEY (id)"
                            + ")";

            try (Connection conn = this.getDbConnection()) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(createTableQuery);
                }
            }


        }

        // create the products table
        if (!this.tableExists("PRODUCT")) {
            logger.warn("Creating PRODUCT table");
            String createTableQuery =
                    "CREATE TABLE PRODUCT ("
                            + "id                               INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                            + "name                             VARCHAR(256),"
                            + "description                      CLOB,"
                            + "image_url                        CLOB,"
                            + "price                            FLOAT,"
                            + "quantity                         INT,"
                            + "category_id                      INT,"
                            + "PRIMARY KEY (id),"
                            + "CONSTRAINT category_id_ref FOREIGN KEY (category_id) REFERENCES CATEGORY(id)"
                            + ")";

            try (Connection conn = this.getDbConnection()) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(createTableQuery);
                }
            }

        }

        if (!this.tableExists("CUSTOMER_ORDER")) {
            logger.warn("Creating CUSTOMER_ORDER table");
            String createTableQuery =
                    "CREATE TABLE CUSTOMER_ORDER ("
                            + "id                               INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                            + "user_id                          INT,"
                            + "order_date                       TIMESTAMP,"
                            + "order_status                     VARCHAR(256),"
                            + "PRIMARY KEY (id),"
                            + "CONSTRAINT customer_order_user_id_ref FOREIGN KEY (user_id) REFERENCES USER_ACCOUNT(id)"
                            + ")";

            try (Connection conn = this.getDbConnection()) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(createTableQuery);
                }
            }

        }

        if (!this.tableExists("ORDER_LINE_ITEM")) {
            logger.warn("Creating ORDER_LINE_ITEM table");
            String createTableQuery =
                    "CREATE TABLE ORDER_LINE_ITEM ("
                            + "order_id                         INT,"
                            + "product_id                       INT,"
                            + "quantity                         INT,"
                            + "PRIMARY KEY (order_id, product_id),"
                            + "CONSTRAINT order_line_item_order_id_ref FOREIGN KEY (order_id) REFERENCES CUSTOMER_ORDER(id),"
                            + "CONSTRAINT order_line_item_product_id_ref FOREIGN KEY (product_id) REFERENCES PRODUCT(id)"
                            + ")";

            try (Connection conn = this.getDbConnection()) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(createTableQuery);
                }
            }

        }

        if (!this.tableExists("INVOICE")) {
            logger.warn("Creating INVOICE table");
            String createTableQuery =
                    "CREATE TABLE INVOICE ("
                            + "id                               INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                            + "order_id                         INT,"
                            + "invoice_date                     DATE,"
                            + "amount                           FLOAT,"
                            + "PRIMARY KEY (id),"
                            + "CONSTRAINT invoice_order_id_ref FOREIGN KEY (order_id) REFERENCES CUSTOMER_ORDER(id)"
                            + ")";

            try (Connection conn = this.getDbConnection()) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(createTableQuery);
                }
            }

        }

        if (!this.tableExists("PAYMENT")) {
            logger.warn("Creating PAYMENT table");
            String createTableQuery =
                    "CREATE TABLE PAYMENT ("
                            + "id                               INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                            + "invoice_id                       INT,"
                            + "date                             TIMESTAMP,"
                            + "payment_method_id                INT,"
                            + "amount                           FLOAT,"
                            + "PRIMARY KEY (id),"
                            + "CONSTRAINT payment_invoice_id_ref FOREIGN KEY (invoice_id) REFERENCES INVOICE(id),"
                            + "CONSTRAINT payment_payment_method_id_ref FOREIGN KEY (payment_method_id) REFERENCES PAYMENT_METHOD(id)"
                            + ")";

            try (Connection conn = this.getDbConnection()) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(createTableQuery);
                }
            }

        }


        logger.info("Database initialized.");

    }

    public Connection getDbConnection() {
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if a table exists in the database.
     *
     * @param tableName the name of the table to check
     * @return true if the table exists, false otherwise
     * @throws SQLException if there is an error checking if the table exists
     */
    private boolean tableExists(String tableName) throws SQLException {
        try (Connection conn = this.getDbConnection()) {
            DatabaseMetaData dbMeta = conn.getMetaData();
            ResultSet rs = dbMeta.getTables(null, null, tableName, null);
            return rs.next();
        }

    }
}