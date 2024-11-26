package com.example.cs157aproject;

import java.sql.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SignUpServlet", value = "/signup")
public class SignupServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Get user input from request
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // SQL query to insert new user into the users table
        String query = "INSERT INTO users (email, password) VALUES (?, ?)";

        // Initialize database connection and resources
        try {
            // Establish connection with the database
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/test_schema",
                    "root",
                    "milk2000A");

            // Create PreparedStatement
            PreparedStatement statement = connection.prepareStatement(query);

            // Bind the parameters to the SQL query
            statement.setString(1, email);
            statement.setString(2, password);

            // Execute the query
            int rowsInserted = statement.executeUpdate();

            // Check if the insertion was successful
            if (rowsInserted > 0) {
                response.sendRedirect("auth.jsp");
            } else {
                response.getWriter().write("Sign up failed. Please try again.");
            }

            // Close the connection and resources
            statement.close();
            connection.close();

        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
            response.getWriter().write("An error occurred during sign up. Please try again later.");
        }
    }
}
