<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User Dashboard - Online Bookstore</title>
  <link rel="stylesheet" href="style_dashboard.css">
</head>
<body>
<div class="dashboard-container">
  <header class="center-header">
    <h1>Welcome to Your Dashboard</h1>

    <p>Hi, ${userEmail}!</p>
    <nav class="dashboard-navigation">
      <ul>
        <li><a href="search.jsp">Search Books</a></li>
        <li><a href="orders.jsp">Make an Order</a></li>
        <li><a href="reviews.jsp">Submit a Review</a></li>
        <li><a href="setting.jsp">User Settings</a></li>
      </ul>
    </nav>
  </header>
  <footer>
    <a href="index.jsp">Logout</a>
  </footer>
</div>
</body>
</html>