/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iotbay.models.entities;

import iotbay.database.DatabaseManager;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user account.
 *
 * @author cmesina
 */
public class User implements Serializable {

    /**
     * The database manager.
     * This is transient because it is not serializable.
     */
    private final transient DatabaseManager db;

    /**
     * The user's unique id.
     * <br>
     * <br>
     * <b>TABLE:</b> USER_ACCOUNT.id
     */
    private int id;

    /**
     * The user's stripe customer id.
     * <br>
     * <br>
     * <b>TABLE:</b> USER_ACCOUNT.stripe_customer_id
     */
    private String stripeCustomerId;

    /**
     * The user's username.
     * <br>
     * <br>
     * <b>TABLE:</b> USER_ACCOUNT.usernameq
     */
    private String username;

    /**
     * The user's password in salted hash form.
     * <br>
     * <br>
     * <b>TABLE:</b> USER_ACCOUNT.password
     */
    private String password;

    /**
     * The user's password salt.
     * <br>
     * <br>
     * <b>TABLE:</b> USER_ACCOUNT.password_salt
     */
    private String passwordSalt;

    /**
     * The user's first name.
     * <br>
     * <br>
     * <b>TABLE:</b> USER_ACCOUNT.first_name
     */
    private String firstName;

    /**
     * The user's last name.
     * <br>
     * <br>
     * <b>TABLE:</b> USER_ACCOUNT.last_name
     */
    private String lastName;

    /**
     * The user's email address.
     * <br>
     * <br>
     * <b>TABLE:</b> USER_ACCOUNT.email
     */
    private String email;

    /**
     * The user's address.
     * <br>
     * <br>
     * <b>TABLE:</b> USER_ACCOUNT.address
     */
    private String address;

    /**
     * The user's phone number.
     * <br>
     * <br>
     * <b>TABLE:</b> USER_ACCOUNT.phone_number
     */
    private int phoneNumber;

    /**
     * Whether the user is a staff member.
     * <br>
     * <br>
     * <b>TABLE:</b> USER_ACCOUNT.is_staff
     */
    private boolean isStaff;

    /**
     * The user's payment methods.
     * <br>
     * <br>
     * <b>TABLE:</b> PAYMENT_METHOD
     */
    private List<PaymentMethod> paymentMethods;

    /**
     * User
     * <br>
     * A model class representing a user.
     *
     * @param db An instance of the database manager.
     */
    public User(DatabaseManager db) {
        this.db = db;
        this.paymentMethods = new ArrayList<>();
    }

    /**
     * <b>User</b>
     * <br>
     * A model class representing a user.
     *
     * @param db An instance of the database manager.
     * @param rs A result set containing the user's data.
     * @throws Exception If the result set is invalid.
     */
    public User(DatabaseManager db, ResultSet rs) throws Exception {
        this.db = db;
        this.paymentMethods = new ArrayList<>();
        this.id = rs.getInt("id");
        this.username = rs.getString("username");
        this.password = rs.getString("password");
        this.passwordSalt = rs.getString("password_salt");
        this.firstName = rs.getString("first_name");
        this.lastName = rs.getString("last_name");
        this.email = rs.getString("email");
        this.address = rs.getString("address");
        this.phoneNumber = rs.getInt("phone_number");
        this.isStaff = rs.getBoolean("is_staff");
        this.stripeCustomerId = rs.getString("stripe_customer_id");
    }

    /**
     * Gets the user's full name.
     *
     * @return The user's full name.
     */
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    /**
     * Gets the user's id.
     *
     * @return The user's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the user's id
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the user's username.
     *
     * @return The user's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's username.
     *
     * @param username The user's username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the user's password.
     *
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password The user's password in salted hash form.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's password salt.
     *
     * @return The user's password salt.
     */
    public String getPasswordSalt() {
        return passwordSalt;
    }

    /**
     * Sets the password salt.
     *
     * @param passwordSalt The user's password salt.
     */
    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    /**
     * Gets the user's first name.
     *
     * @return The user's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the user's first name.
     *
     * @param firstName The user's first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the user's last name.
     *
     * @return The user's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the user's last name.
     *
     * @param lastName The user's last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the user's email address.
     *
     * @return The user's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email The user's email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's address.
     *
     * @return The user's address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the user's address.
     *
     * @param address The user's address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the user's phone number.
     *
     * @return The user's phone number.
     */
    public int getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the user's phone number.
     *
     * @param phoneNumber The user's phone number.
     */
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets whether the user is a staff member.
     *
     * @return Whether the user is a staff member.
     */
    public boolean getIsStaff() {
        return isStaff;
    }

