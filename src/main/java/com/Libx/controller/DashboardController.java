package com.Libx.controller;

import com.Libx.dao.BorrowRecordDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/DashboardController")
public class DashboardController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(DashboardController.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            BorrowRecordDao borrowRecordDao = new BorrowRecordDao();

            List<Map<String, Object>> mostBorrowedBook = borrowRecordDao.getBookBorrowStats(true);
            List<Map<String, Object>> leastBorrowedBook = borrowRecordDao.getBookBorrowStats(false);
            Map<String, Object> mostBorrowedGenre = borrowRecordDao.getMostBorrowedGenre();

            Map<String, Object> mostBookMale = borrowRecordDao.getMostBorrowedBookByGender("male");
            Map<String, Object> mostBookFemale = borrowRecordDao.getMostBorrowedBookByGender("female");
            Map<String, Object> mostGenreMale = borrowRecordDao.getMostBorrowedGenreByGender("male");
            Map<String, Object> mostGenreFemale = borrowRecordDao.getMostBorrowedGenreByGender("female");

            request.setAttribute("mostBorrowedBook", mostBorrowedBook);
            request.setAttribute("leastBorrowedBook", leastBorrowedBook);
            request.setAttribute("mostBorrowedGenre", mostBorrowedGenre);

            request.setAttribute("mostBookMale", mostBookMale);
            request.setAttribute("mostBookFemale", mostBookFemale);
            request.setAttribute("mostGenreMale", mostGenreMale);
            request.setAttribute("mostGenreFemale", mostGenreFemale);
            
            System.out.println("mostBorrowedGenre: " + mostBorrowedGenre);
            System.out.println("mostGenreFemale: " + mostGenreFemale);
            System.out.println("mostGenreMale: " + mostGenreMale);
            System.out.println("mostBookFemale: " + mostBookFemale);
            System.out.println("mostBorrowedBook: " + mostBorrowedBook);
            System.out.println("leastBorrowedBook: " + leastBorrowedBook);
            System.out.println("mostBookMale: " + mostBookMale);


        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while retrieving dashboard data", e);
            request.setAttribute("errorMessage", "Unable to load dashboard data.");
        }
        
        request.getRequestDispatcher("pages/admin/Dashboard.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
