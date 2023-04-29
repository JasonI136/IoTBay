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

        <jsp:include page="../components/common-header-html.jsp"/>

    </head>
    <body class="animsition cl6 p-b-26">

        <!-- Header -->
        <jsp:include page="../components/navbar/admin-master-navbar.jsp"/>

        <section class="txt-center p-lr-15 p-tb-20 bg-dark">
            <h2 class="ltext-105 cl0 txt-center">
                Event Log
            </h2>
        </section>


        <section class="bg0 p-t-50 p-b-116">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <p class="text-muted">Displaying the latest 100 logs</p> <!-- Add this line -->
                        <div class="table-responsive">
                            <table class="table table-hover table-bordered">
                                <thead>
                                    <tr class="bg-dark text-white">
                                        <th scope="col" class="text-center" style="width: 10%">#</th>
                                        <th scope="col" class="text-center" style="width: 20%" class="text-center">
                                            Time
                                        </th>
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

    <jsp:include page="../components/common-footer-html.jsp"/>

</html>
