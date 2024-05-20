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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    btncon.setOnAction(actionEvent -> login());
    }
    public void login(){
        PreparedStatement st=null;
        ResultSet rs=null;
        Connection con= ConnexionDB.getConnection();
        try {
            st =con.prepareStatement("SELECT * FROM users WHERE USERNAME =? AND PASSWORD =?");
            st.setString(1,tname.getText());
            st.setString(2,tpass.getText());
            rs=st.executeQuery();
            if(rs.next()){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/b.fxml"));
                Parent root = loader.load();

                // Get the stage from the button and set the new scene
                Stage stage = (Stage) btncon.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING,"Login error",ButtonType.OK);
                alert.show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
