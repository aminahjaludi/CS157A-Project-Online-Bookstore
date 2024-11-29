package com.example.cs157aproject;

import java.sql.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SignUpServlet", value = "/signup")
public class SignupServlet extends HttpServlet {

    public Boolean isValidPassword(String password, HttpServletRequest request, HttpServletResponse response) {
        if (password == null) return false;

        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        boolean isEightChars = password.length() >= 8;

        // Define the set of special characters
        String specialCharacters = "!@#$%^&*()-+=<>?/{}[]~|\\\"'\\\\;-/*`";
        StringBuilder error_msg = new StringBuilder();

        //Check each character in the password
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (specialCharacters.contains(String.valueOf(c))) {
                hasSpecialChar = true;
            }
        }

        // Check each condition separately
        if (!isEightChars) {
            error_msg.append("Password must contain at least 8 characters. ");
        }
        if (!hasUpperCase) {
            error_msg.append("Password must contain at least one uppercase letter. ");
        }
        if (!hasLowerCase) {
            error_msg.append("Password must contain at least one lowercase letter. ");
        }
        if (!hasDigit) {
            error_msg.append("Password must contain at least one number. ");
        }
        if (!hasSpecialChar) {
            error_msg.append("Password must contain at least one special character. ");
        }

        // All conditions were satisfied
        if (!hasUpperCase || !hasLowerCase || !hasDigit || !hasSpecialChar || !isEightChars) {
            request.setAttribute("signup_error_msg", error_msg.toString());
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/signup.jsp");
            try {
                rd.forward(request, response);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return false;
        }
        return true;
    }

    public static String sanitizePassword(String password) {
        if (password == null) {
            return null;
        }

        // Escape any SQL special characters to prevent injection attacks
        String sanitized = password.replaceAll("([\"'\\\\;\\-/*`])", "\\\\$1");

        // Trim any leading or trailing spaces
        sanitized = sanitized.trim();

        return sanitized;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Get user input from request
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Initialize database connection and resources
        try {
            // Establish connection with the database
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/test_schema",
                    "root",
                    "milk2000A");

            // Check if email and password already exist in users table
            String query = "SELECT * FROM users WHERE email = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            //If user with that email already exists
            if(resultSet.next())
            {
                statement.close();
                connection.close();
                request.setAttribute("signup_error_msg",
                        "Email already exists, please choose a different email or login.");

                RequestDispatcher rd = getServletContext().getRequestDispatcher("/signup.jsp");
                rd.forward(request, response);
                return;
            }

            request.setAttribute("signup_error_msg", null);
            if (!isValidPassword(password, request, response)) {
                return;
            }

            // Sanitize password before inserting into database
            password = sanitizePassword(password);

            // SQL query to insert new user into the users table, using a hashing algorithm
            // to store password securely
            String query2 = "INSERT INTO users (email, password) VALUES (?, SHA(?))";

            // Create PreparedStatement
            PreparedStatement statement2 = connection.prepareStatement(query2);

            // Bind the parameters to the SQL query
            statement2.setString(1, email);
            statement2.setString(2, password);

            // Execute the query
            int rowsInserted = statement2.executeUpdate();

            // Check if the insertion was successful
            if (rowsInserted > 0) {
                response.sendRedirect("auth.jsp");
            } else {
                request.setAttribute("signup_error_msg", "Error during signup. Please try again.");
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/signup.jsp");
                rd.forward(request, response);
            }

            // Close the connection and resources
            statement.close();
            statement2.close();
            connection.close();

        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
            response.getWriter().write("An error occurred during sign up. Please try again later.");
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}

