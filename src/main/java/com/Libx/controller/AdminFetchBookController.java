package com.Libx.controller;

import com.Libx.dao.BookDao;
import com.Libx.model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/AdminFetchBookController")
public class AdminFetchBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Fetch filter parameters from request
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        String availabilityStr = request.getParameter("availability");

        Boolean available = null;

        // Convert availability
        if (availabilityStr != null && !availabilityStr.isEmpty()) {
            if (availabilityStr.equalsIgnoreCase("Available")) {
                available = true;
            } else if (availabilityStr.equalsIgnoreCase("Checked Out")) {
                available = false;
            }
        }

        try {
            BookDao bookDao = new BookDao();
            List<Book> books = bookDao.getFilteredBooks(title, author, genre, available);
            
            for (Book book : books) {
                int available_copies = bookDao.getAvailableCopies(book.getBook_id());
                book.setAvailable_copies(available_copies);  
            }

            // Set book list as request attribute
            request.setAttribute("books", books);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error fetching books.");
        }

        // Forward to JSP
        request.getRequestDispatcher("/pages/admin/Books.jsp").forward(request, response);
    }
}
