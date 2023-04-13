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

    /**
     * Refreshes the user in the session
     * @param request servlet request object
     * @param response servlet response object
     * @param users Users collection
     * @return true if the user was not logged in, false otherwise.
     * @throws ServletException
     */
    public static boolean refreshUser(HttpServletRequest request, HttpServletResponse response, Users users) throws ServletException {
        if (request.getSession().getAttribute("user") == null) {
            try {
                response.sendRedirect(request.getContextPath() + "/login");
            } catch (IOException e) {
                throw new ServletException(e);
            }
            return true;
        } else {
            User currentUser = (User) request.getSession().getAttribute("user");
            try {
                request.getSession().setAttribute("user", users.getUser(currentUser.getUsername()));
            } catch (UserNotFoundException e) {
                try {
                    response.sendRedirect(request.getServletContext().getContextPath() + "/login");
                } catch (IOException ex) {
                    throw new ServletException(ex);
                }
                return true;
            } catch (Exception e) {
                throw new ServletException(e);
            }
            return false;
        }
    }

        public static boolean refreshUser(HttpServletRequest request, HttpServletResponse response, Users users, String redirectUrl) throws ServletException {
            if (request.getSession().getAttribute("user") == null) {
            try {
                response.sendRedirect(request.getContextPath() + "/login?redirect=" + redirectUrl);
            } catch (IOException e) {
                throw new ServletException(e);
            }
            return true;
        } else {
            User currentUser = (User) request.getSession().getAttribute("user");
            try {
                request.getSession().setAttribute("user", users.getUser(currentUser.getUsername()));
            } catch (UserNotFoundException e) {
                try {
                    response.sendRedirect(request.getServletContext().getContextPath() + "/login?redirect=" + redirectUrl);
                } catch (IOException ex) {
                    throw new ServletException(ex);
                }
                return true;
            } catch (Exception e) {
                throw new ServletException(e);
            }
            return false;
        }

    }

    public static boolean userIsStaff(HttpServletRequest request, HttpServletResponse response, Users users) throws ServletException {
        if (refreshUser(request, response, users)) return true;

        User currentUser = (User) request.getSession().getAttribute("user");
        if (!currentUser.isStaff()) {
            request.getSession().setAttribute("message", "You do not have permission to access this page.");
            try {
                response.sendError(403);
            } catch (IOException e) {
                throw new ServletException(e);
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean userIsStaff(HttpServletRequest request, HttpServletResponse response, Users users, String redirectUrl) throws ServletException {
        if (refreshUser(request, response, users, redirectUrl)) return true;

        return userIsStaff(request, response, users);
    }
}
