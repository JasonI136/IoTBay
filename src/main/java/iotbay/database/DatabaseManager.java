/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iotbay.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import iotbay.annotations.GlobalServletField;
import iotbay.database.collections.*;
import iotbay.exceptions.UserExistsException;
import iotbay.models.User;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;

/**
 * Represents the database manager
 */
@Getter
public class DatabaseManager {


    /**
     * The hikari data source
     */
    protected HikariDataSource dataSource;

    /**
     * The logger
     */
    private static final Logger logger = LogManager.getLogger(DatabaseManager.class);

    /**
     * An instance of the users collection
     */
    @GlobalServletField("users")
    private Users users;

    /**
     * An instance of the products collection
     */
    @GlobalServletField("products")
    private Products products;

    /**
     * An instance of the categories collection
     */
    @GlobalServletField("categories")
    private Categories categories;

    /**
     * An instance of the orders collection
     */
    @GlobalServletField("orders")
    private Orders orders;

    /**
     * An instance of the order line items collection
     */
    @GlobalServletField("orderLineItems")
    private OrderLineItems orderLineItems;

    /**
     * An instance of the payments collection
     */
    @GlobalServletField("payments")
    private Payments payments;

    /**
     * An instance of the invoices collection
     */
    @GlobalServletField("invoices")
    private Invoices invoices;

    /**
     * An instance of the shipments collection
     */
    @GlobalServletField("shipments")
    private Shipments shipments;

    /**
     * An instance of the payment methods collection
     */
    @GlobalServletField("paymentMethods")
    private PaymentMethods paymentMethods;

    /**
     * An instance of the logs collection
     */
    @GlobalServletField("logs")
    private Logs logs;

    boolean skipAdminUserCreation;

    boolean skipCustomerUserCreation;


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
    public DatabaseManager(String dbUrl, String dbUser, String dbPass, String dbName, boolean skipAdminUserCreation, boolean skipCustomerUserCreation) throws ClassNotFoundException, SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl + dbName);
        config.setUsername(dbUser);
        config.setPassword(dbPass);
        config.setMaximumPoolSize(20);
        config.setLeakDetectionThreshold(10000);

        this.skipAdminUserCreation = skipAdminUserCreation;
        this.skipCustomerUserCreation = skipCustomerUserCreation;

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
                            + "phone_number                     VARCHAR(256),"
                            + "is_staff                         BOOLEAN,"
                            + "stripe_customer_id               VARCHAR(256),"
                            + "registration_date                TIMESTAMP,"
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
                            + "deleted                          BOOLEAN,"
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

            // populate the category table from categories.sql
            logger.warn("Populating CATEGORY table");


            try {
                try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("categories.sql")) {
                    if (inputStream == null) {
                        throw new FileNotFoundException("Could not find categories.sql");
                    }
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        try (Connection conn = this.getDbConnection()) {
                            try (Statement stmt = conn.createStatement()) {
                                stmt.execute(line);
                            }
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                logger.error("Could not find categories.sql");
            } catch (IOException e) {
                logger.error("Error reading categories.sql");
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

            try {
                try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("products.sql")) {
                    if (inputStream == null) {
                        throw new FileNotFoundException("Could not find products.sql");
                    }
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        try (Connection conn = this.getDbConnection()) {
                            try (Statement stmt = conn.createStatement()) {
                                stmt.execute(line);
                            }
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                logger.error("Could not find products.sql");
            } catch (IOException e) {
                logger.error("Error reading products.sql");
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
                            + "stripe_payment_intent_id         VARCHAR(256),"
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
                            + "price                           FLOAT,"
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

        if (!this.tableExists("SHIPMENT")) {
            logger.warn("Creating SHIPMENT table");
            String createTableQuery =
                    "CREATE TABLE SHIPMENT ("
                            + "id                               INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                            + "order_id                         INT,"
                            + "dispatch_date                    DATE,"
                            + "delivery_date                    DATE,"
                            + "courier_name                     VARCHAR(256),"
                            + "tracking_number                  VARCHAR(256),"
                            + "status                           VARCHAR(256),"
                            + "PRIMARY KEY (id),"
                            + "CONSTRAINT shipment_order_id_ref FOREIGN KEY (order_id) REFERENCES CUSTOMER_ORDER(id)"
                            + ")";

            try (Connection conn = this.getDbConnection()) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(createTableQuery);
                }
            }

        }

//        <JDBC name="iotbay_logs" tableName="EVENT_LOG">
//            <ConnectionFactory class="iotbay.database.StaticDatabaseManager" method="getConnection"/>
//            <Column name="ID" pattern="%u"/>
//            <Column name="TIMESTAMP" pattern="%d{yyyy-MM-dd HH:mm:ss.SSS}"/>
//            <Column name="LEVEL" pattern="%level"/>
//            <Column name="MESSAGE" pattern="%msg"/>
//        </JDBC>

        if (!this.tableExists("EVENT_LOG")) {
            logger.warn("Creating EVENT_LOG table");
            String createTableQuery =
                    "CREATE TABLE EVENT_LOG ("
                            + "id                               INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                            + "timestamp                        TIMESTAMP,"
                            + "level                            VARCHAR(256),"
                            + "message                          VARCHAR(256),"
                            + "PRIMARY KEY (id)"
                            + ")";

            try (Connection conn = this.getDbConnection()) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(createTableQuery);
                }
            }

        }

        // Initialize DAOs
        this.users = new Users(this);
        this.products = new Products(this);
        this.categories = new Categories(this);
        this.orders = new Orders(this);
        this.invoices = new Invoices(this);
        this.payments = new Payments(this);
        this.shipments = new Shipments(this);
        this.paymentMethods = new PaymentMethods(this);
        this.orderLineItems = new OrderLineItems(this, this.orders, this.products);
        this.logs = new Logs(this);

        // create the default admin user if it doesn't exist
        if (this.getUsers().getUser("admin") == null && !skipAdminUserCreation) {
            User user = new User(this);
            user.setUsername("admin");
            user.setPassword("admin");
            user.setFirstName("Admin");
            user.setLastName("Admin");
            user.setEmail("admin@example.com");
            user.setStaff(true);
            user.setPhoneNumber("0400000000");
            user.setAddress("123 Admin Street, Admin City, Admin State, Admin Postcode");

            try {
                this.getUsers().registerUser(user);
            } catch (UserExistsException e) {
                logger.warn("Default admin user already exists, skipping creation");
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                logger.error("Error creating default admin user. " + e.getMessage());
            }
        }

        // create the default customer user if it doesn't exist
        if (this.getUsers().getUser("customer") == null && !skipCustomerUserCreation) {
            User user = new User(this);
            user.setUsername("customer");
            user.setPassword("customer");
            user.setFirstName("Customer");
            user.setLastName("Customer");
            user.setEmail("customer@example.com");
            user.setStaff(false);
            user.setPhoneNumber("0400000000");
            user.setAddress("123 Customer Street, Customer City, Customer State, Customer Postcode");

            try {
                this.getUsers().registerUser(user);
            } catch (UserExistsException e) {
                logger.warn("Default customer user already exists, skipping creation");
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                logger.error("Error creating default customer user. " + e.getMessage());
            }
        }

        logger.info("Database initialized.");

    }

    /**
     * Gets a database connection from the connection pool.
     *
     * @return a database connection
     */
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
