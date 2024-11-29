<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Authentication</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<div class="auth-container">
    <h1>Online Bookstore</h1>
    <form id="auth-form" method="POST" action="login">
        <h2>Login</h2>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
        <button type="submit">Login</button>
        <p>Don't have an account? <a href="signup.jsp">Sign Up</a></p>
    </form>
    <a href="index.jsp" class="home-link">Home</a>
    <%
        String errorMessage = (String) request.getAttribute("login_error_msg");
        if (errorMessage != null) {
    %>
    <p style="color: red;"><%= errorMessage %></p>
    <%
        }
    %>
</div>
</body>
</html>
