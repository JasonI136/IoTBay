<%-- 
    Document   : admin-users
    Created on : 30/03/2023, 5:54:36 PM
    Author     : jasonmba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Users</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://unpkg.com/tabulator-tables@5.4.4/dist/css/tabulator.min.css" rel="stylesheet">
    <script type="text/javascript" src="https://unpkg.com/tabulator-tables@5.4.4/dist/js/tabulator.min.js"></script>

    <jsp:include page="../components/common-header-html.jsp"/>

</head>
<body class="animsition cl6 p-b-26">
    <header class="header-v4">
        <div class="container-menu-desktop">
            <jsp:include page="../components/header-navbar.jsp"/>
            <jsp:include page="../components/admin-navbar.jsp"/>
        </div>
    </header>

    <div class="container-fluid">
        <section class="bg0 p-b-20 w-75 m-auto">
            <div>
                <div class=" bor10 p-lr-70 p-t-55 p-b-70 p-lr-15-lg w-full-md">
                    <div class="container mt-4 pb-3">
                        <div class="p-b-10">
                        <h3 class="ltext-103 cl5">
                            User Management
                        </h3>
                    </div>
                        <div id="user-table"></div>
                    </div>

                </div>
            </div>
        </section>
    </div>


</body>

<script>
    var table = new Tabulator("#user-table", {
        ajaxURL: "${pageContext.request.contextPath}/admin/users/get",
        ajaxConfig: "GET",
        ajaxResponse: function (url, params, response) {
            return response.data;
        },
        layout: "fitColumns",
        pagination: "local",
        paginationSize: 10,
        columns: [
            {title: "User ID", field: "id", width: 150},
            {title: "Username", field: "username", width: 150},
            {title: "First Name", field: "firstName", width: 150},
            {title: "Last Name", field: "lastName", width: 150},
            {title: "Email", field: "email", width: 150},
            {
                title: "Staff",
                field: "isStaff",
                hozAlign: "center",
                width: 50,
                formatter: "tickCross",
            },
        ],
    });
</script>


<jsp:include page="../components/common-footer-html.jsp"/>
</html>
