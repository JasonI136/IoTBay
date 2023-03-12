/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iotbay.database;

import iotbay.exceptions.UserNotFoundException;
import iotbay.models.User;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
                + "userId INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                + "username VARCHAR(256) UNIQUE,"
                + "password VARCHAR(256),"
                + "passwordSalt VARCHAR(256),"
                + "firstName VARCHAR(256),"
                + "lastName VARCHAR(256),"
                + "email VARCHAR(256) UNIQUE,"
                + "address VARCHAR(256),"
                + "phoneNumber INT,"
                + "isStaff BOOLEAN,"
                + "PRIMARY KEY (userId)"
                + ")";
        
            Statement stmt = this.conn.createStatement();
            stmt.execute(createTableQuery);
            conn.commit();
        }
        
        
        System.out.println("Database initalised successfully.");

                
    }
    
    public User getUser(String username) throws SQLException, UserNotFoundException {
        PreparedStatement userQuery = this.conn.prepareStatement("SELECT * FROM USERS WHERE username = ?");
        userQuery.setString(1, username);
        
        ResultSet rs = userQuery.executeQuery();
        
        if (!rs.next()) {
            throw new UserNotFoundException("The user with username " + username + " does not exist.");
        }
        
       User user = new User();
       user.setUserId(rs.getInt("userId"));
       user.setUsername(rs.getString("username"));
       user.setPassword(rs.getString("password"));
       user.setPasswordSalt(rs.getString("passwordSalt"));
       user.setFirstName(rs.getString("firstName"));
       user.setLastName(rs.getString("lastName"));
       user.setEmail(rs.getString("email"));
       user.setAddress(rs.getString("address"));
       user.setPhoneNumber(rs.getInt("phoneNumber"));
       user.setIsStaff(rs.getBoolean("isStaff"));
       
       return user;
    }
    
    public void addUser(User user) throws SQLException { 
        PreparedStatement addUserQuery = this.conn.prepareStatement(
                "INSERT INTO USERS (username, password, passwordSalt, firstName, lastName, email, address, phoneNumber) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
        );
        
        addUserQuery.setString(1, user.getUsername());
        addUserQuery.setString(2, user.getPassword());
        addUserQuery.setString(3, user.getPasswordSalt());
        addUserQuery.setString(4, user.getFirstName());
        addUserQuery.setString(5, user.getLastName());
        addUserQuery.setString(6, user.getEmail());
        addUserQuery.setString(7, user.getAddress());
        addUserQuery.setInt(8, user.getPhoneNumber());
        
        int affectedRows = addUserQuery.executeUpdate();
        
        if (affectedRows == 1) {
            System.out.println("User " + user.getUsername() + " was added to the database.");
            this.conn.commit();
        }

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
