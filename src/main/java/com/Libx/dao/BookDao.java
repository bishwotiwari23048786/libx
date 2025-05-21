package com.Libx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Libx.database.DatabaseConnection;
import com.Libx.model.Book;

public class BookDao {
    private final Connection con;

    public BookDao() throws ClassNotFoundException, SQLException {
        this.con = DatabaseConnection.getConnection();
    }

    public boolean addBook(Book book) throws SQLException {
        String query = "INSERT INTO book (title, author, genre, isbn, publisher, cover_img_path, publish_year, total_copies) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getGenre());
            ps.setString(4, book.getIsbn());
            ps.setString(5, book.getPublisher());
            ps.setString(6, book.getCover_img_path());
            ps.setInt(7, book.getPublish_year());
            ps.setInt(8, book.getTotal_copies());

            return ps.executeUpdate() > 0;  
        } catch (SQLException e) {
            e.printStackTrace();  
            return false; 
        }
    }
    
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM book";
        try (PreparedStatement ps = con.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Book book = new Book();
                book.setBook_id(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setGenre(rs.getString("genre"));
                book.setPublish_year(rs.getInt("publish_year"));
                book.setTotal_copies(rs.getInt("total_copies"));
                book.setCover_img_path(rs.getString("cover_img_path"));
                books.add(book);
            }
        }
        return books;
    }

    // Get filtered books based on multiple search parameters
    public List<Book> getFilteredBooks(String title, String author, String genre, Boolean available) throws SQLException {
        List<Book> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM book WHERE 1=1");

        if (title != null && !title.isEmpty()) {
            sql.append(" AND title LIKE ?");
        }
        if (author != null && !author.isEmpty()) {
            sql.append(" AND author LIKE ?");
        }
        if (genre != null && !genre.isEmpty()) {
            sql.append(" AND genre = ?");
        }
        if (available != null) {
            sql.append(available ? " AND total_copies > 0" : " AND total_copies = 0");
        }

        try (PreparedStatement stmt = con.prepareStatement(sql.toString())) {
            int index = 1;
            
            if (title != null && !title.isEmpty()) {
                stmt.setString(index++, "%" + title + "%");
            }
            if (author != null && !author.isEmpty()) {
                stmt.setString(index++, "%" + author + "%");
            }
            if (genre != null && !genre.isEmpty()) {
                stmt.setString(index++, genre);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Book b = new Book();
                    b.setBook_id(rs.getInt("book_id"));
                    b.setTitle(rs.getString("title"));
                    b.setAuthor(rs.getString("author"));
                    b.setGenre(rs.getString("genre"));
                    b.setIsbn(rs.getString("isbn"));
                    b.setPublisher(rs.getString("publisher"));
                    b.setCover_img_path(rs.getString("cover_img_path"));
                    b.setPublish_year(rs.getInt("publish_year"));
                    b.setTotal_copies(rs.getInt("total_copies"));
                    list.add(b);
                }
            }
        }
        return list;
    }
    
    public int getAvailableCopies(int bookId) throws SQLException {
        String sql = 
            "SELECT b.total_copies - IFNULL(br.borrowed_count, 0) AS available_copies " +
            "FROM book b " +
            "LEFT JOIN (" +
            "   SELECT book_id, COUNT(*) AS borrowed_count " +
            "   FROM borrow_record " +
            "   WHERE return_date IS NULL " +
            "   GROUP BY book_id " +
            ") br ON b.book_id = br.book_id " +
            "WHERE b.book_id = ?";
            
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("available_copies");
                }
            }
        }
        return 0;
    }
    
    public List<Book> searchBooks(String query) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book WHERE title LIKE ? OR author LIKE ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, "%" + query + "%");
            stmt.setString(2, "%" + query + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Book book = new Book();
                book.setBook_id(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                
                int availableCopies = getAvailableCopies(book.getBook_id());
                
                if (availableCopies > 0) {
                    book.setAvailable_copies(availableCopies);
                    books.add(book);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
    
    public Book getBookById(int bookId) throws SQLException {
        String sql = "SELECT * FROM book WHERE book_id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Book book = new Book();
                    book.setBook_id(rs.getInt("book_id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setGenre(rs.getString("genre"));
                    book.setIsbn(rs.getString("isbn"));
                    book.setPublisher(rs.getString("publisher"));
                    book.setCover_img_path(rs.getString("cover_img_path"));
                    book.setPublish_year(rs.getInt("publish_year"));
                    book.setTotal_copies(rs.getInt("total_copies"));

                    int availableCopies = getAvailableCopies(bookId);
                    book.setAvailable_copies(availableCopies);

                    return book;
                }
            }
        }
        return null; 
    }
    
    public boolean addToStock(int bookId, int additionalCopies) throws SQLException {
        String sql = "UPDATE book SET total_copies = total_copies + ? WHERE book_id = ?";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, additionalCopies);
            stmt.setInt(2, bookId);
            return stmt.executeUpdate() > 0;
        }
    }
    
}