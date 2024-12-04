package com.example.cs157aproject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SubmitReviewServlet")
public class SubmitReviewServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String email = StringUtils.sanitizeInput(request.getParameter("email"));
        String book = StringUtils.sanitizeInput(request.getParameter("book"));
        String review = StringUtils.sanitizeInput(request.getParameter("review"));
        String ratingStr = StringUtils.sanitizeInput(request.getParameter("rating"));

        try {
            int rating = Integer.parseInt(ratingStr);

            // Validate input
            if (book == null || book.isEmpty() || review == null || review.isEmpty() || rating < 1 || rating > 5) {
                generateHtml(response, "Invalid input. Please try again.", "red");
                return;
            }

            // Establish database connection
            Connection conn = DBConnection.getConnection();

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

                RequestDispatcher rd = getServletContext().getRequestDispatcher("/reviews.jsp");
                rd.forward(request, response);
                return;
            }

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


