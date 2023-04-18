<%-- 
    Document   : admin-users
    Created on : 30/03/2023, 5:54:36 PM
    Author     : jasonmba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Admin Users</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <jsp:include page="../components/header-links.jsp"/>

    </head>
    <body class="animsition cl6 p-b-26">

        <header>
            <div class="container-menu-desktop">
                <jsp:include page="../components/header-navbar.jsp"/>
                <jsp:include page="../components/admin-navbar.jsp"/>
            </div>
        </header>


        <section class="bg0 p-t-104 p-b-116">

            <div class="container">
                <div class="row">
                    <div class="col-md-4">
                        <div class="card rounded-lg shadow admin-user-card">
                            <div class="card-body pb-4">
                                <h5 class="card-title">{firstname} {lastname}</h5>
                                <p class="card-text"><b>Email:</b> {email}</p>
                                <p class="card-text"><b>Location:</b> {location}</p>
                                <p class="card-text"><b>Phone:</b> {phone}</p>
                                <p class="card-text"><b>Orders:</b> {order amount}</p>
                                <div class="d-flex justify-content-end">
                                    <button class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer mt-3">
                                        View Profile
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card rounded-lg shadow admin-user-card">
                            <div class="card-body pb-4">
                                <h5 class="card-title">{firstname} {lastname}</h5>
                                <p class="card-text"><b>Email:</b> {email}</p>
                                <p class="card-text"><b>Location:</b> {location}</p>
                                <p class="card-text"><b>Phone:</b> {phone}</p>
                                <p class="card-text"><b>Orders:</b> {order amount}</p>
                                <div class="d-flex justify-content-end">
                                    <button class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer mt-3">
                                        View Profile
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card rounded-lg shadow admin-user-card">
                            <div class="card-body pb-4">
                                <h5 class="card-title">{firstname} {lastname}</h5>
                                <p class="card-text"><b>Email:</b> {email}</p>
                                <p class="card-text"><b>Location:</b> {location}</p>
                                <p class="card-text"><b>Phone:</b> {phone}</p>
                                <p class="card-text"><b>Orders:</b> {order amount}</p>
                                <div class="d-flex justify-content-end">
                                    <button class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer mt-3">
                                        View Profile
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card rounded-lg shadow admin-user-card">
                            <div class="card-body pb-4">
                                <h5 class="card-title">{firstname} {lastname}</h5>
                                <p class="card-text"><b>Email:</b> {email}</p>
                                <p class="card-text"><b>Location:</b> {location}</p>
                                <p class="card-text"><b>Phone:</b> {phone}</p>
                                <p class="card-text"><b>Orders:</b> {order amount}</p>
                                <div class="d-flex justify-content-end">
                                    <button class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer mt-3">
                                        View Profile
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>


    </body>


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
    <script src="${pageContext.request.contextPath}/public/vendor/isotope/isotope.pkgd.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/vendor/sweetalert/sweetalert.min.js"></script>
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
</html>
