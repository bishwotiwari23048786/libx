package com.Libx.controller;

import com.Libx.dao.BorrowRecordDao;
import com.Libx.dao.BookDao;
import com.Libx.dao.UserDao;
import com.Libx.model.Book;
import com.Libx.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/ManageBorrowController")
public class ManageBorrowController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(ManageBorrowController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	String userQuery = request.getParameter("userQuery");
    	String bookQuery = request.getParameter("bookQuery");

        List<User> users = null;
        List<Book> books = null;
        
        try {
            BookDao bookDao = new BookDao();
            UserDao userDao = new UserDao();
            
            if (userQuery != null && !userQuery.trim().isEmpty()) {
                users = userDao.searchUsers(userQuery);
            }

            if (bookQuery != null && !bookQuery.trim().isEmpty()) {
                books = bookDao.searchBooks(bookQuery);
            }

        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Failed to search users or books", e);
            request.setAttribute("error", "Failed to search users or books.");
        }

        request.setAttribute("users", users);
        request.setAttribute("books", books);

        request.getRequestDispatcher("pages/admin/ManageBorrow.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            String borrowDate = request.getParameter("borrowDate"); 
            String dueDate = request.getParameter("dueDate");

            // Create a BorrowRecordDao object to check if the user can borrow the book
            BorrowRecordDao borrowRecordDao = new BorrowRecordDao();

            // Check if the user can borrow this book
            if (borrowRecordDao.canBorrow(userId, bookId)) {
                // If user can borrow, proceed with borrowing the book
                borrowRecordDao.borrowBook(userId, bookId, borrowDate, dueDate);
                response.sendRedirect("pages/admin/ManageBorrow.jsp?success=true");
            } else {
                // If user has already borrowed the book, show an error
                response.sendRedirect("pages/admin/ManageBorrow.jsp?error=already_borrowed");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while borrowing book", e);
            response.sendRedirect("pages/admin/ManageBorrow.jsp?error=true");
        }
    }
}
