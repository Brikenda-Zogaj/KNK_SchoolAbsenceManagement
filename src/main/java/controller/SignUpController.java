package controller;

import database.ConnexionDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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

    private void signUp() {
        PreparedStatement insertStmt = null;
        Connection con = null;
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

            // Inserting into signup_users
            PreparedStatement st = con.prepareStatement("INSERT INTO signup_users (first_name, last_name, username, password, subject) VALUES (?, ?, ?, ?, ?)");
            st.setString(1, firstName);
            st.setString(2, lastName);
            st.setString(3, username);
            st.setString(4, password);
            st.setString(5, subject);
            st.executeUpdate();
            st.close();

            // Transfer data to users table
            insertStmt = con.prepareStatement("INSERT INTO users (username, password) " +
                    "SELECT username, password FROM signup_users");

            insertStmt.executeUpdate();




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
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
