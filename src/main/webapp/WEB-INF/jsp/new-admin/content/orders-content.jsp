<%--
  Created by IntelliJ IDEA.
  User: cmesina
  Date: 1/5/2023
  Time: 10:59 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="https://unpkg.com/tabulator-tables@5.4.4/dist/css/tabulator.min.css" rel="stylesheet">
<script type="text/javascript" src="https://unpkg.com/tabulator-tables@5.4.4/dist/js/tabulator.min.js"></script>
<script type="text/javascript" src="https://moment.github.io/luxon/global/luxon.min.js"></script>

<main>
    <div class="container-fluid px-4">
        <h1 class="mt-4">Orders</h1>
        <div id="order-table"></div>
    </div>
</main>


<script src="${pageContext.request.contextPath}/public/js/jsp/admin-orders.js.jsp"></script>
