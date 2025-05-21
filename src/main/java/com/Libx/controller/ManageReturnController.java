package com.Libx.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Libx.dao.BookDao;
import com.Libx.dao.BorrowRecordDao;
import com.Libx.dao.UserDao;
import com.Libx.model.Book;
import com.Libx.model.BorrowRecord;
import com.Libx.model.User;

@WebServlet("/ManageReturnController")
public class ManageReturnController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String userQuery = request.getParameter("userQuery");

	    if (userQuery != null && !userQuery.trim().isEmpty()) {
	        try {
	            UserDao userDao = new UserDao();
	            BorrowRecordDao borrowRecordDao = new BorrowRecordDao();
	            BookDao bookDao = new BookDao();

	            List<User> users = userDao.searchUsers(userQuery.trim());  
	            List<Map<String, Object>> displayList = new ArrayList<>();

	            for (User user : users) {
	                List<BorrowRecord> records = borrowRecordDao.getBorrowRecordsByUserId(user.getUser_id());

	                for (BorrowRecord record : records) {
	                    Book book = bookDao.getBookById(record.getBook_id());
	                    
	                    int overdueDays = borrowRecordDao.calculateOverdueDays(record.getBorrow_id());

	                    Map<String, Object> entry = new HashMap<>();
	                    entry.put("borrowId", record.getBorrow_id());
	                    entry.put("userId", user.getUser_id());
	                    entry.put("username", user.getUsername());
	                    entry.put("email", user.getEmail());
	                    entry.put("bookTitle", book.getTitle());
	                    entry.put("author", book.getAuthor());
	                    entry.put("overdueDays", overdueDays);
	                    
	                    displayList.add(entry);
	                }
	            }

	            request.setAttribute("records", displayList);
	        } catch (Exception e) {
	            e.printStackTrace();  
	            request.setAttribute("errorMessage", "Error occurred while fetching records.");
	        }
	    }

	    request.getRequestDispatcher("pages/admin/ManageReturn.jsp").forward(request, response);
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String[] selectedBorrowIds = request.getParameterValues("borrowIds");
	    String returnDateStr = request.getParameter("returnDate");

	    if (selectedBorrowIds != null && returnDateStr != null) {
	        try {
	            BorrowRecordDao borrowRecordDao = new BorrowRecordDao();

	            for (String borrowIdStr : selectedBorrowIds) {
	                int borrowId = Integer.parseInt(borrowIdStr);
	                borrowRecordDao.updateReturnDate(borrowId, returnDateStr);
	            }

	            response.sendRedirect("ManageReturnController?success=true");
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.sendRedirect("ManageReturnController?error=true");
	        }
	    } else {
	        response.sendRedirect("ManageReturnController?error=true");
	    }
	}

}
