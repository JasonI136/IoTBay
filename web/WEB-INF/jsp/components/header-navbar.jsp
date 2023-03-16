<%-- 
    Document   : header-navbar
    Created on : 15/03/2023, 8:12:13 PM
    Author     : jasonmba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>

    <div class="top-bar">
        <div class="content-topbar flex-sb-m h-full container">
            <div class="left-top-bar">
                Free shipping for standard order over $100
            </div>

            <div class="right-top-bar flex-w h-full">


                <c:if test = "${sessionScope.user != null}">
                    <a href="#" class="flex-c-m trans-04 p-lr-25">
                        ${sessionScope.user.firstName} ${sessionScope.user.lastName}
                    </a>
                     <a href="<%=request.getContextPath()%>/logout" class="flex-c-m trans-04 p-lr-25">
                        Logout
                    </a>
                </c:if>



                <c:if test = "${sessionScope.user == null}">
                    <a href="${pageContext.request.contextPath}/login" class="flex-c-m trans-04 p-lr-25">
                        Login
                    </a>
                    <a href="${pageContext.request.contextPath}/register" class="flex-c-m trans-04 p-lr-25">
                        Register
                    </a>
                </c:if>
                
            </div>
        </div>
    </div>
</html>
