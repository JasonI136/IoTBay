<%-- 
    Document   : login
    Created on : 12 Mar 2023, 3:28:26 pm
    Author     : cmesina
--%>


<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <jsp:include page="components/common-header-html.jsp"/>
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
                     style="background-image: url('${pageContext.request.contextPath}/public/images/bg-01.jpg');">
                <h2 class="ltext-105 cl0 txt-center">
                    Login to IoTBay
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
                    Login
                </span>
            </div>
        </div>
        <!-- Content page -->
        <section class="bg0 p-t-104 p-b-116">
            <div class="container">

                <div class=" bor10 p-lr-70 p-t-55 p-b-70 p-lr-15-lg w-full-md">
                    <form method="post" action="login">
                        <h4 class="mtext-105 cl2 txt-center p-b-30">
                            Login
                        </h4>

                        <div class="bor8 m-b-20 how-pos4-parent">
                            <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" name="username"
                                   id="form3Example3" placeholder="Your Username" required>
                            <img class="how-pos4 pointer-none"
                                 src="${pageContext.request.contextPath}/public/images/icons/user.svg" alt="ICON">
                        </div>

                        <div class="bor8 m-b-30 how-pos4-parent">
                            <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="password" name="password"
                                   id="form3Example4" placeholder="Your Password" required>
                            <img class="how-pos4 pointer-none"
                                 src="${pageContext.request.contextPath}/public/images/icons/lock.svg" alt="ICON">
                        </div>

                        <button class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer">
                            Login
                        </button>
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

