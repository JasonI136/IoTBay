<%-- 
    Document   : contact
    Created on : 19/03/2023, 11:47:35 PM
    Author     : jasonmba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Contact</title>
		<meta charset="UTF-8">

		<jsp:include page="components/common-header-html.jsp" />
	</head>
	<body class="animsition">
		<!-- Header -->
        <jsp:include page="components/navbar/master-navbar.jsp"/>

		<div >
			<section class="bg-img1 txt-center p-lr-15 p-tb-92" style="background-image: url('${pageContext.request.contextPath}/public/images/bg-orders.jpg');">
				<h2 class="ltext-105 cl0 txt-center">
					Contact Us
				</h2>
			</section>
		</div>
		<!-- Content page -->
		<section class="bg0 p-t-104 p-b-116">
			<div class="container">
				<div class="flex-w flex-tr">
					<div class="size-210 bor10 p-lr-70 p-t-55 p-b-70 p-lr-15-lg w-full-md">
						<form>
							<h4 class="mtext-105 cl2 txt-center p-b-30">
								Wanna have a chat
							</h4>

							<div class="bor8 m-b-20 how-pos4-parent">
								<input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" name="email" placeholder="Your Email Address">
								<img class="how-pos4 pointer-none" src="${pageContext.request.contextPath}/public/images/icons/user.svg" alt="ICON">
							</div>

							<div class="bor8 m-b-30">
								<textarea class="stext-111 cl2 plh3 size-120 p-lr-28 p-tb-25" name="msg" placeholder="How Can We Help?"></textarea>
							</div>

							<button class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer">
								Submit
							</button>
						</form>
					</div>

					<div class="size-210 bor10 flex-w flex-col-m p-lr-93 p-tb-30 p-lr-15-lg w-full-md">
						<div class="flex-w w-full p-b-42">
							<span class="fs-18 cl5 txt-center size-211">
								<span class="lnr lnr-map-marker"></span>
							</span>

							<div class="size-212 p-t-2">
								<span class="mtext-110 cl2">
									Our Address
								</span>

								<p class="stext-115 cl6 size-213 p-t-18">
									UTS Building 11 (CB11 Level 6), Broadway, Ultimo NSW 2007
								</p>
							</div>
						</div>

						<div class="flex-w w-full p-b-42">
							<span class="fs-18 cl5 txt-center size-211">
								<span class="lnr lnr-phone-handset"></span>
							</span>

							<div class="size-212 p-t-2">
								<span class="mtext-110 cl2">
									Call us 
								</span>

								<p class="stext-115 cl1 size-213 p-t-18">
									+61 800 888888
								</p>
							</div>
						</div>

						<div class="flex-w w-full">
							<span class="fs-18 cl5 txt-center size-211">
								<span class="lnr lnr-envelope"></span>
							</span>

							<div class="size-212 p-t-2">
								<span class="mtext-110 cl2">
									Technical/sale Support
								</span>

								<p class="stext-115 cl1 size-213 p-t-18">
									support.iotbay@gmail.com
								</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>	


		<!-- Map -->
		<div class="map">
			<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3624.5412960740077!2d151.1973250388616!3d-33.88399159122976!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x6b12ae262cecaf93%3A0xb3bd5402d8d21e95!2sBuilding%2011%2C%2081%20Broadway%2C%20Ultimo%20NSW%202007!5e0!3m2!1sen!2sau!4v1679987831593!5m2!1sen!2sau" width="100%" height="450"></iframe>
		</div>



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

		<jsp:include page="components/common-footer-html.jsp"/>
	</body>
</html>