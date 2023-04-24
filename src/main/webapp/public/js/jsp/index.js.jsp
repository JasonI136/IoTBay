<%--
  Created by IntelliJ IDEA.
  User: cmesina
  Date: 24/4/2023
  Time: 9:42 am
  To change this template use File | Settings | File Templates.
--%>
    <%@ page contentType="text/javascript" %>

    function fetchProductDetails(productId) {
        fetch('${pageContext.request.contextPath}/product/' + productId)
            .then(response => response.json())
            .then(json => {
                document.querySelector('#product-name').innerHTML = json.data.name;
                document.querySelector('#img-product-modal').src = json.data.imageURL;
                document.querySelector('#product-description').innerHTML = json.data.description;
                document.querySelector('#product-price').innerHTML = "$ " + json.data.price;
                document.querySelector('#add-to-cart').value = json.data.id;
                document.querySelector('#product-image-full').href = json.data.imageURL;
            });
    }

    function addToCart(productId, quantity) {
        const data = new URLSearchParams()
        data.append('productId', productId);
        data.append('quantity', quantity);
        return fetch('${pageContext.request.contextPath}/cart', {
            method: 'POST',
            body: data
        })

    }

    $('.js-addwish-b2').on('click', function (e) {
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
            addToCart(document.querySelector('#add-to-cart').value, document.querySelector('#quantity').value)
                .then(response => {
                    if (response.status === 200) {
                        Swal.fire({
                            title: 'Success!',
                            icon: 'success',
                            text: 'Item added to cart!',
                            showCancelButton: false,
                            confirmButtonColor: '#3085d6',
                            confirmButtonText: 'OK',
                            target: document.querySelector('#modal-content')
                        }).then((result) => {
                            if (result.isConfirmed) {
                                window.location.href = "${pageContext.request.contextPath}/cart";
                            }
                        });
                    }
                });
        });
    });
