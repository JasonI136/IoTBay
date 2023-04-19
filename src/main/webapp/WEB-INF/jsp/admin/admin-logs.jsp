<%-- 
    Document   : admin-users
    Created on : 30/03/2023, 5:54:36 PM
    Author     : jasonmba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Logs</title>
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
                    <div class="col-md-12">
                        <p class="text-muted">Displaying the latest 100 logs</p> <!-- Add this line -->
                        <div class="table-responsive">
                            <table class="table table-hover table-bordered">
                                <thead>
                                    <tr class="bg-dark text-white">
                                        <th scope="col" class="text-center" style="width: 10%">#</th>
                                        <th scope="col" class="text-center" style="width: 20%" class="text-center">Time</th>
                                        <th scope="col" class="text-center">Level</th>
                                        <th scope="col" class="text-center">Message</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="logEntry" items="${logs}">
                                        <tr <c:choose>
                                                <c:when test="${logEntry.level == 'WARN'}">
                                                    class="table-warning"
                                                </c:when>
                                                <c:when test="${logEntry.level == 'ERROR'}">
                                                    class="table-danger"
                                                </c:when>
                                            </c:choose>>

                                            <td class="text-center py-2">${logEntry.id}</td>
                                            <td class="text-center py-2">${logEntry.timestamp.substring(0, 19)}</td>
                                            <td class="text-center py-2"><b>${logEntry.level}</b></td>
                                            <td class="py-2">${logEntry.message}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
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
