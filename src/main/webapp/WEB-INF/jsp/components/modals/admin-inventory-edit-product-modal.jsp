<%--
  Created by IntelliJ IDEA.
  User: cmesina
  Date: 29/4/2023
  Time: 5:32 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="wrap-modal1 js-modal1 p-t-60 p-b-20" id="edit-product-modal">
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
                            <span class="input-group-text">Product Name</span>
                            <input type="text" class="form-control" id="product-name" aria-describedby="basic-addon1">
                        </div>
                        <div class="input-group mb-3">
                            <span class="input-group-text">Price</span>
                            <input type="text" class="form-control" id="product-price" aria-describedby="basic-addon1"
                            >
                        </div>
                        <div class="input-group p-b-15">
                            <span class="input-group-text">Description</span>
                            <textarea class="form-control" id="product-description" rows="10"></textarea>
                        </div>
                        <div class="input-group mb-3">
                            <span class="input-group-text">Stock</span>
                            <input type="text" class="form-control" id="product-quantity"
                                   aria-describedby="basic-addon1">
                        </div>
                        <div class="input-group mb-3">
                            <span class="input-group-text">Image URL</span>
                            <input type="text" class="form-control" id="product-image-url"
                                   aria-describedby="basic-addon1">
                        </div>

                        <div class="input-group mb-3">
                            <span class="input-group-text">Category</span>
                            <select class="form-select" id="product-category">
                            </select>
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