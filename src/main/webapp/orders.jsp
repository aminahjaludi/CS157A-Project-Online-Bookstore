<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Order Processing</title>
  <link rel="stylesheet" href="style_order.css">
  <style>
    /* Hide number input spinner */
    input[type="number"]::-webkit-inner-spin-button,
    input[type="number"]::-webkit-outer-spin-button {
      -webkit-appearance: none;
      margin: 0;
    }
  </style>
</head>
<body>
<div class="order-container">
  <h1>Place Your Order</h1>
  <form id="order-form" action="order" method="POST">
    <!-- Add email field -->
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required>

    <!-- Select Book -->
    <label for="book">Select Book:</label>
    <select id="book" name="book" required>
      <option value="book1">To Kill a Mockingbird</option>
      <option value="book2">The Catcher in the Rye</option>
      <option value="book3">1984</option>
      <option value="book4">Pride and Prejudice</option>
      <option value="book5">The Great Gatsby</option>
      <option value="book6">Moby Dick</option>
      <option value="book7">Little Women</option>
      <option value="book8">The Hobbit</option>
      <option value="book9">The Alchemist</option>
      <option value="book10">The Lord of the Rings</option>
      <option value="book11">The Book Thief</option>
      <option value="book12">Romeo and Juliet</option>
      <option value="book13">The Kite Runner</option>
      <option value="book14">Jane Eyre</option>
      <option value="book15">Animal Farm</option>
    </select>

    <!-- Quantity Input -->
    <label for="quantity">Quantity:</label>
    <input type="number" id="quantity" name="quantity" min="1" required>

    <!-- Submit Button -->
    <button type="submit">Order Now</button>
  </form>

  <!-- Home Link -->
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
