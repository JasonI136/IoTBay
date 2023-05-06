<%-- 
    Document   : register
    Created on : 12 Mar 2023, 2:14:59 pm
    Author     : cmesina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Register</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <jsp:include page="components/common-header-html.jsp"/>
    </head>
    <body>
        <!-- Header -->
        <jsp:include page="components/navbar/master-navbar.jsp"/>

        <!-- Title page -->
        <div>
            <section class="bg-img1 txt-center p-lr-15 p-tb-92"
                     style="background-image: url('${pageContext.request.contextPath}/public/images/bg-02.jpg');">
                <h2 class="ltext-105 cl0 txt-center">
                    Sign up for IoTBay
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
                    Register
                </span>
            </div>
        </div>

        <!-- Content page -->
        <section class="bg0 p-t-104 p-b-116">
            <div class="container">
                <div class="bor10 p-lr-70 p-t-55 p-b-70 p-lr-15-lg w-full-md">
                    <form method="POST" action="register">
                        <h4 class="mtext-105 cl2 txt-center p-b-30">
                            Register
                        </h4>

                        <c:if test="${demo == true}">
                            <div class="row alert alert-warning" role="alert">
                                <span>
                                    <i class="fa-solid fa-triangle-exclamation"></i>
                                    This is a demo website. Please do not enter any sensitive information. Website is reset
                                    every 24 hours.
                                </span>
                            </div>
                        </c:if>

                        <div class="row">
                            <div class="col bor8 m-b-20 how-pos4-parent">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" id="username"
                                       name="username" required placeholder="Your User Name">
                                <img class="how-pos4 pointer-none"
                                     src="${pageContext.request.contextPath}/public/images/icons/user.svg" alt="ICON">
                            </div>
                        </div>

                        <div class="row" style="gap: 10px">
                            <div class="col bor8 m-b-20 how-pos4-parent">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" id="firstName"
                                       name="firstName" required placeholder="Your First Name">
                                <img class="how-pos4 pointer-none"
                                     src="${pageContext.request.contextPath}/public/images/icons/user.svg" alt="ICON">
                            </div>
                            <div class="col bor8 m-b-20 how-pos4-parent">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" id="lastName"
                                       name="lastName" required placeholder="Your Last Name">
                                <img class="how-pos4 pointer-none"
                                     src="${pageContext.request.contextPath}/public/images/icons/user.svg" alt="ICON">
                            </div>
                        </div>

                        <div class="row">
                            <div class="col bor8 m-b-20 how-pos4-parent">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="email" id="email"
                                       name="email" required placeholder="Your Email">
                                <img class="how-pos4 pointer-none"
                                     src="${pageContext.request.contextPath}/public/images/icons/at-sign.svg"
                                     alt="ICON">
                            </div>
                        </div>


                        <div class="row">
                            <div class="col bor8 m-b-20 how-pos4-parent">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="password" id="password"
                                       name="password" required placeholder="Your Password">
                                <img class="how-pos4 pointer-none"
                                     src="${pageContext.request.contextPath}/public/images/icons/lock.svg" alt="ICON">
                            </div>
                        </div>


                        <div class="row">
                            <div class="col bor8 m-b-20 how-pos4-parent">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" id="address"
                                       name="address" required placeholder="Your Address">
                                <img class="how-pos4 pointer-none"
                                     src="${pageContext.request.contextPath}/public/images/icons/map-pin.svg"
                                     alt="ICON">
                            </div>
                        </div>


                        <div class="row">
                            <div class="col bor8 m-b-20 how-pos4-parent">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="tel" id="phone"
                                       name="phone" required placeholder="Your Phone Number">
                                <img class="how-pos4 pointer-none"
                                     src="${pageContext.request.contextPath}/public/images/icons/phone.svg" alt="ICON">
                            </div>
                        </div>


                        <div class="">
                            <button type="submit"
                                    class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer">
                                Create an Account
                            </button>
                        </div>

                    </form>
                </div>


            </div>
        </section>

        <c:if test="${(not empty success_title) or (not empty success_msg)}">
            <script>
                swal.fire({
                    title: '${success_title}',
                    icon: 'success',
                    text: '${success_msg}',
                    showCancelButton: false,
                    confirmButtonColor: '#3085d6',
                    confirmButtonText: 'OK'
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = "${pageContext.request.contextPath}/user";
                    }
                });
            </script>
        </c:if>

        <c:if test="${(not empty error_title) or (not empty error_msg)}">
            <script>
                swal.fire({
                    title: '${error_title}',
                    icon: 'error',
                    text: '${error_msg}',
                    showCancelButton: false,
                    confirmButtonColor: '#3085d6',
                    confirmButtonText: 'OK'
                });
            </script>
        </c:if>

        <!-- Footer -->
        <jsp:include page="components/footer.jsp"/>


        <!-- Back to top -->
        <div class="btn-back-to-top" id="myBtn">
            <span class="symbol-btn-back-to-top">
                <i class="zmdi zmdi-chevron-up"></i>
            </span>
        </div>


        <jsp:include page="components/common-footer-html.jsp"/>

        <script src="https://challenges.cloudflare.com/turnstile/v0/api.js" async defer></script>
        <script>
            window.turnstileCallbackFunction = function () {
                const turnstileOptions = {
                    sitekey: '0x4AAAAAAADUHrspn0fJpmst',
                    callback: function (token) {
                        console.log(`Challenge Success: ${token}`);
                    }
                };
                turnstile.render('#container', turnstileOptions);
            };
        </script>
    </body>
</html>
