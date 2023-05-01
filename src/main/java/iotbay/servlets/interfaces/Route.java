package iotbay.servlets.interfaces;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface Route {
    void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
