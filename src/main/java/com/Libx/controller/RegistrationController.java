package com.Libx.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Libx.dao.UserDao;
import com.Libx.model.User;

@WebServlet("/RegistrationController")
public class RegistrationController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(RegistrationController.class.getName());

    public RegistrationController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String ageParam = request.getParameter("age");
        String gender = request.getParameter("gender");

        try {
            int age = Integer.parseInt(ageParam);  // Basic type conversion

            UserDao userdao = new UserDao();

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setAge(age);            
            user.setGender(gender);      

            boolean isRegistered = userdao.register(user);

            if (isRegistered) {
                request.setAttribute("successMessage", "Registration successful! Please log in.");
                request.getRequestDispatcher("/pages/user/Login.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Registration failed. Please try again.");
                request.getRequestDispatcher("/pages/member/Registration.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid age format.");
            request.getRequestDispatcher("/pages/member/Registration.jsp").forward(request, response);
        } catch (ClassNotFoundException | SQLException e) {
            logger.log(Level.SEVERE, "Error occurred during user registration", e);
            request.setAttribute("errorMessage", "A system error occurred. Please try again later.");
            request.getRequestDispatcher("/pages/member/Registration.jsp").forward(request, response);
        }
    }


}
