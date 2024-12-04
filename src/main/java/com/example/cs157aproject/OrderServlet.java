package com.example.cs157aproject;

import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
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

        String bookCode = StringUtils.sanitizeInput(request.getParameter("book"));
        String email = StringUtils.sanitizeInput(request.getParameter("email"));

        if (bookCode == null || bookCode.isEmpty() || email == null || email.isEmpty()) {
            response.getWriter().println("<h1>Invalid book code or email input!</h1>");
            return;
        }

        String quantityParam = StringUtils.sanitizeInput(request.getParameter("quantity")); // Get quantity input
        int quantity = 1; // Default to 1 if parsing fails

        try {
            quantity = Integer.parseInt(quantityParam); // Parse quantity
            if (quantity <= 0) { // Validate that quantity is positive
                response.getWriter().println("<h1>Invalid quantity specified!</h1>");
                return;
            }
        }
        catch (NumberFormatException e) {
            response.getWriter().println("<h1>Invalid quantity format!</h1>");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            // Check if email exist in users table
            String query = "SELECT * FROM users WHERE email = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            //If no user with that email already exists
            if(!resultSet.next())
            {
                statement.close();
                conn.close();
                request.setAttribute("email_error_msg",
                        "Email is not registered with bookstore. Please use a registered email.");

                RequestDispatcher rd = getServletContext().getRequestDispatcher("/orders.jsp");
                rd.forward(request, response);
                return;
            }

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
                        String updateQuery = "UPDATE books SET quantity = quantity - ? WHERE book_code = ? AND quantity - ? > 0";
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                            updateStmt.setInt(1, quantity);
                            updateStmt.setString(2, bookCode);
                            updateStmt.setInt(3, quantity);
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

                        // Pass the order details to the JSP
                        request.setAttribute("bookName", bookName);
                        request.setAttribute("quantity", quantity);
                        request.setAttribute("totalCost", totalCost);

                        // Forward to the JSP
                        request.getRequestDispatcher("orderConfirmation.jsp").forward(request, response);

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






