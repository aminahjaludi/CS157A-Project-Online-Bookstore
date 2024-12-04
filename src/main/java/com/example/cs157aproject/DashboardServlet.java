package com.example.cs157aproject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Fetch the user ID or email from the session
        String userEmail = (String) request.getSession().getAttribute("userEmail");

        if (userEmail == null) {
            response.sendRedirect("login.jsp"); // Redirect to login if no user is logged in
            return;
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT email FROM users WHERE email = ?")) {

            stmt.setString(1, userEmail);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Set the userEmail as an attribute for the JSP
                    request.setAttribute("userEmail", userEmail);

                    // Forward the request to the JSP
                    request.getRequestDispatcher("dashboard.jsp").forward(request, response);
                } else {
                    response.sendRedirect("login.jsp");
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        }
    }
}

