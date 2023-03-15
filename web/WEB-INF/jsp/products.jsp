<%-- 
    Document   : products
    Created on : 14 Mar 2023, 9:28:36 pm
    Author     : cmesina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Products</h1>
        <ul>
        <c:forEach items="${products}" var="product" >
            <li>${product.name}, ${product.description}, Price: AUD$${product.price}</li>
        </c:forEach>
        </ul>
    </body>
</html>
