<%-- 
    Document   : products
    Created on : 14 Mar 2023, 9:28:36 pm
    Author     : cmesina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Products</title>

        <jsp:include page="components/header-links.jsp"/>

    </head>
    <body class="animsition">
        <header class="header-v4">
            <div class="container-menu-desktop">
                <jsp:include page="components/header-navbar.jsp"/>
                <jsp:include page="components/main-navbar.jsp"/>
            </div>
        </header>

        <!-- breadcrumb -->
        <div class="container">
            <div class="bread-crumb flex-w p-l-25 p-r-15 p-t-30 p-lr-0-lg">
                <a href="index.html" class="stext-109 cl8 hov-cl1 trans-04">
                    Home
                    <i class="fa fa-angle-right m-l-9 m-r-10" aria-hidden="true"></i>
                </a>

                <span class="stext-109 cl4">
                    Shop
                </span>
            </div>
        </div>
        <!-- Product -->
        <section class="bg0 p-t-23 p-b-140">
            <div class="container">
                <div class="p-b-10">
                    <c:choose>
                        <c:when test="${empty searchName}">
                            <h3 class="ltext-103 cl5">
                                All Products
                            </h3>
                        </c:when>
                        <c:otherwise>
                            <h3 class="ltext-103 cl5">
                                Search Results for: ${searchName}
                            </h3>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="flex-w flex-sb-m p-b-52">
                    <div class="flex-w flex-l-m filter-tope-group m-tb-10">
                        <button class="stext-106 cl6 hov1 bor3 trans-04 m-r-32 m-tb-5 how-active1" id="allProductsBtn"
                                data-filter="*">
                            All Products
                        </button>

                        <c:forEach var="category" items="${categories}">
                            <button class="stext-106 cl6 hov1 bor3 trans-04 m-r-32 m-tb-5"
                                    data-filter=".${category.nameNoSpace}">
                                    ${category.name}
                            </button>
                        </c:forEach>
                    </div>

                    <div class="flex-w flex-c-m m-tb-10">
                        <div class="flex-c-m stext-106 cl6 size-105 bor4 pointer hov-btn3 trans-04 m-tb-4 js-show-search">
                            <i class="icon-search cl2 m-r-6 fs-15 trans-04 zmdi zmdi-search"></i>
                            <i class="icon-close-search cl2 m-r-6 fs-15 trans-04 zmdi zmdi-close dis-none"></i>
                            Search
                        </div>
                    </div>

                    <!-- Search product -->
                    <div class="dis-none panel-search w-full p-t-10 p-b-15">
                        <div class="bor8 dis-flex p-l-15">
                            <button class="size-113 flex-c-m fs-16 cl2 hov-cl1 trans-04">
                                <i class="zmdi zmdi-search"></i>
                            </button>

                            <form method="get" action="${pageContext.request.contextPath}/shop">
                                <input class="mtext-107 cl2 size-114 plh2 p-r-15" name="searchName" type="text"
                                       id="searchInput"
                                       placeholder="Search products..." value="${empty searchName ? '' : searchName}">
                            </form>

                        </div>
                    </div>
                </div>

                <div class="row isotope-grid" id="productList">

                    <c:if test="${empty products}">
                        No products found
                    </c:if>

                    <c:forEach items="${products}" var="product">
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
                    </c:forEach>

                </div>

                <c:set var="searchName" value="${empty searchName ? '' : '&searchName=' += searchName}"/>
                <nav aria-label="Page navigation example">
                    <ul class="pagination justify-content-center">

                        <%-- Handle the << and previous buttons.   --%>
                        <c:choose>
                            <%-- If the current page is the first page, disable the << and previous buttons. --%>
                            <c:when test="${currentPage == 1}">
                                <li class="page-item disabled"><a class="page-link" href="#"><<</a></li>
                                <li class="page-item disabled"><a class="page-link" href="#">&lt;</a></li>
                            </c:when>
                            <%-- If the current page is not the first page, enable the << and previous buttons. --%>
                            <c:otherwise>
                                <li><a class="page-link"
                                       href="${pageContext.request.contextPath}/shop?limit=${limit}&offset=0${searchName}"><<</a>
                                </li>
                                <li class="page-item"><a class="page-link"
                                                         href="${pageContext.request.contextPath}/shop?limit=${limit}&offset=${prevOffset}${searchName}">&lt;</a>
                                </li>
                            </c:otherwise>
                        </c:choose>

                        <%-- Handle the page numbers. --%>
                        <c:choose>
                            <%-- If the current page is less than or equal to 2, display the first 5 pages. --%>
                            <c:when test="${currentPage <= 2}">
                                <c:choose>
                                    <%-- If the number of pages is less than or equal to 5, display all the pages. --%>
                                    <c:when test="${numberOfPages <= 5}">
                                        <c:forEach var="i" begin="1" end="${numberOfPages}">
                                            <c:choose>
                                                <%-- If the current page is the same as the page number, highlight the page number. --%>
                                                <c:when test="${i == currentPage}">
                                                    <li class="page-item active"><a class="page-link"
                                                                                    href="${pageContext.request.contextPath}/shop?limit=${limit}&offset=${(i - 1) * limit}${searchName}">${i}</a>
                                                    </li>
                                                </c:when>
                                                <%-- If the current page is not the same as the page number, display the page number. --%>
                                                <c:otherwise>
                                                    <li class="page-item"><a class="page-link"
                                                                             href="${pageContext.request.contextPath}/shop?limit=${limit}&offset=${(i - 1) * limit}${searchName}">${i}</a>
                                                    </li>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </c:when>
                                    <%-- If the number of pages is greater than 5, display the first 5 pages. --%>
                                    <c:otherwise>
                                        <c:forEach var="i" begin="1" end="5">
                                            <c:choose>
                                                <%-- If the current page is the same as the page number, highlight the page number. --%>
                                                <c:when test="${i == currentPage}">
                                                    <li class="page-item active"><a class="page-link"
                                                                                    href="${pageContext.request.contextPath}/shop?limit=${limit}&offset=${(i - 1) * limit}${searchName}">${i}</a>
                                                    </li>
                                                </c:when>
                                                <%-- If the current page is not the same as the page number, display the page number. --%>
                                                <c:otherwise>
                                                    <li class="page-item"><a class="page-link"
                                                                             href="${pageContext.request.contextPath}/shop?limit=${limit}&offset=${(i - 1) * limit}${searchName}">${i}</a>
                                                    </li>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <%-- If we are near the end of the pages, display the last 5 pages. --%>
                            <c:when test="${currentPage >= numberOfPages - 2}">
                                <c:choose>
                                    <%-- If the number of pages is less than or equal to 5, display all the pages. --%>
                                    <c:when test="${numberOfPages <= 5}">
                                        <c:forEach var="i" begin="1" end="${numberOfPages}">
                                            <c:choose>
                                                <%-- If the current page is the same as the page number, highlight the page number. --%>
                                                <c:when test="${i == currentPage}">
                                                    <li class="page-item active"><a class="page-link"
                                                                                    href="${pageContext.request.contextPath}/shop?limit=${limit}&offset=${(i - 1) * limit}${searchName}">${i}</a>
                                                    </li>
                                                </c:when>
                                                <%-- If the current page is not the same as the page number, display the page number. --%>
                                                <c:otherwise>
                                                    <li class="page-item"><a class="page-link"
                                                                             href="${pageContext.request.contextPath}/shop?limit=${limit}&offset=${(i - 1) * limit}${searchName}">${i}</a>
                                                    </li>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </c:when>
                                    <%-- If the number of pages is greater than 5, display the last 5 pages. --%>
                                    <c:otherwise>
                                        <c:forEach var="i" begin="${numberOfPages - 4}" end="${numberOfPages}">
                                            <c:choose>
                                                <%-- If the current page is the same as the page number, highlight the page number. --%>
                                                <c:when test="${i == currentPage}">
                                                    <li class="page-item active"><a class="page-link"
                                                                                    href="${pageContext.request.contextPath}/shop?limit=${limit}&offset=${(i - 1) * limit}${searchName}">${i}</a>
                                                    </li>
                                                </c:when>
                                                <%-- If the current page is not the same as the page number, display the page number. --%>
                                                <c:otherwise>
                                                    <li class="page-item"><a class="page-link"
                                                                             href="${pageContext.request.contextPath}/shop?limit=${limit}&offset=${(i - 1) * limit}${searchName}">${i}</a>
                                                    </li>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <%-- If we are greater than 2 pages, display the current page as the middle button --%>
                            <c:when test="${currentPage > 2}">
                                <%-- Display the last 2 pages before the current page. --%>
                                <c:forEach var="i" begin="${currentPage - 2}" end="${currentPage - 1}">
                                    <li class="page-item"><a class="page-link"
                                                             href="${pageContext.request.contextPath}/shop?limit=${limit}&offset=${(i - 1) * limit}${searchName}">${i}</a>
                                    </li>
                                </c:forEach>
                                <%-- Display the current page. --%>
                                <li class="page-item active"><a class="page-link"
                                                                href="${pageContext.request.contextPath}/shop?limit=${limit}&offset=${(currentPage - 1) * limit}${searchName}">${currentPage}</a>
                                </li>
                                <%-- Display the next 2 pages after the current page. --%>
                                <c:forEach var="i" begin="${currentPage + 1}" end="${currentPage + 2}">
                                    <li class="page-item"><a class="page-link"
                                                             href="${pageContext.request.contextPath}/shop?limit=${limit}&offset=${(i - 1) * limit}${searchName}">${i}</a>
                                    </li>
                                </c:forEach>
                            </c:when>

                        </c:choose>

                        <%-- Display the next and last buttons. --%>
                        <c:choose>
                            <%-- If the current page is the last page, disable the next and last buttons. --%>
                            <c:when test="${currentPage == numberOfPages}">
                                <li class="page-item disabled"><a class="page-link" href="#">&gt;</a></li>
                                <li class="page-item disabled"><a class="page-link" href="#">>></a></li>
                            </c:when>
                            <%-- If the current page is not the last page, display the next and last buttons. --%>
                            <c:otherwise>
                                <li class="page-item"><a class="page-link"
                                                         href="${pageContext.request.contextPath}/shop?limit=${limit}&offset=${nextOffset}${searchName}">&gt;</a>
                                </li>
                                <li class="page-item"><a class="page-link"
                                                         href="${pageContext.request.contextPath}/shop?limit=${limit}&offset=${lastOffset}${searchName}">>></a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </nav>


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

                                                <a class="flex-c-m size-108 how-pos1 bor0 fs-16 cl10 bg0 hov-btn3 trans-04"
                                                   href="${pageContext.request.contextPath}/public/images/product-detail-01.jpg">
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
                                                   id="quantity"
                                                   oninput="this.value = Math.abs(this.value)" min="1"/>
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
    </body>

    <footer class="bg3 p-t-75 p-b-32">
        <jsp:include page="components/footer.jsp"/>
    </footer>


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

    <script src="${pageContext.request.contextPath}/public/vendor/isotope/isotope.pkgd.min.js"></script>


    <script src="${pageContext.request.contextPath}/public/vendor/sweetalert/sweetalert.min.js"></script>
    <script>
        $('.js-addwish-b2, .js-addwish-detail').on('click', function (e) {
            e.preventDefault();
        });

        $('.js-addwish-b2').each(function () {
            var nameProduct = $(this).parent().parent().find('.js-name-b2').html();
            $(this).on('click', function () {
                swal(nameProduct, "is added to wishlist !", "success");

                $(this).addClass('js-addedwish-b2');
                $(this).off('click');
            });
        });

        $('.js-addwish-detail').each(function () {
            var nameProduct = $(this).parent().parent().parent().find('.js-name-detail').html();

            $(this).on('click', function () {
                swal(nameProduct, "is added to wishlist !", "success");

                $(this).addClass('js-addedwish-detail');
                $(this).off('click');
            });
        });

        /*---------------------------------------------*/

        $('.js-addcart-detail').each(function () {
            var nameProduct = $(this).parent().parent().parent().parent().find('.js-name-detail').html();
            $(this).on('click', function () {
                addToCart(document.querySelector('#add-to-cart').value, document.querySelector('#quantity').value)
                    .then(response => {
                        return response.json();
                    }).then(json => {
                    if (json.statusCode === 200) {
                        Swal.fire({
                            title: json.message,
                            icon: 'success',
                            text: json.data,
                            showCancelButton: false,
                            confirmButtonColor: '#3085d6',
                            confirmButtonText: 'OK',
                            target: document.querySelector('#modal-content')
                        }).then((result) => {
                            if (result.isConfirmed) {
                                window.location.href = "${pageContext.request.contextPath}/shop";
                            }
                        });
                    } else {
                        Swal.fire({
                            title: json.message,
                            icon: 'error',
                            text: json.data,
                            showCancelButton: false,
                            confirmButtonColor: '#3085d6',
                            confirmButtonText: 'OK',
                            target: document.querySelector('#modal-content')
                        });
                    }
                }).catch(error => {
                    Swal.fire({
                        title: 'Error',
                        icon: 'error',
                        text: 'Something went wrong!',
                        showCancelButton: false,
                        confirmButtonColor: '#3085d6',
                        confirmButtonText: 'OK',
                        target: document.querySelector('#modal-content')
                    });
                });
            });
        });

    </script>

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

    <script>

        function fetchProductDetails(productId) {
            fetch('${pageContext.request.contextPath}/product/' + productId)
                .then(response => response.json())
                .then(json => {
                    document.querySelector('#product-name').innerHTML = json.name;
                    document.querySelector('#img-product-modal').src = json.imageURL;
                    document.querySelector('#product-description').innerHTML = json.description;
                    document.querySelector('#product-price').innerHTML = "$ " + json.price;
                    document.querySelector('#add-to-cart').value = json.id;
                });
        }

        function addToCart(productId, quantity) {
            const data = new URLSearchParams()
            data.append('productId', productId);
            data.append('quantity', quantity);
            return fetch('${pageContext.request.contextPath}/cart', {
                method: 'POST',
                body: data
            })

        }

    </script>


    <script src="${pageContext.request.contextPath}/public/js/main.js"></script>
</html>
