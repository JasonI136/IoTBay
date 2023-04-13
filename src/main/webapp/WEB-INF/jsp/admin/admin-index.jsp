<%-- 
    Document   : admin-index
    Created on : 30/03/2023, 1:15:30 PM
    Author     : jasonmba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>


        <title>Admin Dashboard</title>
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
    <body class="animsition stext-112 cl6 p-b-26">

        <header>
            <div class="container-menu-desktop">
                <jsp:include page="../components/header-navbar.jsp"/>
                <jsp:include page="../components/admin-navbar.jsp"/>
            </div>
        </header>


        <section class="bg0 p-t-104 p-b-116">
            <div class="container">
                <div class="row">
                    <div class="col-md-4 mb-4">
                        <div class="card shadow border-3 border-primary">
                            <div class="card-body d-flex align-items-center">
                                <i class="zmdi zmdi-shopping-cart zmdi-hc-5x mr-3" style="color: #6c7ae0"></i>
                                <div>
                                    <h5 class="card-title mb-0">Total Orders</h5>
                                    <p class="card-text">${orderCount}</p>
                                    <!-- Chart for total orders -->
                                </div>
                            </div>

                        </div>
                    </div>
                    <div class="col-md-4 mb-4">
                        <div class="card shadow border-3 border-primary">
                            <div class="card-body d-flex align-items-center">
                                <i class="zmdi zmdi-account zmdi-hc-5x mr-3" style="color: #6c7ae0"></i>
                                <div>
                                    <h5 class="card-title mb-0">Total Customers</h5>
                                    <p class="card-text">${userCount}</p>
                                    <!-- Chart for customer growth -->
                                </div>
                            </div>
                        </div>

                        <div class="chart-container">
                            <canvas id="customerGrowthChart"></canvas>
                        </div>
                    </div>
                    <div class="col-md-4 mb-4">
                        <div class="card shadow border-3 border-primary">
                            <div class="card-body d-flex align-items-center">
                                <i class="zmdi zmdi-archive zmdi-hc-5x mr-3" style="color: #6c7ae0"></i>
                                <div>
                                    <h5 class="card-title mb-0">Total Products</h5>
                                    <p class="card-text">${productCount}</p>
                                    <!-- Chart for total products -->
                                </div>
                            </div>
                        </div>
                        <div class="chart-container">
                            <canvas id="totalProductsChart"></canvas>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 mt-4">
                        <h3>Total Yearly Orders</h3>
                        <!-- Original chart for total yearly orders -->
                        <canvas id="orderChart"></canvas>
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

    <style>
        .mb-4 {
            margin-bottom: 1.5rem;
        }

        .mt-4 {
            margin-top: 1.5rem;
        }

        .card {
            border: 3px solid;
        }
    </style>

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>
        // Create chart for total products
        var ctx2 = document.getElementById('totalProductsChart').getContext('2d');
        var totalProductsChart = new Chart(ctx2, {
            type: 'line',
            data: {
                labels: ['January', 'February', 'March', 'April', 'May', 'June'],
                datasets: [{
                        label: 'Total Products',
                        data: [50, 51, 62, 65, 70, 72],
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 3,
                        lineTension: 0.3,
                        shadowOffsetX: 0,
                        shadowOffsetY: 3,
                        shadowBlur: 5,
                        shadowColor: 'rgba(0, 0, 0, 0.3)'
                    }]
            },
            options: {
                scales: {
                    yAxes: [{
                            ticks: {
                                beginAtZero: true
                            }
                        }]
                }
            }
        });

        // Create chart for customer growth
        var ctx1 = document.getElementById('customerGrowthChart').getContext('2d');
        var customerGrowthChart = new Chart(ctx1, {
            type: 'line',
            data: {
                labels: ['January', 'February', 'March', 'April', 'May', 'June'],
                datasets: [{
                        label: 'Customer Growth',
                        data: [100, 150, 200, 250, 300, 350],
                        backgroundColor: 'rgba(255, 159, 64, 0.2)',
                        borderColor: 'rgba(255, 159, 64, 1)',
                        borderWidth: 3,
                        lineTension: 0.3,
                        shadowOffsetX: 0,
                        shadowOffsetY: 3,
                        shadowBlur: 5,
                        shadowColor: 'rgba(0, 0, 0, 0.3)'
                    }]
            },
            options: {
                scales: {
                    yAxes: [{
                            ticks: {
                                beginAtZero: true
                            }
                        }]
                }
            }
        });

        // Total Order Chart
        var ctx = document.getElementById('orderChart').getContext('2d');
        var orderChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: ['January', 'February', 'March', 'April', 'May', 'June'],
                datasets: [{
                        label: 'Sales',
                        data: [12, 19, 3, 5, 2, 3],
                        backgroundColor: 'rgba(108, 122, 224, 0.2)',
                        borderColor: 'rgba(108, 122, 224, 1)',
                        borderWidth: 3, // Set line width to 3
                        lineTension: 0.3, // Set line tension to make lines curved
                        shadowOffsetX: 0, // Set shadow offset
                        shadowOffsetY: 3,
                        shadowBlur: 5,
                        shadowColor: 'rgba(0, 0, 0, 0.3)' // Set shadow color
                    }]
            },
            options: {
                scales: {
                    yAxes: [{
                            ticks: {
                                beginAtZero: true
                            }
                        }]
                }
            }
        });
    </script>
    <script src="${pageContext.request.contextPath}/public/js/main.js"></script>
</html>
