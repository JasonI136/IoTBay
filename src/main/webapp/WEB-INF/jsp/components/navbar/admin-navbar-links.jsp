<%--
  Created by IntelliJ IDEA.
  User: cmesina
  Date: 29/4/2023
  Time: 10:15 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<li class="${(requestScope['jakarta.servlet.forward.request_uri'] == pageContext.request.contextPath.concat("/admin") || requestScope['jakarta.servlet.forward.request_uri'] == pageContext.request.contextPath) ? 'active-menu' : ''}">
    <a href="${pageContext.request.contextPath}/admin">Dashboard</a>
</li>

<li class="${requestScope['jakarta.servlet.forward.request_uri'] == pageContext.request.contextPath.concat('/admin/inventory') ? 'active-menu' : ''}">
    <a href="${pageContext.request.contextPath}/admin/inventory">Inventory Management</a>
</li>

<li class="${requestScope['jakarta.servlet.forward.request_uri'] == pageContext.request.contextPath.concat('/admin/orders') ? 'active-menu' : ''}">
    <a href="${pageContext.request.contextPath}/admin/orders">Order Management</a>
</li>

<li class="${requestScope['jakarta.servlet.forward.request_uri'] == pageContext.request.contextPath.concat('/admin/users') ? 'active-menu' : ''}">
    <a href="${pageContext.request.contextPath}/admin/users">User Management</a>
</li>

<li class="${requestScope['jakarta.servlet.forward.request_uri'] == pageContext.request.contextPath.concat('/admin/logs') ? 'active-menu' : ''}">
    <a href="${pageContext.request.contextPath}/admin/logs">Logs</a>
</li>