<%-- 
    Document   : admin-navbar
    Created on : 30/03/2023, 1:25:54 PM
    Author     : jasonmba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>


    <div class="fixed-bottom">
        <div class="container-fluid">
            <hr class="hr-text" data-content="Currently In Admin Panel">
        </div>
    </div>
    <div class="wrap-menu-desktop">
        <nav class="limiter-menu-desktop container">

            <!-- Logo desktop -->		
            <a class="logo">
                <img src="${pageContext.request.contextPath}/public/images/logo-dark.png">
                <span class="notification-icon">
                    <span class="notification-text">A</span>
                </span>
            </a>

            <!-- Menu desktop -->
            <div class="menu-desktop">
                <ul class="main-menu">
                    <li class="${(requestScope['javax.servlet.forward.request_uri'] == pageContext.request.contextPath.concat("/admin") || requestScope['javax.servlet.forward.request_uri'] == pageContext.request.contextPath) ? 'active-menu' : ''}">
                        <a href="${pageContext.request.contextPath}/admin">Dashboard</a>
                    </li>

                    <li class="${requestScope['javax.servlet.forward.request_uri'] == pageContext.request.contextPath.concat('/admin/inventory') ? 'active-menu' : ''}">
                        <a href="${pageContext.request.contextPath}/admin/inventory">Inventory Management</a>
                    </li>

                    <li class="${requestScope['javax.servlet.forward.request_uri'] == pageContext.request.contextPath.concat('/admin/orders') ? 'active-menu' : ''}">
                        <a href="${pageContext.request.contextPath}/admin/orders">Order Management</a>
                    </li>
                    
                     <li class="${requestScope['javax.servlet.forward.request_uri'] == pageContext.request.contextPath.concat('/admin/users') ? 'active-menu' : ''}">
                        <a href="${pageContext.request.contextPath}/admin/users">User Management</a>
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
