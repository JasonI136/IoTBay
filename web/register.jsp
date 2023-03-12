<%-- 
    Document   : register
    Created on : 12 Mar 2023, 2:14:59 pm
    Author     : cmesina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Registration Form</title>
</head>
<body>
	<h1>Registration Form</h1>
	<form method="post" action="register-user">
		<label for="username">Username:</label>
		<input type="text" id="username" name="username" required><br>
		
		<label for="password">Password:</label>
		<input type="password" id="password" name="password" required><br>
		
		<label for="firstName">First Name:</label>
		<input type="text" id="firstName" name="firstName" required><br>
		
		<label for="lastName">Last Name:</label>
		<input type="text" id="lastName" name="lastName" required><br>
		
		<label for="email">Email Address:</label>
		<input type="email" id="email" name="email" required><br>
		
		<label for="address">Address:</label>
		<input type="text" id="address" name="address" required><br>
		
		<label for="phone">Phone Number:</label>
		<input type="tel" id="phone" name="phone" required><br>
		
		<input type="submit" value="Register">
	</form>
</body>
</html>
