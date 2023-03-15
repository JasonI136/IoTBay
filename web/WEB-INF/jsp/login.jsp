<%-- 
    Document   : login
    Created on : 12 Mar 2023, 3:28:26 pm
    Author     : cmesina
--%>

<%@page import="com.sun.xml.rpc.processor.modeler.j2ee.xml.paramValueType"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Login Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
    </head>
    <body>
        <section class="vh-100">
            <div class="container-fluid h-custom">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-md-9 col-lg-6 col-xl-5">
                        <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
                             class="img-fluid" alt="Sample image">
                    </div>
                        <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
                            
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger" role="alert">
                                ${error}
                          </div>
                        </c:if>
                            
                        <c:if test="${not empty success}">
                            <div class="alert alert-success" role="alert">
                                ${success}
                          </div>
                        </c:if>
                    
                    

                        <form method="post" action="login">
                            <!-- Email input -->
                            <div class="form-outline mb-4">
                                <label class="form-label" for="form3Example3">Username</label>
                                <input type="text" name="username" id="form3Example3" class="form-control form-control-lg"
                                       placeholder="Enter a valid username" />
                            </div>

                            <!-- Password input -->
                            <div class="form-outline mb-3">
                                <label class="form-label" for="form3Example4">Password</label>
                                <input type="password" name="password" id="form3Example4" class="form-control form-control-lg"
                                       placeholder="Enter password" />
                            </div>

                            <div class="text-center text-lg-start mt-4 pt-2">
                                <button type="submit" value="Login" class="btn btn-primary btn-lg"
                                        style="padding-left: 2.5rem; padding-right: 2.5rem;">Login</button>
                                <p class="small fw-bold mt-2 pt-1 mb-0">Don't have an account? <a href="${pageContext.request.contextPath}/register"
                                                                                                  class="link-danger">Register</a></p>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>