    /**
     * Sets whether the user is a staff member.
     *
     * @param isStaff Whether the user is a staff member.
     */
    public void setIsStaff(boolean isStaff) {
        this.isStaff = isStaff;
    }

    /**
     * Gets the user's stripe customer id.
     *
     * @return The user's stripe customer id.
     */
    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    /**
     * Sets the user's payment methods.
     *
     * @param paymentMethods The user's payment methods.
     */
    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    /**
     * Gets the user's stripe customer id.
     *
     * @return The user's stripe customer id.
     */
    public String getStripeCustomerId() {
        return stripeCustomerId;
    }

    /**
     * Sets the user's stripe customer id.
     *
     * @param stripeCustomerId The user's stripe customer id.
     */
    public void setStripeCustomerId(String stripeCustomerId) {
        this.stripeCustomerId = stripeCustomerId;
    }

    /**
     * Adds a payment method associated with the user.
     *
     * @param paymentMethod The payment method to add.
     * @throws SQLException if there is an error adding the payment method
     */
    public void addPaymentMethod(PaymentMethod paymentMethod) throws SQLException {
        int affectedRows;
        try (PreparedStatement statement = this.db.prepareStatement(
                "INSERT INTO PAYMENT_METHOD (stripe_payment_method_id, user_id, PAYMENT_METHOD_TYPE, CARD_LAST_4) VALUES (?, ?, ?, ?)",
                paymentMethod.getStripePaymentMethodId(),
                this.id,
                paymentMethod.getPaymentMethodType(),
                paymentMethod.getCardLast4()
        )) {
            affectedRows = statement.executeUpdate();
            if (affectedRows == 1) {
                System.out.println("Payment method " + paymentMethod.getStripePaymentMethodId() + " was added to the database.");
                this.paymentMethods.add(paymentMethod);
                this.db.getDbConnection().commit();
            }
        }


    }

    /**
     * Deletes a payment method associated with the user.
     *
     * @param paymentMethod The payment method to delete.
     * @throws SQLException if there is an error deleting the payment method
     */
    public void deletePaymentMethod(PaymentMethod paymentMethod) throws SQLException {
        int affectedRows;
        try (PreparedStatement statement = this.db.prepareStatement(
                "DELETE FROM PAYMENT_METHOD WHERE id = ?",
                paymentMethod.getId()
        )) {
            affectedRows = statement.executeUpdate();

            if (affectedRows == 1) {
                System.out.println("Payment method " + paymentMethod.getId() + " was deleted from the database.");
                this.paymentMethods.remove(paymentMethod);
                this.db.getDbConnection().commit();
            }
        }


    }

    /**
     * Gets a payment method associated with the user.
     *
     * @param paymentMethodId The id of the payment method to get.
     * @return The payment method as a PaymentMethod object.
     * @throws Exception if there is an error getting the payment method
     */
    public PaymentMethod getPaymentMethod(int paymentMethodId) throws Exception {
        ResultSet rs;
        try (PreparedStatement statement = this.db.prepareStatement(
                "SELECT * FROM PAYMENT_METHOD WHERE id = ?",
                paymentMethodId
        )) {
            rs = statement.executeQuery();

            if (rs.next()) {
                return new PaymentMethod(rs);
            }
        }

        return null;
    }

    /**
     * Gets a payment method associated with the user by its stripe id.
     * @param stripePaymentMethodId The stripe id of the payment method to get.
     * @return The payment method as a PaymentMethod object.
     * @throws Exception if there is an error getting the payment method
     */
    public PaymentMethod getPaymentMethodByStripeId(String stripePaymentMethodId) throws Exception {

        try (PreparedStatement statement = this.db.prepareStatement(
                "SELECT * FROM PAYMENT_METHOD WHERE STRIPE_PAYMENT_METHOD_ID = ?",
                stripePaymentMethodId
        )) {
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new PaymentMethod(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Order> getOrders() throws Exception {
        ArrayList<Order> orders = new ArrayList<>();
        try (PreparedStatement statement = this.db.prepareStatement(
                "SELECT * FROM CUSTOMER_ORDER WHERE user_id = ?",
                this.id
        )) {
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                orders.add(new Order(rs));
            }
        }

        return orders;
    }

}
