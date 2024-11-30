<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book Search</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<div class="search-container">
    <h1>Search Books</h1>

    <!-- The form uses GET and sends the search query to /bookSearch -->
    <form method="GET" action="bookSearch">
        <input type="text" name="search" placeholder="Search by title" required>
        <button type="submit">Search</button>
    </form>
    <a href="dashboard.html" class="home-link">Home</a>
</div>
</body>
</html>
