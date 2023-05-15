<%-- 
    Document   : admin-users
    Created on : 30/03/2023, 5:54:36 PM
    Author     : jasonmba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>


<html lang="en">
    <head>
        <title>IoTBay Admin - Logs</title>
        <jsp:include page="../new-admin/components/common-head-html.jsp"/>
    </head>
    <body class="sb-nav-fixed">
        <jsp:include page="../new-admin/components/top-nav.jsp"/>
        <div id="layoutSidenav">
            <jsp:include page="../new-admin/components/side-nav.jsp"/>
            <div id="layoutSidenav_content">
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
                <jsp:include page="../new-admin/components/footer.jsp"/>
            </div>
        </div>
        <jsp:include page="../new-admin/components/common-footer-html.jsp"/>
    </body>
</html>
