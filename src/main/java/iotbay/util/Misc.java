package iotbay.util;

import iotbay.exceptions.UserNotFoundException;
import iotbay.exceptions.UserNotLoggedInException;
import iotbay.models.collections.Users;
import iotbay.models.entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
