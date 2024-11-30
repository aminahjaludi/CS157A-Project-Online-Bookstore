
package com.example.cs157aproject;

import java.sql.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
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
        String query = "SELECT * FROM users WHERE email = ? AND password = SHA(?)";

        // Initialize database connection and resources
        try {
            // Establish connection with the database
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/test_schema",
                    "root",
                    "Bear12254648Iloveyou@");

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
                // Store the user's email in the session
                request.getSession().setAttribute("userEmail", email);

                response.sendRedirect("dashboard.html");  // Redirect to Dashboard.html
            } else {
                // Failed login logic
                request.setAttribute("login_error_msg", "Email and password combination are incorrect.");

                RequestDispatcher rd = getServletContext().getRequestDispatcher("/auth.jsp");
                rd.forward(request, response);
            }

            // Close the connection and resources
            connection.close();
            statement.close();

        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}










