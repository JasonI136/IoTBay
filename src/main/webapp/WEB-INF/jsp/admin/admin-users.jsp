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
    <body class="animsition stext-112 cl6 p-b-26">

        <header>
            <div class="container-menu-desktop">
                <jsp:include page="../components/header-navbar.jsp"/>
                <jsp:include page="../components/admin-navbar.jsp"/>
            </div>
        </header>


        <div class="container-fluid">
            <section class="bg0 p-t-104 p-b-20">
                <div>
                    <div class=" bor10 p-lr-70 p-t-55 p-b-70 p-lr-15-lg w-full-md">
                        <div class="container">
                            <h1>Manage User Access</h1>    
                        </div>


                        <div class="container mt-4 pb-3">
                            <div class="row">
                                <div class="col-md-12">
                                    <form class="form-inline float-right">
                                        <div class="form-group">
                                            <input type="text" class="form-control mr-2" id="orderNumber" placeholder="Enter User ID">
                                        </div>
                                        <button type="submit" class="btn btn-primary">Search</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <section class="section-table bg0">
                            <div class="container">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="table-responsive">
                                            <table class="table table-hover table-bordered">
                                                <thead>
                                                    <tr class="bg-dark text-white">
                                                        <th scope="col" class="text-center" style="width: 10%">User ID</th>
                                                        <th scope="col" class="text-center" style="width: 10%">Name</th>
                                                        <th scope="col" class="text-center">Email</th>
                                                        <th scope="col" class="text-center">Phone</th>
                                                        <th scope="col" class="text-center">Address</th>
                                                        <th scope="col" class="text-center">Position</th>
                                                        <th scope="col" class="text-center">Status</th>                                                                                                                                                                 
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="order" items="${orders}" varStatus="status">
                                                   <!-- <tr class="${status.index % 2 == 0 ? 'table-primary' : 'table-secondary'}">
                                                        <td class="text-center">${order.id}</td>
                                                        <td class="text-center">${order.userId}</td>
                                                        <td>${order.orderDate}</td>
                                                        <td class="text-center">
                                                            <div class="dropdown">
                                                                <button class="btn btn-secondary dropdown-toggle" type="button" id="statusDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                                    ${order.orderStatus}
                                                                </button>
                                                                <div class="dropdown-menu" aria-labelledby="statusDropdown">
                                                                    <a class="dropdown-item" href="#" value="pending">Active</a>
                                                                    <a class="dropdown-item" href="#" value="processing">Inactive</a>                                                     
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <td class="text-center"><button class="btn btn-primary">Save</button></td> -->
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>
                </div>


            </section>
        </div>


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
