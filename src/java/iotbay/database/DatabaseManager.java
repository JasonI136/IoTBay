/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iotbay.database;

import iotbay.exceptions.ProductNotFoundException;
import iotbay.exceptions.UserExistsException;
import iotbay.exceptions.UserNotFoundException;
import iotbay.models.Category;
import iotbay.models.PaymentMethod;
import iotbay.models.Product;
import iotbay.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cmesina
 */
public class DatabaseManager {

     protected Connection conn;

    /**
     * Creates a new instance of DatabaseManager
     * @param dbUrl the database url
     * @param dbUser the database user
     * @param dbPass the database password
     * @param dbName the database name
     * @throws ClassNotFoundException if the database driver is not found
     * @throws SQLException if there is an error connecting to the database
     */
    public DatabaseManager(String dbUrl, String dbUser, String dbPass, String dbName) throws ClassNotFoundException, SQLException {
        this.conn = DriverManager.getConnection(dbUrl + dbName, dbUser, dbPass);
        this.initDb();
    }

    /**
     * Creates the database tables if they do not exist and performs any other database initialization tasks.
     * @throws SQLException if there is an error creating the tables
     */
    private void initDb() throws SQLException {

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

        System.out.println("Database initalised successfully.");

    }

    public Connection getDbConnection() {
        return this.conn;
    }

    /**
     * Checks if a table exists in the database.
     * @param tableName the name of the table to check
     * @return true if the table exists, false otherwise
     * @throws SQLException if there is an error checking if the table exists
     */
    private boolean tableExists(String tableName) throws SQLException {
        DatabaseMetaData dbMeta = this.conn.getMetaData();
        ResultSet rs = dbMeta.getTables(null, null, tableName, null);
        return rs.next();
    }
}
