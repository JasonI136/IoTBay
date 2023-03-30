<%-- 
    Document   : order
    Created on : 20/03/2023, 12:27:31 PM
    Author     : jasonmba
--%>
<%--<%@page import="iotbay.models.entities.User" %>--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% 
            String orderId = (String) request.getAttribute("order_id"); 
            if (orderId != null && !orderId.isEmpty()) {
        %>
        <h1>Order ID: <%= orderId %></h1>
        <%
            } else {
        %>
        <p>No order ID specified. Make sure to have order_id param</p>
        <%
            }
        %>
    </body>
</html>