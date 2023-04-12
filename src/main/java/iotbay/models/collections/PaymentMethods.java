package iotbay.models.collections;
import iotbay.database.DatabaseManager;
import iotbay.models.entities.PaymentMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PaymentMethods {
    DatabaseManager db;

    public PaymentMethods(DatabaseManager db) {
        this.db = db;
    }

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
}
