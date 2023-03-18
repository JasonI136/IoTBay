/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iotbay.database;

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
import java.util.Base64;

/**
 *
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
