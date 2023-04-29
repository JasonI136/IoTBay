<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
    Document   : about
    Created on : 19/03/2023, 11:49:21 PM
    Author     : jasonmba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD"
              crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
                crossorigin="anonymous"></script>


        <title>Error</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <jsp:include page="components/common-header-html.jsp"/>

    </head>
    <body class="animsition">

        <!-- Header -->
        <jsp:include page="components/navbar/master-navbar.jsp"/>

        <div class="container">
            <h1>${pageContext.response.status}</h1>
            <h2>${message}</h2>
            <br>
            <ul>
                <li>Exception: <c:out value="${requestScope['jakarta.servlet.error.exception']}"/></li>
                <li>Exception type: <c:out value="${requestScope['jakarta.servlet.error.exception_type']}"/></li>
                <li>Exception message: <c:out value="${requestScope['jakarta.servlet.error.message']}"/></li>
                <li>Request URI: <c:out value="${requestScope['jakarta.servlet.error.request_uri']}"/></li>
                <li>Servlet name: <c:out value="${requestScope['jakarta.servlet.error.servlet_name']}"/></li>
                <li>Status code: <c:out value="${requestScope['jakarta.servlet.error.status_code']}"/></li>
            </ul>
        </div>


        <!-- Footer -->
        <footer class="bg3 p-t-75 p-b-32">
            <jsp:include page="components/footer.jsp"/>
        </footer>


        <!-- Back to top -->
        <div class="btn-back-to-top" id="myBtn">
			<span class="symbol-btn-back-to-top">
				<i class="zmdi zmdi-chevron-up"></i>
			</span>
        </div>

        <jsp:include page="components/common-footer-html.jsp"/>

    </body>
</html>