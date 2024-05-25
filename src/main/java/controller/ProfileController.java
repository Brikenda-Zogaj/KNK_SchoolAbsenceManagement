package controller;

import database.ConnexionDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.SessionManager;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    public TextField cUsername;
    public TextField nUsername;
    public Label emri;
    @FXML
    private Label editId;
    @FXML
    private Label privacyId;
    @FXML
    private Label changePassword;
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
    private Stage stage;
    private Connection conn;
    private User currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        privacyId.setOnMouseClicked(actionEvent -> redirectToPrivacy());
        changePassword.setOnMouseClicked(actionEvent -> redirectToPassword());
        logOut.setOnAction(actionEvent -> redirectToLogIn());

        saveEditId.setOnAction(actionEvent ->saveChanges());
        String currentUsername = cUsername.getText(); // Assuming you have a field for current username
        String newUsername = nUsername.getText();

        conn = ConnexionDB.Connect();
        currentUser = SessionManager.getInstance().getCurrentUser();
        if (currentUser != null) {

        } else {

        }
    }
    @FXML
    private void saveChanges() {
        String currentUsername = cUsername.getText();
        String newUsername = nUsername.getText();
        String firstName = fname.getText();
        String lastName = lname.getText();
        String subject = tsubject.getText();
        String email = temail.getText();
        String phone = tphone.getText();

        ProfileUpdater profileUpdater = new ProfileUpdater(currentUsername, newUsername);

        profileUpdater.setFirstName(firstName);
        profileUpdater.setLastName(lastName);
        profileUpdater.setSubject(subject);
        profileUpdater.setEmail(email);
        profileUpdater.setPhone(phone);

        profileUpdater.updateProfileInSignupTable();
        profileUpdater.updateUsernameInUsersTable();
    }


    public void redirectToLogIn() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) logOut.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void redirectToPassword() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Password.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) changePassword.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void redirectToPrivacy() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/PrivacyPolicy.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) privacyId.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


}