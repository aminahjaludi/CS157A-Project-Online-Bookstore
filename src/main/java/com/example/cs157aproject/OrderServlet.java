package com.example.cs157aproject;

import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        String bookCode = request.getParameter("book");
        String email = request.getParameter("email");

        if (bookCode == null || bookCode.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            response.getWriter().println("<h1>Invalid book code or email input!</h1>");
            return;
        }

        int quantity = 1; // Default quantity for this implementation
        try (Connection conn = DBConnection.getConnection()) {
            // Check book availability
            String checkQuery = "SELECT book_id, book_name, price, quantity FROM books WHERE book_code = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setString(1, bookCode);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        int book_id = rs.getInt("book_id");
                        String bookName = rs.getString("book_name");
                        double price = rs.getDouble("price");
                        int availableQuantity = rs.getInt("quantity");

                        if (availableQuantity <= 0) {
                            response.getWriter().println("<h1>Sorry, the book is out of stock!</h1>");
                            return;
                        }

                        // Deduct one from the available quantity
                        String updateQuery = "UPDATE books SET quantity = quantity - 1 WHERE book_code = ? AND quantity > 0";
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                            updateStmt.setString(1, bookCode);
                            int rowsUpdated = updateStmt.executeUpdate();
                            if (rowsUpdated == 0) {
                                response.getWriter().println("<h1>Unable to update stock. Please try again.</h1>");
                                return;
                            }
                        }

                        // Save the order
                        String insertOrderQuery = "INSERT INTO orders (email, book_id, quantity, total_cost) VALUES (?, ?, ?, ?)";
                        double totalCost = price * quantity;
                        try (PreparedStatement orderStmt = conn.prepareStatement(insertOrderQuery)) {
                            orderStmt.setString(1, email);
                            orderStmt.setInt(2, book_id);
                            orderStmt.setInt(3, quantity);
                            orderStmt.setDouble(4, totalCost);
                            orderStmt.executeUpdate();
                        }

                        // Respond to user
                        PrintWriter out = response.getWriter();
                        out.println("<!DOCTYPE html>");
                        out.println("<html lang='en'>");
                        out.println("<head>");
                        out.println("<meta charset='UTF-8'>");
                        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                        out.println("<title>Order Confirmation</title>");
                        out.println("<style>");
                        out.println("body {");
                        out.println("  font-family: Arial, sans-serif;");
                        out.println("  display: flex;");
                        out.println("  justify-content: center;");
                        out.println("  align-items: center;");
                        out.println("  height: 100vh;");
                        out.println("  margin: 0;");
                        out.println("  text-align: center;");
                        out.println("}");
                        out.println(".container {");
                        out.println("  border: 1px solid #ddd;");
                        out.println("  padding: 20px;");
                        out.println("  border-radius: 10px;");
                        out.println("  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);");
                        out.println("}");
                        out.println("a {");
                        out.println("  text-decoration: none;");
                        out.println("  color: #007bff;");
                        out.println("}");
                        out.println("a:hover {");
                        out.println("  text-decoration: underline;");
                        out.println("}");
                        out.println("</style>");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<div class='container'>");
                        out.println("<h1>Order Confirmation</h1>");
                        out.println("<p>Thank you for your order!</p>");
                        out.println("<p><strong>Book:</strong> " + bookName + "</p>");
                        out.println("<p><strong>Quantity:</strong> " + quantity + "</p>");
                        out.println("<p><strong>Total Cost:</strong> $" + String.format("%.2f", totalCost) + "</p>");
                        out.println("<a href='dashboard.html'>Back to Home</a>");
                        out.println("</div>");
                        out.println("</body>");
                        out.println("</html>");

                    } else {
                        response.getWriter().println("<h1>Book not found!</h1>");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("<h1>An error occurred while processing your order.</h1>");
        }
    }
}






