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
            <div class="container card p-t-20 p-b-20 p-l-30 p-r-30">
                <div class="row">
                    <div class="card p-all-10">
                        <div class="flex-b" style="gap: 5px">
                            <div class="dropdown">
                                <button class="btn btn-secondary dropdown-toggle" type="button"
                                        data-bs-toggle="dropdown"
                                        aria-expanded="false">
                                    <i class="fa-solid fa-user"></i>

                                    Users
                                </button>
                                <ul class="dropdown-menu">
                                    <li><a class="dropdown-item" onclick="$('#add-user-modal').modal('show')"><i
                                            class="fa-solid fa-plus"></i> Add</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="card p-all-10 gy-2">
                        <div class="input-group">
                            <span class="input-group-text" id="basic-addon1">Search (Username)</span>
                            <input type="text" class="form-control" placeholder="Enter search term"
                                   aria-describedby="basic-addon1" id="search-input">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div id="user-table" class="gy-2 bg-transparent p-all-0 "></div>
                </div>

            </div>
        </section>

        <jsp:include page="../components/footer.jsp"/>
    </body>

    <jsp:include page="../components/modals/admin-users-add-user-modal.jsp"/>
    <jsp:include page="../components/modals/admin-users-edit-user-modal.jsp"/>

    <jsp:include page="../components/common-footer-html.jsp"/>
    <script src="${pageContext.request.contextPath}/public/js/jsp/admin-users.js.jsp"></script>

</html>
