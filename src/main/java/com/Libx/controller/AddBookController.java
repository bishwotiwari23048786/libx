package com.Libx.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.Libx.dao.BookDao;
import com.Libx.model.Book;
import com.Libx.utility.CoverImageUtil;

@WebServlet("/AddBookController")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 15
)
public class AddBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(AddBookController.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession();

        try {
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String genre = request.getParameter("genre");
            String isbn = request.getParameter("isbn");
            String publisher = request.getParameter("publisher");
            int year = Integer.parseInt(request.getParameter("publishYear"));
            int total = Integer.parseInt(request.getParameter("totalCopies"));

            Part filePart = request.getPart("coverImage");
            String coverImgPath = CoverImageUtil.saveCoverImage(filePart, getServletContext());

            Book book = new Book(title, author, genre, isbn, publisher, coverImgPath, year, total);

            BookDao bookDao = new BookDao();
            boolean isAdded = bookDao.addBook(book);

            if (isAdded) {
                session.setAttribute("success", "Book added successfully!");
            } else {
                session.setAttribute("error", "Failed to add book to database.");
            }
            response.sendRedirect("pages/admin/AddBook.jsp");

        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid number format", e);
            session.setAttribute("error", "Invalid number format for year or total copies.");
            response.sendRedirect("pages/admin/AddBook.jsp");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception in AddBookController", e);
            session.setAttribute("error", "Unexpected error occurred.");
            response.sendRedirect("pages/admin/AddBook.jsp");
        }
    }
    
    
}
