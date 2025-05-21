package com.Libx.controller;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Libx.dao.UserDao;
import com.Libx.model.User;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    public LoginController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/pages/user/Login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            UserDao userDao = new UserDao();
            User user = userDao.login(email, password); 

            if (user != null) {
                // Invalidate old session to prevent session fixation
                HttpSession oldSession = request.getSession(false);
                if (oldSession != null) {
                    oldSession.invalidate();
                }

                // Create a new session and store user
                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);

                String role = user.getRole();
                if ("admin".equalsIgnoreCase(role)) {
                    response.sendRedirect("DashboardController");
                } else {
                    response.sendRedirect(request.getContextPath() + "/pages/member/Home.jsp");
                }

            } else {
                // Login failed – show error and preserve entered email
                request.setAttribute("errorMessage", "Invalid email or password. Please try again.");
                request.setAttribute("enteredEmail", email); // For user convenience
                request.getRequestDispatcher("/pages/user/Login.jsp").forward(request, response);
            }

        } catch (ClassNotFoundException | SQLException e) {
            logger.log(Level.SEVERE, "Error occurred during user login", e);
            request.setAttribute("errorMessage", "A system error occurred. Please try again later.");
            request.getRequestDispatcher("/pages/user/Login.jsp").forward(request, response);
        }
    }
}
