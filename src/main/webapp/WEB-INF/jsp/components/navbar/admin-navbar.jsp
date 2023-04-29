<%-- 
    Document   : admin-navbar
    Created on : 30/03/2023, 1:25:54 PM
    Author     : jasonmba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="container-menu-desktop">
    <jsp:include page="header-navbar.jsp"/>
    <div class="wrap-menu-desktop">
        <nav class="limiter-menu-desktop container">
            <!-- Logo desktop -->
            <a href="#" class="logo">
                <img src="${pageContext.request.contextPath}/public/images/logo-dark.png">
            </a>

            <!-- Menu desktop -->
            <div class="menu-desktop">
                <ul class="main-menu">
                    <jsp:include page="admin-navbar-links.jsp"/>
                </ul>
            </div>
        </nav>
    </div>
</div>
