package controller;

import database.ConnexionDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.SessionManager;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private Label editId;
    @FXML
    private Label privacyId;
    @FXML
    private Label changePassword;
    @FXML
    private Button editId1;
    @FXML
    private TextField fname;
    @FXML
    private TextField lname;
    @FXML
    private TextField tsubject;
    @FXML
    private TextField temail;
    @FXML
    private TextField tphone;
    @FXML
    private Button saveEditId;
    @FXML
    private Button logOut;

    private Connection conn;
    private User currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conn = ConnexionDB.Connection();
        currentUser = SessionManager.getInstance().getCurrentUser();
        if (currentUser != null) {
            fetchAndPopulateUserData(currentUser.getId());
        } else {
            // Handle the case where no user is logged in
        }
        logOut.setOnAction(actionEvent -> logout());
        saveEditId.setOnAction(actionEvent -> saveChanges());
    }

    private void fetchAndPopulateUserData(int userId) {
        try {
            String query = "SELECT first_name, last_name, subject, email, phone FROM signup_users WHERE user_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String subject = rs.getString("subject");
                String email = rs.getString("email");
                String phone = rs.getString("phone");

                // Populate the text fields
                fname.setText(firstName);
                lname.setText(lastName);
                tsubject.setText(subject);
                temail.setText(email);
                tphone.setText(phone);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while fetching the user data.");
            alert.showAndWait();
        }
    }

    private void saveChanges() {
        String firstName = fname.getText();
        String lastName = lname.getText();
        String subject = tsubject.getText();
        String email = temail.getText();
        String phone = tphone.getText();

        try {
            String query = "UPDATE signup_users SET first_name = ?, last_name = ?, subject = ?, email = ?, phone = ? WHERE user_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, subject);
            pstmt.setString(4, email);
            pstmt.setString(5, phone);
            pstmt.setInt(6, currentUser.getId());
            pstmt.executeUpdate();


            // Notify the user
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Profile Updated");
            alert.setHeaderText(null);
            alert.setContentText("Profile updated successfully!");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while updating the profile.");
            alert.showAndWait();
        }
    }

    private void logout() {
        SessionManager.getInstance().setCurrentUser(null);
        redirectToLogin();
    }

    private void redirectToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/LogIn.fxml"));
            Stage stage = (Stage) logOut.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
