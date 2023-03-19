/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iotbay.models;

import iotbay.database.DatabaseManager;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cmesina
 */
public class User implements Serializable {

    // The database manager for the user. This is transient because it is not serializable.
    private final transient DatabaseManager db;

    private int userId;

    private String stripeCustomerId;
    private String username;
    private String password;
    private String passwordSalt;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private int phoneNumber;
    private boolean isStaff;
    private List<PaymentMethod> paymentMethods;
    
    public User(DatabaseManager db) {
        this.db = db;
        this.paymentMethods = new ArrayList<>();
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isIsStaff() {
        return isStaff;
    }

    public void setIsStaff(boolean isStaff) {
        this.isStaff = isStaff;
    }


    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public String getStripeCustomerId() {
        return stripeCustomerId;
    }

    public void setStripeCustomerId(String stripeCustomerId) {
        this.stripeCustomerId = stripeCustomerId;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public void setStaff(boolean staff) {
        isStaff = staff;
    }


    /**
     * Adds a payment method associated with the user.
     * @param paymentMethodId the id of the payment method to add.
     * @throws SQLException if there is an error adding the payment method
     */
    public void addPaymentMethod(String paymentMethodId) throws SQLException {
        PreparedStatement addPaymentMethodQuery = this.db.getDbConnection().prepareStatement(
                "INSERT INTO PAYMENT_METHOD (user_id, stripe_payment_method_id) "
                        + "VALUES (?, ?)"
        );

        addPaymentMethodQuery.setInt(1, this.getUserId());
        addPaymentMethodQuery.setString(2, paymentMethodId);

        int affectedRows = addPaymentMethodQuery.executeUpdate();

        if (affectedRows == 1) {
            System.out.println("Payment method " + paymentMethodId + " was added to the database.");
            this.db.getDbConnection().commit();
        }
    }
}
