package com.Libx.controller;

import com.Libx.dao.BookDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/AddToStockController")
public class AddToStockController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(AddToStockController.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookIdParam = request.getParameter("book_id");
        String quantityParam = request.getParameter("quantity");

        try {
            if (bookIdParam == null || quantityParam == null ||
                bookIdParam.isEmpty() || quantityParam.isEmpty()) {
                LOGGER.warning("Missing book_id or quantity parameter.");
                request.setAttribute("error", "Book ID and quantity are required.");
                request.getRequestDispatcher("/AdminFetchAllBooksController").forward(request, response);
                return;
            }

            int bookId = Integer.parseInt(bookIdParam);
            int quantity = Integer.parseInt(quantityParam);

            if (quantity <= 0) {
                LOGGER.warning("Invalid quantity provided: " + quantity);
                request.setAttribute("error", "Quantity must be a positive number.");
                request.getRequestDispatcher("/AdminFetchAllBooksController").forward(request, response);
                return;
            }

            BookDao bookDao = new BookDao();
            boolean success = bookDao.addToStock(bookId, quantity);

            if (success) {
                LOGGER.info("Successfully added " + quantity + " copies to book ID " + bookId);
                request.setAttribute("message", "Stock successfully updated.");
            } else {
                LOGGER.warning("Failed to add stock. Book ID may not exist: " + bookId);
                request.setAttribute("error", "Failed to update stock.");
            }

        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid number format for book ID or quantity", e);
            request.setAttribute("error", "Invalid number format.");
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Database error occurred while updating stock", e);
            request.setAttribute("error", "Database error: " + e.getMessage());
        }

        request.getRequestDispatcher("/AdminFetchAllBooksController").forward(request, response);
    }
}
