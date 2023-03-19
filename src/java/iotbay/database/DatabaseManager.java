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

    public DatabaseManager(String dbUrl, String dbUser, String dbPass, String dbName) throws ClassNotFoundException, SQLException {
        this.conn = DriverManager.getConnection(dbUrl + dbName, dbUser, dbPass);
        this.initDb();
    }

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

    public List<Product> getProducts(int limit, int offset, boolean description) throws SQLException {
        List<Product> productList = new ArrayList<>();

        String query;

        if (description) {
            query = "SELECT PRODUCT.id, PRODUCT.name, PRODUCT.description, "
                    + "PRODUCT.image_url, PRODUCT.price, PRODUCT.quantity, PRODUCT.category_id, "
                    + "category.name AS category_name "
                    + "FROM PRODUCT "
                    + "INNER JOIN CATEGORY "
                    + "ON PRODUCT.category_id = CATEGORY.id "
                    + "ORDER BY PRODUCT.id "
                    + "OFFSET ? ROWS "
                    + "FETCH NEXT ? ROWS ONLY";
        } else {
            query = "SELECT PRODUCT.id, PRODUCT.name, PRODUCT.image_url, PRODUCT.price, PRODUCT.quantity, PRODUCT.category_id, "
                    + "category.name AS category_name "
                    + "FROM PRODUCT "
                    + "INNER JOIN CATEGORY "
                    + "ON PRODUCT.category_id = CATEGORY.id "
                    + "ORDER BY PRODUCT.id "
                    + "OFFSET ? ROWS "
                    + "FETCH NEXT ? ROWS ONLY";
        }


        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, offset);
            pstmt.setInt(2, limit);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    if (description) {
                        product.setDescription(rs.getString("description"));
                    }

                    product.setPrice(rs.getInt("price"));
                    product.setQuantity(rs.getInt("quantity"));
                    product.setCategoryId(rs.getInt("category_id"));
                    product.setCategoryName(rs.getString("category_name"));
                    product.setImageURL(rs.getString("image_url"));
                    productList.add(product);
                }
            }
        }
        return productList;
    }

    public List<Category> getCategories() throws SQLException {
        List<Category> categoryList = new ArrayList<Category>();

        String query = "SELECT * FROM CATEGORY";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Category category = new Category();
                    category.setCategoryId(rs.getInt("id"));
                    category.setName(rs.getString("name"));
                    categoryList.add(category);
                }
            }
        }
        return categoryList;


    }

    public User getUser(String username) throws SQLException, UserNotFoundException {
        PreparedStatement userQuery = this.conn.prepareStatement("SELECT * FROM USER_ACCOUNT WHERE username = ?");

        userQuery.setString(1, username);


        ResultSet rs = userQuery.executeQuery();

        if (!rs.next()) {
            throw new UserNotFoundException("The user with username " + username + " does not exist.");
        }
        PreparedStatement userPaymentMethodsQuery = this.conn.prepareStatement("SELECT * FROM PAYMENT_METHOD WHERE user_id = ?");
        userPaymentMethodsQuery.setInt(1, rs.getInt("id"));

        List<PaymentMethod> paymentMethods = new ArrayList<>();
        ResultSet paymentMethodsRs = userPaymentMethodsQuery.executeQuery();
        while (paymentMethodsRs.next()) {
            PaymentMethod paymentMethod = new PaymentMethod();
            paymentMethod.setPaymentMethodId(paymentMethodsRs.getInt("id"));
            paymentMethod.setUserId(paymentMethodsRs.getInt("user_id"));
            paymentMethod.setStripePaymentMethodId(paymentMethodsRs.getString("stripe_payment_method_id"));
            paymentMethods.add(paymentMethod);
        }


        User user = new User();
        user.setUserId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setPasswordSalt(rs.getString("password_salt"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setAddress(rs.getString("address"));
        user.setPhoneNumber(rs.getInt("phone_number"));
        user.setIsStaff(rs.getBoolean("is_staff"));
        user.setStripeCustomerId(rs.getString("stripe_customer_id"));
        user.setPaymentMethods(paymentMethods);

        return user;
    }

    public Product getProduct(int productId) throws SQLException, ProductNotFoundException {
        PreparedStatement productQuery = this.conn.prepareStatement("SELECT * FROM PRODUCT WHERE id = ?");
        productQuery.setInt(1, productId);

        ResultSet rs = productQuery.executeQuery();

        if (!rs.next()) {
            throw new ProductNotFoundException("The product with id " + productId + " does not exist.");
        }

        Product product = new Product();
        product.setProductId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getInt("price"));
        product.setQuantity(rs.getInt("quantity"));
        product.setCategoryId(rs.getInt("category_id"));
        product.setImageURL(rs.getString("image_url"));

        return product;
    }

    public void addUser(User user) throws SQLException, UserExistsException {

        PreparedStatement checkUserQuery = this.conn.prepareStatement(
                "SELECT COUNT(*) FROM USER_ACCOUNT WHERE username = ? OR email = ?"
        );

        checkUserQuery.setString(1, user.getUsername());
        checkUserQuery.setString(2, user.getEmail());

        ResultSet rs = checkUserQuery.executeQuery();

        if (rs.next() && rs.getInt(1) > 0) {
            throw new UserExistsException("User already exists.");
        }

        PreparedStatement addUserQuery = this.conn.prepareStatement(
                "INSERT INTO USER_ACCOUNT (username, password, password_salt, first_name, last_name, email, address, phone_number, stripe_customer_id) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );

        addUserQuery.setString(1, user.getUsername());
        addUserQuery.setString(2, user.getPassword());
        addUserQuery.setString(3, user.getPasswordSalt());
        addUserQuery.setString(4, user.getFirstName());
        addUserQuery.setString(5, user.getLastName());
        addUserQuery.setString(6, user.getEmail());
        addUserQuery.setString(7, user.getAddress());
        addUserQuery.setInt(8, user.getPhoneNumber());
        addUserQuery.setString(9, user.getStripeCustomerId());

        int affectedRows = addUserQuery.executeUpdate();

        if (affectedRows == 1) {
            System.out.println("User " + user.getUsername() + " was added to the database.");
            this.conn.commit();
        }

    }

    public void addPaymentMethod(User user, String paymentMethodId) throws SQLException {
        PreparedStatement addPaymentMethodQuery = this.conn.prepareStatement(
                "INSERT INTO PAYMENT_METHOD (user_id, stripe_payment_method_id) "
                        + "VALUES (?, ?)"
        );

        addPaymentMethodQuery.setInt(1, user.getUserId());
        addPaymentMethodQuery.setString(2, paymentMethodId);

        int affectedRows = addPaymentMethodQuery.executeUpdate();

        if (affectedRows == 1) {
            System.out.println("Payment method " + paymentMethodId + " was added to the database.");
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
