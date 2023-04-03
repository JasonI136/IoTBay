<%-- 
    Document   : about
    Created on : 19/03/2023, 11:49:21 PM
    Author     : jasonmba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>


		<title>Track Orders</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">

		<jsp:include page="components/header-links.jsp" />

	</head>
	<body class="animsition">

		<!-- Header -->
		<header class="header-v4">
			<div class="container-menu-desktop">
				<jsp:include page="components/header-navbar.jsp" />
				<jsp:include page="components/main-navbar.jsp" />
			</div>
		</header>


		<!-- Cart -->
		<div class="wrap-header-cart js-panel-cart">
			<div class="s-full js-hide-cart"></div>

			<div class="header-cart flex-col-l p-l-65 p-r-25">
				<div class="header-cart-title flex-w flex-sb-m p-b-8">
					<span class="mtext-103 cl2">
						Your Cart
					</span>

					<div class="fs-35 lh-10 cl2 p-lr-5 pointer hov-cl1 trans-04 js-hide-cart">
						<i class="zmdi zmdi-close"></i>
					</div>
				</div>

				<div class="header-cart-content flex-w js-pscroll">
					<ul class="header-cart-wrapitem w-full">
						<li class="header-cart-item flex-w flex-t m-b-12">
							<div class="header-cart-item-img">
								<img src="" alt="IMG">
							</div>

							<div class="header-cart-item-txt p-t-8">
								<a href="#" class="header-cart-item-name m-b-18 hov-cl1 trans-04">
									White Shirt Pleat
								</a>

								<span class="header-cart-item-info">
									1 x $19.00
								</span>
							</div>
						</li>

						<li class="header-cart-item flex-w flex-t m-b-12">
							<div class="header-cart-item-img">
								<img src="" alt="IMG">
							</div>

							<div class="header-cart-item-txt p-t-8">
								<a href="#" class="header-cart-item-name m-b-18 hov-cl1 trans-04">
									Converse All Star
								</a>

								<span class="header-cart-item-info">
									1 x $39.00
								</span>
							</div>
						</li>

						<li class="header-cart-item flex-w flex-t m-b-12">
							<div class="header-cart-item-img">
								<img src="images/item-cart-03.jpg" alt="IMG">
							</div>

							<div class="header-cart-item-txt p-t-8">
								<a href="#" class="header-cart-item-name m-b-18 hov-cl1 trans-04">
									Nixon Porter Leather
								</a>

								<span class="header-cart-item-info">
									1 x $17.00
								</span>
							</div>
						</li>
					</ul>

					<div class="w-full">
						<div class="header-cart-total w-full p-tb-40">
							Total: $75.00
						</div>

						<div class="header-cart-buttons flex-w w-full">
							<a href="shoping-cart.html" class="flex-c-m stext-101 cl0 size-107 bg3 bor2 hov-btn3 p-lr-15 trans-04 m-r-8 m-b-10">
								View Cart
							</a>

							<a href="shoping-cart.html" class="flex-c-m stext-101 cl0 size-107 bg3 bor2 hov-btn3 p-lr-15 trans-04 m-b-10">
								Check Out
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>


		<!-- Title page -->
		<section class="bg-img1 txt-center p-lr-15 p-tb-92" style="background-image: url('images/bg-01.jpg');">
			<h2 class="ltext-105 cl0 txt-center">
				About
			</h2>
		</section>	


		<!-- Content page -->
		<section class="bg0 p-t-75 p-b-120">
			<div class="container">
				<div class="row p-b-148">
					<div class="col-md-7 col-lg-8">
						<div class="p-t-7 p-r-85 p-r-15-lg p-r-0-md">
							<h3 class="mtext-111 cl2 p-b-16">
								Who are we?
							</h3>

							<p class="stext-113 cl6 p-b-26">
								
IoTBay - Internet of Things Bay -  is the culmination of 30 years in information technology business. Dennis & Brian built the business based on the simple principles of impeccable service and passion for technology, we are also known for the wonderful brick and mortar store located in Sydney. Exceeding peoples expectations is our aim, and where else better to do so than on one of the world’s most technologically advanced cities!
							</p>

							<p class="stext-113 cl6 p-b-26">
							
							</p>

							<p class="stext-113 cl6 p-b-26">
								Any questions? Let us know in store, by email at iotbay@sales.com or phone +61 861 654 222
							</p>
						</div>
					</div>

					<div class="col-11 col-md-5 col-lg-4 m-lr-auto">
						<div class="how-bor1 ">
							<div class="hov-img0">
								<img src="${pageContext.request.contextPath}/public/images/raspberrypi.jpg" alt="IMG">
							</div>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="order-md-2 col-md-7 col-lg-8 p-b-30">
						<div class="p-t-7 p-l-85 p-l-15-lg p-l-0-md">

							<div class="bor16 p-l-29 p-b-9 m-t-22">
								<p class="stext-114 cl6 p-r-40 p-b-11">
								The most important single aspect of software development is to be clear about what you are trying to build.
								</p>

								<span class="stext-111 cl8">
									- Bjarne Stroustrup
								</span>
							</div>
						</div>
					</div>
					
					<!--
					<div class="order-md-1 col-11 col-md-5 col-lg-4 m-lr-auto p-b-30">
						<div class="how-bor2">
							<div class="hov-img0">
								<img src="${pageContext.request.contextPath}/public/raspberrypi.jpg" alt="IMG">
							</div>
						</div>
					</div>
					-->
				</div>
			</div>
		</section>	

		<!-- Footer -->
		<footer class="bg3 p-t-75 p-b-32">
			<jsp:include page="components/footer.jsp" />
		</footer>


		<!-- Back to top -->
		<div class="btn-back-to-top" id="myBtn">
			<span class="symbol-btn-back-to-top">
				<i class="zmdi zmdi-chevron-up"></i>
			</span>
		</div>

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


		<script src="${pageContext.request.contextPath}/public/vendor/isotope/isotope.pkgd.min.js"></script>

		<script src="${pageContext.request.contextPath}/public/vendor/MagnificPopup/jquery.magnific-popup.min.js"></script>

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
		<script src="${pageContext.request.contextPath}/public/js/map-custom.js"></script>

		<script src="${pageContext.request.contextPath}/public/js/main.js"></script>

	</body>
</html>