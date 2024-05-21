package controller;

import database.ConnexionDB;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField tname;
    public PasswordField tpass;
    public Button btncon;
    public Button btnsig1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btncon.setOnAction(actionEvent -> login());
        btnsig1.setOnAction(actionEvent -> direct());

    }

    public void login() {
        PreparedStatement st = null;
        ResultSet rs = null;
        Connection con = ConnexionDB.getConnection();
        try {
            st = con.prepareStatement("SELECT * FROM users WHERE USERNAME =?");
            st.setString(1, tname.getText());
            rs = st.executeQuery();
            if (rs.next()) {
                String storedSalt = rs.getString("salt");
                String storedSaltedHash = rs.getString("password");

                // Generate the salted hash of the entered password using the stored salt
                String enteredSaltedHash = SignUpController.PasswordHasher.generateSaltedHash(tpass.getText(), storedSalt);
                if (storedSaltedHash.equals(enteredSaltedHash)) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/b.fxml"));
                    Parent root = loader.load();

                    // Get the stage from the button and set the new scene
                    Stage stage = (Stage) btncon.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Login error", ButtonType.OK);
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Login error", ButtonType.OK);
                alert.show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void direct() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/SignUp.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the stage from the button and set the new scene
        Stage stage = (Stage) btncon.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
