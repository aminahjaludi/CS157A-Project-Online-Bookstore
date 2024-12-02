<%@ page import="java.util.List" %>
<%@ page import="com.example.cs157aproject.Book" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book Search Results</title>
    <link rel="stylesheet" href="style_search.css">
</head>
<body>
<div class="results-container">
    <h1>Search Results</h1>

    <%
        List<Book> books = (List<Book>) request.getAttribute("books");
        if (books != null && !books.isEmpty()) {
    %>
    <ul>
        <% for (Book book : books) { %>
        <li>
            <p><strong>Book:</strong> <%= book.getTitle() %></p>
            <p><strong>Quantity:</strong> <%= book.getQuantity() %></p>
            <p><strong>Price:</strong> $<%= book.getPrice() %></p> <!-- Display price -->
        </li>
        <% } %>
    </ul>
    <% } else { %>
    <p>No books found matching your search.</p>
    <% } %>

    <a href="search.jsp" class="home-link">Back to Search</a>
</div>
</body>
</html>
