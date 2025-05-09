<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delete Account - Online Bookstore</title>
    <link rel="stylesheet" href="style_settings.css">
</head>
<body>
<div class="auth-container">
    <header>
        <h1>Delete Account</h1>
        <p>We're sorry to see you go!</p>
    </header>
    <form id="delete-account-form" action="DeleteAccountServlet" method="post">
        <label for="email">Email:</label>
        <input type="text" id="email" name="email" placeholder="Enter your email" required>
        <button type="submit" class="delete-button">Delete My Account</button>
    </form>
    <footer>
        <a href="dashboard">Back to Dashboard</a>
    </footer>
</div>
</body>
</html>

