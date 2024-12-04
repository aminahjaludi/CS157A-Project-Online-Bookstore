package com.example.cs157aproject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DeleteAccountServlet")
public class DeleteAccountServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        String email = StringUtils.sanitizeInput(request.getParameter("email"));

        if (email == null || email.isEmpty()) {
            response.getWriter().println(
                    "<html><body>" +
                            "<h1>Error: Email is required.</h1>" +
                            "<a href='setting.jsp'>Try Again</a>" +
                            "</body></html>"
            );
            return;
        }

        try {
            // Establish database connection
            Connection conn = DBConnection.getConnection();

            // Verify email existence in the database
            String selectQuery = "SELECT * FROM users WHERE email = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
            selectStmt.setString(1, email);

            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                // Delete account
                String deleteQuery = "DELETE FROM users WHERE email = ?";
                PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
                deleteStmt.setString(1, email);
                int rowsDeleted = deleteStmt.executeUpdate();

                if (rowsDeleted > 0) {
                    response.getWriter().println(
                            "<html><body style='text-align: center; padding-top: 20px;'>" +
                                    "<h1>Account deleted successfully.</h1>" +
                                    "<a href='index.jsp'>Return to Home</a>" +
                                    "</body></html>"
                    );
                } else {
                    response.getWriter().println(
                            "<html><body>" +
                                    "<h1>Error: Unable to delete account.</h1>" +
                                    "<a href='setting.jsp'>Try Again</a>" +
                                    "</body></html>"
                    );
                }

                deleteStmt.close();
            } else {
                response.getWriter().println(
                        "<html><body>" +
                                "<h1>Error: No account found with the provided email.</h1>" +
                                "<a href='setting.jsp'>Try Again</a>" +
                                "</body></html>"
                );
            }

            selectStmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println(
                    "<html><body>" +
                            "<h1>An error occurred. Please try again later.</h1>" +
                            "<a href='setting.jsp'>Try Again</a>" +
                            "</body></html>"
            );
        }
    }
}



