package iotbay.filters;

import iotbay.database.DatabaseManager;
import iotbay.models.entities.User;

import iotbay.servlets.LoginServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthFilter implements Filter {

    DatabaseManager db;

    private static final Logger logger = LogManager.getLogger(AuthFilter.class);
    private static final Logger iotbayLogger = LogManager.getLogger("iotbayLogger");

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
        HttpServletResponse res = (HttpServletResponse) response;
        String reqPath = req.getRequestURI().substring(req.getContextPath().length());

        // Check if the path is protected
        for (ProtectedPath path : protectedPaths) {
            if (path.pathMatches(reqPath)) {

                // Check if the user is logged in
                User user = (User) req.getSession().getAttribute("user");
                if (user == null) {
                    // If not, redirect to login page
                    req.getSession().setAttribute("loginRedirect", req.getRequestURI());
                    res.sendRedirect(req.getContextPath() + "/login");
                    return;
                } else {
                    // Refresh the user session object to ensure it's up to date.
                    try {
                        user = this.db.getUsers().getUser(user.getUsername());
                        req.getSession().setAttribute("user", user);
                    } catch (Exception e) {
                        throw new ServletException(e);
                    }
                }

                // Check if the user is authorized to view the page
                if (path.isUserAllowed(user)) {
                    // If so, continue
                    chain.doFilter(request, response);
                    return;
                } else {
                    // If not, send 403
                    logger.warn("User " + user.getUsername() + " tried to access " + reqPath + " but was not authorized");
                    iotbayLogger.warn("User " + user.getUsername() + " tried to access " + reqPath + " but was not authorized");
                    req.getSession().setAttribute("message", "You are not authorized to view this page");
                    res.sendError(403);
                    return;
                }
            }
        }

        // If the path is not protected, continue
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }

}
