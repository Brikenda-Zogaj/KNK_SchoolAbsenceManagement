package model;

import database.ConnexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public User getUserById(int userId) {
        User user = null;
        String query = "SELECT first_name, last_name, subject, email, phone FROM signup_users WHERE user_id = ?";

        try (Connection connection = ConnexionDB.Connect();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String subject = rs.getString("subject");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                user = new User(userId, firstName, lastName, subject, email, phone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception if necessary
        }
        return user;
    }
}
