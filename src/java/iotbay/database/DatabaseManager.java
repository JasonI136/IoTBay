/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iotbay.database;

import iotbay.exceptions.ProductNotFoundException;
import iotbay.exceptions.UserExistsException;
import iotbay.exceptions.UserNotFoundException;
import iotbay.models.Product;
import iotbay.models.User;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
        
        if (!this.tableExists("PRODUCTS")) {
            String createTableQuery = "CREATE TABLE PRODUCTS (" 
                + "productId INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," 
                + "name VARCHAR(256)," 
                + "description CLOB," 
                + "price INT," 
                + "quantity INT," 
                + "PRIMARY KEY (productId)"
                + ")";
            
            Statement stmt = this.conn.createStatement();
            stmt.execute(createTableQuery);
            conn.commit();
        }
        
        
        System.out.println("Database initalised successfully.");

                
    }
    
    public List<Product> getProducts(int limit, int offset) throws SQLException {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCTS ORDER BY productId OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, offset);
            pstmt.setInt(2, limit);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getInt("productId"));
                    product.setName(rs.getString("name"));
                    product.setDescription(rs.getString("description"));
                    product.setPrice(rs.getInt("price"));
                    product.setQuantity(rs.getInt("quantity"));
                    productList.add(product);
                }
            }
        }
        return productList;
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
    
    public Product getProduct(int productId) throws SQLException, ProductNotFoundException {
        PreparedStatement productQuery = this.conn.prepareStatement("SELECT * FROM PRODUCTS WHERE productid = ?");
        productQuery.setInt(1, productId);
        
        ResultSet rs = productQuery.executeQuery();
        
        if (!rs.next()) {
            throw new ProductNotFoundException("The product with id " + productId + " does not exist.");
        }
        
       Product product = new Product();
       product.setProductId(rs.getInt("productid"));
       product.setName(rs.getString("name"));
       product.setDescription(rs.getString("description"));
       product.setPrice(rs.getInt("price"));
       product.setQuantity(rs.getInt("quantity"));
        
        
       
       return product;
    }
    
     public User getUserByEmail(String email) throws SQLException, UserNotFoundException {
        PreparedStatement userQuery = this.conn.prepareStatement("SELECT * FROM USERS WHERE email = ?");
        userQuery.setString(1, email);
        
        ResultSet rs = userQuery.executeQuery();
        
        if (!rs.next()) {
            throw new UserNotFoundException("The user with email " + email + " does not exist.");
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
    
    public void addUser(User user) throws SQLException, UserExistsException {
        
        PreparedStatement checkUserQuery = this.conn.prepareStatement(
        "SELECT COUNT(*) FROM USERS WHERE username = ? OR email = ?"
        );
        
        checkUserQuery.setString(1, user.getUsername());
        checkUserQuery.setString(2, user.getEmail());
        
        ResultSet rs = checkUserQuery.executeQuery();
        
        if (rs.next() && rs.getInt(1) > 0) {
            throw new UserExistsException("User already exists.");
        }
        
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
}/***
 * Hello word
 * 
 * 
 */