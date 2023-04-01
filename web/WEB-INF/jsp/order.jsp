<%-- 
    Document   : order
    Created on : 20/03/2023, 12:27:31 PM
    Author     : jasonmba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>


        <title>Order Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/public/images/icons/favicon.png"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/vendor/bootstrap/css/bootstrap.min.css">

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/fonts/iconic/css/material-design-iconic-font.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/fonts/linearicons-v1.0.0/icon-font.min.css">

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/vendor/animate/animate.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/vendor/css-hamburgers/hamburgers.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/vendor/animsition/css/animsition.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/vendor/select2/select2.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/vendor/daterangepicker/daterangepicker.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/vendor/slick/slick.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/vendor/MagnificPopup/magnific-popup.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/vendor/perfect-scrollbar/perfect-scrollbar.css">

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/util.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/main.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </head>
    <body>
        <header class="header-v4">
            <div class="container-menu-desktop">
                <jsp:include page="components/header-navbar.jsp" />
                <jsp:include page="components/main-navbar.jsp" />
            </div>
        </header>

        <!-- Title page -->
        <div >
            <section class="bg-img1 txt-center p-lr-15 p-tb-92" style="background-image: url('${pageContext.request.contextPath}/public/images/bg-orders.jpg');">
                <h2 class="ltext-105 cl0 txt-center">
                    Order Tracking
                </h2>
            </section>
        </div>

        <!-- breadcrumb -->
        <div class="container">
            <div class="bread-crumb flex-w p-l-25 p-r-15 p-t-30 p-lr-0-lg">
                <a href="index.html" class="stext-109 cl8 hov-cl1 trans-04">
                    Home
                    <i class="fa fa-angle-right m-l-9 m-r-10" aria-hidden="true"></i>
                </a>
                <span class="stext-109 cl4">
                    Order ID Page
                </span>
            </div>
        </div>
        <% 
            String orderId = (String) request.getAttribute("order_id"); 
            String orderStatus = (String) request.getAttribute("order_status"); 
            String orderDate = (String) request.getAttribute("order_date");
            String progressColor = "bg-danger";
            int progress = 0;
            if (orderStatus.equals("PROCESSING")) {
                    orderStatus = "Your order is currently being processed";
                    progressColor = "bg-info";
                    progress = 40;
                } else if (orderStatus.equals("PENDING")) {
                    orderStatus = "Your order is pending";
                    progressColor = "bg-warning";
                    progress = 10;
                } else if (orderStatus.equals("SHIPPED")) {
                    orderStatus = "Your order is has been shipped";
                    progressColor = "bg-sucess";
                    progress = 70;
                } else if (orderStatus.equals("DELIVERED")) {
                    progressColor = "bg-success";
                    progress = 100;
                    orderStatus = "Your order has been delivered";
                } else if (orderStatus.equals("CANCELLED")) {
                    progress = 10;
                    orderStatus = "Your order has been cancelled";
                    progressColor = "bg-warning";
                } else {
                    progress = 0;
                    orderStatus = "There seems to be a technical issue with your order";
                }
            if (orderId != null && !orderId.isEmpty()) {
        %>
        <div class="d-flex justify-content-center align-items-center" style="padding-top:50px;padding-bottom: 50px;">
            <div class="d-flex flex-column justify-content-center align-items-center">
                <h1 class="ltext-105 txt-center mb-4" style="font-size: 30px !important;"><%= orderStatus%></h1>
                <div class="progress" style="height: 10px; width: 200%">
                    <div class="progress-bar <%= progressColor%>" role="progressbar" style="width: <%=progress%>%; margin-right: -100%" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100"></div>
                </div>
            </div>
        </div>
        <c:if test="${not empty product_list}">
            <c:forEach var="product" items="${product_list}">
                <h1 class="ltext-105 txt-center mb-4">Product ID: ${product.getId()}</h1>
                <h1 class="ltext-105 txt-center mb-4">Product Name: ${product.getName()}</h1>
                <h1 class="ltext-105 txt-center mb-4">Product Price: ${product.getPrice()}</h1>
                <img src="${product.getImageURL()}">
            </c:forEach>
        </c:if>
        <%
            } else {
        %>
        <p>No order ID specified. Make sure to have order_id param</p>
        <%
            }
        %>
        <!-- Footer -->
        <footer class="bg3 p-t-75 p-b-32">
            <jsp:include page="components/footer.jsp" />
        </footer>


        <!-- Back to top -->
        <div class="btn-back-to-top" id="myBtn">
            <span class="symbol-btn-back-to-top">
                <i class="zmdi zmdi-chevron-up"></i>
            </span>
        </div>
        
        <script src="${pageContext.request.contextPath}/public/vendor/jquery/jquery-3.2.1.min.js"></script>

        <script src="${pageContext.request.contextPath}/public/vendor/animsition/js/animsition.min.js"></script>

        <script src="${pageContext.request.contextPath}/public/vendor/bootstrap/js/popper.js"></script>
        <script src="${pageContext.request.contextPath}/public/vendor/bootstrap/js/bootstrap.min.js"></script>

        <script src="${pageContext.request.contextPath}/public/vendor/select2/select2.min.js"></script>
        <script>
            $(".js-select2").each(function () {
                $(this).select2({
                    minimumResultsForSearch: 20,
                    dropdownParent: $(this).next('.dropDownSelect2')
                });
            })
        </script>


        <script src="${pageContext.request.contextPath}/public/vendor/isotope/isotope.pkgd.min.js"></script>

        <script src="${pageContext.request.contextPath}/public/vendor/MagnificPopup/jquery.magnific-popup.min.js"></script>

        <script src="${pageContext.request.contextPath}/public/vendor/perfect-scrollbar/perfect-scrollbar.min.js"></script>
        <script>
            $('.js-pscroll').each(function () {
                $(this).css('position', 'relative');
                $(this).css('overflow', 'hidden');
                var ps = new PerfectScrollbar(this, {
                    wheelSpeed: 1,
                    scrollingThreshold: 1000,
                    wheelPropagation: false,
                });

                $(window).on('resize', function () {
                    ps.update();
                })
            });
        </script>
        
         <script src="${pageContext.request.contextPath}/public/js/map-custom.js"></script>

        <script src="${pageContext.request.contextPath}/public/js/main.js"></script>
    </body>
</html>