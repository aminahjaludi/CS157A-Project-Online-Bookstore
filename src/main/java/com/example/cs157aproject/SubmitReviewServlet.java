package com.example.cs157aproject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SubmitReviewServlet")
public class SubmitReviewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test_schema";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "milk2000A";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String email = request.getParameter("email");
        String book = request.getParameter("book");
        String review = request.getParameter("review");
        String ratingStr = request.getParameter("rating");

        try {
            int rating = Integer.parseInt(ratingStr);

            // Validate input
            if (book == null || book.isEmpty() || review == null || review.isEmpty() || rating < 1 || rating > 5) {
                generateHtml(response, "Invalid input. Please try again.", "red");
                return;
            }

            // Establish database connection
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Insert review into the database
            String sql = "INSERT INTO reviews (email, book, review, rating) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, book);
            pstmt.setString(3, review);
            pstmt.setInt(4, rating);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                generateHtml(response, "Review submitted successfully!", "green");
            } else {
                generateHtml(response, "Error submitting review. Please try again later.", "red");
            }

            pstmt.close();
            conn.close();
        } catch (NumberFormatException e) {
            generateHtml(response, "Rating must be a number between 1 and 5.", "red");
        } catch (Exception e) {
            e.printStackTrace();
            generateHtml(response, "An error occurred while submitting your review.", "red");
        }
    }

    private void generateHtml(HttpServletResponse response, String message, String color)
            throws IOException {
        response.getWriter().println(
                "<!DOCTYPE html>" +
                        "<html lang='en'>" +
                        "<head>" +
                        "<meta charset='UTF-8'>" +
                        "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                        "<title>Review Submission</title>" +
                        "<link rel='stylesheet' href='style_review.css'>" + // Optional CSS for styling
                        "</head>" +
                        "<body>" +
                        "<div class='response-container'>" +
                        "<h1 style='color: " + color + ";'>" + message + "</h1>" +
                        "<a href='dashboard' class='back-link'>Go back to Dashboard</a>" +
                        "</div>" +
                        "</body>" +
                        "</html>"
        );
    }
}


