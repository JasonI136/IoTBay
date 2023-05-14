<%-- 
    Document   : index
    Created on : 7 Mar 2023, 6:12:09 pm
    Author     : cmesina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>IoTBay Home</title>
        <meta charset="UTF-8">

        <jsp:include page="components/common-header-html.jsp"/>
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/public/images/icons/favicon.png"/>
    </head>
    <body class="animsition">
        <!-- Header -->
        <jsp:include page="components/navbar/master-navbar.jsp"/>

        <section class="section-slide">
            <div class="wrap-slick1">
                <div class="slick1">
                    <div class="item-slick1"
                         style="background-image: url(${pageContext.request.contextPath}/public/images/slide-03.png);">
                        <div class="container h-full">
                            <div class="flex-col-l-m h-full p-t-100 p-b-30 respon5">
                                <div class="layer-slick1 animated visible-false" data-appear="fadeInDown"
                                     data-delay="0">
                                    <span class="ltext-101 cl2 respon2">
                                        IoT Security Products
                                    </span>
                                </div>

                                <div class="layer-slick1 animated visible-false" data-appear="fadeInUp"
                                     data-delay="800">
                                    <h2 class="ltext-104 cl2 p-t-19 p-b-43 respon1">
                                        Security RFID
                                    </h2>
                                </div>

                                <div class="layer-slick1 animated visible-false" data-appear="zoomIn" data-delay="1600">
                                    <a href="${pageContext.request.contextPath}/shop"
                                       class="flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04">
                                        Shop Now
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="item-slick1"
                         style="background-image: url(${pageContext.request.contextPath}/public/images/slide-02.png);">
                        <div class="container h-full">
                            <div class="flex-col-l-m h-full p-t-100 p-b-30 respon5">
                                <div class="layer-slick1 animated visible-false" data-appear="rollIn" data-delay="0">
                                    <span class="ltext-101 cl2 respon2">
                                        IoT Monitor
                                    </span>
                                </div>

                                <div class="layer-slick1 animated visible-false" data-appear="lightSpeedIn"
                                     data-delay="800">
                                    <h2 class="ltext-104 cl2 p-t-19 p-b-43 respon1">
                                        2023 Displays
                                    </h2>
                                </div>

                                <div class="layer-slick1 animated visible-false" data-appear="slideInUp"
                                     data-delay="1600">
                                    <a href="${pageContext.request.contextPath}/shop"
                                       class="flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04">
                                        Shop Now
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="item-slick1"
                         style="background-image: url(${pageContext.request.contextPath}/public/images/slide-01.png);">
                        <div class="container h-full">
                            <div class="flex-col-l-m h-full p-t-100 p-b-30 respon5">
                                <div class="layer-slick1 animated visible-false" data-appear="rotateInDownLeft"
                                     data-delay="0">
                                    <span class="ltext-101 cl2 respon2">
                                        IoT Arduino 
                                    </span>
                                </div>

                                <div class="layer-slick1 animated visible-false" data-appear="rotateInUpRight"
                                     data-delay="800">
                                    <h2 class="ltext-104 cl2 p-t-19 p-b-43 respon1">
                                        New arrivals
                                    </h2>
                                </div>

                                <div class="layer-slick1 animated visible-false" data-appear="rotateIn"
                                     data-delay="1600">
                                    <a href="${pageContext.request.contextPath}/shop"
                                       class="flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04">
                                        Shop Now
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- Product -->
        <section class="bg0 p-t-23 p-b-140">
            <div class="container">
                <c:if test="${demo == true}">
                    <div class="row alert alert-warning m-lr-1" role="alert">
                        <span>
                            <i class="fa-solid fa-triangle-exclamation"></i>
                            This is a demo website. Items on this website are <b><u>not for sale</u></b>. Website is reset every 24 hours.
                        </span>
                    </div>
                </c:if>
                <div class="p-b-10">
                    <h3 class="ltext-103 cl5">
                        Featured Items
                    </h3>
                </div>


                <div class="row isotope-grid" id="productList">
                    <c:forEach items="${products}" var="product">
                        <c:if test="${product.categoryNameNoSpace eq 'WirelessSensors'}">
                            <div class="col-sm-6 col-md-4 col-lg-3 p-b-35 isotope-item ${product.categoryNameNoSpace}">
                                <!-- Block2 -->
                                <div class="block2">
                                    <div class="block2-pic hov-img0 img-thumbnail">
                                        <img src="${product.imageURL}"  onClick="fetchProductDetails(${product.id})" class="js-show-modal1 product-img-listing" alt="IMG-PRODUCT">

                                        <a href="#"
                                           class="block2-btn flex-c-m stext-103 cl2 size-102 bg0 bor2 hov-btn1 p-lr-15 trans-04 js-show-modal1"
                                           onClick="fetchProductDetails(${product.id})">
                                            Quick View
                                        </a>
                                    </div>

                                    <div class="block2-txt flex-w flex-t p-t-14">
                                        <div class="block2-txt-child1 flex-col-l ">
                                            <a href="" onClick="fetchProductDetails(${product.id})" class="stext-104 cl4 hov-cl1 trans-04 js-name-b2 p-b-6 js-show-modal1">
                                                    ${product.name}
                                            </a>

                                            <span class="stext-105 cl3">
                                                <fmt:formatNumber type="currency" value="${product.price}"
                                                                  maxFractionDigits="2"/>
                                            </span>
                                        </div>

