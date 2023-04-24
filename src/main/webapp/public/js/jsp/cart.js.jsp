<%--
  Created by IntelliJ IDEA.
  User: cmesina
  Date: 24/4/2023
  Time: 9:59 am
  To change this template use File | Settings | File Templates.
--%>
    <%@ page contentType="text/javascript" %>
    function updateCart() {
        const quantityInputs = document.querySelectorAll("#cartItem-product-quantity");

        const payload = {};

        quantityInputs.forEach(input => {
            const productId = input.getAttribute("name");
            const quantity = input.value;
            payload[productId] = quantity;
        });

        fetch("${pageContext.request.contextPath}/cart/update", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {
                "Content-Type": "application/json"
            }
        }).then(response => {
            return response.json();
        }).then(json => {
            Swal.fire({
                title: json.message,
                icon: json.statusCode === 200 ? 'success' : 'error',
                text: json.data,
                showCancelButton: false,
                confirmButtonColor: '#3085d6',
                confirmButtonText: 'OK',
                target: document.querySelector('#modal-content')
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.reload();
                }
            });
        }).catch(error => {
            Swal.fire({
                title: "Error",
                icon: 'error',
                text: 'Something went wrong',
                showCancelButton: false,
                confirmButtonColor: '#3085d6',
                confirmButtonText: 'OK',
                target: document.querySelector('#modal-content')
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.reload();
                }
            });
        })
    }



    $('.js-addwish-b2, .js-addwish-detail').on('click', function (e) {
        e.preventDefault();
    });

    $('.js-addwish-b2').each(function () {
        var nameProduct = $(this).parent().parent().find('.js-name-b2').html();
        $(this).on('click', function () {
            swal(nameProduct, "is added to wishlist !", "success");

            $(this).addClass('js-addedwish-b2');
            $(this).off('click');
        });
    });

    $('.js-addwish-detail').each(function () {
        var nameProduct = $(this).parent().parent().parent().find('.js-name-detail').html();

        $(this).on('click', function () {
            swal(nameProduct, "is added to wishlist !", "success");

            $(this).addClass('js-addedwish-detail');
            $(this).off('click');
        });
    });

    /*---------------------------------------------*/

    $('.js-addcart-detail').each(function () {
        var nameProduct = $(this).parent().parent().parent().parent().find('.js-name-detail').html();
        $(this).on('click', function () {
            swal(nameProduct, "is added to cart !", "success");
        });
    });