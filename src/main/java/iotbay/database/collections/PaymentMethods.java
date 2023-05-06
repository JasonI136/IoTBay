package iotbay.database.collections;

import iotbay.database.DatabaseManager;
import iotbay.models.PaymentMethod;
import iotbay.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * Represents a collection of payment methods
 */
public class PaymentMethods {

    /**
     * An instance of the database manager
     */
    DatabaseManager db;

    /**
     * Initializes the payment methods collection with the database manager
     *
     * @param db
     */
    public PaymentMethods(DatabaseManager db) {
        this.db = db;
    }

    /**
     * The logger for this class
     */
    private static final Logger logger = LogManager.getLogger(PaymentMethods.class);

    /**
     * Gets a payment method by the stripe payment method id.
     *
     * @param stripePaymentMethodId the stripe payment method id
     * @return a {@link iotbay.models.PaymentMethod} object, or null if not found.
     * @throws SQLException if there is an error retrieving the payment method
     */
    public PaymentMethod getPaymentMethod(String stripePaymentMethodId) throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PAYMENT_METHOD WHERE stripe_payment_method_id = ?")) {
                stmt.setString(1, stripePaymentMethodId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new PaymentMethod(rs);
                    }
                }
            }
        }

        return null;
    }

    /**
     * Gets a payment method by the id.
     *
     * @param id the id
     * @return a {@link iotbay.models.PaymentMethod} object, or null if not found.
     * @throws SQLException if there is an error retrieving the payment method
     */
    public PaymentMethod getPaymentMethod(int id) throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PAYMENT_METHOD WHERE id = ?")) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return new PaymentMethod(rs);
                }
            }
        }

        return null;
    }

    /**
     * Adds a payment method to the database.
     *
     * @param paymentMethod the payment method to add
     * @param user          the user to add the payment method to
     * @return the payment method that was added
     * @throws SQLException if there is an error adding the payment method
     */
    public PaymentMethod addPaymentMethod(PaymentMethod paymentMethod, User user) throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO PAYMENT_METHOD (stripe_payment_method_id, user_id, PAYMENT_METHOD_TYPE, CARD_LAST_4) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, paymentMethod.getStripePaymentMethodId());
                stmt.setInt(2, user.getId());
                stmt.setString(3, paymentMethod.getPaymentMethodType());
                stmt.setInt(4, paymentMethod.getCardLast4());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 1) {
                    logger.info("Payment method " + paymentMethod.getStripePaymentMethodId() + " was added to the database.");
                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            paymentMethod.setId(rs.getInt(1));
                            return paymentMethod;
                        } else {
                            logger.error("Payment method " + paymentMethod.getStripePaymentMethodId() + " was not added to the database.");
                            throw new SQLException("Payment method " + paymentMethod.getStripePaymentMethodId() + " was not added to the database.");
                        }
                    }
                } else {
                    logger.error("Payment method " + paymentMethod.getStripePaymentMethodId() + " was not added to the database.");
                    throw new SQLException("Payment method " + paymentMethod.getStripePaymentMethodId() + " was not added to the database.");
                }
            }
        }
    }

    /**
     * Deletes a payment method from the database.
     *
     * @param paymentMethod the payment method to delete
     * @throws SQLException if there is an error deleting the payment method
     */
    public void deletePaymentMethod(PaymentMethod paymentMethod) throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM PAYMENT_METHOD WHERE id = ?")) {
                stmt.setInt(1, paymentMethod.getId());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 1) {
                    logger.info("Payment method " + paymentMethod.getId() + " was deleted from the database.");
                } else {
                    logger.error("Payment method " + paymentMethod.getId() + " was not deleted from the database.");
                    throw new SQLException("Payment method " + paymentMethod.getId() + " was not deleted from the database.");
                }
            }
        }
    }

    public void updatePaymentMethod(PaymentMethod paymentMethod) throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("UPDATE PAYMENT_METHOD SET stripe_payment_method_id = ?, PAYMENT_METHOD_TYPE = ?, CARD_LAST_4 = ?, USER_ID = ?, DELETED = ? WHERE id = ?")) {
                stmt.setString(1, paymentMethod.getStripePaymentMethodId());
                stmt.setString(2, paymentMethod.getPaymentMethodType());
                stmt.setInt(3, paymentMethod.getCardLast4());
                stmt.setInt(4, paymentMethod.getUserId());
                stmt.setBoolean(5, paymentMethod.isDeleted());
                stmt.setInt(6, paymentMethod.getId());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 1) {
                    logger.info("Payment method " + paymentMethod.getId() + " was updated in the database.");
                } else {
                    logger.error("Payment method " + paymentMethod.getId() + " was not updated in the database.");
                    throw new SQLException("Payment method " + paymentMethod.getId() + " was not updated in the database.");
                }
            }
        }
    }
}
