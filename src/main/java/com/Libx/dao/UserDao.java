package com.Libx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Libx.database.DatabaseConnection;
import com.Libx.model.User;
import com.Libx.utility.EncryptDecrypt;

public class UserDao {
    private final Connection con;

    public UserDao() throws ClassNotFoundException, SQLException {
        this.con = DatabaseConnection.getConnection();
    }

    // User Registration
    public boolean register(User user) throws SQLException {
        boolean isUserRegistered = false;
        String query = "INSERT INTO user (username, password, email, age, gender) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement st = con.prepareStatement(query)) {
            st.setString(1, user.getUsername());

            // Encrypt password
            String encryptedPassword = EncryptDecrypt.encrypt(user.getPassword());
            st.setString(2, encryptedPassword);

            st.setString(3, user.getEmail());
            st.setInt(4, user.getAge());
            st.setString(5, user.getGender());

            if (st.executeUpdate() > 0) {
                isUserRegistered = true;
            }
        }
        return isUserRegistered;
    }

    // User Authentication (Login)
    public User login(String email, String password) throws SQLException {
        User user = null;
        String query = "SELECT * FROM user WHERE email = ?";

        try (PreparedStatement st = con.prepareStatement(query)) {
            st.setString(1, email);
            try (ResultSet userSet = st.executeQuery()) {
                if (userSet.next()) {
                    String storedEncryptedPassword = userSet.getString("password");
                    String decryptedPassword = EncryptDecrypt.decrypt(storedEncryptedPassword);
                    if (password.equals(decryptedPassword)) {
                        user = new User(
                            userSet.getInt("user_id"),
                            userSet.getString("username"),
                            storedEncryptedPassword, // store encrypted version
                            userSet.getString("email"),
                            userSet.getString("role"),
                            userSet.getInt("age"),
                            userSet.getString("gender")
                        );
                    }
                }
            }
        }
        return user;
    }

    // Search users by username/email
    public List<User> searchUsers(String query) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE (email LIKE ? OR username LIKE ?) AND role = 'member'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + query + "%");
            stmt.setString(2, "%" + query + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setAge(rs.getInt("age"));
                user.setGender(rs.getString("gender"));

                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
