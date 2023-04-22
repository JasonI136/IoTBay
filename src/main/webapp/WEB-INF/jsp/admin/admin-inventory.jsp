<%-- 
    Document   : admin-inventory
    Created on : 30/03/2023, 1:47:41 PM
    Author     : jasonmba
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Admin Inventory</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <jsp:include page="../components/header-links.jsp"/>

    </head>
    <body class="animsition">

        <header class="header-v4">
            <div class="container-menu-desktop">
                <jsp:include page="../components/header-navbar.jsp"/>
                <jsp:include page="../components/admin-navbar.jsp"/>
            </div>
        </header>


        <!-- Product -->
        <section class="bg0 p-t-23 p-b-140">
            <div class="container">
                <div class="p-b-10">
                    <h3 class="ltext-103 cl5">
                        Product Overview
                    </h3>
                </div>

                <div class="flex-w flex-sb-m p-b-52">
                    <div class="flex-w flex-l-m filter-tope-group m-tb-10">
                        <button class="stext-106 cl6 hov1 bor3 trans-04 m-r-32 m-tb-5 how-active1" id="allProductsBtn"
                                data-filter="*">
                            All Products
                        </button>

                        <c:forEach var="category" items="${categories}">
                            <button class="stext-106 cl6 hov1 bor3 trans-04 m-r-32 m-tb-5"
                                    data-filter=".${category.nameNoSpace}">
                                ${category.name}
                            </button>
                        </c:forEach>
                    </div>

                    <div class="flex-w flex-c-m m-tb-10">
                        <div class="flex-c-m stext-106 cl6 size-105 bor4 pointer hov-btn3 trans-04 m-tb-4 js-show-search">
                            <i class="icon-search cl2 m-r-6 fs-15 trans-04 zmdi zmdi-search"></i>
                            <i class="icon-close-search cl2 m-r-6 fs-15 trans-04 zmdi zmdi-close dis-none"></i>
                            Search
                        </div>
                    </div>

                    <!-- Search product -->
                    <div class="dis-none panel-search w-full p-t-10 p-b-15">
                        <div class="bor8 dis-flex p-l-15">
                            <button class="size-113 flex-c-m fs-16 cl2 hov-cl1 trans-04">
                                <i class="zmdi zmdi-search"></i>
                            </button>

                            <input class="mtext-107 cl2 size-114 plh2 p-r-15" type="text" id="searchInput"
                                   placeholder="Search products...">
                        </div>
                    </div>
                </div>

                <div class="row isotope-grid" id="productList">

                    <c:forEach items="${products}" var="product">
                        <div class="col-sm-6 col-md-4 col-lg-3 p-b-35 isotope-item ${product.categoryNameNoSpace}">
                            <!-- Block2 -->
                            <div class="block2">
                                <div class="block2-pic hov-img0">
                                    <img src="${product.imageURL}" alt="IMG-PRODUCT">

                                    <a href="#"
                                       class="block2-btn flex-c-m stext-103 cl2 size-102 bg0 bor2 hov-btn1 p-lr-15 trans-04 js-show-modal1"
                                       onClick="fetchProductDetails(${product.id})">
                                        Quick View
                                    </a>
                                </div>

                                <div class="block2-txt flex-w flex-t p-t-14">
                                    <div class="block2-txt-child1 flex-col-l ">
                                        <a href="product-detail.html" class="stext-104 cl4 hov-cl1 trans-04 js-name-b2 p-b-6">
                                            ${product.name}
                                        </a>

                                        <span class="stext-105 cl3">
                                            $${product.price}
                                        </span>
                                    </div>

                                    <div class="block2-txt-child2 flex-r p-t-3">
                                        <a href="#" class="btn-addwish-b2 dis-block pos-relative js-addwish-b2">
                                            <img class="icon-heart1 dis-block trans-04"
                                                 src="${pageContext.request.contextPath}/public/images/icons/icon-heart-01.png"
                                                 alt="ICON">
                                            <img class="icon-heart2 dis-block trans-04 ab-t-l"
                                                 src="${pageContext.request.contextPath}/public/images/icons/icon-heart-02.png"
                                                 alt="ICON">
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </section>

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
                                        <div class="item-slick3" data-thumb="product-image" id="img-product-modal-thumb">
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
                                <p class="stext-102 cl3 p-t-23">Quantity:&nbsp;&nbsp;<span id="product-quantity"></span></p>
                                <div class="flex-w flex-r-m p-b-10">
                                    <div class="size-204 flex-w flex-m respon6-next">
                                        <div class="wrap-num-product flex-w m-r-20 m-tb-10">
                                            <div class="btn-num-product-down-modal cl8 hov-btn3 trans-04 flex-c-m">
                                                <i class="fs-16 zmdi zmdi-minus"></i>
                                            </div>

                                            <input class="mtext-104 cl3 txt-center num-product" type="number" name="num-product"
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
                    <a href="${pageContext.request.contextPath}/admin/inventory/add" class="btn btn-primary btn-floating">
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
                        document.querySelector('#product-quantity').innerHTML = json.quantity;
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
