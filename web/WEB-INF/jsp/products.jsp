<%-- 
    Document   : products
    Created on : 14 Mar 2023, 9:28:36 pm
    Author     : cmesina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Shop</h1>
        <h2>Shopping Cart</h2>
        <ul>
           <c:forEach items="${sessionScope.shoppingCart}" var="product" >
            <li>${product.name}, ${product.description}, Price: AUD$${product.price}     
            </li>
        </c:forEach>
        </ul>
        <h1>Items for sale</h1>
        <ul>
        <c:forEach items="${products}" var="product" >
            <li>${product.name}, ${product.description}, Price: AUD$${product.price}
                <form method="POST" action="shop/addtocart">
                <button type="submit" name="productId" value="${product.productId}">
                    Add to cart.
                </button>
                </form>
            </li>
        </c:forEach>
        </ul>
    </body>
</html>
