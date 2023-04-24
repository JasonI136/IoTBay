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
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <jsp:include page="components/common-header-html.jsp"/>
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/public/images/icons/favicon.png"/>
    </head>
    <body class="animsition">

        <header>
            <div class="container-menu-desktop">
                <jsp:include page="components/header-navbar.jsp"/>
                <jsp:include page="components/main-navbar.jsp"/>
            </div>
        </header>

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
                                    <div class="block2-pic hov-img0">
                                        <img src="${product.imageURL}" alt="IMG-PRODUCT">

                                        <a href="#"
                                           class="block2-btn flex-c-m stext-103 cl2 size-102 bg0 bor2 hov-btn1 p-lr-15 trans-04 js-show-modal1"
                                           onClick="fetchProductDetails(${product.id})">
                                            Quick View
                                        </a>
                                    </div>

                                    <div class="block2-txt flex-w flex-t p-t-14">
                                        <div class="block2-txt-child1 flex-col-l ">
                                            <a href="product-detail.html"
                                               class="stext-104 cl4 hov-cl1 trans-04 js-name-b2 p-b-6">
                                                    ${product.name}
                                            </a>

                                            <span class="stext-105 cl3">
                                                <fmt:formatNumber type="currency" value="${product.price}"
                                                                  maxFractionDigits="2"/>
                                            </span>
                                        </div>

                                        <div class="block2-txt-child2 flex-r p-t-3">
                                            <a href="#" class="btn-addwish-b2 dis-block pos-relative js-addwish-b2">
                                                <img class="icon-heart1 dis-block trans-04"
                                                     src="${pageContext.request.contextPath}/public/images/icons/icon-heart-01.png"
                                                     alt="ICON">
                                                <img class="icon-heart2 dis-block trans-04 ab-t-l"
                                                     src="${pageContext.request.contextPath}/public/images/icons/icon-heart-02.png"
                                                     alt="ICON">
                                            </a>
                                        </div>
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


        <!-- PRODUCT MODAL -->
        <div class="wrap-modal1 js-modal1 p-t-60 p-b-20">
            <div class="overlay-modal1 js-hide-modal1"></div>

            <div class="container">
                <div class="bg0 p-t-60 p-b-30 p-lr-15-lg how-pos3-parent" id="modal-content">
                    <button class="how-pos3 hov3 trans-04 js-hide-modal1">
                        <img src="${pageContext.request.contextPath}/public/images/icons/icon-close.png" alt="CLOSE">
                    </button>

                    <div class="row">
                        <div class="col-md-6 col-lg-7 p-b-30">
                            <div class="p-l-25 p-r-30 p-lr-0-lg">
                                <div class="wrap-slick3 flex-sb flex-w">
                                    <div class="wrap-slick3-arrows flex-sb-m flex-w"></div>

                                    <div class="slick3 gallery-lb">
                                        <div class="item-slick3" data-thumb="product-image"
                                             id="img-product-modal-thumb">
                                            <div class="wrap-pic-w pos-relative">
                                                <img src="" alt="IMG-PRODUCT" id="img-product-modal">

                                                <a class="flex-c-m size-108 how-pos1 bor0 fs-16 cl10 bg0 hov-btn3 trans-04" id="product-image-full">
                                                    <i class="fa fa-expand"></i>
                                                </a>
                                            </div>
                                        </div>


                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6 col-lg-5 p-b-30">
                            <div class="p-r-50 p-t-5 p-lr-0-lg">
                                <h4 class="mtext-105 cl2 js-name-detail p-b-14" id="product-name">

                                </h4>

                                <span class="mtext-106 cl2" id="product-price">

                                </span>

                                <p class="stext-102 cl3 p-t-23" id="product-description">

                                </p>

                                <div class="flex-w flex-r-m p-b-10">
                                    <div class="size-204 flex-w flex-m respon6-next">
                                        <div class="wrap-num-product flex-w m-r-20 m-tb-10">
                                            <div class="btn-num-product-down-modal cl8 hov-btn3 trans-04 flex-c-m">
                                                <i class="fs-16 zmdi zmdi-minus"></i>
                                            </div>

                                            <input class="mtext-104 cl3 txt-center num-product" type="number"
                                                   name="num-product"
                                                   min="1" value="1" id="quantity">

                                            <div class="btn-num-product-up cl8 hov-btn3 trans-04 flex-c-m">
                                                <i class="fs-16 zmdi zmdi-plus"></i>
                                            </div>
                                        </div>

                                        <button value=""
                                                class="flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04 js-addcart-detail"
                                                name="productId" id="add-to-cart">
                                            Add to cart
                                        </button>
                                    </div>
                                </div>
                            </div>


                        </div>
                    </div>
                </div>

            </div>
        </div>

        <footer class="bg3 p-t-75 p-b-32">
            <jsp:include page="components/footer.jsp"/>
        </footer>


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

