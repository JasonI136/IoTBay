
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


<%--        <div class="container-fluid">--%>
<%--            <section class="bg0 p-t-50 p-b-20">--%>
<%--                <div>--%>
<%--                    <div class=" bor10 p-lr-70 p-t-55 p-b-70 p-lr-15-lg w-full-md">--%>
<%--                        <div class="container mt-4 pb-3">--%>
<%--                            <div class="row">--%>
<%--                                <div class="col-md-12">--%>
<%--                                    <form class="form-inline float-right">--%>
<%--                                        <div class="form-group">--%>
<%--                                            <input type="text" class="form-control mr-2" id="orderNumber" placeholder="Enter Order Number">--%>
<%--                                        </div>--%>
<%--                                        <button type="submit" class="btn btn-primary">Search</button>--%>
<%--                                    </form>--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                        <section class="section-table bg0">--%>
<%--                            <div class="container">--%>
<%--                                <div class="row">--%>
<%--                                    <div class="col-md-12">--%>
<%--                                        <div class="table-responsive">--%>
<%--                                            <table class="table table-hover table-bordered">--%>
<%--                                                <thead>--%>
<%--                                                    <tr class="bg-dark text-white">--%>
<%--                                                        <th scope="col" class="text-center" style="width: 10%">Order ID</th>--%>
<%--                                                        <th scope="col" class="text-center" style="width: 10%">User</th>--%>
<%--                                                        <th scope="col" class="text-center">Order Date</th>--%>
<%--                                                        <th scope="col" class="text-center">Status</th>--%>
<%--                                                        <th scope="col" class="text-center">Action</th>--%>
<%--                                                    </tr>--%>
<%--                                                </thead>--%>
<%--                                                <tbody>--%>
<%--                                                    <c:forEach var="order" items="${orders}" varStatus="status">--%>
<%--                                                        <tr class="${status.index % 2 == 0 ? 'table-primary' : 'table-secondary'}">--%>
<%--                                                            <td class="text-center">${order.id}</td>--%>
<%--                                                            <td class="text-center">${order.userId}</td>--%>
<%--                                                            <td>${order.orderDate}</td>--%>
<%--                                                            <td class="text-center">--%>
<%--                                                                <div class="dropdown">--%>
<%--                                                                    <button class="btn btn-secondary dropdown-toggle" type="button" id="statusDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">--%>
<%--                                                                        ${order.orderStatus}--%>
<%--                                                                    </button>--%>
<%--                                                                    <div class="dropdown-menu" aria-labelledby="statusDropdown">--%>
<%--                                                                        <a class="dropdown-item" href="#" value="pending">Pending</a>--%>
<%--                                                                        <a class="dropdown-item" href="#" value="processing">Shipped</a>--%>
<%--                                                                        <a class="dropdown-item" href="#" value="shipped">Delivered</a>--%>
<%--                                                                        <a class="dropdown-item" href="#" value="cancelled">Cancelled</a>--%>
<%--                                                                    </div>--%>
<%--                                                                </div>--%>
<%--                                                            </td>--%>
<%--                                                            <td class="text-center"><button class="btn btn-primary">Save</button></td>--%>
<%--                                                        </tr>--%>
<%--                                                    </c:forEach>--%>
<%--                                                </tbody>--%>
<%--                                            </table>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                        </section>--%>
<%--                    </div>--%>
<%--                </div>--%>


<%--            </section>--%>
<%--        </div>--%>
    </body>


    <jsp:include page="../components/common-footer-html.jsp"/>
    <script src="${pageContext.request.contextPath}/public/js/jsp/admin-orders.js.jsp"></script>
</html>
