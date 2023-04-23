/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package iotbay.servlets;

import iotbay.database.DatabaseManager;
import iotbay.models.httpResponses.GenericApiResponse;
import iotbay.util.Misc;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import iotbay.models.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * @author shayanrazavi
 */

public class UserDetailsServlet extends HttpServlet {
    DatabaseManager db;

    private static final Logger logger = LogManager.getLogger(CartServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
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
            out.println("<title>Servlet UserDetailsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserDetailsServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        try {
            Gson gson = new Gson();
            JsonObject requestData = gson.fromJson(request.getReader(), JsonObject.class);

            String username = requestData.get("username").getAsString();
            String firstname = requestData.get("firstname").getAsString();
            String lastname = requestData.get("lastname").getAsString();
            String address = requestData.get("address").getAsString();
            String email = requestData.get("email").getAsString();
            String phone = requestData.get("phone").getAsString();


            User user = (User) request.getSession().getAttribute("user");
            if (user.getUsername() != username && this.db.getUsers().getUser(username) == null) {
                user.setUsername(username);
            }

            if (username != null && firstname != null && address != null && email != null && phone != null && phone != null) {
                user.setFirstName(firstname);
                user.setLastName(lastname);
                user.setAddress(address);
                user.setEmail(email);
                int number = Integer.parseInt(phone);
                user.setPhoneNumber(number);
                this.db.getUsers().updateUser(user);
            }

            Misc.sendJsonResponse(response,
                    GenericApiResponse.<String>builder()
                            .statusCode(200)
                            .message("Success")
                            .data("Details updated successfully")
                            .error(false)
                            .build()
            );
//            response.getWriter().write("{\"status\": \"success\"}");
        } catch (SQLException e) {
            logger.error(e);

            Misc.sendJsonResponse(response,
                    GenericApiResponse.<String>builder()
                            .statusCode(500)
                            .message("Error")
                            .data(e.getMessage())
                            .error(false)
                            .build()
            );
        } catch (NumberFormatException e) {
            logger.error(e);

            Misc.sendJsonResponse(response,
                    GenericApiResponse.<String>builder()
                            .statusCode(400)
                            .message("Error")
                            .data("Invalid phone number")
                            .error(false)
                            .build()
            );
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
