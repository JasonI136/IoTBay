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

        <jsp:include page="../components/common-header-html.jsp"/>

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
                    <div class="col-md-4">
                        <div class="card rounded-lg shadow admin-user-card">
                            <div class="card-body pb-4">
                                <h5 class="card-title">{firstname} {lastname}</h5>
                                <p class="card-text"><b>Email:</b> {email}</p>
                                <p class="card-text"><b>Location:</b> {location}</p>
                                <p class="card-text"><b>Phone:</b> {phone}</p>
                                <p class="card-text"><b>Orders:</b> {order amount}</p>
                                <div class="d-flex justify-content-end">
                                    <button class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer mt-3">
                                        View Profile
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card rounded-lg shadow admin-user-card">
                            <div class="card-body pb-4">
                                <h5 class="card-title">{firstname} {lastname}</h5>
                                <p class="card-text"><b>Email:</b> {email}</p>
                                <p class="card-text"><b>Location:</b> {location}</p>
                                <p class="card-text"><b>Phone:</b> {phone}</p>
                                <p class="card-text"><b>Orders:</b> {order amount}</p>
                                <div class="d-flex justify-content-end">
                                    <button class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer mt-3">
                                        View Profile
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card rounded-lg shadow admin-user-card">
                            <div class="card-body pb-4">
                                <h5 class="card-title">{firstname} {lastname}</h5>
                                <p class="card-text"><b>Email:</b> {email}</p>
                                <p class="card-text"><b>Location:</b> {location}</p>
                                <p class="card-text"><b>Phone:</b> {phone}</p>
                                <p class="card-text"><b>Orders:</b> {order amount}</p>
                                <div class="d-flex justify-content-end">
                                    <button class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer mt-3">
                                        View Profile
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card rounded-lg shadow admin-user-card">
                            <div class="card-body pb-4">
                                <h5 class="card-title">{firstname} {lastname}</h5>
                                <p class="card-text"><b>Email:</b> {email}</p>
                                <p class="card-text"><b>Location:</b> {location}</p>
                                <p class="card-text"><b>Phone:</b> {phone}</p>
                                <p class="card-text"><b>Orders:</b> {order amount}</p>
                                <div class="d-flex justify-content-end">
                                    <button class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer mt-3">
                                        View Profile
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>


    </body>


    <jsp:include page="../components/common-footer-html.jsp"/>
</html>
