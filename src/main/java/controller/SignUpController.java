package controller;

import database.ConnexionDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.util.Base64;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private TextField fname;
    @FXML
    private TextField lname;
    @FXML
    private TextField tname;
    @FXML
    private PasswordField tpass;
    @FXML
    private ComboBox<String> tsubject;
    @FXML
    private Button btnsig;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnsig.setOnAction(actionEvent -> signUp());
    }


    public class PasswordHasher {
        private static final int SALT_LENGTH = 32; // length of salt in bytes
        private static final int HASH_LENGTH = 256; // length of hash in bytes
        private static final String HASH_ALGORITHM = "SHA-256";

        //    TODO: Create method that generates salt
        public static String generateSalt() {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);
            return Base64.getEncoder().encodeToString(salt);
        }

        public static String generateSaltedHash(String password, String salt) {
            byte[] hash = hashWithSalt(password, salt);

            // Combine salt and hash into a single string
            StringBuilder sb = new StringBuilder(SALT_LENGTH + HASH_LENGTH);

            byte [] saltBytes = salt.getBytes();
            for (int i = 0; i < saltBytes.length; i++) {
                sb.append(String.format("%02x", saltBytes[i]));
            }
            for (int i = 0; i < hash.length; i++) {
                sb.append(String.format("%02x", hash[i]));
            }
            return sb.toString();
        }

        public static boolean compareSaltedHash(String password, String salt, String saltedHash) {
            byte[] expectedHash = new byte[HASH_LENGTH];
            for (int i = 0; i < HASH_LENGTH; i++) {
                int index = (SALT_LENGTH + i) * 2;
                expectedHash[i] = (byte) Integer.parseInt(saltedHash.substring(index, index + 2), 16);
            }
            byte[] actualHash = hashWithSalt(password, salt);
            return MessageDigest.isEqual(expectedHash, actualHash);
        }

        private static byte[] hashWithSalt(String password, String salt) {
            try {
                MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
                digest.reset();
                digest.update(salt.getBytes());
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
    private String hashPassword(String password) {
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-1");

            md.update(password.getBytes());
            byte[] hashedBytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void signUp() {


        Connection con = null;
        boolean signUpSuccess = false;
        PreparedStatement insertStmt = null;
        try {
            String firstName = fname.getText();
            String lastName = lname.getText();
            String username = tname.getText();
            String password = tpass.getText();
            String subject = tsubject.getValue();

            // Validation
            if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || subject == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill all fields", ButtonType.OK);
                alert.show();
                return;
            }

            con = ConnexionDB.getConnection();
            String hashedPassword = hashPassword(password);

            // Inserting into signup_users
            PreparedStatement st = con.prepareStatement("INSERT INTO signup_users (first_name, last_name, username, password, subject) VALUES (?, ?, ?, ?, ?)");
            st.setString(1, firstName);
            st.setString(2, lastName);
            st.setString(3, username);
            st.setString(4, hashedPassword);
            st.setString(5, subject);
            st.executeUpdate();
            st.close();
            signUpSuccess = true;
            PreparedStatement selectStmt = con.prepareStatement("SELECT username, password FROM signup_users");
            ResultSet rs = selectStmt.executeQuery();

            // Transfer data to users table
            insertStmt = con.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            while (rs.next()) {
                insertStmt.setString(1, rs.getString("username"));
                insertStmt.setString(2, rs.getString("password"));
                insertStmt.executeUpdate();
            }
            rs.close();
            selectStmt.close();
            insertStmt.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/b.fxml"));
            Parent root = loader.load();


            // Get the stage from the button and set the new scene
            Stage stage = (Stage) btnsig.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sign-Up Failed: " + e.getMessage(), ButtonType.OK);
            alert.show();
            signUpSuccess = false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources
            if (insertStmt != null) {
                try {
                    insertStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    if (!signUpSuccess) {
                        // Delete records from signup_users table
                        // Delete records from signup_users table
                        PreparedStatement cleanStmt = con.prepareStatement("DELETE FROM signup_users WHERE id = (SELECT MAX(id) FROM signup_users)");

                        cleanStmt.executeUpdate();
                        cleanStmt.close();
                    }
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
