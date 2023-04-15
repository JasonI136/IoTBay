/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iotbay.models.entities;

import iotbay.database.DatabaseManager;
import lombok.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user account.
 *
 * @author cmesina
 */
@Data
public class User implements Serializable {

    /**
     * The database manager.
     * This is transient because it is not serializable.
     */
    private final transient DatabaseManager db;

    private static final Logger logger = LogManager.getLogger(User.class);

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

    private Timestamp registrationDate;

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
        this.registrationDate = rs.getTimestamp("registration_date");
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
     * Adds a payment method associated with the user.
     *
     * @param paymentMethod The payment method to add.
     * @throws SQLException if there is an error adding the payment method
     */
    public void addPaymentMethod(PaymentMethod paymentMethod) throws Exception {
        this.paymentMethods.add(this.db.getPaymentMethods().addPaymentMethod(paymentMethod, this));
    }



    /**
     * Deletes a payment method associated with the user.
     *
     * @param paymentMethod The payment method to delete.
     * @throws SQLException if there is an error deleting the payment method
     */
    public void deletePaymentMethod(PaymentMethod paymentMethod) throws Exception {
        this.db.getPaymentMethods().deletePaymentMethod(paymentMethod);
    }

    /**
     * Gets a payment method associated with the user.
     *
     * @param paymentMethodId The id of the payment method to get.
     * @return The payment method as a PaymentMethod object.
     * @throws Exception if there is an error getting the payment method
     */
    public PaymentMethod getPaymentMethod(int paymentMethodId) throws Exception {
        return this.db.getPaymentMethods().getPaymentMethod(paymentMethodId);
    }

    public List<Order> getOrders() throws Exception {
       return this.db.getOrders().getOrders(this.id);
    }

}
