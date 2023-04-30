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
        <!-- Header -->
        <jsp:include page="../components/navbar/admin-master-navbar.jsp"/>

        <section class="txt-center p-lr-15 p-tb-20 bg-dark">
            <h2 class="ltext-105 cl0 txt-center">
                User Management
            </h2>
        </section>

        <!-- Content -->
        <section class="bg0 p-t-50 p-b-116">
            <div class="container">
                <div id="user-table"></div>
            </div>
        </section>

        <jsp:include page="../components/footer.jsp"/>
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
