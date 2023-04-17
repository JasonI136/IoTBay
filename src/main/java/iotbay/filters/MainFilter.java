package iotbay.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * The main filter.
 */
public class MainFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        // Check if the application failed to initialize.
        String initError = (String) request.getServletContext().getAttribute("initError");
        if (initError != null) {
            // If so, send a 500 error.
            req.setAttribute("message", "Application failed to initialize");
            res.sendError(500, initError);
            return;
        }


        chain.doFilter(request, response);
    }
}
