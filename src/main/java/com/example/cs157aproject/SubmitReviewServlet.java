package com.example.cs157aproject;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/submitReview")
public class SubmitReviewServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String book = request.getParameter("book");
        String review = request.getParameter("review");
        String rating = request.getParameter("rating");

        // Check for missing fields
        if (book == null || review == null || rating == null || book.isEmpty() || review.isEmpty() || rating.isEmpty()) {
            request.setAttribute("errorMessage", "All fields are required!");
            request.setAttribute("book", book);
            request.setAttribute("review", review);  // Corrected typo here
            request.setAttribute("rating", rating);

            // Forward to the reviews page with error message
            RequestDispatcher dispatcher = request.getRequestDispatcher("reviews.html");
            dispatcher.forward(request, response);
            return;
        }

        // Forward to results page if all fields are valid
        request.setAttribute("book", book);
        request.setAttribute("review", review);
        request.setAttribute("rating", rating);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/results.jsp");
        dispatcher.forward(request, response);
    }
}
