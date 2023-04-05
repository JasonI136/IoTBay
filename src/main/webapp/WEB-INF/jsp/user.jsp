<%-- 
    Document   : user
    Created on : 14 Mar 2023, 5:53:56 pm
    Author     : cmesina
--%>

<%@page import="iotbay.models.entities.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>User</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <jsp:include page="components/header-links.jsp" />
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
                     style="background-image: url('${pageContext.request.contextPath}/public/images/bg-02.jpg');">
                <h2 class="ltext-105 cl0 txt-center">
                    Welcome ${user.firstName} ${user.lastName}
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
                    User Management
                </span>
            </div>
        </div>
        <!-- Content page -->
        <section class="bg0 p-t-104 p-b-116">
            <div class="container">
                <div class="flex-w flex-tr">
                    <div class="size-210 bor10 p-lr-70 p-t-55 p-b-70 p-lr-15-lg w-full-md">

                        <h4 class="mtext-105 cl2 txt-center p-b-30">
                            Your Account Details
                        </h4>
                        <div class="row">
                            <div class="col bor8 m-b-20 how-pos4-parent">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" id="username"
                                       name="username" required placeholder="Your User Name" value='${user.username}'
                                       disabled>
                                <img class="how-pos4 pointer-none"
                                     src="${pageContext.request.contextPath}/public/images/icons/user.svg" alt="ICON">
                            </div>
                        </div>

                        <div class="row" style="gap: 10px">
                            <div class="bor8 m-b-20 how-pos4-parent col">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" id="firstName"
                                       name="firstName" required placeholder="Your First Name" value='${user.firstName}'
                                       disabled>
                                <img class="how-pos4 pointer-none"
                                     src="${pageContext.request.contextPath}/public/images/icons/user.svg" alt="ICON">
                            </div>
                            <div class="bor8 m-b-20 how-pos4-parent col">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" id="lastName"
                                       name="lastName" required placeholder="Your Last Name" value='${user.lastName}'
                                       disabled>
                                <img class="how-pos4 pointer-none"
                                     src="${pageContext.request.contextPath}/public/images/icons/user.svg" alt="ICON">
                            </div>
                        </div>

                        <div class="row">
                            <div class="col bor8 m-b-20 how-pos4-parent">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="email" id="email" name="email"
                                       required placeholder="Your Email" value='${user.email}' disabled>
                                <img class="how-pos4 pointer-none"
                                     src="${pageContext.request.contextPath}/public/images/icons/at-sign.svg" alt="ICON">
                            </div>
                        </div>


                        <div class="row">
                            <div class="col bor8 m-b-20 how-pos4-parent">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" id="address" name="address"
                                       required placeholder="Your Address" value='${user.address}' disabled>
                                <img class="how-pos4 pointer-none"
                                     src="${pageContext.request.contextPath}/public/images/icons/map-pin.svg" alt="ICON">
                            </div>
                        </div>


                        <div class="row">
                            <div class="col bor8 m-b-20 how-pos4-parent">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="tel" id="phone" name="phone"
                                       required placeholder="Your Phone Number" value='${user.phoneNumber}' disabled>
                                <img class="how-pos4 pointer-none"
                                     src="${pageContext.request.contextPath}/public/images/icons/phone.svg" alt="ICON">
                            </div>
                        </div>

                        <div class="row">
                            <button href="" class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer"
                                    onclick="toggleEdit()">
                                Edit Account Details
                            </button>
                        </div>

                        <hr>
                        <h4 class="mtext-105 cl2 txt-center p-b-30">
                            Payment Methods
                        </h4>

                        <c:if test="${empty sessionScope.user.paymentMethods}">
                            <div class="row">
                                <h2 class="mtext-105 cl2 p-b-30 txt-center">No available payments.</h2>
                            </div>
                        </c:if>
                        <c:forEach items="${sessionScope.user.paymentMethods}" var="paymentmethod">
                            <div class="row">
                                <div class="col-md-6">
                                    <h6 class="mtext-105 cl2 p-b-30"><b>${paymentmethod.paymentMethodType.toUpperCase()}</b> ${paymentmethod.cardLast4} </h6>
                                </div>
                                <div class="col-md-6">
                                    <form action="${pageContext.request.contextPath}/user/payments/remove" method="post" onsubmit="return confirm('Are you sure you want to delete this payment method?')">
                                        <button type="submit" name="paymentMethodId" value="${paymentmethod.id}" class="flex-c-m stext-101 cl0 size-121 bg3 bor4 hov-btn3 p-lr-15 trans-04 pointer">
                                            Delete
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </c:forEach>
                        <form action="${pageContext.request.contextPath}/user/payments/add" method="post">
                            <button type="submit"
                                    class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer">
                                Add Payment Method
                            </button>
                        </form>
                    </div>

                    <!--ORDERS-->
                    <div class="size-210 bor10 p-lr-70 p-t-55 p-b-70 p-lr-15-lg w-full-md">
                        <h4 class="mtext-105 cl2 txt-center p-b-30">
                            Your Orders
                        </h4>

                        <c:forEach var="order" items="${sessionScope.user.orders}">
                            <div class="row">
                                <form class="col" method="post" action="orderTracking">
                                    <button type="submit" name="orderid" value="${order.id}" class="flex-c-m stext-101 cl0 size-121 bg3 bor4 hov-btn3 p-lr-15 trans-04 pointer">
                                    ${order.id} | ${order.orderDate} | ${order.orderStatus.toString()}
                                    </button>
                                </form>

                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </section>

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

        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAKFWBqlKAGCeS1rMVoaNlwyayu0e0YRes"></script>
        <script src="${pageContext.request.contextPath}/public/js/map-custom.js"></script>

        <script>
                                        function toggleEdit() {
                                            var inputs = document.getElementsByTagName("input");
                                            for (var i = 0; i < inputs.length; i++) {
                                                if (inputs[i].getAttribute("disabled")) {
                                                    inputs[i].removeAttribute("disabled");
                                                } else {
                                                    inputs[i].setAttribute("disabled", "disabled");
                                                }
                                            }
                                        }
        </script>

        <script src="${pageContext.request.contextPath}/public/js/main.js"></script>
    </body>
</html>