/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iotbay.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author cmesina
 */
public class DatabaseManager {
   
    protected Connection conn;
    
    public DatabaseManager(String dbUrl, String dbUser, String dbPass, String dbName) throws ClassNotFoundException, SQLException {
        this.conn = DriverManager.getConnection(dbUrl + dbName, dbUser, dbPass);
        this.initDb();
    }
    
    private void initDb() throws SQLException {
        
        if (!this.tableExists("USERS")) {
            String createTableQuery = "CREATE TABLE USERS ("
                + "userId INT PRIMARY KEY,"
                + "username VARCHAR(256),"
                + "password VARCHAR(256),"
                + "firstName VARCHAR(256),"
                + "lastName VARCHAR(256),"
                + "email VARCHAR(256),"
                + "address VARCHAR(256),"
                + "phoneNumber INT,"
                + "isStaff BOOLEAN"
                + ")";
        
            Statement stmt = this.conn.createStatement();
            stmt.execute(createTableQuery);
            conn.commit();
        }
        
        
        System.out.println("Database initalised successfully.");

                
    }
    
    private boolean tableExists(String tableName) throws SQLException {
        DatabaseMetaData dbMeta = this.conn.getMetaData();
        ResultSet rs = dbMeta.getTables(null, null, tableName, null);
        if (rs.next()) {
            return true;
        } else {
            return false;
        }
    }
}
