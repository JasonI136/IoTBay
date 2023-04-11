package iotbay.util;

import iotbay.exceptions.UserNotLoggedInException;
import iotbay.models.entities.User;
import iotbay.models.collections.Users;

import jakarta.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Misc {
    public static void refreshUser(HttpServletRequest request, Users users) throws Exception {
        // check if the user is logged in
        if (request.getSession().getAttribute("user") == null) {
            throw new UserNotLoggedInException("User not logged in");
        } else {
            request.getSession().setAttribute("user", users.getUser(((User) request.getSession().getAttribute("user")).getUsername()));
        }

    }

    public static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        try {
            rs.findColumn(columnName);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
}
