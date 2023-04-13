package iotbay.models.entities;

import lombok.*;

import java.io.Serializable;
import java.sql.ResultSet;

/**
 * Represents a payment method
 * @author cmesina
 */
@Data
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

}
