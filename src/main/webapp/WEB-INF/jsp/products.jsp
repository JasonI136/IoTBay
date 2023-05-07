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

        <jsp:include page="components/common-header-html.jsp"/>

    </head>
    <body class="animsition">
        <!-- Header -->
        <jsp:include page="components/navbar/master-navbar.jsp"/>

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
                <c:if test="${demo == true}">
                    <div class="row alert alert-warning m-l-1 m-r-1" role="alert">
                        <span>
                            <i class="fa-solid fa-triangle-exclamation"></i>
                            This is a demo website. Items on this website are <b><u>not for sale</u></b>. Website is reset every 24 hours.
                        </span>
                    </div>
                </c:if>
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
                                <div class="block2-pic hov-img0 js-show-modal1" onClick="fetchProductDetails(${product.id})">
                                    <img src="${product.imageURL}" alt="IMG-PRODUCT">

                                    <a href="#"
                                       class="block2-btn flex-c-m stext-103 cl2 size-102 bg0 bor2 hov-btn1 p-lr-15 trans-04 js-show-modal1"
                                       onClick="fetchProductDetails(${product.id})">
                                        Quick View
                                    </a>
                                </div>

                                <div class="block2-txt flex-w flex-t p-t-14">
                                    <div class="block2-txt-child1 flex-col-l ">
                                        <a href="#" onClick="fetchProductDetails(${product.id})"
                                           class="stext-104 cl4 hov-cl1 trans-04 js-name-b2 p-b-6 js-show-modal1">
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
                            <c:when test="${paginationHandler.currentPage == 1}">
                                <li class="page-item disabled"><a class="page-link" href="#"><<</a></li>
                                <li class="page-item disabled"><a class="page-link" href="#">&lt;</a></li>
                            </c:when>
                            <%-- If the current page is not the first page, enable the << and previous buttons. --%>
                            <c:otherwise>
                                <li><a class="page-link"
                                       href="${pageContext.request.contextPath}/shop?limit=${paginationHandler.pageSize}&page=1${searchName}"><<</a>
                                </li>
                                <li class="page-item"><a class="page-link"
                                                         href="${pageContext.request.contextPath}/shop?limit=${paginationHandler.pageSize}&page=${paginationHandler.currentPage - 1}${searchName}">&lt;</a>
                                </li>
                            </c:otherwise>
                        </c:choose>

                        <%-- Handle the page numbers. --%>
                        <c:choose>
                            <%-- If the current page is less than or equal to 2, display the first 5 pages. --%>
                            <c:when test="${paginationHandler.currentPage <= 2}">
                                <c:choose>
                                    <%-- If the number of pages is less than or equal to 5, display all the pages. --%>
                                    <c:when test="${paginationHandler.totalPages <= 5}">
                                        <c:forEach var="i" begin="1" end="${paginationHandler.totalPages}">
                                            <c:choose>
                                                <%-- If the current page is the same as the page number, highlight the page number. --%>
                                                <c:when test="${i == paginationHandler.currentPage}">
                                                    <li class="page-item active"><a class="page-link"
                                                                                    href="${pageContext.request.contextPath}/shop?limit=${paginationHandler.pageSize}&page=${i}${searchName}">${i}</a>
                                                    </li>
                                                </c:when>
                                                <%-- If the current page is not the same as the page number, display the page number. --%>
                                                <c:otherwise>
                                                    <li class="page-item"><a class="page-link"
                                                                             href="${pageContext.request.contextPath}/shop?limit=${paginationHandler.pageSize}&page=${i}${searchName}">${i}</a>
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
                                                <c:when test="${i == paginationHandler.currentPage}">
                                                    <li class="page-item active"><a class="page-link"
                                                                                    href="${pageContext.request.contextPath}/shop?limit=${paginationHandler.pageSize}&page=${i}${searchName}">${i}</a>
                                                    </li>
                                                </c:when>
                                                <%-- If the current page is not the same as the page number, display the page number. --%>
                                                <c:otherwise>
                                                    <li class="page-item"><a class="page-link"
                                                                             href="${pageContext.request.contextPath}/shop?limit=${paginationHandler.pageSize}&page=${i}${searchName}">${i}</a>
                                                    </li>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <%-- If we are near the end of the pages, display the last 5 pages. --%>
                            <c:when test="${paginationHandler.currentPage >= paginationHandler.totalPages - 2}">
                                <c:choose>
                                    <%-- If the number of pages is less than or equal to 5, display all the pages. --%>
                                    <c:when test="${paginationHandler.totalPages <= 5}">
                                        <c:forEach var="i" begin="1" end="${paginationHandler.totalPages}">
                                            <c:choose>
                                                <%-- If the current page is the same as the page number, highlight the page number. --%>
                                                <c:when test="${i == paginationHandler.currentPage}">
                                                    <li class="page-item active"><a class="page-link"
                                                                                    href="${pageContext.request.contextPath}/shop?limit=${paginationHandler.pageSize}&page=${i}${searchName}">${i}</a>
                                                    </li>
                                                </c:when>
                                                <%-- If the current page is not the same as the page number, display the page number. --%>
                                                <c:otherwise>
                                                    <li class="page-item"><a class="page-link"
                                                                             href="${pageContext.request.contextPath}/shop?limit=${paginationHandler.pageSize}&page=${i}${searchName}">${i}</a>
                                                    </li>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </c:when>
                                    <%-- If the number of pages is greater than 5, display the last 5 pages. --%>
                                    <c:otherwise>
                                        <c:forEach var="i" begin="${paginationHandler.totalPages - 4}"
                                                   end="${paginationHandler.totalPages}">
                                            <c:choose>
                                                <%-- If the current page is the same as the page number, highlight the page number. --%>
                                                <c:when test="${i == paginationHandler.currentPage}">
                                                    <li class="page-item active"><a class="page-link"
                                                                                    href="${pageContext.request.contextPath}/shop?limit=${paginationHandler.pageSize}&page=${i}${searchName}">${i}</a>
                                                    </li>
                                                </c:when>
                                                <%-- If the current page is not the same as the page number, display the page number. --%>
                                                <c:otherwise>
                                                    <li class="page-item"><a class="page-link"
                                                                             href="${pageContext.request.contextPath}/shop?limit=${paginationHandler.pageSize}&page=${i}${searchName}">${i}</a>
                                                    </li>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <%-- If we are greater than 2 pages, display the current page as the middle button --%>
                            <c:when test="${paginationHandler.currentPage > 2}">
                                <%-- Display the last 2 pages before the current page. --%>
                                <c:forEach var="i" begin="${paginationHandler.currentPage - 2}"
                                           end="${paginationHandler.currentPage - 1}">
                                    <li class="page-item"><a class="page-link"
                                                             href="${pageContext.request.contextPath}/shop?limit=${paginationHandler.pageSize}&page=${i}${searchName}">${i}</a>
                                    </li>
                                </c:forEach>
                                <%-- Display the current page. --%>
                                <li class="page-item active"><a class="page-link"
                                                                href="${pageContext.request.contextPath}/shop?limit=${paginationHandler.pageSize}&page=${paginationHandler.currentPage}${searchName}">${paginationHandler.currentPage}</a>
                                </li>
                                <%-- Display the next 2 pages after the current page. --%>
                                <c:forEach var="i" begin="${paginationHandler.currentPage + 1}"
                                           end="${paginationHandler.currentPage + 2}">
                                    <li class="page-item"><a class="page-link"
                                                             href="${pageContext.request.contextPath}/shop?limit=${paginationHandler.pageSize}&page=${i}${searchName}">${i}</a>
                                    </li>
                                </c:forEach>
                            </c:when>

                        </c:choose>

                        <%-- Display the next and last buttons. --%>
                        <c:choose>
                            <%-- If the current page is the last page, disable the next and last buttons. --%>
                            <c:when test="${paginationHandler.currentPage == paginationHandler.totalPages}">
                                <li class="page-item disabled"><a class="page-link" href="#">&gt;</a></li>
                                <li class="page-item disabled"><a class="page-link" href="#">>></a></li>
                            </c:when>
                            <%-- If the current page is not the last page, display the next and last buttons. --%>
                            <c:otherwise>
                                <li class="page-item"><a class="page-link"
                                                         href="${pageContext.request.contextPath}/shop?limit=${paginationHandler.pageSize}&page=${paginationHandler.currentPage + 1}${searchName}">&gt;</a>
                                </li>
                                <li class="page-item"><a class="page-link"
                                                         href="${pageContext.request.contextPath}/shop?limit=${paginationHandler.pageSize}&page=${paginationHandler.totalPages}${searchName}">>></a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </nav>


            </div>
        </section>

        <jsp:include page="components/modals/product-details-modal.jsp"/>
    </body>

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
    <script src="${pageContext.request.contextPath}/public/js/jsp/products.js.jsp"></script>
</html>
