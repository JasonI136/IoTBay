<%--
  Created by IntelliJ IDEA.
  User: cmesina
  Date: 7/5/2023
  Time: 12:05 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

                    <div class="wrap-slick3 flex-sb flex-w justify-content-center">
                        <div class="wrap-slick3-arrows flex-sb-m flex-w"></div>

                        <div class="slick3 gallery-lb">
                            <div class="item-slick3" data-thumb="product-image"
                                 id="img-product-modal-thumb">
                                <div class="wrap-pic-w pos-relative">
                                    <img src="" alt="IMG-PRODUCT" id="img-product-modal" class="img-thumbnail">

                                    <a class="flex-c-m size-108 how-pos1 bor0 fs-16 cl10 bg0 hov-btn3 trans-04"
                                       id="product-image-full">
                                        <i class="fa fa-expand"></i>
                                    </a>
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

                        <div class="flex-w flex-c-m p-b-10">
                            <div class="size-204 flex-w flex-m respon6-next justify-content-center" style="gap: 10px">
                                <div class="wrap-num-product flex-w m-tb-10" >
                                    <div class="btn-num-product-down-modal cl8 hov-btn3 trans-04 flex-c-m">
                                        <i class="fs-16 zmdi zmdi-minus"></i>
                                    </div>

                                    <input class="mtext-104 cl3 txt-center num-product" type="number"
                                           name="num-product"
                                           id="quantity"
                                           oninput="this.value = Math.abs(this.value)" min="1" value="1"/>
                                    <div class="btn-num-product-up cl8 hov-btn3 trans-04 flex-c-m">
                                        <i class="fs-16 zmdi zmdi-plus"></i>
                                    </div>
                                </div>

                                <button value=""
                                        class="flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04 js-addcart-detail"
                                        name="productId" id="add-to-cart">
                                    Add to cart
                                </button>
                            </div>
                        </div>
                    </div>


                </div>
            </div>
        </div>

    </div>
</div>