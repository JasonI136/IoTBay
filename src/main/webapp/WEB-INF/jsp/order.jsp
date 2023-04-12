<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

    <head>
        <title>Order Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <jsp:include page="components/header-links.jsp"/>
    </head>

    <body>
        <header class="header-v4">
            <div class="container-menu-desktop">
                <jsp:include page="components/header-navbar.jsp"/>
                <jsp:include page="components/main-navbar.jsp"/>
            </div>
        </header>

        <!-- Title page -->
        <div>
            <section class="bg-img1 txt-center p-lr-15 p-tb-92"
                     style="background-image: url('${pageContext.request.contextPath}/public/images/bg-orders.jpg');">
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
                        Order
                        <i class="fa fa-angle-right m-l-9 m-r-10" aria-hidden="true"></i>
                    </span>
                <span class="stext-109 cl4">
                        Order ID: ${order.id}
                    </span>
            </div>
        </div>

        <!-- Order Status -->
        <section class="bg0 p-t-20 p-b-50">
            <div class="container">
                <div class="d-flex justify-content-center align-items-center">
                    <div class="d-flex flex-column justify-content-center align-items-center">
                        <c:choose>
                        <c:when test="${order.orderStatus eq 'PROCESSING'}">
                        <h1 class="ltext-105 txt-center mb-4" style="font-size: 30px !important;">Order ID ${order.id}
                            is currently being processed</h1>
                        <div class="progress" style="height: 10px; width: 200%">
                            <div class="progress-bar bg-info" role="progressbar"
                                 style="width: 40%; margin-right: -100%" aria-valuenow="40" aria-valuemin="0"
                                 aria-valuemax="100">
                            </div>
                            </c:when>

                            <c:when test="${order.orderStatus eq 'PENDING'}">
                            <h1 class="ltext-105 txt-center mb-4" style="font-size: 30px !important;">Order
                                ID ${order.id}
                                is pending</h1>
                            <div class="progress" style="height: 10px; width: 200%">
                                <div class="progress-bar bg-warning" role="progressbar"
                                     style="width: 10%; margin-right: -100%" aria-valuenow="10" aria-valuemin="0"
                                     aria-valuemax="100">
                                </div>
                                </c:when>

                                <c:when test="${order.orderStatus eq 'SHIPPED'}">
                                <h1 class="ltext-105 txt-center mb-4" style="font-size: 30px !important;">Order
                                    ID ${order.id}
                                    is has been shipped</h1>
                                <div class="progress" style="height: 10px; width: 200%">
                                    <div class="progress-bar bg-success" role="progressbar"
                                         style="width: 70%; margin-right: -100%" aria-valuenow="70" aria-valuemin="0"
                                         aria-valuemax="100">
                                    </div>
                                    </c:when>

                                    <c:when test="${order.orderStatus eq 'DELIVERED'}">
                                    <h1 class="ltext-105 txt-center mb-4" style="font-size: 30px !important;">Order
                                        ID ${order.id}
                                        is has been delivered</h1>
                                    <div class="progress" style="height: 10px; width: 200%">
                                        <div class="progress-bar bg-success" role="progressbar"
                                             style="width: 100%; margin-right: -100%" aria-valuenow="100"
                                             aria-valuemin="0" aria-valuemax="100">
                                        </div>
                                        </c:when>

                                        <c:when test="${order.orderStatus eq 'CANCELLED'}">
                                        <h1 class="ltext-105 txt-center mb-4" style="font-size: 30px !important;">Order
                                            ID ${order.id}
                                            has been cancelled</h1>
                                        <div class="progress" style="height: 10px; width: 200%">
                                            <div class="progress-bar bg-warning" role="progressbar"
                                                 style="width: 0; margin-right: -100%" aria-valuenow="0"
                                                 aria-valuemin="0"
                                                 aria-valuemax="100">
                                            </div>
                                            </c:when>

                                            <c:otherwise>
                                            <h1 class="ltext-105 txt-center mb-4" style="font-size: 30px !important;">
                                                There
                                                seems to be a technical issue with your order</h1>
                                            <div class="progress" style="height: 10px; width: 200%">
                                                <div class="progress-bar bg-danger" role="progressbar"
                                                     style="width: 0; margin-right: -100%" aria-valuenow="0"
                                                     aria-valuemin="0"
                                                     aria-valuemax="100">
                                                </div>
                                                </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                    </div>
        </section>

        <form class="bg0 p-t-75 p-b-85">
            <div class="container">
                <div class="row">
                    <div class="col-lg-10 col-xl-7 m-lr-auto m-b-50">
                        <div class="m-l-25 m-r--38 m-lr-0-xl">
                            <div class="wrap-table-shopping-cart">
                                <table class="table-shopping-cart">
                                    <tr class="table_head">
                                        <th class="column-1">Product</th>
                                        <th class="column-2"></th>
                                        <th class="column-3">Price</th>
                                        <th class="column-4">Quantity</th>
                                        <th class="column-5">Total</th>
                                    </tr>

                                    <c:if test="${not empty orderLineItemsList}">
                                        <c:forEach var="orderLineItem" items="${orderLineItemsList}">

                                            <tr class="table_row">
                                                <td class="column-1">
                                                    <div class="how-itemcart1">
                                                        <img src="${orderLineItem.product.imageURL}}" alt="IMG">
                                                    </div>
                                                </td>
                                                <td class="column-2">${orderLineItem.product.name}</td>
                                                <td class="column-3"><fmt:formatNumber type="currency" value="${orderLineItem.product.price}" maxFractionDigits="2" /></td>
                                                <td class="column-4">${orderLineItem.quantity}</td>
                                                <td class="column-5"><fmt:formatNumber type="currency" value="${orderLineItem.price}" maxFractionDigits="2" /></td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>

                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>


        <!-- Footer -->
        <footer class="bg3 p-t-75 p-b-32">
            <jsp:include page="components/footer.jsp"/>
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
        <script
                src="${pageContext.request.contextPath}/public/vendor/MagnificPopup/jquery.magnific-popup.min.js"></script>
        <script
                src="${pageContext.request.contextPath}/public/vendor/perfect-scrollbar/perfect-scrollbar.min.js"></script>
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