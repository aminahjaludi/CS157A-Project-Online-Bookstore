package com.example.cs157aproject;

import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "OrderServlet", value = "/order")
public class OrderServlet extends HttpServlet {

    // Handle POST request to process the order
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        // Get the logged-in user's email from the session
        String email = (String) request.getSession().getAttribute("userEmail");

        if (email == null) {
            response.sendRedirect("auth.jsp");  // Redirect to login if no user is logged in
            return;
        }

        String bookCode = request.getParameter("book");
        int quantity = 1; // Fixed quantity for this example

        String bookName = null;
        double price = 0.0;
        double totalCost = 0.0;

        try (Connection conn = DBConnection.getConnection()) {
            // Check availability in the books table
            String selectQuery = "SELECT book_name, price, quantity FROM books WHERE book_code = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
            selectStmt.setString(1, bookCode);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                bookName = rs.getString("book_name");
                price = rs.getDouble("price");
                int availableQuantity = rs.getInt("quantity");

                if (availableQuantity <= 0) {
                    response.getWriter().println("<h1>Sorry, this book is out of stock!</h1>");
                    return;
                }

                // Update the books table to reduce quantity by 1
                String updateQuery = "UPDATE books SET quantity = quantity - 1 WHERE book_code = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setString(1, bookCode);
                updateStmt.executeUpdate();

                // Calculate the total cost
                totalCost = price * quantity;

                // Insert the order into the orders table, associating it with the logged-in user
                String insertQuery = "INSERT INTO orders (email, book_name, quantity, total_cost) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setString(1, email);  // Associate order with the logged-in user
                insertStmt.setString(2, bookName);
                insertStmt.setInt(3, quantity);
                insertStmt.setDouble(4, totalCost);
                insertStmt.executeUpdate();
            } else {
                response.getWriter().println("<h1>Book not found!</h1>");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("<h1>Error processing the order.</h1>");
            return;
        }

        // Generate confirmation response
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<title>Order Confirmation</title>");
        out.println("<style>");
        out.println("  body {");
        out.println("    display: flex;");
        out.println("    justify-content: center;");
        out.println("    align-items: center;");
        out.println("    min-height: 100vh;");
        out.println("    margin: 0;");
        out.println("    font-family: Arial, sans-serif;");
        out.println("    background-color: #f4f4f4;");
        out.println("  }");
        out.println("  .container {");
        out.println("    text-align: center;");
        out.println("    background: white;");
        out.println("    padding: 20px;");
        out.println("    box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);");
        out.println("    border-radius: 8px;");
        out.println("  }");
        out.println("  a {");
        out.println("    display: inline-block;");
        out.println("    margin-top: 10px;");
        out.println("    padding: 10px 20px;");
        out.println("    background-color: #007bff;");
        out.println("    color: white;");
        out.println("    text-decoration: none;");
        out.println("    border-radius: 5px;");
        out.println("  }");
        out.println("  a:hover {");
        out.println("    background-color: #0056b3;");
        out.println("  }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h1>Order Confirmation</h1>");
        out.println("<p>Thank you for your order!</p>");
        out.println("<p><strong>Book:</strong> " + bookName + "</p>");
        out.println("<p><strong>Quantity:</strong> " + quantity + "</p>");
        out.println("<p><strong>Total Cost:</strong> $" + String.format("%.2f", totalCost) + "</p>");
        out.println("<a href='dashboard.html'>Back to Dashboard</a>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}





