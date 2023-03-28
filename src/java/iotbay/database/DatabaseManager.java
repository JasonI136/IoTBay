/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iotbay.database;

import java.sql.*;

/**
 * @author cmesina
 */
public class DatabaseManager {

    protected Connection conn;

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
        this.conn = DriverManager.getConnection(dbUrl + dbName, dbUser, dbPass);
        this.initDb();
    }

    /**
     * Creates the database tables if they do not exist and performs any other database initialization tasks.
     *
     * @throws SQLException if there is an error creating the tables
     */
    private void initDb() throws SQLException {

//        DatabaseMetaData metadata = conn.getMetaData();
//        ResultSet rs = metadata.getTypeInfo();
//        while (rs.next()) {
//            String typeName = rs.getString("TYPE_NAME");
//            int dataType = rs.getInt("DATA_TYPE");
//            System.out.println("Type name: " + typeName + ", Data type: " + dataType);
//        }

        // create the user table
        if (!this.tableExists("USER_ACCOUNT")) {
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

            Statement stmt = this.conn.createStatement();
            stmt.execute(createTableQuery);
            conn.commit();
        }

        // create the payment method table
        if (!this.tableExists("PAYMENT_METHOD")) {
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

            Statement stmt = this.conn.createStatement();
            stmt.execute(createTableQuery);
            conn.commit();
        }

        // create the category table
        if (!this.tableExists("CATEGORY")) {
            String createTableQuery =
                    "CREATE TABLE CATEGORY ("
                            + "id                               INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                            + "name                             VARCHAR(256),"
                            + "PRIMARY KEY (id)"
                            + ")";

            Statement stmt = this.conn.createStatement();
            stmt.execute(createTableQuery);
            conn.commit();

        }

        // create the products table
        if (!this.tableExists("PRODUCT")) {
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

            Statement stmt = this.conn.createStatement();
            stmt.execute(createTableQuery);
            conn.commit();
        }

        if (!this.tableExists("CUSTOMER_ORDER")) {
            String createTableQuery =
                    "CREATE TABLE CUSTOMER_ORDER ("
                            + "id                               INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                            + "user_id                          INT,"
                            + "order_date                       TIMESTAMP,"
                            + "order_status                     VARCHAR(256),"
                            + "PRIMARY KEY (id),"
                            + "CONSTRAINT customer_order_user_id_ref FOREIGN KEY (user_id) REFERENCES USER_ACCOUNT(id)"
                            + ")";

            Statement stmt = this.conn.createStatement();
            stmt.execute(createTableQuery);
            conn.commit();
        }

        if (!this.tableExists("ORDER_LINE_ITEM")) {
            String createTableQuery =
                    "CREATE TABLE ORDER_LINE_ITEM ("
                            + "order_id                         INT,"
                            + "product_id                       INT,"
                            + "quantity                         INT,"
                            + "PRIMARY KEY (order_id, product_id),"
                            + "CONSTRAINT order_line_item_order_id_ref FOREIGN KEY (order_id) REFERENCES CUSTOMER_ORDER(id),"
                            + "CONSTRAINT order_line_item_product_id_ref FOREIGN KEY (product_id) REFERENCES PRODUCT(id)"
                            + ")";

            Statement stmt = this.conn.createStatement();
            stmt.execute(createTableQuery);
            conn.commit();
        }

        if (!this.tableExists("INVOICE")) {
            String createTableQuery =
                    "CREATE TABLE INVOICE ("
                            + "id                               INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                            + "order_id                         INT,"
                            + "invoice_date                     DATE,"
                            + "amount                           FLOAT,"
                            + "PRIMARY KEY (id),"
                            + "CONSTRAINT invoice_order_id_ref FOREIGN KEY (order_id) REFERENCES CUSTOMER_ORDER(id)"
                            + ")";

            Statement stmt = this.conn.createStatement();
            stmt.execute(createTableQuery);
            conn.commit();
        }

        if (!this.tableExists("PAYMENT")) {
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

            Statement stmt = this.conn.createStatement();
            stmt.execute(createTableQuery);
            conn.commit();
        }


        System.out.println("Database initalised successfully.");

    }

    public Connection getDbConnection() {
        return this.conn;
    }

    /**
     * Checks if a table exists in the database.
     *
     * @param tableName the name of the table to check
     * @return true if the table exists, false otherwise
     * @throws SQLException if there is an error checking if the table exists
     */
    private boolean tableExists(String tableName) throws SQLException {
        DatabaseMetaData dbMeta = this.conn.getMetaData();
        ResultSet rs = dbMeta.getTables(null, null, tableName, null);
        return rs.next();
    }

    public PreparedStatement prepareStatement(String sql, Object... params) throws SQLException {
        PreparedStatement statement = this.getDbConnection().prepareStatement(sql);

        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }

        return statement;
    }
}
