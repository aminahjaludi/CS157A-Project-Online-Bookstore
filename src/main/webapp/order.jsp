<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Processing</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="order-container">
        <h1>Place Your Order</h1>
        <form id="order-form">
            <label for="book">Select Book:</label>
            <select id="book" name="book">
                <option value="book1">Book 1</option>
                <option value="book2">Book 2</option>
                <!-- Add more books dynamically -->
            </select>
            <label for="quantity">Quantity:</label>
            <input type="number" id="quantity" name="quantity" min="1" value="1">
            <button type="submit">Order Now</button>
        </form>
        <a href="index.jsp" class="home-link">Home</a>
    </div>
</body>
</html>
