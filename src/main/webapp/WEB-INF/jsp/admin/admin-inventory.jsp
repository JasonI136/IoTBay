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
    <section class="bg0 p-b-20 w-full m-auto">
        <div>
            <div class=" bor10 p-lr-70 p-t-55 p-b-70 p-lr-15-lg w-full-md">
                <div class="container mt-4 pb-3">
                    <div class="p-b-10">
                        <h3 class="ltext-103 cl5">
                            Inventory Management
                        </h3>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="basic-addon1">Search</span>
                        </div>
                        <input type="text" class="form-control" placeholder="Enter search term"
                               aria-describedby="basic-addon1" id="search-input">
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
                                           id="product-image-full">
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
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text">Product Name</span>
                            </div>
                            <input type="text" class="form-control" id="product-name" aria-describedby="basic-addon1"
                            >
                        </div>
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text">Price</span>
                            </div>
                            <input type="text" class="form-control" id="product-price" aria-describedby="basic-addon1"
                            >
                        </div>
                        <div class="input-group p-b-15">
                            <div class="input-group-prepend">
                                <span class="input-group-text">Description</span>
                            </div>
                            <textarea class="form-control" id="product-description" rows="10"></textarea>
                        </div>
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text">Stock</span>
                            </div>
                            <input type="text" class="form-control" id="product-quantity"
                                   aria-describedby="basic-addon1">
                        </div>
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text">Image URL</span>
                            </div>
                            <input type="text" class="form-control" id="product-image-url"
                                   aria-describedby="basic-addon1">
                        </div>

                        <button class="btn btn-primary" id="btn-update-product" data-product-id="0"
                                onclick="updateProduct()">Update
                        </button>
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
<script src="${pageContext.request.contextPath}/public/js/jsp/admin-inventory.js.jsp"></script>
</html>
