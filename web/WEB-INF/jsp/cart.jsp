<%-- 
    Document   : cart
    Created on : 16/03/2023, 7:22:00 PM
    Author     : jasonmba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Cart</title>
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/public/images/icons/favicon.png"/>

        <jsp:include page="components/header-links.jsp" />

    </head>
    <body class="animsition">
        <header class="header-v4">
            <div class="container-menu-desktop">
                <jsp:include page="components/header-navbar.jsp" />
                <jsp:include page="components/main-navbar.jsp" />
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
                    Shopping Cart
                </span>
            </div>
        </div>


        <!-- Shoping Cart -->
        <div class="bg0 p-t-75 p-b-85">
            <div class="container">
                <div class="row">
                    <div class="col-lg-10 col-xl-7 m-lr-auto m-b-50">
                        <div class="m-l-25 m-r--38 m-lr-0-xl">
                            <c:choose>
                                <c:when test="${empty sessionScope.shoppingCart.cartItems}">
                                    <div class="empty-cart-message">
                                        <i class="fa fa-shopping-cart"></i>
                                        <p>
                                            <span class="mtext-110 cl2">Your cart is currently empty.</span>
                                        </p>

                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="wrap-table-shopping-cart">
                                        <table class="table-shopping-cart">
                                            <tr class="table_head">
                                                <th class="column-1">Product</th>
                                                <th class="column-2"></th>
                                                <th class="column-3">Price</th>
                                                <th class="column-4">Quantity</th>
                                                <th class="column-5">Total</th>
                                            </tr>
                                            <c:forEach var="cartItem" items="${sessionScope.shoppingCart.cartItems}">
                                                <tr class="table_row">
                                                    <td class="column-1">
                                                        <div class="how-itemcart1">
                                                            <img src="${cartItem.product.imageURL}" alt="IMG">
                                                        </div>
                                                    </td>
                                                    <td class="column-2">${cartItem.product.name}</td>
                                                    <td class="column-3">$ ${cartItem.product.price}</td>
                                                    <td class="column-4">
                                                        <div class="wrap-num-product flex-w m-l-auto m-r-0">
                                                            <div class="btn-num-product-down cl8 hov-btn3 trans-04 flex-c-m">
                                                                <i class="fs-16 zmdi zmdi-minus"></i>
                                                            </div>
                                                            <input class="mtext-104 cl3 txt-center num-product" id="cartItem-product-quantity" type="number" name="${cartItem.product.id}" value="${cartItem.cartQuantity}">
                                                            <div class="btn-num-product-up cl8 hov-btn3 trans-04 flex-c-m">
                                                                <i class="fs-16 zmdi zmdi-plus"></i>
                                                            </div>
                                                        </div>
                                                    </td>
                                                    <td class="column-5">$ ${cartItem.totalPrice}</td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </div>
                                    <div class="flex-w flex-sb-m bor15 p-t-18 p-b-15 p-lr-40 p-lr-15-sm">
                                        <div class="flex-c-m stext-101 cl2 size-119 bg8 bor13 hov-btn3 p-lr-15 trans-04 pointer m-tb-10" onclick="updateCart()">
                                            Update Cart
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <div class="col-sm-10 col-lg-7 col-xl-5 m-lr-auto m-b-50">
                        <div class="bor10 p-lr-40 p-t-30 p-b-40 m-l-63 m-r-40 m-lr-0-xl p-lr-15-sm">
                            <h4 class="mtext-109 cl2 p-b-30">
                                Cart Totals
                            </h4>

                            <div class="flex-w flex-t bor12 p-b-13">
                                <div class="size-208">
                                    <span class="stext-110 cl2">
                                        Subtotal:
                                    </span>
                                </div>

                                <div class="size-209">
                                    <span class="mtext-110 cl2">
                                        $${sessionScope.shoppingCart.totalPrice}
                                    </span>
                                </div>
                            </div>

                            <div class="flex-w flex-t bor12 p-t-15 p-b-30">
                                <div class="size-208">
                                    <span class="stext-110 cl2">
                                        Shipping:
                                    </span>
                                </div>

                                <div class="size-209">
                                    <span class="mtext-110 cl2">
                                        Free
                                    </span>
                                </div>
                            </div>

                            <div class="flex-w flex-t p-t-27 p-b-33">
                                <div class="size-208">
                                    <span class="mtext-101 cl2">
                                        Total:
                                    </span>
                                </div>

                                <div class="size-209 p-t-1">
                                    <span class="mtext-110 cl2">
                                        $${sessionScope.shoppingCart.totalPrice}
                                    </span>
                                </div>
                            </div>

                            <c:choose>
                                <c:when test="${empty sessionScope.shoppingCart.cartItems}">
                                    <button class="flex-c-m stext-101 cl0 size-116 bg2 bor14 p-lr-15 trans-04 pointer" disabled>
                                        Proceed to Checkout
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button class="flex-c-m stext-101 cl0 size-116 bg3 bor14 hov-btn3 p-lr-15 trans-04 pointer" onclick="location.href = '${pageContext.request.contextPath}/cart/checkout'">
                                        Proceed to Checkout
                                    </button>
                                </c:otherwise>
                            </c:choose>

                        </div>

                    </div>
                </div>
            </div>
        </div>

        <footer class="bg3 p-t-75 p-b-32">
            <jsp:include page="components/footer.jsp" />
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
                                                swal(nameProduct, "is added to cart !", "success");
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

            function updateCart() {
                const quantityInputs = document.querySelectorAll("#cartItem-product-quantity");

                const payload = {};

                quantityInputs.forEach(input => {
                    const productId = input.getAttribute("name");
                    const quantity = input.value;
                    payload[productId] = quantity;
                });

                fetch("${pageContext.request.contextPath}/cart/update", {
                    method: "POST",
                    body: JSON.stringify(payload),
                    headers: {
                        "Content-Type": "application/json"
                    }
                }).then(response => {
                    if (response.status == 200) {
                        location.reload();
                    }
                })
            }


        </script>

        <script src="${pageContext.request.contextPath}/public/js/main.js"></script>
    </body>
</html>
