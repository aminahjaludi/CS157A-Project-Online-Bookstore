
package com.example.cs157aproject;

import java.sql.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Get user input from request
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // SQL query to check user credentials
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";

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
            ResultSet resultSet = statement.executeQuery();

            // Check if user credentials are valid
            if (resultSet.next()) {
                // Successful login logic
                response.sendRedirect("dashboard.html");  // Redirect to Dashboard.html
            } else {
                // Failed login logic
                response.getWriter().write("Invalid username or password.");
            }

            // Close the connection and resources
            connection.close();
            statement.close();

        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
        }
    }
}








