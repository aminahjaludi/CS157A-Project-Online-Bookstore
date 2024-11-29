<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up</title>
    <link rel="stylesheet" href="style_signup.css">
</head>
<body>
<div class="auth-container">
    <h1>Online Bookstore</h1>
    <form id="signup-form" method="POST" action="signup">
        <h2>Sign Up</h2>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
        <p>Password must contain at least 8 characters, at least 1 uppercase, at least 1 lowercase letter, at least 1 number, and at least 1 special character.</p>
        <button type="submit">Sign Up</button>
        <p>Already have an account? <a href="auth.jsp">Login</a></p>
    </form>
    <a href="index.jsp" class="home-link">Home</a>

    <%
        String errorMessage = (String) request.getAttribute("signup_error_msg");
        if (errorMessage != null) {
    %>
    <p style="color: red;"><%= errorMessage %></p>
    <%
        }
    %>
</div>
</body>
</html>
