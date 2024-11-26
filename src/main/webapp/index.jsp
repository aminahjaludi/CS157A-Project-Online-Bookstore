<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online Bookstore</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<div class="home-container">
    <h1>Welcome to the Online Bookstore</h1>
    <nav class="navigation">
        <ul>
            <li><a href="<%= request.getContextPath() %>/auth.jsp">Login</a></li>
        </ul>
    </nav>
</div>
<div class="image-container">
    <img src="<%= request.getContextPath() %>/book3.png" alt="Book Image">
</div>
</body>
</html>
