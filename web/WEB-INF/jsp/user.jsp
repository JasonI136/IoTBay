<%-- 
    Document   : user
    Created on : 14 Mar 2023, 5:53:56 pm
    Author     : cmesina
--%>

<%@page import="iotbay.models.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>


        <title>Register</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/public/images/icons/favicon.png"/>
        <link rel="stylesheet" type="text/css"
              href="${pageContext.request.contextPath}/public/vendor/bootstrap/css/bootstrap.min.css">

        <link rel="stylesheet" type="text/css"
              href="${pageContext.request.contextPath}/public/fonts/iconic/css/material-design-iconic-font.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/vendor/animate/animate.css">
        <link rel="stylesheet" type="text/css"
              href="${pageContext.request.contextPath}/public/vendor/css-hamburgers/hamburgers.min.css">
        <link rel="stylesheet" type="text/css"
              href="${pageContext.request.contextPath}/public/vendor/animsition/css/animsition.min.css">
        <link rel="stylesheet" type="text/css"
              href="${pageContext.request.contextPath}/public/vendor/select2/select2.min.css">
        <link rel="stylesheet" type="text/css"
              href="${pageContext.request.contextPath}/public/vendor/daterangepicker/daterangepicker.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/vendor/slick/slick.css">
        <link rel="stylesheet" type="text/css"
              href="${pageContext.request.contextPath}/public/vendor/MagnificPopup/magnific-popup.css">
        <link rel="stylesheet" type="text/css"
              href="${pageContext.request.contextPath}/public/vendor/perfect-scrollbar/perfect-scrollbar.css">
        <link rel="stylesheet" type="text/css"
              href="${pageContext.request.contextPath}/public/fonts/linearicons-v1.0.0/icon-font.min.css">
        <link rel="stylesheet" type="text/css"
              href="${pageContext.request.contextPath}/public/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/main.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/util.css">
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
                    Welcome <%=user.getFirstName()%> <%=user.getLastName()%>
                </h2>
            </section>
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
                            <div class="bor8 m-b-20 how-pos4-parent">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" id="username"
                                       name="username" required placeholder="Your User Name" value='<%=user.getUsername()%>'
                                       disabled>
                                <img class="how-pos4 pointer-none"
                                     src="${pageContext.request.contextPath}/public/images/icons/user.svg" alt="ICON">
                            </div>
                        </div>

                        <div class="row">
                            <div class="bor8 m-b-20 how-pos4-parent col-sm">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" id="firstName"
                                       name="firstName" required placeholder="Your First Name" value='<%=user.getFirstName()%>'
                                       disabled>
                                <img class="how-pos4 pointer-none"
                                     src="${pageContext.request.contextPath}/public/images/icons/user.svg" alt="ICON">
                            </div>
                            <div class="bor8 m-b-20 how-pos4-parent col-sm">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" id="lastName"
                                       name="lastName" required placeholder="Your Last Name" value='<%=user.getLastName()%>'
                                       disabled>
                                <img class="how-pos4 pointer-none"
                                     src="${pageContext.request.contextPath}/public/images/icons/user.svg" alt="ICON">
                            </div>
                        </div>

                        <div class="row">
                            <div class="bor8 m-b-20 how-pos4-parent">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="email" id="email" name="email"
                                       required placeholder="Your Email" value='<%=user.getEmail()%>' disabled>
                                <img class="how-pos4 pointer-none"
                                     src="${pageContext.request.contextPath}/public/images/icons/at-sign.svg" alt="ICON">
                            </div>
                        </div>


                        <div class="row">
                            <div class="bor8 m-b-20 how-pos4-parent">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" id="address" name="address"
                                       required placeholder="Your Address" value='<%=user.getAddress()%>' disabled>
                                <img class="how-pos4 pointer-none"
                                     src="${pageContext.request.contextPath}/public/images/icons/map-pin.svg" alt="ICON">
                            </div>
                        </div>


                        <div class="row">
                            <div class="bor8 m-b-20 how-pos4-parent">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="tel" id="phone" name="phone"
                                       required placeholder="Your Phone Number" value='<%=user.getPhoneNumber()%>' disabled>
                                <img class="how-pos4 pointer-none"
                                     src="${pageContext.request.contextPath}/public/images/icons/phone.svg" alt="ICON">
                            </div>
                        </div>

                            
                        <button href="" class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer" onclick="toggleEdit()">
                            Edit Account Details
                        </button>

                    </div>

                    <div class="size-210 bor10 p-lr-70 p-t-55 p-b-70 p-lr-15-lg w-full-md">
                        <h4 class="mtext-105 cl2 txt-center p-b-30">
                            Your Orders
                        </h4>


                        <div class="row">
                            <button class="flex-c-m stext-101 cl0 size-121 bg3 bor4 hov-btn3 p-lr-15 trans-04 pointer">
                                Order ID | DATE | ITEM #
                            </button>
                        </div>
                        <div class="row">
                            <button class="flex-c-m stext-101 cl0 size-121 bg3 bor4 hov-btn3 p-lr-15 trans-04 pointer">
                                Order ID | DATE | ITEM #
                            </button>
                        </div>
                        <div class="row">
                            <button class="flex-c-m stext-101 cl0 size-121 bg3 bor4 hov-btn3 p-lr-15 trans-04 pointer">
                                Order ID | DATE | ITEM #
                            </button>
                        </div>


                    </div>

                    <div>
                        <h1>Payment Methods</h1>
                        <ul>
                            <c:forEach items="${sessionScope.user.paymentMethods}" var="paymentmethod">
                                <li>Stripe Payment Method ID: ${paymentmethod.stripePaymentMethodId}</li>
                                </c:forEach>
                        </ul>

                        <form action="${pageContext.request.contextPath}/user/addpaymentmethod" method="post">
                            <button type="submit"  class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer">
                                Add Payment Method
                            </button>
                        </form>



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