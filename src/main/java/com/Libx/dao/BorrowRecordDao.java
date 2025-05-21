package com.Libx.dao;

import com.Libx.database.DatabaseConnection;
import com.Libx.model.BorrowRecord;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BorrowRecordDao {
	private final Connection con;
	
	public BorrowRecordDao() throws ClassNotFoundException, SQLException {
        this.con = DatabaseConnection.getConnection();
    }
	
	public boolean canBorrow(int userId, int bookId) {
	    String sql = "SELECT COUNT(*) FROM borrow_record WHERE user_id = ? AND book_id = ? AND return_date IS NULL";
	    try (PreparedStatement stmt = con.prepareStatement(sql)) {
	        stmt.setInt(1, userId);
	        stmt.setInt(2, bookId);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1) == 0; // If result is 0, that means the user hasn't borrowed this book yet
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	
	public boolean borrowBook(int userId, int bookId, String borrowDate, String dueDate) {
        String sql = "INSERT INTO borrow_record (user_id, book_id, borrow_date, due_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            stmt.setDate(3, Date.valueOf(borrowDate));
            stmt.setDate(4, Date.valueOf(dueDate));

            stmt.executeUpdate();
            return true; // Successful borrow
        } catch (SQLException e){
            e.printStackTrace();
            return false; // Failure to borrow
        }
    }
	
	public List<BorrowRecord> getBorrowRecordsByUserId(int userId) {
	    List<BorrowRecord> list = new ArrayList<>();
	    String sql = "SELECT * FROM borrow_record WHERE user_id = ? AND return_date IS NULL";

	    try (PreparedStatement stmt = con.prepareStatement(sql)) {
	        stmt.setInt(1, userId);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            BorrowRecord br = new BorrowRecord();
	            br.setBorrow_id(rs.getInt("borrow_id"));
	            br.setUser_id(rs.getInt("user_id"));
	            br.setBook_id(rs.getInt("book_id"));
	            br.setBorrow_date(rs.getDate("borrow_date"));
	            br.setDue_date(rs.getDate("due_date"));
	            br.setReturn_date(rs.getDate("return_date"));
	            list.add(br);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return list;
	}

	public void updateReturnDate(int borrowId, String returnDate) {
	    String sql = "UPDATE borrow_record SET return_date = ? WHERE borrow_id = ?";

	    try (PreparedStatement stmt = con.prepareStatement(sql)) {
	        stmt.setDate(1, Date.valueOf(returnDate));
	        stmt.setInt(2, borrowId);
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public int calculateOverdueDays(int borrowId) {
        int overdueDays = 0;
        try {
            String query = "SELECT due_date, return_date FROM borrow_record WHERE borrow_id = ?";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setInt(1, borrowId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    Date dueDate = rs.getDate("due_date");
                    Date returnDate = rs.getDate("return_date");

                    if (returnDate == null && dueDate != null) {
                        LocalDate due = dueDate.toLocalDate();
                        LocalDate today = LocalDate.now();
                        if (today.isAfter(due)) {
                            overdueDays = (int) ChronoUnit.DAYS.between(due, today);
                        }
                    } else if (returnDate != null && dueDate != null) {
                        LocalDate due = dueDate.toLocalDate();
                        LocalDate returned = returnDate.toLocalDate();
                        if (returned.isAfter(due)) {
                            overdueDays = (int) ChronoUnit.DAYS.between(due, returned);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return overdueDays;
    }
	
	public List<Map<String, Object>> getCurrentBorrowsByUser(int userId) {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT br.borrow_id, br.borrow_date, br.due_date, b.title " +
                     "FROM borrow_record br JOIN book b ON br.book_id = b.book_id " +
                     "WHERE br.user_id = ? AND br.return_date IS NULL";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> record = new HashMap<>();
                record.put("borrow_id", rs.getInt("borrow_id"));
                record.put("title", rs.getString("title"));
                record.put("borrow_date", rs.getDate("borrow_date"));
                record.put("due_date", rs.getDate("due_date"));
                list.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Map<String, Object>> getOverdueBorrowsByUser(int userId) {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT br.borrow_id, br.borrow_date, br.due_date, b.title " +
                     "FROM borrow_record br JOIN book b ON br.book_id = b.book_id " +
                     "WHERE br.user_id = ? AND br.return_date IS NULL AND br.due_date < CURRENT_DATE";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> record = new HashMap<>();
                record.put("borrow_id", rs.getInt("borrow_id"));
                record.put("title", rs.getString("title"));
                record.put("borrow_date", rs.getDate("borrow_date"));
                record.put("due_date", rs.getDate("due_date"));
                list.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Map<String, Object>> getBookBorrowStats(boolean mostBorrowed) {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT b.book_id, b.title, b.cover_img_path, b.author, b.genre, COUNT(br.book_id) AS borrow_count " +
                     "FROM borrow_record br " +
                     "JOIN book b ON br.book_id = b.book_id " +
                     "GROUP BY b.book_id, b.title, b.cover_img_path, b.author, b.genre " +
                     "ORDER BY borrow_count " + (mostBorrowed ? "DESC" : "ASC") + " LIMIT 1";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> book = new HashMap<>();
                book.put("book_id", rs.getInt("book_id"));
                book.put("title", rs.getString("title"));
                book.put("cover_img_path", rs.getString("cover_img_path"));
                book.put("author", rs.getString("author"));
                book.put("genre", rs.getString("genre"));
                book.put("borrow_count", rs.getInt("borrow_count"));
                list.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public Map<String, Object> getMostBorrowedGenre() {
        Map<String, Object> genreStats = new HashMap<>();
        String sql = "SELECT b.genre, COUNT(*) AS borrow_count " +
                     "FROM borrow_record br " +
                     "JOIN book b ON br.book_id = b.book_id " +
                     "GROUP BY b.genre " +
                     "ORDER BY borrow_count DESC LIMIT 1";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                genreStats.put("genre", rs.getString("genre"));
                genreStats.put("borrow_count", rs.getInt("borrow_count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return genreStats;
    }

    public Map<String, Object> getMostBorrowedBookByGender(String gender) {
        Map<String, Object> bookStats = new HashMap<>();
        String sql = "SELECT b.book_id, b.title, b.cover_img_path, b.author, b.genre, COUNT(*) AS borrow_count " +
                     "FROM borrow_record br " +
                     "JOIN book b ON br.book_id = b.book_id " +
                     "JOIN user u ON br.user_id = u.user_id " +
                     "WHERE u.gender = ? " +
                     "GROUP BY b.book_id, b.title, b.cover_img_path, b.author, b.genre " +
                     "ORDER BY borrow_count DESC LIMIT 1";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, gender);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                bookStats.put("book_id", rs.getInt("book_id"));
                bookStats.put("title", rs.getString("title"));
                bookStats.put("cover_img_path", rs.getString("cover_img_path"));
                bookStats.put("author", rs.getString("author"));
                bookStats.put("genre", rs.getString("genre"));
                bookStats.put("borrow_count", rs.getInt("borrow_count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookStats;
    }

    public Map<String, Object> getMostBorrowedGenreByGender(String gender) {
        Map<String, Object> genreStats = new HashMap<>();
        String sql = "SELECT b.genre, COUNT(*) AS borrow_count " +
                     "FROM borrow_record br " +
                     "JOIN book b ON br.book_id = b.book_id " +
                     "JOIN user u ON br.user_id = u.user_id " +
                     "WHERE u.gender = ? " +
                     "GROUP BY b.genre " +
                     "ORDER BY borrow_count DESC LIMIT 1";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, gender);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                genreStats.put("genre", rs.getString("genre"));
                genreStats.put("borrow_count", rs.getInt("borrow_count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return genreStats;
    }
    
}
