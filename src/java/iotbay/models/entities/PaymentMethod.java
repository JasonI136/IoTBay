package iotbay.models.entities;

import java.io.Serializable;
import java.sql.ResultSet;

/**
 * Represents a payment method
 * @author cmesina
 */
public class PaymentMethod implements Serializable {
    /**
     * The payment method ID
     * <br>
     * <br>
     * <b>TABLE:</b> PAYMENT_METHOD.id
     */
    private int id;

    /**
     * The user ID
     * <br>
     * <br>
     * <b>TABLE:</b> PAYMENT_METHOD.user_id
     */
    private int userId;

    /**
     * The Stripe payment method ID
     * <br>
     * <br>
     * <b>TABLE:</b> PAYMENT_METHOD.stripe_payment_method_id
     */
    private String stripePaymentMethodId;

    /**
     * The payment method type
     * <br>
     * <br>
     * <b>TABLE:</b> PAYMENT_METHOD.payment_method_type
     */
    private String paymentMethodType;

    /**
     * The last 4 digits of the card
     * <br>
     * <br>
     * <b>TABLE:</b> PAYMENT_METHOD.card_last_4
     */
    private int cardLast4;

    /**
     * Empty constructor
     */
    public PaymentMethod() { }

    /**
     * Constructor for creating a payment method from a ResultSet
     * @param rs The ResultSet to create the payment method from
     * @throws Exception  If the ResultSet is invalid
     */
    public PaymentMethod(ResultSet rs) throws Exception {
        this.id = rs.getInt("id");
        this.userId = rs.getInt("user_id");
        this.stripePaymentMethodId = rs.getString("stripe_payment_method_id");
        this.paymentMethodType = rs.getString("payment_method_type");
        this.cardLast4 = rs.getInt("card_last_4");
    }

    /**
     * Get the payment method ID
     * @return The payment method ID
     */
    public int getId() {
        return id;
    }

    /**
     * Set the payment method ID
     * @param id The payment method ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the Stripe payment method ID
     * @return The Stripe payment method ID
     */
    public String getStripePaymentMethodId() {
        return stripePaymentMethodId;
    }

    /**
     * Set the Stripe payment method ID
     * @param stripePaymentMethodId The Stripe payment method ID
     */
    public void setStripePaymentMethodId(String stripePaymentMethodId) {
        this.stripePaymentMethodId = stripePaymentMethodId;
    }

    /**
     * Get the user ID
     * @return The user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Set the user ID
     * @param userId The user ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Get the payment method type
     * @return The payment method type
     */
    public String getPaymentMethodType() {
        System.out.println("Payment method type: " + paymentMethodType);
        return paymentMethodType;
    }

    /**
     * Set the payment method type
     * @param paymentMethodType The payment method type
     */
    public void setPaymentMethodType(String paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    /**
     * Get the last 4 digits of the card
     * @return The last 4 digits of the card
     */
    public int getCardLast4() {
        return cardLast4;
    }

    /**
     * Set the last 4 digits of the card
     * @param cardLast4 The last 4 digits of the card
     */
    public void setCardLast4(int cardLast4) {
        this.cardLast4 = cardLast4;
    }
}
