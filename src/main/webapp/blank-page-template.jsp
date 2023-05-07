<%--
  Created by IntelliJ IDEA.
  User: cmesina
  Date: 29/4/2023
  Time: 9:07 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>

        <jsp:include page="WEB-INF/jsp/components/common-header-html.jsp"/>
    </head>
    <body class="">
        <!-- Header -->
        <jsp:include page="WEB-INF/jsp/components/navbar/master-navbar.jsp"/>

        <!-- Title page -->
        <section class="bg-img1 txt-center p-lr-15 p-tb-92"
                 style="background-image: url('${pageContext.request.contextPath}/public/images/bg-orders.jpg');">
            <h2 class="ltext-105 cl0 txt-center">
                Banner
            </h2>
        </section>

        <!-- Content -->
        <section class="bg0 p-t-104 p-b-116">
            <div class="container">

            </div>
        </section>

        <!-- Back to top -->
        <div class="btn-back-to-top" id="myBtn">
			<span class="symbol-btn-back-to-top">
				<i class="zmdi zmdi-chevron-up"></i>
			</span>
        </div>


        <!-- Footer -->
        <footer class="bg3 p-t-75 p-b-32">
            <jsp:include page="WEB-INF/jsp/components/footer.jsp"/>
        </footer>

        <jsp:include page="WEB-INF/jsp/components/common-footer-html.jsp"/>
    </body>
</html>
