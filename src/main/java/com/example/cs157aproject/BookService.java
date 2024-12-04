package com.example.cs157aproject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookService {

    public List<Book> searchBooks(String searchQuery) {
        List<Book> books = new ArrayList<>();

        // SQL query to search books
        String sql = "SELECT book_name, quantity, price FROM books WHERE book_name LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the search query in the prepared statement
            stmt.setString(1, "%" + searchQuery + "%");

            // Execute the query
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String bookName = rs.getString("book_name");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price"); // Retrieve price

                    // Create Book object and add to the list
                    books.add(new Book(bookName, quantity, price));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in a real application
        }

        return books;
    }
}


