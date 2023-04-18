<%-- 
    Document   : main-navbar
    Created on : Mar 16, 2023, 11:07:55 PM
    Author     : dpnam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

    
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <div class="wrap-menu-desktop">
        <nav class="limiter-menu-desktop container">

            <!-- Logo desktop -->		
            <a href="#" class="logo">
                <img src="${pageContext.request.contextPath}/public/images/logo-dark.png">
            </a>

            <!-- Menu desktop -->
            <div class="menu-desktop">
                <ul class="main-menu">
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
                </ul>
            </div>	

            <!-- Icon header -->
            <div class="wrap-icon-header flex-w flex-r-m">
<!--                <div class="icon-header-item cl2 hov-cl1 trans-04 p-l-22 p-r-11 js-show-modal-search">
                    <i class="zmdi zmdi-search"></i>
                </div>-->

                <a href="${pageContext.request.contextPath}/cart" class="icon-header-item cl2 hov-cl1 trans-04 p-l-22 p-r-11 icon-header-noti js-show-cart" data-notify="${(sessionScope.shoppingCart.totalQuantity == null) ? 0 : sessionScope.shoppingCart.totalQuantity}">
                    <i class="zmdi zmdi-shopping-cart"></i>
                </a>

<!--                <a href="#" class="dis-block icon-header-item cl2 hov-cl1 trans-04 p-l-22 p-r-11 icon-header-noti" data-notify="0">
                    <i class="zmdi zmdi-favorite-outline"></i>
                </a>-->
            </div>
        </nav>
    </div>	

</html>





