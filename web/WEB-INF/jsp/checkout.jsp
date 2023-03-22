<%--
  Created by IntelliJ IDEA.
  User: cmesina
  Date: 21/3/2023
  Time: 6:47 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Title</title>
    <script src="https://js.stripe.com/v3/"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
<h2>Your Items</h2>

<ul>
    <c:forEach var="cartItem" items="${sessionScope.shoppingCart.cartItems}">
        <li>${cartItem.product.name} - ${cartItem.cartQuantity} - ${cartItem.totalPrice}</li>
    </c:forEach>
</ul>

<label for="payment_method">Select Payment Method</label>
<select name="payment_method" id="payment_method">
    <c:forEach items="${sessionScope.user.paymentMethods}" var="paymentmethod">
        <option value="${paymentmethod.stripePaymentMethodId}">${paymentmethod.paymentMethodType} ${paymentmethod.cardLast4}</option>
    </c:forEach>
</select>
<br>
<button onclick="checkOut()">
    Checkout
</button>

</body>
<script>
    function checkOut() {
        let paymentMethodId = document.getElementById("payment_method").value;
        fetch("${pageContext.request.contextPath}/cart/checkout", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            }
        }).then(response => {
            if (response.status === 200) {
                return response.json();
            }
        }).then(json => {
            var stripe = Stripe('pk_test_51Mn84VBy1COsuBfJE5pCaFGN2dfkJem1F99HjxWRjlKd7TtIP5GRdRpOxEo4FPXaWTkFEVNIklWt9cwQkWpYvutk00lSMgFuPC');
            stripe.confirmCardPayment(json.client_secret, {
                payment_method: paymentMethodId
            }).then(function (result) {
                if (result.error) {
                    // Show error to your customer (e.g., insufficient funds)
                    Swal.fire({
                        title: 'Payment Failed!',
                        icon: 'error',
                        text: result.error.message,
                        showCancelButton: false,
                        confirmButtonColor: '#3085d6',
                        confirmButtonText: 'OK',
                    }).then((result) => {
                        if (result.isConfirmed) {
                            window.location.href = "${pageContext.request.contextPath}/user";
                        }
                    });
                } else {
                    // The payment has been processed!
                    if (result.paymentIntent.status === 'succeeded') {
                        fetch(`${pageContext.request.contextPath}/order/confirm?paymentIntentId=\${result.paymentIntent.id}`, {
                            method: "POST",
                            headers: {
                                "Content-Type": "application/json"
                            }
                        }).then(response => {
                            if (response.status === 200) {
                                // Show a success message to your customer
                                // There's a risk of the customer closing the window before callback
                                // execution. Set up a webhook or plugin to listen for the
                                // payment_intent.succeeded event that handles any business critical
                                // post-payment actions.
                                Swal.fire({
                                    title: 'Payment Successful!',
                                    icon: 'success',
                                    text: 'Your payment was successful.',
                                    showCancelButton: false,
                                    confirmButtonColor: '#3085d6',
                                    confirmButtonText: 'OK',
                                }).then((result) => {
                                    if (result.isConfirmed) {
                                        window.location.href = "${pageContext.request.contextPath}/user";
                                    }
                                });
                            } else {
                                Swal.fire({
                                    title: 'Payment Failed!',
                                    icon: 'error',
                                    text: "There was an error processing your order.",
                                    showCancelButton: false,
                                    confirmButtonColor: '#3085d6',
                                    confirmButtonText: 'OK',
                                }).then((result) => {
                                    if (result.isConfirmed) {
                                        window.location.href = "${pageContext.request.contextPath}/user";
                                    }
                                });
                            }
                        })


                    }
                }
            });
        })
    }
</script>
</html>
