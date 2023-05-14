/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package iotbay.servlets;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.stripe.exception.StripeException;
import iotbay.database.DatabaseManager;
import iotbay.exceptions.UserExistsException;
import iotbay.models.User;
import iotbay.models.httpResponses.GenericApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

/**
 * @author cmesina
 */
public class RegisterServlet extends HttpServlet {

    DatabaseManager db;

    private static final Logger logger = LogManager.getLogger(RegisterServlet.class);

    private static final Logger iotbayLogger = LogManager.getLogger("iotbayLogger");

    @Override
    public void init() throws ServletException {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody

        this.db = (DatabaseManager) getServletContext().getAttribute("db");
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegisterServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String emailAddress = request.getParameter("email");
        String address = request.getParameter("address");
        String phoneNumber = request.getParameter("phone");

        if (!username.matches("^\\S+$")) {
            request.setAttribute("error_title", "Error");
            request.setAttribute("error_msg", "Username cannot contain spaces.");
            request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
            return;
        }

        // check if the fields are empty and return an error with the fields that are empty
        if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || emailAddress.isEmpty() || address.isEmpty() || phoneNumber.isEmpty()) {
            request.setAttribute("error_title", "Empty fields");
            request.setAttribute("error_msg", "Please fill in all the fields.");
            request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
            return;
        }

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber phoneNum = null;
        try {
            phoneNum = phoneUtil.parse(phoneNumber, "AU");
            if (!phoneUtil.isValidNumber(phoneNum)) {
                request.setAttribute("error_title", "Invalid phone number");
                request.setAttribute("error_msg", "Please enter a valid Australian phone number.");
                request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
                return;
            }
        } catch (Exception e) {
            request.setAttribute("error_title", "Invalid phone number");
            request.setAttribute("error_msg", "Please enter a valid Australian phone number.");
            request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
            return;
        }

        // check email is valid
        if (!emailAddress.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            request.setAttribute("error_title", "Invalid email address");
            request.setAttribute("error_msg", "Please enter a valid email address.");
            request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
            return;
        }

        User newUser = new User((DatabaseManager) getServletContext().getAttribute("db"));
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(emailAddress);
        newUser.setAddress(address);
        newUser.setPhoneNumber(phoneUtil.format(phoneNum, PhoneNumberUtil.PhoneNumberFormat.E164));
        newUser.setRegistrationDate(new java.sql.Timestamp(System.currentTimeMillis()));

        try {
            this.db.getUsers().registerUser(newUser);
        } catch (Exception e) {
            if (e instanceof UserExistsException) {
                logger.error("User " + username + " already exists.");
                iotbayLogger.error("User with username " + username + " attempted to register but already exists.");
                request.setAttribute("error_title", "User already exists");
                request.setAttribute("error_msg", e.getMessage());
                request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
                return;
            }

            logger.error("Registration Failed: " + e.getMessage());
            iotbayLogger.error("Registration Failed: " + e.getMessage());
            request.setAttribute("error_title", "Error");
            request.setAttribute("error_msg", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
            return;

        }

        logger.info("User " + username + " has been registered.");
        iotbayLogger.info("User " + username + " has been registered.");

        request.setAttribute("success_title", "Registration successful");
        request.setAttribute("success_msg", "You have successfully registered. Please login to continue.");
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);

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
