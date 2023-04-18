package iotbay.util;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Misc {

    public static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        try {
            rs.findColumn(columnName);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
}
