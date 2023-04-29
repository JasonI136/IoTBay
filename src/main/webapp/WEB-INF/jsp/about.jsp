<%-- 
    Document   : about
    Created on : 19/03/2023, 11:49:21 PM
    Author     : jasonmba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD"
              crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
                crossorigin="anonymous"></script>


        <title>About Us</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <jsp:include page="components/common-header-html.jsp"/>

    </head>
    <body class="animsition">

        <!-- Header -->
        <jsp:include page="components/navbar/master-navbar.jsp"/>


        <!-- Title page -->
        <section class="bg-img1 txt-center p-lr-15 p-tb-92"
                 style="background-image: url('${pageContext.request.contextPath}/public/images/bg-orders.jpg');">
            <h2 class="ltext-105 cl0 txt-center">
                About IoTBay
            </h2>
        </section>


        <!-- Content page -->
        <section class="bg0 p-t-75 p-b-120">
            <div class="container">
                <div class="row p-b-148">
                    <div class="col-md-7 col-lg-8">
                        <div class="p-t-7 p-r-85 p-r-15-lg p-r-0-md">
                            <h3 class="mtext-111 cl2 p-b-16">
                                Who are we?
                            </h3>

                            <p class="stext-113 cl6 p-b-26">

                                IoTBay - Internet of Things Bay - is the culmination of 30 years in information
                                technology business. Dennis & Brian built the business based on the simple principles of
                                impeccable service and passion for technology, we are also known for the wonderful brick
                                and mortar store located in Sydney. Exceeding peoples expectations is our aim, and where
                                else better to do so than on one of the world’s most technologically advanced cities!
                            </p>

                            <p class="stext-113 cl6 p-b-26">

                            </p>

                            <p class="stext-113 cl6 p-b-26">
                                Any questions? Let us know in store, by email at iotbay@sales.com or phone +61 861 654
                                222
                            </p>
                        </div>
                    </div>

                    <div class="col-11 col-md-5 col-lg-4 m-lr-auto">
                        <div class="how-bor1 ">
                            <div class="hov-img0">
                                <img src="${pageContext.request.contextPath}/public/images/raspberrypi.jpg" alt="IMG">
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="order-md-2 col-md-7 col-lg-8 p-b-30">
                        <div class="p-t-7 p-l-85 p-l-15-lg p-l-0-md">

                            <div class="bor16 p-l-29 p-b-9 m-t-22">
                                <p class="stext-114 cl6 p-r-40 p-b-11">
                                    The most important single aspect of software development is to be clear about what
                                    you are trying to build.
                                </p>

                                <span class="stext-111 cl8">
                                    - Bjarne Stroustrup
                                </span>
                            </div>
                        </div>
                    </div>

                    <!--
                    <div class="order-md-1 col-11 col-md-5 col-lg-4 m-lr-auto p-b-30">
                            <div class="how-bor2">
                                    <div class="hov-img0">
                                            <img src="${pageContext.request.contextPath}/public/raspberrypi.jpg" alt="IMG">
                                    </div>
                            </div>
                    </div>
                    -->
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

        <jsp:include page="components/common-footer-html.jsp"/>

    </body>
</html>