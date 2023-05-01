/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iotbay.database.collections;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import iotbay.database.DatabaseManager;
import iotbay.exceptions.UserExistsException;
import iotbay.exceptions.UserNotFoundException;
import iotbay.models.PaymentMethod;
import iotbay.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Represents a collection of users
 *
 * @author cmesina
 */
public class Users implements ModelDAO<User> {

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

    private static final Logger logger = LogManager.getLogger(DatabaseManager.class);

    /**
     * Initalizes the users collection with the database manager
     *
     * @param db
     */
    public Users(DatabaseManager db) {
        this.db = db;
    }

    /**
     * Gets the number of users in the database.
     *
     * @return the number of users in the database
     * @throws SQLException if there is an error getting the user count
     */
    public int count() throws SQLException {
        try (Connection conn = db.getDbConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM USER_ACCOUNT");
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    public int count(String searchTerm) throws SQLException {
        try (Connection conn = db.getDbConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM USER_ACCOUNT WHERE LOWER(USERNAME) LIKE ?");
            stmt.setString(1, "%" + searchTerm.toLowerCase() + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    /**
     * Registers a new user.
     *
     * @param newUser the user to register
     * @throws SQLException             if there is an error registering the user
     * @throws UserExistsException      if the user already exists
     * @throws NoSuchAlgorithmException if the password encryption algorithm is not found
     * @throws InvalidKeySpecException  if the password encryption key is invalid
     */
    public void registerUser(User newUser) throws SQLException, UserExistsException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = this.createSalt();
        byte[] passwordHash = this.encryptPassword(newUser.getPassword(), salt);

        newUser.setPassword(Base64.getEncoder().encodeToString(passwordHash));
        newUser.setPasswordSalt(Base64.getEncoder().encodeToString(salt));

        if (this.checkUserExists(newUser)) {
            throw new UserExistsException("User already exists");
        }

        createStripeCustomer(newUser);

        this.addUser(newUser);
    }

    /**
     * For JUnit testing only. Skips the creation of a Stripe customer.
     *
     * @param newUser the user to register
     * @throws SQLException             if there is an error registering the user
     * @throws UserExistsException      if the user already exists
     * @throws NoSuchAlgorithmException if the password encryption algorithm is not found
     * @throws InvalidKeySpecException  if the password encryption key is invalid
     * @deprecated - For JUnit testing only. Skips the creation of a Stripe customer.
     */
    public void registerUserTest(User newUser) throws SQLException, UserExistsException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = this.createSalt();
        byte[] passwordHash = this.encryptPassword(newUser.getPassword(), salt);

        newUser.setPassword(Base64.getEncoder().encodeToString(passwordHash));
        newUser.setPasswordSalt(Base64.getEncoder().encodeToString(salt));

        if (this.checkUserExists(newUser)) {
            throw new UserExistsException("User already exists");
        }

        this.addUser(newUser);
    }

    /**
     * Creates a customer in Stripe and sets the strip customer ID in the user object.
     *
     * @param newUser the user to create a Stripe customer for
     * @throws SQLException if there is an error creating the Stripe customer
     */
    private static void createStripeCustomer(User newUser) throws SQLException {
        Map<String, Object> params = new HashMap<>();
        params.put("email", newUser.getEmail());
        params.put("description", newUser.getFullName());

        Customer stripeCustomer;

        try {
            stripeCustomer = Customer.create(params);
            newUser.setStripeCustomerId(stripeCustomer.getId());
        } catch (StripeException e) {
            throw new SQLException("Failed to create Stripe customer: " + e.getMessage());
        }
    }

    /**
     * Authenticates a user.
     *
     * @param username the username of the user to authenticate
     * @param password the encrypted password of the user to authenticate
     * @return the user if authentication is successful, null otherwise
     * @throws SQLException             if there is an error authenticating the user
     * @throws UserNotFoundException    if the user does not exist
     * @throws NoSuchAlgorithmException if the password encryption algorithm is not found
     * @throws InvalidKeySpecException  if the password encryption key is invalid
     */
    public User authenticateUser(String username, String password) throws SQLException, UserNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
        User user = this.getUser(username);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

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
     */
    public User getUser(String username) throws SQLException {

        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement userQueryStmt = conn.prepareStatement(
                    "SELECT * FROM USER_ACCOUNT WHERE username = ?"
            )) {
                userQueryStmt.setString(1, username);

                try (ResultSet userRs = userQueryStmt.executeQuery()) {
                    if (!userRs.next()) {
                        return null;
                    }

                    try (PreparedStatement userPaymentMethodsQueryStmt = conn.prepareStatement(
                            "SELECT * FROM PAYMENT_METHOD WHERE user_id = ?"
                    )) {
                        userPaymentMethodsQueryStmt.setInt(1, userRs.getInt("id"));

                        try (ResultSet paymentMethodsRs = userPaymentMethodsQueryStmt.executeQuery()) {
                            List<PaymentMethod> paymentMethods = new ArrayList<>();

                            while (paymentMethodsRs.next()) {
                                paymentMethods.add(new PaymentMethod(paymentMethodsRs));
                            }

                            User user = new User(this.db, userRs);
                            user.setPaymentMethods(paymentMethods);

                            return user;
                        }
                    }
                }


            }
        }
    }

    /**
     * Retrieves a user from the database.
     *
     * @param id the id of the user to retrieve
     * @return the user as a User object
     * @throws SQLException if there is an error retrieving the user
     */
    public User getUser(int id) throws SQLException {

        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement userQueryStmt = conn.prepareStatement(
                    "SELECT * FROM USER_ACCOUNT WHERE id = ?"
            )) {
                userQueryStmt.setInt(1, id);

                try (ResultSet userRs = userQueryStmt.executeQuery()) {
                    if (!userRs.next()) {
                        return null;
                    }

                    try (PreparedStatement userPaymentMethodsQueryStmt = conn.prepareStatement(
                            "SELECT * FROM PAYMENT_METHOD WHERE user_id = ?"
                    )) {
                        userPaymentMethodsQueryStmt.setInt(1, userRs.getInt("id"));

                        try (ResultSet paymentMethodsRs = userPaymentMethodsQueryStmt.executeQuery()) {
                            List<PaymentMethod> paymentMethods = new ArrayList<>();

                            while (paymentMethodsRs.next()) {
                                paymentMethods.add(new PaymentMethod(paymentMethodsRs));
                            }

                            User user = new User(this.db, userRs);
                            user.setPaymentMethods(paymentMethods);

                            return user;
                        }
                    }
                }


            }
        }
    }

    /**
     * Checks if a user exists in the database.
     *
     * @param user the user to check
     * @return true if the user exists, false otherwise
     * @throws SQLException if there is an error checking if the user exists
     */
    private boolean checkUserExists(User user) throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement checkUserQuery = conn.prepareStatement(
                    "SELECT COUNT(*) FROM USER_ACCOUNT WHERE username = ? OR email = ?"
            )) {
                checkUserQuery.setString(1, user.getUsername());
                checkUserQuery.setString(2, user.getEmail());

                try (ResultSet checkUserRs = checkUserQuery.executeQuery()) {
                    checkUserRs.next();

                    return checkUserRs.getInt(1) > 0;
                }
            }
        }
    }

    /**
     * Checks if a user exists in the database by username.
     * @param username the username to check
     * @return true if the user exists, false otherwise
     * @throws SQLException
     */
    private boolean checkUserExistsByUsername(String username) throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement checkUserQuery = conn.prepareStatement(
                    "SELECT COUNT(*) FROM USER_ACCOUNT WHERE username = ?"
            )) {
                checkUserQuery.setString(1, username);

                try (ResultSet checkUserRs = checkUserQuery.executeQuery()) {
                    checkUserRs.next();

                    return checkUserRs.getInt(1) > 0;
                }
            }
        }
    }

    /**
     * Checks if a user exists in the database by email.
     * @param email the email to check
     * @return true if the user exists, false otherwise
     * @throws SQLException
     */
    private boolean checkUserExistsByEmail(String email) throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement checkUserQuery = conn.prepareStatement(
                    "SELECT COUNT(*) FROM USER_ACCOUNT WHERE email = ?"
            )) {
                checkUserQuery.setString(1, email);

                try (ResultSet checkUserRs = checkUserQuery.executeQuery()) {
                    checkUserRs.next();

                    return checkUserRs.getInt(1) > 0;
                }
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

        try (Connection conn = this.db.getDbConnection()) {

            if (this.checkUserExists(user)) {
                throw new UserExistsException("The user with username " + user.getUsername() + " already exists.");
            }

            try (PreparedStatement addUserQuery = conn.prepareStatement(
                    "INSERT INTO USER_ACCOUNT (username, password, password_salt, first_name, last_name, email, address, phone_number, stripe_customer_id, is_staff, registration_date) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            )) {
                addUserQuery.setString(1, user.getUsername());
                addUserQuery.setString(2, user.getPassword());
                addUserQuery.setString(3, user.getPasswordSalt());
                addUserQuery.setString(4, user.getFirstName());
                addUserQuery.setString(5, user.getLastName());
                addUserQuery.setString(6, user.getEmail());
                addUserQuery.setString(7, user.getAddress());
                addUserQuery.setInt(8, user.getPhoneNumber());
                addUserQuery.setString(9, user.getStripeCustomerId());
                addUserQuery.setBoolean(10, user.isStaff());
                addUserQuery.setTimestamp(11, user.getRegistrationDate());


                int affectedRows = addUserQuery.executeUpdate();
                if (affectedRows == 1) {
                    logger.info("User " + user.getUsername() + " added to database.");
                } else {
                    throw new SQLException("Failed to add user to database.");
                }
            }
        }


    }

    public void updateUser(User user) throws SQLException, UserExistsException, NoSuchAlgorithmException, InvalidKeySpecException {

        // check if the username has changed
        User oldUser = this.getUser(user.getId());
        if (!oldUser.getUsername().equals(user.getUsername())) {
            // check if the new username is already taken
            if (this.checkUserExistsByUsername(user.getUsername())) {
                throw new UserExistsException("The username " + user.getUsername() + " already exists.");
            }
        }

        // check if the email has changed
        if (!oldUser.getEmail().equals(user.getEmail())) {
            // check if the new email is already taken
            if (this.checkUserExistsByEmail(user.getEmail())) {
                throw new UserExistsException("The email " + user.getEmail() + " already exists.");
            }
        }

        // check if the password has changed, if so hash it
        if (!oldUser.getPassword().equals(user.getPassword())) {
            byte[] salt = this.createSalt();
            byte[] passwordHash = this.encryptPassword(user.getPassword(), salt);

            user.setPassword(Base64.getEncoder().encodeToString(passwordHash));
            user.setPasswordSalt(Base64.getEncoder().encodeToString(salt));
        }

        try (Connection conn = this.db.getDbConnection()) {

            try (PreparedStatement updateUserQuery = conn.prepareStatement(
                    "UPDATE USER_ACCOUNT SET username = ?, first_name = ?, last_name = ?, email = ?, address = ?, phone_number = ?, password = ?, password_salt = ?, is_staff = ? "
                            + "WHERE id = ?"
            )) {
                updateUserQuery.setString(1, user.getUsername());
                updateUserQuery.setString(2, user.getFirstName());
                updateUserQuery.setString(3, user.getLastName());
                updateUserQuery.setString(4, user.getEmail());
                updateUserQuery.setString(5, user.getAddress());
                updateUserQuery.setInt(6, user.getPhoneNumber());
                updateUserQuery.setString(7, user.getPassword());
                updateUserQuery.setString(8, user.getPasswordSalt());
                updateUserQuery.setBoolean(9, user.isStaff());
                updateUserQuery.setInt(10, user.getId());



                int affectedRows = updateUserQuery.executeUpdate();
                if (affectedRows == 1) {
                    logger.info("User " + user.getUsername() + " updated in the database.");
                } else {
                    throw new SQLException("Failed to update user in the database.");
                }
            }
        }
    }

    public void deleteUser(int userId) throws SQLException {
        String query = "DELETE FROM USER_ACCOUNT WHERE id = ?";

        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, userId);

                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 1) {
                    logger.info("User with id " + userId + " deleted from the database.");
                } else {
                    throw new SQLException("Failed to delete user from the database.");
                }
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
     * @return the encrypted password as a byte array.
     * @throws NoSuchAlgorithmException if the algorithm is not found
     * @throws InvalidKeySpecException  if the key is invalid
     */
    private byte[] encryptPassword(String plainTextPassword, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        SecretKey secretKey = keyFactory.generateSecret(new PBEKeySpec(plainTextPassword.toCharArray(), salt, iterations, 256));
        return secretKey.getEncoded();

    }

    public List<User> get(int offset, int limit) throws SQLException {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * "
                + "FROM USER_ACCOUNT "
                + "OFFSET ? ROWS "
                + "FETCH NEXT ? ROWS ONLY";


        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, offset);
                stmt.setInt(2, limit);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        userList.add(new User(this.db, rs));
                    }
                }
            }
        }

        return userList;
    }

    public List<User> get(int offset, int limit, String searchTerm) throws SQLException {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * "
                + "FROM USER_ACCOUNT "
                + "WHERE LOWER(username) LIKE ? "
                + "OFFSET ? ROWS "
                + "FETCH NEXT ? ROWS ONLY";

        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, "%" + searchTerm.toLowerCase() + "%");
                stmt.setInt(2, offset);
                stmt.setInt(3, limit);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        userList.add(new User(this.db, rs));
                    }
                }
            }
        }

        return userList;
    }
}