<%--                                        <div class="block2-txt-child2 flex-r p-t-3">--%>
<%--                                            <a href="#" class="btn-addwish-b2 dis-block pos-relative js-addwish-b2">--%>
<%--                                                <img class="icon-heart1 dis-block trans-04"--%>
<%--                                                     src="${pageContext.request.contextPath}/public/images/icons/icon-heart-01.png"--%>
<%--                                                     alt="ICON">--%>
<%--                                                <img class="icon-heart2 dis-block trans-04 ab-t-l"--%>
<%--                                                     src="${pageContext.request.contextPath}/public/images/icons/icon-heart-02.png"--%>
<%--                                                     alt="ICON">--%>
<%--                                            </a>--%>
<%--                                        </div>--%>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>

                <!-- Load more -->
                <div class="flex-c-m flex-w w-full p-t-45">
                    <a href="${pageContext.request.contextPath}/shop"
                       class="flex-c-m stext-101 cl5 size-103 bg2 bor1 hov-btn1 p-lr-15 trans-04">
                        Show More
                    </a>
                </div>
            </div>
        </section>


        <jsp:include page="components/modals/product-details-modal.jsp"/>

        <jsp:include page="components/footer.jsp"/>


        <jsp:include page="components/common-footer-html.jsp"/>
        <script src="${pageContext.request.contextPath}/public/vendor/daterangepicker/moment.min.js"></script>
        <script src="${pageContext.request.contextPath}/public/vendor/daterangepicker/daterangepicker.js"></script>
        <script src="${pageContext.request.contextPath}/public/vendor/slick/slick.min.js"></script>
        <script src="${pageContext.request.contextPath}/public/js/slick-custom.js"></script>
        <script src="${pageContext.request.contextPath}/public/vendor/parallax100/parallax100.js"></script>
        <script>
            $('.parallax100').parallax100();
        </script>
        <script src="${pageContext.request.contextPath}/public/vendor/MagnificPopup/jquery.magnific-popup.min.js"></script>
        <script>
            $('.gallery-lb').each(function () { // the containers for all your galleries
                $(this).magnificPopup({
                    delegate: 'a', // the selector for gallery item
                    type: 'image',
                    gallery: {
                        enabled: true
                    },
                    mainClass: 'mfp-fade'
                });
            });
        </script>
        <script src="${pageContext.request.contextPath}/public/js/jsp/index.js.jsp"></script>

    </body>
</html>

