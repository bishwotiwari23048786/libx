package com.Libx.controller;

import com.Libx.dao.BookDao;
import com.Libx.model.Book;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/FetchAllBooksController")
public class FetchAllBooksController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            BookDao bookDao = new BookDao();
            List<Book> books = bookDao.getAllBooks();
            for (Book book : books) {
                int available_copies = bookDao.getAvailableCopies(book.getBook_id());
                book.setAvailable_copies(available_copies);
            }

            request.setAttribute("books", books);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("error", "Unable to load books.");
        }

        // Always forward to JSP regardless of success or failure
        request.getRequestDispatcher("/pages/member/Books.jsp").forward(request, response);
    }
}
