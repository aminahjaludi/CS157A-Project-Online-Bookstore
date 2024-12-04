package com.example.cs157aproject;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bookSearch")
public class BookSearchServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve search query from the request (if any)
        String searchQuery = StringUtils.sanitizeInput(request.getParameter("search"));

        // Use BookService to get the list of books (replace with actual search logic)
        BookService bookService = new BookService();
        List<Book> books = bookService.searchBooks(searchQuery);

        // Set the list of books as a request attribute
        request.setAttribute("books", books);

        // Forward to the JSP page for displaying the results
        request.getRequestDispatcher("/results.jsp").forward(request, response);
    }
}

