package iotbay.models.collections;

import iotbay.database.DatabaseManager;
import iotbay.models.entities.PaymentMethod;
import iotbay.models.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class PaymentMethods {
    DatabaseManager db;

    public PaymentMethods(DatabaseManager db) {
        this.db = db;
    }

    private static final Logger logger = LogManager.getLogger(PaymentMethods.class);

    public PaymentMethod getPaymentMethod(String stripePaymentMethodId) throws Exception {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PAYMENT_METHOD WHERE stripe_payment_method_id = ?")) {
                stmt.setString(1, stripePaymentMethodId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new PaymentMethod(rs);
                    } else {
                        return null;
                    }
                }
            }
        }
    }

    public PaymentMethod getPaymentMethod(int id) throws Exception {
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

    public PaymentMethod addPaymentMethod(PaymentMethod paymentMethod, User user) throws Exception {
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
                            throw new Exception("Payment method " + paymentMethod.getStripePaymentMethodId() + " was not added to the database.");
                        }
                    }
                } else {
                    logger.error("Payment method " + paymentMethod.getStripePaymentMethodId() + " was not added to the database.");
                    throw new Exception("Payment method " + paymentMethod.getStripePaymentMethodId() + " was not added to the database.");
                }
            }
        }
    }

    public void deletePaymentMethod(PaymentMethod paymentMethod) throws Exception {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM PAYMENT_METHOD WHERE id = ?")) {
                stmt.setInt(1, paymentMethod.getId());
                stmt.executeUpdate();

                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 1) {
                    logger.info("Payment method " + paymentMethod.getId() + " was deleted from the database.");
                } else {
                    logger.error("Payment method " + paymentMethod.getId() + " was not deleted from the database.");
                    throw new Exception("Payment method " + paymentMethod.getId() + " was not deleted from the database.");
                }
            }
        }
    }
}
