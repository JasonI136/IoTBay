<%--
  Created by IntelliJ IDEA.
  User: cmesina
  Date: 29/4/2023
  Time: 9:57 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<li class="${(requestScope['jakarta.servlet.forward.request_uri'] == pageContext.request.contextPath.concat("/") || requestScope['jakarta.servlet.forward.request_uri'] == pageContext.request.contextPath) ? 'active-menu' : ''}">
    <a href="${pageContext.request.contextPath}/">Home</a>
</li>

<li class="${requestScope['jakarta.servlet.forward.request_uri'] == pageContext.request.contextPath.concat('/shop') ? 'active-menu' : ''}">
    <a href="${pageContext.request.contextPath}/shop">Shop</a>
</li>

<li class="${requestScope['jakarta.servlet.forward.request_uri'] == pageContext.request.contextPath.concat('/orderTracking') ? 'active-menu' : ''}">
    <a href="${pageContext.request.contextPath}/orderTracking">Track Order</a>
</li>

<li class="${requestScope['jakarta.servlet.forward.request_uri'] == pageContext.request.contextPath.concat('/contactUs') ? 'active-menu' : ''}">
    <a href="${pageContext.request.contextPath}/contactUs">Contact</a>
</li>

<li class="${requestScope['jakarta.servlet.forward.request_uri'] == pageContext.request.contextPath.concat('/aboutUs') ? 'active-menu' : ''}">
    <a href="${pageContext.request.contextPath}/aboutUs">About Us</a>
</li>
