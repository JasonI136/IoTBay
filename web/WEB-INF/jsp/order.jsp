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
        <title>Order Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <jsp:include page="components/header-links.jsp" />
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
        <section class="bg0 p-t-104 p-b-116">
            <div class="container">
                <div class="d-flex justify-content-center align-items-center" style="padding-top:50px;padding-bottom: 50px;">
                    Order ID: ${order.id}
                    <div class="d-flex flex-column justify-content-center align-items-center">
                        <c:choose>
                            <c:when test="${order.orderStatus eq 'PROCESSING'}">
                                <h1 class="ltext-105 txt-center mb-4" style="font-size: 30px !important;">Your order is currently being processed</h1>
                                <div class="progress" style="height: 10px; width: 200%">
                                    <div class="progress-bar bg-info" role="progressbar" style="width: 40%; margin-right: -100%" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100">
                                </div>
                            </c:when>

                            <c:when test="${order.orderStatus eq 'PENDING'}">
                                <h1 class="ltext-105 txt-center mb-4" style="font-size: 30px !important;">Your order is pending</h1>
                                <div class="progress" style="height: 10px; width: 200%">
                                    <div class="progress-bar bg-warning" role="progressbar" style="width: 10%; margin-right: -100%" aria-valuenow="10" aria-valuemin="0" aria-valuemax="100">
                                </div>
                            </c:when>

                            <c:when test="${order.orderStatus eq 'SHIPPED'}">
                                <h1 class="ltext-105 txt-center mb-4" style="font-size: 30px !important;">Your order is has been shipped</h1>
                                <div class="progress" style="height: 10px; width: 200%">
                                    <div class="progress-bar bg-success" role="progressbar" style="width: 70%; margin-right: -100%" aria-valuenow="70" aria-valuemin="0" aria-valuemax="100">
                                </div>
                            </c:when>

                            <c:when test="${order.orderStatus eq 'DELIVERED'}">
                                <h1 class="ltext-105 txt-center mb-4" style="font-size: 30px !important;">Your order is has been shipped</h1>
                                <div class="progress" style="height: 10px; width: 200%">
                                    <div class="progress-bar bg-success" role="progressbar" style="width: 100%; margin-right: -100%" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100">
                                </div>
                            </c:when>

                            <c:when test="${order.orderStatus eq 'CANCELLED'}">
                                <h1 class="ltext-105 txt-center mb-4" style="font-size: 30px !important;">Your order has been cancelled</h1>
                                <div class="progress" style="height: 10px; width: 200%">
                                    <div class="progress-bar bg-warning" role="progressbar" style="width: 0; margin-right: -100%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100">
                                </div>
                            </c:when>

                            <c:otherwise>
                                <h1 class="ltext-105 txt-center mb-4" style="font-size: 30px !important;">There seems to be a technical issue with your order</h1>
                                <div class="progress" style="height: 10px; width: 200%">
                                    <div class="progress-bar bg-danger" role="progressbar" style="width: 0; margin-right: -100%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100">
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </section>

        <c:if test="${not empty requestScope.productList}">
            <c:forEach var="product" items="${requestScope.productList}">
                <h1 class="ltext-105 txt-center mb-4">Product ID: ${product.getId()}</h1>
                <h1 class="ltext-105 txt-center mb-4">Product Name: ${product.getName()}</h1>
                <h1 class="ltext-105 txt-center mb-4">Product Price: ${product.getPrice()}</h1>
                <img src="${product.getImageURL()}">
            </c:forEach>
        </c:if>

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