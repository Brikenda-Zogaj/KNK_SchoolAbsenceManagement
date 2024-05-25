package controller;

import database.ConnexionDB;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileUpdater {
    private String currentUsername;
    private String newUsername;
    private String firstName;
    private String lastName;
    private String subject;
    private String email;
    private String phone;

    public ProfileUpdater(String currentUsername, String newUsername) {
        this.currentUsername = currentUsername;
        this.newUsername = newUsername;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void updateUsernameInUsersTable() {
        try (Connection connection = ConnexionDB.getConnection()) {
            updateUsername(connection);
            showMessage("Username updated successfully in the users table.");
        } catch (SQLException e) {
            e.printStackTrace();
            showMessage("An error occurred while updating the username in the users table.");
        }
    }

    public void updateProfileInSignupTable() {
        try (Connection connection = ConnexionDB.getConnection()) {
            updateProfile(connection);
            showMessage("Profile updated successfully in the signup_users table.");
        } catch (SQLException e) {
            e.printStackTrace();
            showMessage("An error occurred while updating the profile in the signup_users table.");
        }
    }

    public void fetchProfileInfo(String username) {
        try (Connection connection = ConnexionDB.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT username, first_name, last_name, subject, email, phone FROM signup_users WHERE username = ?")) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    this.newUsername = resultSet.getString("username");
                    this.firstName = resultSet.getString("first_name");
                    this.lastName = resultSet.getString("last_name");
                    this.subject = resultSet.getString("subject");
                    this.email = resultSet.getString("email");
                    this.phone = resultSet.getString("phone");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateUsername(Connection connection) throws SQLException {
        String query = "UPDATE users SET username = ? WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, newUsername);
            pstmt.setString(2, currentUsername);
            pstmt.executeUpdate();
        }
    }

    private void updateProfile(Connection connection) throws SQLException {
        String query = "UPDATE signup_users SET username = ?, first_name = ?, last_name = ?, subject = ?, email = ?, phone = ? WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, newUsername); // Update username
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, subject);
            pstmt.setString(5, email);
            pstmt.setString(6, phone);
            pstmt.setString(7, currentUsername); // Where condition
            pstmt.executeUpdate();
        }
    }

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.show();
    }
}
