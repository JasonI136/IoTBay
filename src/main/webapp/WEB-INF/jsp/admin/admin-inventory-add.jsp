<%-- 
    Document   : admin-inventory-add
    Created on : 18 Apr 2023, 6:04:41 pm
    Author     : jasonmba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Admin Add Product</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <jsp:include page="../components/common-header-html.jsp"/>

    </head>
    <body class="animsition">

        <header class="header-v4">
            <div class="container-menu-desktop">
                <jsp:include page="../components/header-navbar.jsp"/>
                <jsp:include page="../components/admin-navbar.jsp"/>
            </div>
        </header>

        <!-- Title page -->
        <div>
            <section class="bg-img1 txt-center p-lr-15 p-tb-92" style="background-image: url('${pageContext.request.contextPath}/public/images/bg-add-item.png');">
                <h2 class="ltext-105 cl0 txt-center">
                    Add new Product
                </h2>
            </section>
        </div>

        <!-- breadcrumb -->
        <div class="container">
            <div class="bread-crumb flex-w p-l-25 p-r-15 p-t-30 p-lr-0-lg">
                <a href="${pageContext.request.contextPath}/admin" class="stext-109 cl8 hov-cl1 trans-04">
                    Admin
                    <i class="fa fa-angle-right m-l-9 m-r-10" aria-hidden="true"></i>
                </a>

                <a href="${pageContext.request.contextPath}/admin/inventory" class="stext-109 cl8 hov-cl1 trans-04">
                    Inventory
                    <i class="fa fa-angle-right m-l-9 m-r-10" aria-hidden="true"></i>
                </a>

                <span class="stext-109 cl4">
                    Add Product
                </span>
            </div>
        </div>

        <section class="bg0 p-t-104 p-b-116">
            <div class="container">
                <div class="bor10 p-lr-70 p-t-55 p-b-70 p-lr-15-lg w-full-md">
                    <form method="POST" action="addProduct">
                        <h4 class="mtext-105 cl2 txt-center p-b-30">
                            Add a Product
                        </h4>

                        <div class="row" style="gap: 10px">
                            <div class="col bor8 m-b-20 how-pos4-parent">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" id="productName" name="productName" required placeholder="Product Title">
                                <img class="how-pos4 pointer-none" src="${pageContext.request.contextPath}/public/images/icons/package.svg" alt="ICON">
                            </div>
                            <div class="col bor8 m-b-20 how-pos4-parent">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="number" id="productPrice" name="productPrice" required placeholder="Product Price">
                                <img class="how-pos4 pointer-none" src="${pageContext.request.contextPath}/public/images/icons/dollar-sign.svg" alt="ICON">
                            </div>
                        </div>

                        <div class="row" style="gap: 10px">
                            <div class="col bor8 m-b-20 how-pos4-parent">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" id="productCategory" name="productCategory" required placeholder="Product Category">
                                <img class="how-pos4 pointer-none" src="${pageContext.request.contextPath}/public/images/icons/list.svg" alt="ICON">
                            </div>
                            <div class="col bor8 m-b-20 how-pos4-parent">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="number" id="productQuantity" name="productQuantity" required placeholder="Product Quantity">
                                <img class="how-pos4 pointer-none" src="${pageContext.request.contextPath}/public/images/icons/hash.svg" alt="ICON">
                            </div>
                        </div>
                            
                        <div class="row">
                            <div class="col bor8 m-b-20 how-pos4-parent">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" id="productImage" name="productImage" required placeholder="Product Image URL">
                                <img class="how-pos4 pointer-none" src="${pageContext.request.contextPath}/public/images/icons/image.svg" alt="ICON">
                            </div>
                        </div>


                        <div class="row">
                            <div class="col bor8 m-b-20 how-pos4-parent">
                                <textarea class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" id="productDescription" name="productDescription" required placeholder="Product Description" rows="4" cols="50"></textarea>
                                <img class="how-pos4 pointer-none" src="${pageContext.request.contextPath}/public/images/icons/align-justify.svg" alt="ICON">
                            </div>
                        </div>


                        <div class="">
                            <button type="submit" class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer">
                                Add Product
                            </button>
                        </div>

                    </form>
                </div>
            </div>
        </section>

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

    <script src="${pageContext.request.contextPath}/public/vendor/jquery/jquery-3.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/vendor/animsition/js/animsition.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/vendor/bootstrap/js/popper.js"></script>
    <script src="${pageContext.request.contextPath}/public/vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/vendor/select2/select2.min.js"></script>
    <script>
        $(".js-select2").each(function () {
            $(this).select2({
                minimumResultsForSearch: 20,
                dropdownParent: $(this).next('.dropDownSelect2')
            });
        })
    </script>
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
    <script src="${pageContext.request.contextPath}/public/vendor/isotope/isotope.pkgd.min.js"></script>
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
    <script src="${pageContext.request.contextPath}/public/vendor/perfect-scrollbar/perfect-scrollbar.min.js"></script>
    <script>
        $('.js-pscroll').each(function () {
            $(this).css('position', 'relative');
            $(this).css('overflow', 'hidden');
            var ps = new PerfectScrollbar(this, {
                wheelSpeed: 1,
                scrollingThreshold: 1000,
                wheelPropagation: false,
            });

            $(window).on('resize', function () {
                ps.update();
            })
        });
    </script>
    <script>

        function fetchProductDetails(productId) {
            fetch('${pageContext.request.contextPath}/product/' + productId)
                    .then(response => response.json())
                    .then(json => {
                        document.querySelector('#product-name').innerHTML = json.name;
                        document.querySelector('#img-product-modal').src = json.imageURL;
                        document.querySelector('#product-description').innerHTML = json.description;
                        document.querySelector('#product-price').innerHTML = "$ " + json.price;
                        document.querySelector('#add-to-cart').value = json.id;
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
    <script src="${pageContext.request.contextPath}/public/js/main.js"></script>
</html>
