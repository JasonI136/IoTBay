
<%-- 
    Document   : admin-orders
    Created on : 30/03/2023, 5:52:39 PM
    Author     : jasonmba
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Admin Orders</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="https://unpkg.com/tabulator-tables@5.4.4/dist/css/tabulator.min.css" rel="stylesheet">
        <script type="text/javascript" src="https://unpkg.com/tabulator-tables@5.4.4/dist/js/tabulator.min.js"></script>
        <script type="text/javascript" src="https://moment.github.io/luxon/global/luxon.min.js"></script>
        <jsp:include page="../components/common-header-html.jsp"/>

    </head>
    <body class="animsition cl6 p-b-26">

        <!-- Header -->
        <jsp:include page="../components/navbar/admin-master-navbar.jsp"/>

        <section class="txt-center p-lr-15 p-tb-20 bg-dark">
            <h2 class="ltext-105 cl0 txt-center">
                Order Management
            </h2>
        </section>

        <!-- Content -->
        <section class="bg0 p-t-50 p-b-116">
            <div class="container">
                <div id="order-table"></div>
            </div>
        </section>

    </body>

    <jsp:include page="../components/footer.jsp"/>
    <jsp:include page="../components/common-footer-html.jsp"/>
    <script src="${pageContext.request.contextPath}/public/js/jsp/admin-orders.js.jsp"></script>
</html>
