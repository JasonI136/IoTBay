/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iotbay.models;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import iotbay.database.DatabaseManager;
import iotbay.exceptions.UserExistsException;
import iotbay.exceptions.UserNotFoundException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author cmesina
 */
public class Users {

    private static final int saltLength = 16;
    private static final int iterations = 10000;
    private final DatabaseManager db;

    public Users(DatabaseManager db) {
        this.db = db;
    }

    public void registerUser(User newUser) throws Exception {
        byte[] salt = this.createSalt();
        byte[] passwordHash = this.encryptPassword(newUser.getPassword(), salt);

        newUser.setPassword(Base64.getEncoder().encodeToString(passwordHash));
        newUser.setPasswordSalt(Base64.getEncoder().encodeToString(salt));

        createStripeCustomer(newUser);

        this.addUser(newUser);
    }

    private static void createStripeCustomer(User newUser) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("email", newUser.getEmail());
        params.put("description", newUser.getFullName());

        Customer stripeCustomer;

        try {
            stripeCustomer = Customer.create(params);
            newUser.setStripeCustomerId(stripeCustomer.getId());
        } catch (StripeException e) {
            throw new Exception("Failed to create Stripe customer: " + e.getMessage());
        }
    }

    public User authenticateUser(String username, String password) throws Exception {
        User user = this.getUser(username);

        byte[] salt = Base64.getDecoder().decode(user.getPasswordSalt());
        byte[] encryptedPassword = encryptPassword(password, salt);

        if (MessageDigest.isEqual(encryptedPassword, Base64.getDecoder().decode(user.getPassword()))) {
            if (user.isStaff()) {
                System.out.println("THIS IS AN ADMIN"); // print to console if user is staff
            }
            return user;
        } else {
            return null;
        }
    }

    /**
     * Retrieves a user from the database.
     *
     * @param username the username of the user to retrieve
     * @return the user as a User object
     * @throws SQLException if there is an error retrieving the user
     * @throws UserNotFoundException if the user does not exist
     */
    public User getUser(String username) throws SQLException, UserNotFoundException {
        PreparedStatement userQuery = this.db.getDbConnection().prepareStatement("SELECT * FROM USER_ACCOUNT WHERE username = ?");

        userQuery.setString(1, username);

        ResultSet rs = userQuery.executeQuery();

        if (!rs.next()) {
            throw new UserNotFoundException("The user with username " + username + " does not exist.");
        }
        PreparedStatement userPaymentMethodsQuery = this.db.getDbConnection().prepareStatement("SELECT * FROM PAYMENT_METHOD WHERE user_id = ?");
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

        User user = new User(this.db);
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

    /**
     * Adds a user to the database.
     *
     * @param user the user to add as a User object
     * @throws SQLException if there is an error adding the user
     * @throws UserExistsException if the user already exists
     */
    private void addUser(User user) throws SQLException, UserExistsException {

        PreparedStatement checkUserQuery = this.db.getDbConnection().prepareStatement(
                "SELECT COUNT(*) FROM USER_ACCOUNT WHERE username = ? OR email = ?"
        );

        checkUserQuery.setString(1, user.getUsername());
        checkUserQuery.setString(2, user.getEmail());

        ResultSet rs = checkUserQuery.executeQuery();

        if (rs.next() && rs.getInt(1) > 0) {
            throw new UserExistsException("User already exists.");
        }

        PreparedStatement addUserQuery = this.db.getDbConnection().prepareStatement(
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
            this.db.getDbConnection().commit();
        }

    }

    private byte[] createSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[saltLength];
        random.nextBytes(salt);
        return salt;
    }

    private byte[] encryptPassword(String plainTextPassword, byte[] salt) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            SecretKey secretKey = keyFactory.generateSecret(new PBEKeySpec(plainTextPassword.toCharArray(), salt, iterations, 256));
            return secretKey.getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Failed to encrypt password: " + e.getMessage());
        }
    }

}
