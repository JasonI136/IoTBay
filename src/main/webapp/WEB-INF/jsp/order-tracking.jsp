<%-- 
    Document   : order-tracking
    Created on : 19/03/2023, 10:00:05 AM
    Author     : jasonmba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Track Orders</title>
        <meta charset="UTF-8">


        <jsp:include page="components/common-header-html.jsp"/>
    </head>
    <body>
        <!-- Header -->
        <jsp:include page="components/navbar/master-navbar.jsp"/>

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
                        Track Order
                    </span>
            </div>
        </div>
        <!-- Content page -->
        <section class="bg0 p-t-104 p-b-116">
            <div class="container">


                <div class="flex-w flex-tr">
                    <div class="size-210 bor10 p-lr-70 p-t-55 p-b-70 p-lr-15-lg w-full-md">


                        <form method="post" action="orderTracking">
                            <h4 class="mtext-105 cl2 txt-center p-b-30">
                                Find your Order
                            </h4>

                            <div class="bor8 m-b-20 how-pos4-parent">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" name="lastname"
                                       id="form3Example3" placeholder="Your Last Name">
                                <img class="how-pos4 pointer-none"
                                     src="${pageContext.request.contextPath}/public/images/icons/user.svg" alt="ICON">
                            </div>

                            <div class="bor8 m-b-30 how-pos4-parent">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="number" name="orderid"
                                       id="form3Example4" placeholder="Your Order ID Number">
                                <img class="how-pos4 pointer-none"
                                     src="${pageContext.request.contextPath}/public/images/icons/package.svg"
                                     alt="ICON">
                            </div>

                            <button class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer">
                                Search
                            </button>
                        </form>


                    </div>

                    <div class="size-210 bor10 p-lr-70 p-t-55 p-b-70 p-lr-15-lg w-full-md">
                        <h4 class="mtext-105 cl2 txt-center p-b-30">
                            How does this work?
                        </h4>
                        <div class="flex-w w-full p-b-25">
                                <span class="fs-18 cl5 txt-center size-211">
                                    <span class="lnr lnr-chevron-right"></span>
                                </span>

                            <div class="size-212 p-t-2">
                                    <span class="mtext-110 cl2">
                                        Input your Last name and Order ID
                                    </span>
                            </div>
                        </div>

                        <div class="flex-w w-full p-b-25">
                                <span class="fs-18 cl5 txt-center size-211">
                                    <span class="lnr lnr-chevron-right"></span>
                                </span>

                            <div class="size-212 p-t-2">
                                    <span class="mtext-110 cl2">
                                        Get the Status of your Order
                                    </span>


                            </div>
                        </div>

                        <div class="flex-w w-full">
                                <span class="fs-18 cl5 txt-center size-211">
                                    <span class="lnr lnr-envelope"></span>
                                </span>

                            <div class="size-212 p-t-2">
                                    <span class="mtext-110 cl2">
                                        Customer Support
                                    </span>

                                <p class="stext-115 cl1 size-213 p-t-18">
                                    support@iotbay.com
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

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
        <script src="${pageContext.request.contextPath}/public/vendor/isotope/isotope.pkgd.min.js"></script>


    </body>
</html>
