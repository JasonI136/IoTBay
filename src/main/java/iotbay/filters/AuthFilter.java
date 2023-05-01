package iotbay.filters;

import iotbay.database.DatabaseManager;
import iotbay.models.User;
import iotbay.models.httpResponses.GenericApiResponse;
import iotbay.util.CustomHttpServletResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * A filter that checks if the user is logged in and authorized to view the page.
 */
public class AuthFilter implements Filter {

    /**
     * An instance of the database manager
     */
    DatabaseManager db;

    /**
     * The logger for this class
     */
    private static final Logger logger = LogManager.getLogger(AuthFilter.class);

    /**
     * The database logger for this class
     */
    private static final Logger iotbayLogger = LogManager.getLogger("iotbayLogger");

    /**
     * A list of protected paths
     */
    private final List<ProtectedPath> protectedPaths = Arrays.asList(
            new ProtectedPath("/admin", true),
            new ProtectedPath("/user", false),
            new ProtectedPath("/cart/checkout", false)
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.db = (DatabaseManager) filterConfig.getServletContext().getAttribute("db");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        CustomHttpServletResponse res1 = (CustomHttpServletResponse) response;
        HttpServletResponse res = (HttpServletResponse) response;
        String reqPath = req.getRequestURI().substring(req.getContextPath().length());

        // Check if the path is protected
        for (ProtectedPath path : protectedPaths) {
            if (path.pathMatches(reqPath)) {

                // Check if the user is logged in
                User user = (User) req.getSession().getAttribute("user");
                if (user == null) {
                    // If not, redirect to login page
                    if (req.getContentType() == null) {
                        req.getSession().setAttribute("loginRedirect", req.getRequestURI());
                        res.sendRedirect(req.getContextPath() + "/login");
                        return;
                    } else if (req.getContentType().equals("application/json")) {
                        res1.sendJsonResponse(GenericApiResponse.<String>builder()
                                .statusCode(401)
                                .message("Unauthorized")
                                .data("You must be logged in to access this page.")
                                .error(true)
                                .build());
                        return;
                    }

                } else {
                    // Refresh the user session object to ensure it's up to date.
                    try {
                        user = this.db.getUsers().getUser(user.getUsername());
                        req.getSession().setAttribute("user", user);
                    } catch (SQLException e) {
                        throw new ServletException(e);
                    }
                }

                // Check if the user is authorized to view the page
                if (path.isUserAllowed(user)) {
                    // If so, continue

                    if (path.isStaffOnly()) {
                        String logMessage = String.format("Staff member %s (%s) accessed %s", user.getId(), user.getUsername(), reqPath);
                        logger.info(logMessage);
                        iotbayLogger.info(logMessage);
                    }

                    chain.doFilter(request, response);
                    return;
                } else {
                    // If not, send 403
                    String logMessage = String.format("User %s (%s) tried to access %s but was not authorized", user.getId(), user.getUsername(), reqPath);
                    logger.warn(logMessage);
                    iotbayLogger.warn(logMessage);
                    req.getSession().setAttribute("message", "You are not authorized to view this page");
                    res.sendError(403);
                    return;
                }
            }
        }

        // If the path is not protected, continue
        chain.doFilter(request, response);

    }

}
