<%-- 
    Document   : admin-inventory
    Created on : 30/03/2023, 1:47:41 PM
    Author     : jasonmba
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Inventory</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="https://unpkg.com/tabulator-tables@5.4.4/dist/css/tabulator.min.css" rel="stylesheet">
    <script type="text/javascript" src="https://unpkg.com/tabulator-tables@5.4.4/dist/js/tabulator.min.js"></script>
    <jsp:include page="../components/common-header-html.jsp"/>

</head>
<body class="animsition cl6 p-b-26">

<header class="header-v4">
    <div class="container-menu-desktop">
        <jsp:include page="../components/header-navbar.jsp"/>
        <jsp:include page="../components/admin-navbar.jsp"/>
    </div>
</header>

<div class="container-fluid">
    <section class="bg0 p-b-20 w-75 m-auto">
        <div>
            <div class=" bor10 p-lr-70 p-t-55 p-b-70 p-lr-15-lg w-full-md">
                <div class="container mt-4 pb-3">
                    <div class="p-b-10">
                        <h3 class="ltext-103 cl5">
                            Inventory Management
                        </h3>
                    </div>
                    <div id="product-table"></div>
                </div>

            </div>
        </div>
    </section>
</div>

<!-- PRODUCT MODAL -->
<div class="wrap-modal1 js-modal1 p-t-60 p-b-20">
    <div class="overlay-modal1 js-hide-modal1"></div>
    <div class="container">
        <div class="bg0 p-t-60 p-b-30 p-lr-15-lg how-pos3-parent" id="modal-content">
            <button class="how-pos3 hov3 trans-04 js-hide-modal1">
                <img src="${pageContext.request.contextPath}/public/images/icons/icon-close.png" alt="CLOSE">
            </button>

            <div class="row">
                <div class="col-md-6 col-lg-7 p-b-30">
                    <div class="p-l-25 p-r-30 p-lr-0-lg">
                        <div class="wrap-slick3 flex-sb flex-w">
                            <div class="wrap-slick3-arrows flex-sb-m flex-w"></div>

                            <div class="slick3 gallery-lb">
                                <div class="item-slick3" data-thumb="product-image"
                                     id="img-product-modal-thumb">
                                    <div class="wrap-pic-w pos-relative">
                                        <img src="" alt="IMG-PRODUCT" id="img-product-modal">

                                        <a class="flex-c-m size-108 how-pos1 bor0 fs-16 cl10 bg0 hov-btn3 trans-04"
                                           href="${pageContext.request.contextPath}/public/images/product-detail-01.jpg">
                                            <i class="fa fa-expand"></i>
                                        </a>
                                    </div>
                                </div>


                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-6 col-lg-5 p-b-30">
                    <div class="p-r-50 p-t-5 p-lr-0-lg">
                        <h4 class="mtext-105 cl2 js-name-detail p-b-14" id="product-name">

                        </h4>

                        <span class="mtext-106 cl2" id="product-price">

                                </span>

                        <p class="stext-102 cl3 p-t-23" id="product-description">

                        </p>
                        <p class="stext-102 cl3 p-t-23">Quantity:&nbsp;&nbsp;<span id="product-quantity"></span>
                        </p>
                        <div class="flex-w flex-r-m p-b-10">
                            <div class="size-204 flex-w flex-m respon6-next">
                                <div class="wrap-num-product flex-w m-r-20 m-tb-10">
                                    <div class="btn-num-product-down-modal cl8 hov-btn3 trans-04 flex-c-m">
                                        <i class="fs-16 zmdi zmdi-minus"></i>
                                    </div>

                                    <input class="mtext-104 cl3 txt-center num-product" type="number"
                                           name="num-product"
                                           min="0" value="1" id="quantity">

                                    <div class="btn-num-product-up cl8 hov-btn3 trans-04 flex-c-m">
                                        <i class="fs-16 zmdi zmdi-plus"></i>
                                    </div>
                                </div>

                                <button value=""
                                        class="flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04 js-addcart-detail"
                                        name="productId" id="add-to-cart">
                                    Update Quantity
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="container">
    <div class="row">
        <div class="col-md-12">
            <a href="${pageContext.request.contextPath}/admin/inventory/add"
               class="btn btn-primary btn-floating">
                <i class="fa fa-plus"></i>
            </a>
        </div>
    </div>
</div>


</body>


