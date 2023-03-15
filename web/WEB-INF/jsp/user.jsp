<%-- 
    Document   : user
    Created on : 14 Mar 2023, 5:53:56 pm
    Author     : cmesina
--%>

<%@page import="iotbay.models.User"%>

<%
    User user = (User) session.getAttribute("user"); 
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Page</title>
    </head>
    <body>
        <h1>Welcome <%=user.getFirstName()%> <%=user.getLastName()%></h1>
        <a href="<%=request.getContextPath()%>/logout">Logout</a>
    </body>
</html>
