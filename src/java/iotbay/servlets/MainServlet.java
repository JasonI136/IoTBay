/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package iotbay.servlets;

import com.stripe.Stripe;
import iotbay.database.DatabaseManager;
import iotbay.database.UserManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Properties;

/**
 *
 * @author cmesina
 */
public class MainServlet extends HttpServlet {
    
    private Properties appConfig;

    private Properties secrets;

    @Override
    public void init() throws ServletException {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        
        // Load the application configuration.
        InputStream inputStream = getServletContext().getResourceAsStream("/WEB-INF/app-config.properties");

        InputStream inputStreamSecrets = getServletContext().getResourceAsStream("/WEB-INF/secrets.properties");
        
        appConfig = new Properties();
        secrets = new Properties();
        
        try {
            appConfig.load(inputStream);
            secrets.load(inputStreamSecrets);
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
        // Make secrets accessible from other servlets.
        getServletContext().setAttribute("secrets", secrets);
        // Make user manager accessible from other servlets.
        getServletContext().setAttribute("userManager", userManager);

        Stripe.apiKey = secrets.getProperty("stripe.api.key");

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
            String path = request.getRequestURI().substring(request.getContextPath().length());
            if (path.startsWith("/public/")) {
            // Serve a local file
            String filePath = request.getServletContext().getRealPath(path);
            File file = new File(filePath);
            if (file.exists()) {
                response.setContentType(getServletContext().getMimeType(filePath));
                FileInputStream input = new FileInputStream(file);
                OutputStream output = response.getOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                input.close();
                output.flush();
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
    } else {
        // Forward the request to the JSP page
        request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
    }
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
