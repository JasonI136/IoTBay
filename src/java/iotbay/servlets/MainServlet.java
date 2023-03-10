/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package iotbay.servlets;

import iotbay.database.DatabaseManager;
import iotbay.database.UserManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cmesina
 */
public class MainServlet extends HttpServlet {
    
    private Properties appConfig;

    @Override
    public void init() throws ServletException {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        
        // Load the application configuration.
        InputStream inputStream = getServletContext().getResourceAsStream("/WEB-INF/app-config.properties");
        
        appConfig = new Properties();
        
        try {
            appConfig.load(inputStream);
        } catch (IOException err) {
            throw new ServletException("Application configuration failed to load.");
        }
        
        // Initialise the database
        DatabaseManager db;
        try {
             db = new DatabaseManager(
                appConfig.getProperty("database.url"), 
                appConfig.getProperty("database.username"),
                appConfig.getProperty("database.password"),
                appConfig.getProperty("database.name")
            );
        } catch (Exception e) {
            throw new ServletException("An error occurred whilst intialising the database: " + e.getMessage());
        }
        
        UserManager userManager = new UserManager(db, Integer.parseInt(appConfig.getProperty("auth.saltLength")), Integer.parseInt(appConfig.getProperty("auth.encryptionIterations")));
        
        // Make the db object accessible from other servlets.
        getServletContext().setAttribute("db", db);
        // Make config accessible from other servlets.
        getServletContext().setAttribute("appConfig", appConfig);
        // Make user manager accessible from other servlets.
        getServletContext().setAttribute("userManager", userManager);
        
        
        
        
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
            out.println("<title>Servlet MainServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MainServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        processRequest(request, response);
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
