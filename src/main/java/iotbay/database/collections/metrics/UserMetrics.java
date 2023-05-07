package iotbay.database.collections.metrics;

import iotbay.database.collections.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class UserMetrics {
    private Users users;

    public UserMetrics(Users users) {
        this.users = users;
    }

    // Get the number of users registered per month
    public Map<String, Integer> getRegisteredUsersPerMonth() throws SQLException {
        String query = "SELECT YEAR(REGISTRATION_DATE) AS \"year\", MONTH(REGISTRATION_DATE) AS \"month\", COUNT(*) AS num_users " +
                "FROM USER_ACCOUNT " +
                "WHERE REGISTRATION_DATE IS NOT NULL " +
                "GROUP BY YEAR(REGISTRATION_DATE), MONTH(REGISTRATION_DATE) " +
                "ORDER BY YEAR(REGISTRATION_DATE), MONTH(REGISTRATION_DATE) ";

        Map<String, Integer> registeredUsersPerMonth = new TreeMap<>();

        try (Connection conn = users.getDb().getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.executeQuery();
                while (stmt.getResultSet().next()) {
                    String year = stmt.getResultSet().getString("year");
                    String month = stmt.getResultSet().getString("month");
                    int numUsers = stmt.getResultSet().getInt("num_users");
                    registeredUsersPerMonth.put(year + "-" + month, numUsers);
                }
            }
        }

        return registeredUsersPerMonth;
    }

}
