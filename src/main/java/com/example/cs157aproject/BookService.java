package com.example.cs157aproject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookService {

    // Database credentials
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/test_schema";
    private static final String USER = "root";
    private static final String PASSWORD = "milk2000A";

    public List<Book> searchBooks(String searchQuery) {
        List<Book> books = new ArrayList<>();

        // SQL query to search books using the correct column name 'book_name'
        String sql = "SELECT book_name, quantity FROM books WHERE book_name LIKE ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the search query in the prepared statement (wildcards for partial match)
            stmt.setString(1, "%" + searchQuery + "%");

            // Execute the query
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String bookName = rs.getString("book_name");  // Updated to 'book_name'
                    int quantity = rs.getInt("quantity");

                    // Create Book object and add to the list
                    books.add(new Book(bookName, quantity));  // Updated to 'bookName'
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in a real application
        }

        return books;
    }
}


