<%--
  Created by IntelliJ IDEA.
  User: cmesina
  Date: 29/4/2023
  Time: 9:50 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Header Mobile -->
<div class="wrap-header-mobile">
    <!-- Logo moblie -->
    <div class="logo-mobile">
        <a href="index.html"><img src="${pageContext.request.contextPath}/public/images/logo-dark.png"
                                  alt="IMG-LOGO"></a>
    </div>

    <!-- Icon header -->
    <div class="wrap-icon-header flex-w flex-r-m m-r-15">
        <a href="${pageContext.request.contextPath}/cart"
           class="icon-header-item cl2 hov-cl1 trans-04 p-r-11 p-l-10 icon-header-noti js-show-cart"
           data-notify="${(sessionScope.shoppingCart.totalQuantity == null) ? 0 : sessionScope.shoppingCart.totalQuantity}">
            <i class="zmdi zmdi-shopping-cart"></i>
        </a>
    </div>

    <!-- Button show menu -->
    <div class="btn-show-menu-mobile hamburger hamburger--squeeze">
				<span class="hamburger-box">
					<span class="hamburger-inner"></span>
				</span>
    </div>
</div>


<!-- Menu Mobile -->
<div class="menu-mobile">
    <jsp:include page="header-navbar.jsp"/>
    <ul class="main-menu-m">
        <jsp:include page="admin-navbar-links.jsp"/>
    </ul>
</div>