<!-- SEARCH FUNCTIONALITY -->
<script>
    const searchInput = document.getElementById('searchInput');
    const productList = document.getElementById('productList');
    const allProductsBtn = document.querySelector('.how-active1');

    searchInput.addEventListener('input', () => {
        const query = searchInput.value.toLowerCase();

        for (const product of productList.children) {
            const name = product.innerText.toLowerCase();
            if (name.includes(query)) {
                product.style.display = 'block';
                if (productList.firstChild !== product) {
                    productList.insertBefore(product, productList.firstChild);
                }
            } else {
                product.style.display = 'none';
            }
        }

        // trigger click event on the "All Products" button
        allProductsBtn.click();
    });
</script>

<jsp:include page="../components/common-footer-html.jsp"/>
<script src="${pageContext.request.contextPath}/public/vendor/daterangepicker/moment.min.js"></script>
<script src="${pageContext.request.contextPath}/public/vendor/daterangepicker/daterangepicker.js"></script>
<script src="${pageContext.request.contextPath}/public/vendor/slick/slick.min.js"></script>
<script src="${pageContext.request.contextPath}/public/js/slick-custom.js"></script>
<script src="${pageContext.request.contextPath}/public/vendor/parallax100/parallax100.js"></script>
<script>
    $('.parallax100').parallax100();
</script>
<script src="${pageContext.request.contextPath}/public/vendor/MagnificPopup/jquery.magnific-popup.min.js"></script>
<script>
    $('.gallery-lb').each(function () { // the containers for all your galleries
        $(this).magnificPopup({
            delegate: 'a', // the selector for gallery item
            type: 'image',
            gallery: {
                enabled: true
            },
            mainClass: 'mfp-fade'
        });
    });
</script>
<script src="${pageContext.request.contextPath}/public/vendor/sweetalert/sweetalert.min.js"></script>
<script>
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
                                window.location.href = "${pageContext.request.contextPath}/product";
                            }
                        });
                    }
                });
        });
    });

</script>
<script>

    function fetchProductDetails(productId) {
        fetch('${pageContext.request.contextPath}/product/' + productId)
            .then(response => response.json())
            .then(json => {
                document.querySelector('#product-name').innerHTML = json.data.name;
                document.querySelector('#img-product-modal').src = json.data.imageURL;
                document.querySelector('#product-description').innerHTML = json.data.description;
                document.querySelector('#product-price').innerHTML = "$ " + json.data.price;
                document.querySelector('#add-to-cart').value = json.data.id;
                document.querySelector('#product-quantity').innerHTML = json.data.quantity;
                document.querySelector('#quantity').value = json.data.quantity;
            }).finally(() => {
            // workaround for js-show-modal1 not working as the anchor tags in the tabulator table are dynamically generated.
            $('.js-modal1').addClass('show-modal1');
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

</script>

<script>
    var table = new Tabulator("#product-table", {
        ajaxURL: "${pageContext.request.contextPath}/shop",
        ajaxParams: {
            "json": true,
        },
        ajaxConfig: "GET",
        ajaxResponse: function (url, params, response) {
            return {
                "data": response.data.products,
                "last_page": response.data.numberOfPages,
            };
        },
        layout: "fitColumns",
        paginationMode: "remote",
        paginationSize: 10,
        pagination: true,
        columns: [
            {title: "ID", field: "id", width: 20},
            {title: "Name", field: "name", width: 150},
            {
                title: "Price",
                field: "price",
                width: 100,
                formatter: "money",
                formatterParams: {
                    decimal: ".",
                    thousand: ",",
                    symbol: "$",
                    symbolAfter: false,
                    precision: 2,
                },
            },
            {title: "Stock", field: "quantity", width: 100},
            {title: "Category", field: "categoryName", width: 300},
            {
                title: "Image",
                field: "imageURL",
                width: 200,
                formatter: "image",
                formatterParams: {
                    height: "100px",
                    width: "100px",
                },
            },
            // add an edit button
            {
                formatter: function (cell, formatterParams) {
                    return `
                        <a class='btn btn-primary btn-sm js-show-modal1' onClick='fetchProductDetails(\${cell.getData().id})'>Edit</a>
                        <a class='btn btn-danger btn-sm' href='${pageContext.request.contextPath}/product/delete/\${cell.getData().id}'>Delete</a>
                    `
                },
                width: 150,
                hozAlign: "center",
            },
        ],
        rowFormatter: function (row) {
            var data = row.getData();
            if (data.quantity <= 0) {
                row.getElement().style.backgroundColor = "#dc3545";
            }

            if (data.quantity > 0 && data.quantity <= 10) {
                row.getElement().style.backgroundColor = "#ffc107";
            }
        }
    });
</script>
</html>
