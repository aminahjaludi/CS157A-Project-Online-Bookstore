<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmation</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            text-align: center;
        }
        .container {
            border: 1px solid #ddd;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        a {
            text-decoration: none;
            color: #007bff;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Order Confirmation</h1>
    <p>Thank you for your order!</p>

    <p><strong>Book:</strong> ${bookName}</p>
    <p><strong>Quantity:</strong> ${quantity}</p>
    <p><strong>Total Cost:</strong> $${totalCost}</p>

    <a href="dashboard">Back to Home</a>
</div>
</body>
</html>