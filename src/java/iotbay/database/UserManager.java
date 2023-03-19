/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iotbay.database;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import iotbay.exceptions.UserExistsException;
import iotbay.exceptions.UserNotFoundException;
import iotbay.models.User;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cmesina
 */
public class UserManager {
    private int saltLength = 16;
    private int iterations = 10000;
    private DatabaseManager db;

    public UserManager(DatabaseManager db, int saltLength, int iterations) {
        this.saltLength = saltLength;
        this.iterations = iterations;
        this.db = db;
    }

    public User registerUser(User newUser) throws Exception, UserExistsException {
        byte[] salt = this.createSalt();
        byte[] passwordHash = this.encryptPassword(newUser.getPassword(), salt);

        newUser.setPassword(Base64.getEncoder().encodeToString(passwordHash));
        newUser.setPasswordSalt(Base64.getEncoder().encodeToString(salt));

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("email", newUser.getEmail());
        params.put("description", newUser.getFullName());

        Customer stripeCustomer = null;

        try {
            stripeCustomer = Customer.create(params);
            newUser.setStripeCustomerId(stripeCustomer.getId());
        } catch (StripeException e) {
            throw new Exception("Failed to create Stripe customer: " + e.getMessage());
        }


        this.db.addUser(newUser);
        return newUser;
    }

    public User authenticateUser(String username, String password) throws UserNotFoundException, Exception {
        User user = this.db.getUser(username);

        byte[] salt = Base64.getDecoder().decode(user.getPasswordSalt());
        byte[] encryptedPassword = encryptPassword(password, salt);

        if (MessageDigest.isEqual(encryptedPassword, Base64.getDecoder().decode(user.getPassword()))) {
            return user;
        } else {
            return null;
        }
    }

    public User getUser(String username) throws UserNotFoundException, Exception {
        return this.db.getUser(username);
    }

    private byte[] createSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[this.saltLength];
        random.nextBytes(salt);
        return salt;
    }

    private byte[] encryptPassword(String plainTextPassword, byte[] salt) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            SecretKey secretKey = keyFactory.generateSecret(new PBEKeySpec(plainTextPassword.toCharArray(), salt, this.iterations, 256));
            return secretKey.getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Failed to encrypt password: " + e.getMessage());
        }
    }

}
