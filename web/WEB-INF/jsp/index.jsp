<%-- 
    Document   : index
    Created on : 7 Mar 2023, 6:12:09 pm
    Author     : cmesina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>


        <title>Home</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/public/images/icons/favicon.png"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/vendor/bootstrap/css/bootstrap.min.css">

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/fonts/iconic/css/material-design-iconic-font.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/vendor/animate/animate.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/vendor/css-hamburgers/hamburgers.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/vendor/animsition/css/animsition.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/vendor/select2/select2.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/vendor/daterangepicker/daterangepicker.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/vendor/slick/slick.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/vendor/MagnificPopup/magnific-popup.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/vendor/perfect-scrollbar/perfect-scrollbar.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/fonts/linearicons-v1.0.0/icon-font.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/main.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/util.css">

    </head>
    <body class="animsition">

        <header>
            <div class="container-menu-desktop">
                <jsp:include page="components/header-navbar.jsp" />

                <div class="wrap-menu-desktop">
                    <nav class="limiter-menu-desktop container">

                        <!-- Logo desktop -->		
                        <a href="#" class="logo">
                            <img src="${pageContext.request.contextPath}/public/images/logo-dark.png">
                        </a>

                        <!-- Menu desktop -->
                        <div class="menu-desktop">
                            <ul class="main-menu">
                                <li class="active-menu">
                                    <a href="index.html">Home</a>
                                    <ul class="sub-menu">
                                        <li><a href="index.html">Homepage 1</a></li>
                                        <li><a href="home-02.html">Homepage 2</a></li>
                                        <li><a href="home-03.html">Homepage 3</a></li>
                                    </ul>
                                </li>

                                <li>
                                    <a href="${pageContext.request.contextPath}/shop">Shop</a>
                                </li>

                                <li class="label1" data-label1="hot">
                                    <a href="shoping-cart.html">Features</a>
                                </li>

                                <li>
                                    <a href="blog.html">Blog</a>
                                </li>

                                <li>
                                    <a href="about.html">About</a>
                                </li>

                                <li>
                                    <a href="contact.html">Contact</a>
                                </li>
                            </ul>
                        </div>	

                        <!-- Icon header -->
                        <div class="wrap-icon-header flex-w flex-r-m">
                            <div class="icon-header-item cl2 hov-cl1 trans-04 p-l-22 p-r-11 js-show-modal-search">
                                <i class="zmdi zmdi-search"></i>
                            </div>

                            <div class="icon-header-item cl2 hov-cl1 trans-04 p-l-22 p-r-11 icon-header-noti js-show-cart" data-notify="2">
                                <i class="zmdi zmdi-shopping-cart"></i>
                            </div>

                            <a href="#" class="dis-block icon-header-item cl2 hov-cl1 trans-04 p-l-22 p-r-11 icon-header-noti" data-notify="0">
                                <i class="zmdi zmdi-favorite-outline"></i>
                            </a>
                        </div>
                    </nav>
                </div>	
            </div>
        </header>

        <section class="section-slide">
            <div class="wrap-slick1">
                <div class="slick1">
                    <div class="item-slick1" style="background-image: url(${pageContext.request.contextPath}/public/images/slide-03.png);">
                        <div class="container h-full">
                            <div class="flex-col-l-m h-full p-t-100 p-b-30 respon5">
                                <div class="layer-slick1 animated visible-false" data-appear="fadeInDown" data-delay="0">
                                    <span class="ltext-101 cl2 respon2">
                                        IoT Security Products
                                    </span>
                                </div>

                                <div class="layer-slick1 animated visible-false" data-appear="fadeInUp" data-delay="800">
                                    <h2 class="ltext-201 cl2 p-t-19 p-b-43 respon1">
                                        Security RFID
                                    </h2>
                                </div>

                                <div class="layer-slick1 animated visible-false" data-appear="zoomIn" data-delay="1600">
                                    <a href="product.html" class="flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04">
                                        Shop Now
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="item-slick1" style="background-image: url(${pageContext.request.contextPath}/public/images/slide-02.png);">
                        <div class="container h-full">
                            <div class="flex-col-l-m h-full p-t-100 p-b-30 respon5">
                                <div class="layer-slick1 animated visible-false" data-appear="rollIn" data-delay="0">
                                    <span class="ltext-101 cl2 respon2">
                                        IoT Monitor
                                    </span>
                                </div>

                                <div class="layer-slick1 animated visible-false" data-appear="lightSpeedIn" data-delay="800">
                                    <h2 class="ltext-201 cl2 p-t-19 p-b-43 respon1">
                                        2023 Displays
                                    </h2>
                                </div>

                                <div class="layer-slick1 animated visible-false" data-appear="slideInUp" data-delay="1600">
                                    <a href="product.html" class="flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04">
                                        Shop Now
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="item-slick1" style="background-image: url(${pageContext.request.contextPath}/public/images/slide-01.png);">
                        <div class="container h-full">
                            <div class="flex-col-l-m h-full p-t-100 p-b-30 respon5">
                                <div class="layer-slick1 animated visible-false" data-appear="rotateInDownLeft" data-delay="0">
                                    <span class="ltext-101 cl2 respon2">
                                        IoT Arduino 
                                    </span>
                                </div>

                                <div class="layer-slick1 animated visible-false" data-appear="rotateInUpRight" data-delay="800">
                                    <h2 class="ltext-201 cl2 p-t-19 p-b-43 respon1">
                                        New arrivals
                                    </h2>
                                </div>

                                <div class="layer-slick1 animated visible-false" data-appear="rotateIn" data-delay="1600">
                                    <a href="product.html" class="flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04">
                                        Shop Now
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <footer class="bg3 p-t-75 p-b-32">
            <div class="container">
                <div class="row">
                    <div class="col-sm-6 col-lg-3 p-b-50">
                        <h4 class="stext-301 cl0 p-b-30">
                            Categories
                        </h4>

                        <ul>
                            <li class="p-b-10">
                                <a href="#" class="stext-107 cl7 hov-cl1 trans-04">
                                    Women
                                </a>
                            </li>

                            <li class="p-b-10">
                                <a href="#" class="stext-107 cl7 hov-cl1 trans-04">
                                    Men
                                </a>
                            </li>

                            <li class="p-b-10">
                                <a href="#" class="stext-107 cl7 hov-cl1 trans-04">
                                    Shoes
                                </a>
                            </li>

                            <li class="p-b-10">
                                <a href="#" class="stext-107 cl7 hov-cl1 trans-04">
                                    Watches
                                </a>
                            </li>
                        </ul>
                    </div>

                    <div class="col-sm-6 col-lg-3 p-b-50">
                        <h4 class="stext-301 cl0 p-b-30">
                            Help
                        </h4>

                        <ul>
                            <li class="p-b-10">
                                <a href="#" class="stext-107 cl7 hov-cl1 trans-04">
                                    Track Order
                                </a>
                            </li>

                            <li class="p-b-10">
                                <a href="#" class="stext-107 cl7 hov-cl1 trans-04">
                                    Returns 
                                </a>
                            </li>

                            <li class="p-b-10">
                                <a href="#" class="stext-107 cl7 hov-cl1 trans-04">
                                    Shipping
                                </a>
                            </li>

                            <li class="p-b-10">
                                <a href="#" class="stext-107 cl7 hov-cl1 trans-04">
                                    FAQs
                                </a>
                            </li>
                        </ul>
                    </div>

                    <div class="col-sm-6 col-lg-3 p-b-50">
                        <h4 class="stext-301 cl0 p-b-30">
                            GET IN TOUCH
                        </h4>

                        <p class="stext-107 cl7 size-201">
                            Any questions? Let us know in store at 8th floor, 379 Hudson St, New York, NY 10018 or call us on (+1) 96 716 6879
                        </p>

                        <div class="p-t-27">
                            <a href="#" class="fs-18 cl7 hov-cl1 trans-04 m-r-16">
                                <i class="fa fa-facebook"></i>
                            </a>

                            <a href="#" class="fs-18 cl7 hov-cl1 trans-04 m-r-16">
                                <i class="fa fa-instagram"></i>
                            </a>

                            <a href="#" class="fs-18 cl7 hov-cl1 trans-04 m-r-16">
                                <i class="fa fa-pinterest-p"></i>
                            </a>
                        </div>
                    </div>

                    <div class="col-sm-6 col-lg-3 p-b-50">
                        <h4 class="stext-301 cl0 p-b-30">
                            Newsletter
                        </h4>

                        <form>
                            <div class="wrap-input1 w-full p-b-4">
                                <input class="input1 bg-none plh1 stext-107 cl7" type="text" name="email" placeholder="email@example.com">
                                <div class="focus-input1 trans-04"></div>
                            </div>

                            <div class="p-t-18">
                                <button class="flex-c-m stext-101 cl0 size-103 bg1 bor1 hov-btn2 p-lr-15 trans-04">
                                    Subscribe
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="p-t-40">
                    <div class="flex-c-m flex-w p-b-18">
                        <a href="#" class="m-all-1">
                            <img src="${pageContext.request.contextPath}/public/images/icons/icon-pay-01.png" alt="ICON-PAY">
                        </a>

                        <a href="#" class="m-all-1">
                            <img src="${pageContext.request.contextPath}/public/images/icons/icon-pay-02.png" alt="ICON-PAY">
                        </a>

                        <a href="#" class="m-all-1">
                            <img src="${pageContext.request.contextPath}/public/images/icons/icon-pay-03.png" alt="ICON-PAY">
                        </a>

                        <a href="#" class="m-all-1">
                            <img src="${pageContext.request.contextPath}/public/images/icons/icon-pay-04.png" alt="ICON-PAY">
                        </a>

                        <a href="#" class="m-all-1">
                            <img src="${pageContext.request.contextPath}/public/images/icons/icon-pay-05.png" alt="ICON-PAY">
                        </a>
                    </div>

                    <p class="stext-107 cl6 txt-center">
                        <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                        Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="fa fa-heart-o" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
                        <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->

                    </p>
                </div>
            </div>
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
                            $('.js-addwish-b2').on('click', function (e) {
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

        <script src="${pageContext.request.contextPath}/public/js/main.js"></script>
    </body>
</html>

