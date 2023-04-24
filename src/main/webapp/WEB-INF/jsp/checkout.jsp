<%--
  Created by IntelliJ IDEA.
  User: cmesina
  Date: 21/3/2023
  Time: 6:47 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
        <title>Checkout</title>
        <script src="https://js.stripe.com/v3/"></script>
        <script type="text/javascript">
            var stripePublishableKey = "${stripe_pk}";
        </script>
        <jsp:include page="components/common-header-html.jsp"/>
    </head>
    <body class="stext-112 cl6 p-b-26">


        <header class="header-v4">
            <div class="container-menu-desktop">
                <jsp:include page="components/header-navbar.jsp"/>
                <jsp:include page="components/main-navbar.jsp"/>
            </div>
        </header>

        <div class="container mt-5">

            <div class="row">
                <div class="col-md-6">
                    <div class="p-b-10">
                        <h3 class="ltext-103 cl5">
                            Order Summary
                        </h3>
                    </div>
                    <div class="card">
                        <div class="card-body">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Item</th>
                                        <th>Quantity</th>
                                        <th>Price</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:set var="totalCost" value="0"/>
                                    <c:forEach var="cartItem" items="${sessionScope.shoppingCart.cartItems}">
                                        <tr>
                                            <td>${cartItem.product.name}</td>
                                            <td>${cartItem.cartQuantity}</td>
                                            <td><fmt:formatNumber type="currency" value="${cartItem.totalPrice}" maxFractionDigits="2"/></td>
                                        </tr>
                                        <c:set var="totalCost" value="${totalCost + cartItem.totalPrice}"/>
                                    </c:forEach>
                                    <tr>
                                        <td><b>Total</b></td>
                                        <td></td>
                                        <td><b><fmt:formatNumber type="currency" value="${totalCost}" maxFractionDigits="2"/></b></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="p-b-10">
                        <h3 class="ltext-103 cl5">
                            Payment Information
                        </h3>
                    </div>

                    <c:if test="${empty sessionScope.user.paymentMethods}">
                        <div class="p-t-50">
                            <h2 class="mtext-105 cl2 p-b-30 txt-center">No available payments.</h2>
                        </div>


                    </c:if>
                    <c:if test="${not empty sessionScope.user.paymentMethods}">
                        <form>
                            <div class="form-group">
                                <label for="payment-method">Payment Method:</label>

                                <select class="form-control" id="payment-method" onchange="updateCardNumber()">
                                    <option value="" selected disabled>Pick a card</option>
                                    <c:forEach items="${sessionScope.user.paymentMethods}" var="paymentmethod">
                                        <option value="${paymentmethod.stripePaymentMethodId}"
                                                data-last4="${paymentmethod.cardLast4}">
                                                ${paymentmethod.paymentMethodType.toUpperCase()} ${paymentmethod.cardLast4}
                                        </option>
                                    </c:forEach>
                                </select>


                            </div>
                            <div id="credit-card-fields">
                                <div class="form-group">
                                    <label for="card-number">Credit Card Number:</label>
                                    <input type="text" class="form-control" id="card-number"
                                           placeholder="Enter your credit card number" value="" disabled>

                                </div>
                                <div class="form-row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="expiry">Expiration Date:</label>
                                            <input type="text" class="form-control" id="expiry" placeholder="MM/YY"
                                                   disabled>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="cvv">CVV:</label>
                                            <input type="password" class="form-control" id="cvv" placeholder="Enter CVV"
                                                   maxlength="3">
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <a type="submit" class="flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04"
                               onclick="checkOut()" style="color: white; width: 100%;">
                                Confirm Order
                            </a>
                        </form>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
    <jsp:include page="components/common-footer-html.jsp"/>
    <script src="${pageContext.request.contextPath}/public/js/jsp/checkout.js.jsp"></script>
</html>
