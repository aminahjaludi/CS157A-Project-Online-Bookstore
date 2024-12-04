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
                        try (PrintWriter out = response.getWriter()) {
                        out.println("<!DOCTYPE html>");
                        out.println("<html lang=\"en\">");
                        out.println("<head>");
                        out.println("    <meta charset=\"UTF-8\">");
                        out.println("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                        out.println("    <title>User Dashboard - Online Bookstore</title>");
                        out.println("    <link rel=\"stylesheet\" href=\"style_dashboard.css\">");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("    <div class=\"dashboard-container\">");
                        out.println("        <header class=\"center-header\">");
                        out.println("            <h1>Welcome to Your Dashboard</h1>");
                        out.println("            <p>Hi, "+ userEmail + "!</p>");
                        out.println("            <nav class=\"dashboard-navigation\">");
                        out.println("                <ul>");
                        out.println("                    <li><a href=\"search.jsp\">Search Books</a></li>");
                        out.println("                    <li><a href=\"orders.html\">Make an Order</a></li>");
                        out.println("                    <li><a href=\"reviews.html\">Submit a Review</a></li>");
                        out.println("                    <li><a href=\"setting.html\">User Settings</a></li>");
                        out.println("                </ul>");
                        out.println("            </nav>");
                        out.println("        </header>");
                        out.println("        <footer>");
                        out.println("            <a href=\"index.jsp\">Logout</a>");
                        out.println("        </footer>");
                        out.println("    </div>");
                        out.println("</body>");
                        out.println("</html>");
                    }
                } else {
                    response.sendRedirect("login.jsp");
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        }
    }
}

