<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Submit Review</title>
  <link rel="stylesheet" href="styles.css">
</head>
<body>
<div class="review-container">
  <h1>Submit a Review</h1>
  <form id="review-form" action="SubmitReviewServlet" method="post">
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required>
    <label for="book">Book:</label>
    <select id="book" name="book">
      <option value="To Kill a Mockingbird">To Kill a Mockingbird</option>
      <option value="The Catcher in the Rye">The Catcher in the Rye</option>
      <option value="1984">1984</option>
      <option value="Pride and Prejudice">Pride and Prejudice</option>
      <option value="The Great Gatsby">The Great Gatsby</option>
      <option value="Moby Dick">Moby Dick</option>
      <option value="Little Women">Little Women</option>
      <option value="The Hobbit">The Hobbit</option>
      <option value="The Alchemist">The Alchemist</option>
      <option value="The Lord of the Rings">The Lord of the Rings</option>
      <option value="The Book Thief">The Book Thief</option>
      <option value="Romeo and Juliet">Romeo and Juliet</option>
      <option value="The Kite Runner">The Kite Runner</option>
      <option value="Jane Eyre">Jane Eyre</option>
      <option value="Animal Farm">Animal Farm</option>
    </select>
    <label for="review">Your Review:</label>
    <textarea id="review" name="review" rows="4" placeholder="Write your review..."></textarea>
    <label for="rating">Rating:</label>
    <input type="number" id="rating" name="rating" min="1" max="5" placeholder="Rate out of 5">
    <button type="submit">Submit Review</button>
  </form>
  <a href="dashboard" class="home-link">Home</a>
  <%
    String errorMessage = (String) request.getAttribute("email_error_msg");
    if (errorMessage != null) {
  %>
  <p style="color: red;"><%= errorMessage %></p>
  <%
    }
  %>
</div>
</body>
</html>
