/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package iotbay.servlets;

import iotbay.database.DatabaseManager;
import iotbay.models.collections.Users;
import iotbay.exceptions.UserNotFoundException;
import iotbay.models.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author cmesina
 */
public class LoginServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(LoginServlet.class);
    DatabaseManager db;

    @Override
    public void init() throws ServletException {
        super.init();
        db = (DatabaseManager) getServletContext().getAttribute("db");
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // check if the user is already logged in
        if (request.getSession().getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/user");
            return;
        }


        //request.getSession().setAttribute("login", request.getParameter("redirect"));
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // check if the fields are empty
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            response.setStatus(400);
            request.setAttribute("error_title", "Login failed");
            request.setAttribute("error_msg", "Please fill in all the fields.");
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            return;
        }

        try {
            User user = this.db.getUsers().authenticateUser(username, password);
            if (user != null) {
                request.getSession().setAttribute("user", user);
                request.setAttribute("success_title", "Login successful");
                request.setAttribute("success_msg", "Welcome " + user.getFirstName() + " " + user.getLastName() + "!");

                logger.info("User " + user.getUsername() + " logged in.");

                String redirect = (String) request.getSession().getAttribute("loginRedirect");
                if (redirect != null) {
                    response.sendRedirect(redirect);
                    return;
                }

                //request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
                response.sendRedirect(getServletContext().getContextPath() + "/user");
            } else {
                response.setStatus(401);
                request.setAttribute("error_title", "Login failed");
                request.setAttribute("error_msg", "The username or password is incorrect.");
                request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
               
            }
        } catch (Exception e) {
            if (e instanceof UserNotFoundException) {
                response.setStatus(404);
                request.setAttribute("error_title", "Account not found");
                request.setAttribute("error_msg", "The account does not exist.");
                request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            } else {
                throw new ServletException("Error: " + e.getMessage());
            }

        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
