<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>IoTBay Admin - Users</title>
        <jsp:include page="components/common-head-html.jsp"/>
    </head>
    <body class="sb-nav-fixed">
        <jsp:include page="components/top-nav.jsp"/>
        <div id="layoutSidenav">
            <jsp:include page="components/side-nav.jsp"/>
            <div id="layoutSidenav_content">
                <jsp:include page="content/users-content.jsp"/>
                <jsp:include page="components/footer.jsp"/>
            </div>
        </div>
        <jsp:include page="components/common-footer-html.jsp"/>
    </body>
</html>
