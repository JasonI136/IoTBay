package iotbay.database.collections.metrics;

import iotbay.database.collections.Orders;
import iotbay.database.collections.Users;
import iotbay.models.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public class OrderMetrics {
    private Orders orders;

    public OrderMetrics(Orders orders) {
        this.orders = orders;
    }

    // Get the number of users registered per month
    public Map<String, Integer> getOrdersPerMonth() throws SQLException {
        String query = "SELECT YEAR(ORDER_DATE) AS \"year\", MONTH(ORDER_DATE) AS \"month\", COUNT(*) AS num_orders " +
                "FROM CUSTOMER_ORDER " +
                "WHERE ORDER_DATE IS NOT NULL " +
                "GROUP BY YEAR(ORDER_DATE), MONTH(ORDER_DATE) " +
                "ORDER BY YEAR(ORDER_DATE), MONTH(ORDER_DATE) ";

        Map<String, Integer> ordersPerMonth = new TreeMap<>();

        try (Connection conn = this.orders.getDb().getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.executeQuery();
                while (stmt.getResultSet().next()) {
                    String year = stmt.getResultSet().getString("year");
                    String month = stmt.getResultSet().getString("month");
                    int numUsers = stmt.getResultSet().getInt("num_orders");
                    ordersPerMonth.put(year + "-" + month, numUsers);
                }
            }
        }

        return ordersPerMonth;
    }

}
