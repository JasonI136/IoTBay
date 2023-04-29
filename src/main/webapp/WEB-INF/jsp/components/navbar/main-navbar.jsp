<%-- 
    Document   : main-navbar
    Created on : Mar 16, 2023, 11:07:55 PM
    Author     : dpnam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
                    <jsp:include page="navbar-links.jsp"/>
                </ul>
            </div>

            <div class="wrap-icon-header flex-w flex-r-m">
                <a href="${pageContext.request.contextPath}/cart"
                   class="icon-header-item cl2 hov-cl1 trans-04 p-l-22 p-r-11 icon-header-noti js-show-cart"
                   data-notify="${(sessionScope.shoppingCart.totalQuantity == null) ? 0 : sessionScope.shoppingCart.totalQuantity}">
                    <i class="zmdi zmdi-shopping-cart"></i>
                </a>
            </div>
        </nav>
    </div>
</div>








