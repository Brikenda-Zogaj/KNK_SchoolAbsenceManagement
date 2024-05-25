package controller;

import database.ConnexionDB;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class PasswordChanger {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
    private String username;

    public PasswordChanger(String oldPassword, String newPassword, String confirmNewPassword, String username) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
        this.username = username;
    }

    public void saveChanges() {
        if (oldPassword == null || newPassword == null || confirmNewPassword == null || username == null) {
            showMessage("All fields are required.", AlertType.ERROR);
            return;
        }

        if (newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
            showMessage("New password cannot be empty.", AlertType.ERROR);
            return;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            showMessage("The new passwords do not match.", AlertType.ERROR);
            return;
        }

        // Add any other password validation criteria here, for example:
        // if (newPassword.length() < 8) {
        //     showMessage("The new password must be at least 8 characters long.", AlertType.ERROR);
        //     return;
        // }

        String currentSalt = null;
        String currentHashedPassword = null;

        try (Connection connection = ConnexionDB.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT password, salt FROM users WHERE username = ?")) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    currentHashedPassword = resultSet.getString("password");
                    currentSalt = resultSet.getString("salt");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showMessage("An error occurred while accessing the database.", AlertType.ERROR);
            return;
        }

        if (currentHashedPassword == null || currentSalt == null) {
            showMessage("User not found or missing password/salt.", AlertType.ERROR);
            return;
        }

        String enteredSaltedHash = PasswordHasher.generateSaltedHash(oldPassword, currentSalt);
        if (!currentHashedPassword.equals(enteredSaltedHash)) {
            showMessage("The old password is incorrect.", AlertType.ERROR);
            return;
        }

        String newSalt = PasswordHasher.generateSalt();
        String newSaltedHash = PasswordHasher.generateSaltedHash(newPassword, newSalt);

        try (Connection connection = ConnexionDB.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE users SET password = ?, salt = ? WHERE username = ?")) {
            statement.setString(1, newSaltedHash);
            statement.setString(2, newSalt);
            statement.setString(3, username);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                showMessage("Password changed successfully.", AlertType.INFORMATION);
            } else {
                showMessage("There was an error changing the password. Please try again.", AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showMessage("There was an error changing the password. Please try again.", AlertType.ERROR);
        }
    }

    private void showMessage(String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }

    public static class PasswordHasher {
        private static final int SALT_LENGTH = 16; // length of salt in bytes
        private static final String HASH_ALGORITHM = "SHA-256";

        public static String generateSalt() {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);
            return Base64.getEncoder().encodeToString(salt);
        }

        public static String generateSaltedHash(String password, String salt) {
            byte[] hash = hashWithSalt(password, salt);
            return Base64.getEncoder().encodeToString(hash);
        }

        private static byte[] hashWithSalt(String password, String salt) {
            try {
                MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
                digest.reset();
                digest.update(Base64.getDecoder().decode(salt));
                byte[] hash = digest.digest(password.getBytes());
                for (int i = 0; i < 1000; i++) {
                    digest.reset();
                    hash = digest.digest(hash);
                }
                return hash;
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Failed to hash password: " + e.getMessage(), e);
            }
        }
    }
}
