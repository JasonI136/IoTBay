<%--
  Created by IntelliJ IDEA.
  User: cmesina
  Date: 24/4/2023
  Time: 9:58 am
  To change this template use File | Settings | File Templates.
--%>
    <%@ page contentType="text/javascript" %>


    function updateCardNumber() {
        var paymentMethodSelect = document.getElementById("payment-method");
        var cardNumberInput = document.getElementById("card-number");
        var selectedOption = paymentMethodSelect.options[paymentMethodSelect.selectedIndex];
        var last4 = selectedOption.getAttribute("data-last4");
        cardNumberInput.value = last4;
    }

    function checkOut(event) {
        event.preventDefault();

        var btnPay = document.querySelector('#btn-pay');
        var btnPaySpinner = document.querySelector('#btn-pay-spinner');
        var btnPayText = document.querySelector('#btn-pay-text');

        btnPay.setAttribute('disabled', 'disabled');
        btnPaySpinner.removeAttribute('hidden');
        btnPayText.setAttribute('hidden', 'hidden');

        let paymentMethodId = document.getElementById("payment-method").value;
        let stripe = Stripe(stripePublishableKey);

        fetch("${pageContext.request.contextPath}/cart/checkout", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            }
        }).then(response => {
            return response.json();
        }).then(json => {
            if (json.statusCode === 200) {
                stripe.confirmCardPayment(json.data.client_secret, {
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
                            Swal.fire({
                                title: 'Payment Successful!',
                                icon: 'success',
                                text: 'Your payment was successful.',
                                showCancelButton: false,
                                confirmButtonColor: '#3085d6',
                                confirmButtonText: 'OK',
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    // clear the cart
                                    fetch("${pageContext.request.contextPath}/cart/clear", {
                                        method: "DELETE",
                                        headers: {
                                            "Content-Type": "application/json"
                                        }
                                    }).then(response => {
                                        return response.json();
                                    }).then(json => {
                                        if (json.statusCode === 200) {
                                            window.location.href = "${pageContext.request.contextPath}/user";
                                        }
                                    }).catch(error => {
                                        // ignore the cart clear error
                                        window.location.href = "${pageContext.request.contextPath}/user";
                                    });
                                }
                            });
                        }
                    }

                    btnPay.removeAttribute('disabled');
                    btnPaySpinner.setAttribute('hidden', 'hidden');
                    btnPayText.removeAttribute('hidden');
                });
            } else {
                Swal.fire({
                    title: json.message,
                    icon: 'error',
                    text: json.data,
                    showCancelButton: false,
                    confirmButtonColor: '#3085d6',
                    confirmButtonText: 'OK',
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = "${pageContext.request.contextPath}/user";
                    }
                });
            }
        }).catch(error => {
            Swal.fire({
                title: 'Error',
                icon: 'error',
                text: 'An error occurred while processing your payment.',
                showCancelButton: false,
                confirmButtonColor: '#3085d6',
                confirmButtonText: 'OK',
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = "${pageContext.request.contextPath}/user";
                }
            }).finally(() => {
                btnPay.removeAttribute('disabled');
                btnPaySpinner.setAttribute('hidden', 'hidden');
                btnPayText.removeAttribute('hidden');
            });
        })
    }