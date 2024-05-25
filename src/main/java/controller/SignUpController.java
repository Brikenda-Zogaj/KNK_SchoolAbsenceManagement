package controller;

import database.ConnexionDB;
import javafx.event.ActionEvent;
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
import java.util.Base64;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    public Button btncon1;

    @FXML
    private RadioButton al;
    @FXML
    private RadioButton en;
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
    @FXML
    private Label signup;
    @FXML
    private Label firstname;

    @FXML
    private Label lastname;
    @FXML
    private Label username;
    @FXML
    private Label pass;
    @FXML
    private Label account;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeLanguage();
        btnsig.setOnAction(actionEvent -> signUp());
        btncon1.setOnAction(actionEvent -> direct2());
    }

    public void direct2() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/LogIn.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the stage from the button and set the new scene
        Stage stage = (Stage) btncon1.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static class PasswordHasher {
        private static final int SALT_LENGTH = 16; // length of salt in bytes
        private static final String HASH_ALGORITHM = "SHA-256";

        // Generate a salt
        public static String generateSalt() {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);
            return Base64.getEncoder().encodeToString(salt);
        }

        // Generate a salted hash
        public static String generateSaltedHash(String password, String salt) {
            byte[] hash = hashWithSalt(password, salt);
            return Base64.getEncoder().encodeToString(hash);
        }

        // Hash the password with the salt
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

        // Compare the entered password with the stored salted hash
        public static boolean compareSaltedHash(String password, String salt, String saltedHash) {
            byte[] expectedHash = Base64.getDecoder().decode(saltedHash);
            byte[] actualHash = hashWithSalt(password, salt);
            return MessageDigest.isEqual(expectedHash, actualHash);
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

            con = ConnexionDB.Connection();

            // Generate salt and salted hash
            String salt = PasswordHasher.generateSalt();
            String saltedHash = PasswordHasher.generateSaltedHash(password, salt);

            // Inserting into signup_users
            PreparedStatement st = con.prepareStatement("INSERT INTO signup_users (first_name, last_name, username, password, salt, subject) VALUES (?, ?, ?, ?, ?, ?)");
            st.setString(1, firstName);
            st.setString(2, lastName);
            st.setString(3, username);
            st.setString(4, saltedHash);
            st.setString(5, salt);
            st.setString(6, subject);
            st.executeUpdate();
            st.close();
            signUpSuccess = true;

            // Transfer data to users table
            insertStmt = con.prepareStatement("INSERT INTO users (username, password, salt) SELECT username, password, salt FROM signup_users WHERE username = ?");
            insertStmt.setString(1, username);
            insertStmt.executeUpdate();
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
                        PreparedStatement cleanStmt = con.prepareStatement("DELETE FROM signup_users WHERE username = ?");
                        cleanStmt.setString(1, tname.getText());
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
    @FXML
    void signupaction(ActionEvent event) {
        handleLogin();
    }

    private void handleLogin() {
        // Placeholder method
    }
    public void changeLanguage() {
        ToggleGroup languageToggleGroup = new ToggleGroup();
        al.setToggleGroup(languageToggleGroup);
        en.setToggleGroup(languageToggleGroup);
        languageToggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle == al){
                Locale currentLocale = new Locale("sq", "AL");
                ResourceBundle bundle = ResourceBundle.getBundle("translations.AL_SQ", currentLocale);
                signup.setText(bundle.getString("signup.label"));
                firstname.setText(bundle.getString("firstname.text"));
                lastname.setText(bundle.getString("lastname.label"));
                username.setText(bundle.getString("usern.label"));
                pass.setText(bundle.getString("pass.label"));
                btnsig.setText(bundle.getString("btnsig.button.text"));
                account.setText(bundle.getString("account.label"));
                btncon1.setText(bundle.getString("btncon1.button.text"));

            } else if (newToggle == en){
                Locale currentLocale = new Locale("en", "US");
                ResourceBundle bundle = ResourceBundle.getBundle("translations.US_EN", currentLocale);
                signup.setText(bundle.getString("signup.label"));
                firstname.setText(bundle.getString("firstname.text"));
                lastname.setText(bundle.getString("lastname.label"));
                username.setText(bundle.getString("username.label"));
                pass.setText(bundle.getString("pass.label"));
                btnsig.setText(bundle.getString("signup.button.text"));
                account.setText(bundle.getString("account.label"));
                btncon1.setText(bundle.getString("btncon1.button.text"));
            }

        });
        languageToggleGroup.selectToggle(al);
        }
}
