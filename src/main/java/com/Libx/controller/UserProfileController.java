package com.Libx.controller;

import com.Libx.dao.BorrowRecordDao;
import com.Libx.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/UserProfileController")
public class UserProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch user session data
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        // Get user ID
        int userId = user.getUser_id();

        try {
            BorrowRecordDao borrowDao = new BorrowRecordDao();

            // Fetch current borrows and overdue borrows
            List<Map<String, Object>> currentBorrows = borrowDao.getCurrentBorrowsByUser(userId);
            List<Map<String, Object>> overdueBorrows = borrowDao.getOverdueBorrowsByUser(userId);

            // Set attributes for the JSP page
            request.setAttribute("currentBorrows", currentBorrows);
            request.setAttribute("overdueBorrows", overdueBorrows);

            // Forward to userProfile.jsp
            request.getRequestDispatcher("/pages/user/UserProfile.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load user profile.");
        }
    }

}
