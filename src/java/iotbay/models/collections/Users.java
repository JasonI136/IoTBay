/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iotbay.models.collections;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import iotbay.database.DatabaseManager;
import iotbay.exceptions.UserExistsException;
import iotbay.exceptions.UserNotFoundException;
import iotbay.models.entities.PaymentMethod;
import iotbay.models.entities.User;

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
 * Represents a collection of users
 *
 * @author cmesina
 */
public class Users {

    /**
     * The length of the salt
     */
    private static final int saltLength = 16;

    /**
     * The number of iterations for the password encryption
     */
    private static final int iterations = 10000;

    /**
     * An instance of the database manager
     */
    private final DatabaseManager db;

    /**
     * Initalizes the users collection with the database manager
     *
     * @param db
     */
    public Users(DatabaseManager db) {
        this.db = db;
    }

    /**
     * Registers a new user with an encrypted password and salt and adds it to the database.
     *
     * @param newUser the new user to register
     * @throws Exception if there is an error registering the user
     */
    public void registerUser(User newUser) throws Exception {
        byte[] salt = this.createSalt();
        byte[] passwordHash = this.encryptPassword(newUser.getPassword(), salt);

        newUser.setPassword(Base64.getEncoder().encodeToString(passwordHash));
        newUser.setPasswordSalt(Base64.getEncoder().encodeToString(salt));


        createStripeCustomer(newUser);


        this.addUser(newUser);
    }

    /**
     * Creates a customer in Stripe and sets the strip customer ID in the user object.
     *
     * @param newUser the user to create a Stripe customer for
     * @throws Exception if there is an error creating the Stripe customer
     */
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

    /**
     * Authenticates a user by comparing the password hash and salt in the database with the password hash and salt of the user.
     *
     * @param username the username of the user to authenticate
     * @param password the hashed password of the user to authenticate
     * @return the user if the authentication is successful, null otherwise
     * @throws Exception if there is an error authenticating the user
     */
    public User authenticateUser(String username, String password) throws Exception {
        User user = this.getUser(username);

        byte[] salt = Base64.getDecoder().decode(user.getPasswordSalt());
        byte[] encryptedPassword = encryptPassword(password, salt);

        if (MessageDigest.isEqual(encryptedPassword, Base64.getDecoder().decode(user.getPassword()))) {
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
     * @throws SQLException          if there is an error retrieving the user
     * @throws UserNotFoundException if the user does not exist
     */
    public User getUser(String username) throws Exception {

        ResultSet userRs;
        try (PreparedStatement userQuery = this.db.prepareStatement(
                "SELECT * FROM USER_ACCOUNT WHERE username = ?",
                username
        )) {
            userRs = userQuery.executeQuery();

            if (!userRs.next()) {
                throw new UserNotFoundException("The user with username " + username + " does not exist.");
            }

            List<PaymentMethod> paymentMethods;
            ResultSet paymentMethodsRs;
            try (PreparedStatement userPaymentMethodsQuery = this.db.prepareStatement(
                    "SELECT * FROM PAYMENT_METHOD WHERE user_id = ?",
                    userRs.getInt("id")
            )) {
                paymentMethods = new ArrayList<>();
                paymentMethodsRs = userPaymentMethodsQuery.executeQuery();

                while (paymentMethodsRs.next()) {
                    PaymentMethod paymentMethod = new PaymentMethod(paymentMethodsRs);
                    paymentMethods.add(paymentMethod);
                }


                User user = new User(this.db, userRs);
                user.setPaymentMethods(paymentMethods);

                return user;
            }

        }


    }

    /**
     * Adds a user to the database.
     *
     * @param user the user to add as a User object
     * @throws SQLException        if there is an error adding the user
     * @throws UserExistsException if the user already exists
     */
    private void addUser(User user) throws SQLException, UserExistsException {


        ResultSet rs;
        try (PreparedStatement checkUserQuery = this.db.prepareStatement(
                "SELECT COUNT(*) FROM USER_ACCOUNT WHERE username = ? OR email = ?",
                user.getUsername(),
                user.getEmail()
        )) {
            rs = checkUserQuery.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new UserExistsException("User already exists.");
            }
        }

        int affectedRows;
        try (PreparedStatement addUserQuery = this.db.prepareStatement(
                "INSERT INTO USER_ACCOUNT (username, password, password_salt, first_name, last_name, email, address, phone_number, stripe_customer_id) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                user.getUsername(),
                user.getPassword(),
                user.getPasswordSalt(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAddress(),
                user.getPhoneNumber(),
                user.getStripeCustomerId()
        )) {

            affectedRows = addUserQuery.executeUpdate();
            if (affectedRows == 1) {
                System.out.println("User " + user.getUsername() + " was added to the database.");
                this.db.getDbConnection().commit();
            }
        }


    }

    /**
     * Generates a random salt for the password encryption.
     *
     * @return the salt as a byte array
     */
    private byte[] createSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[saltLength];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Encrypts a password with a salt.
     *
     * @param plainTextPassword the password to encrypt
     * @param salt              the salt to use for encryption
     * @return the encrypted password as a byte array
     */
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
