<%-- 
    Document   : footer
    Created on : 16/03/2023, 11:00:13 PM
    Author     : chadm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>

    <div class="container">
        <div class="row">
            <div class="col-sm-6 col-lg-3 p-b-50">
                <h4 class="stext-301 cl0 p-b-30">
                    Want to Shop?
                </h4>

                <ul>
                    <li class="p-b-10">
                        <a href="${pageContext.request.contextPath}/index" class="stext-107 cl7 hov-cl1 trans-04">
                            Home
                        </a>
                    </li>

                    <li class="p-b-10">
                        <a href="${pageContext.request.contextPath}/shop" class="stext-107 cl7 hov-cl1 trans-04">
                            Shop
                        </a>
                    </li>

                </ul>
            </div>

            <div class="col-sm-6 col-lg-3 p-b-50">
                <h4 class="stext-301 cl0 p-b-30">
                    Need Help With An Order?
                </h4>

                <ul>
                    <li class="p-b-10">
                        <a href="${pageContext.request.contextPath}/orderTracking" class="stext-107 cl7 hov-cl1 trans-04">
                            Track Order
                        </a>
                    </li>

                    <li class="p-b-10">
                        <a href="${pageContext.request.contextPath}/contactUs" class="stext-107 cl7 hov-cl1 trans-04">
                            Contact
                        </a>
                    </li>
                </ul>
            </div>

            <div class="col-sm-6 col-lg-3 p-b-50">
                <h4 class="stext-301 cl0 p-b-30">
                    GET IN TOUCH
                </h4>

                <p class="stext-107 cl7 size-201">
                    Any questions? Let us know in store, by email at iotbay@sales.com or phone +61 861 654 222
                </p>
            </div>

            <div class="col-sm-6 col-lg-3 p-b-50">
                <h4 class="stext-301 cl0 p-b-30">
                    Learn more about us
                </h4>
                <li class="p-b-10">
                    <a href="${pageContext.request.contextPath}/aboutUs" class="stext-107 cl7 hov-cl1 trans-04">
                        About Us
                    </a>
                </li>
                
                <p class="stext-107 cl7 size-201">
                    Payment Options
                </p>
                
                <a href="#" class="m-all-1">
                    <img src="${pageContext.request.contextPath}/public/images/icons/icon-pay-02.png" alt="ICON-PAY">
                </a>

                <a href="#" class="m-all-1">
                    <img src="${pageContext.request.contextPath}/public/images/icons/icon-pay-03.png" alt="ICON-PAY">
                </a>

                <a href="#" class="m-all-1">
                    <img src="${pageContext.request.contextPath}/public/images/icons/icon-pay-04.png" alt="ICON-PAY">
                </a>

                <a href="#" class="m-all-1">
                    <img src="${pageContext.request.contextPath}/public/images/icons/icon-pay-05.png" alt="ICON-PAY">
                </a>
            </div>

            <p class="stext-107 cl6 txt-center">
                <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved 
            </p>
        </div>
    </div>
</html>
