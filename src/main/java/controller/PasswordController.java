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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PasswordController implements Initializable {

    public Label changePassword;
    public Button logOut;
    public Label privacyId;
    public Label editId;
    public TextField username;
    @FXML
    private TextField oldPass;

    @FXML
    private TextField newPass;

    @FXML
    private TextField cNewPass;

    @FXML
    private Button saveEditId;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        privacyId.setOnMouseClicked(actionEvent -> redirectToPrivacy());

        logOut.setOnAction(actionEvent -> redirectToLogIn());
        editId.setOnMouseClicked(actionEvent -> redirectToProfile());


        saveEditId.setOnAction(actionEvent -> changes());


    }

    private void changes() {
        String oldPassword = oldPass.getText();
        String newPassword = newPass.getText();
        String confirmNewPassword = cNewPass.getText();
        String Username = username.getText();

        PasswordChanger changer = new PasswordChanger(oldPassword, newPassword, confirmNewPassword, Username);
        changer.saveChanges();
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

    public void redirectToProfile() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Profile.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) editId.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}





