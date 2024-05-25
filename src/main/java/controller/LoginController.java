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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginController implements Initializable {
    public TextField tname;
    public PasswordField tpass;
    public Button btncon;
    public Button btnsig1;
    @FXML
    private RadioButton alButton;
    @FXML
    private RadioButton enButton;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passLabel;
    @FXML
    private Label dha;
    @FXML
    private Label l;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeLanguage();
        btncon.setOnAction(actionEvent -> login());
        btnsig1.setOnAction(actionEvent -> direct());

        btncon.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> handleKeyPress(event));
            }
        });
    }

    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (btncon.isFocused()) {
                login();
            } else if (btnsig1.isFocused()) {
                direct();
            }
        } else if (event.getCode() == KeyCode.TAB) {
            if (event.isShiftDown()) {
                focusPrevious();
            } else {
                focusNext();
            }
            event.consume();
        }
    }

    private void focusNext() {
        if (tname.isFocused()) {
            tpass.requestFocus();
        } else if (tpass.isFocused()) {
            btncon.requestFocus();
        } else if (btncon.isFocused()) {
            btnsig1.requestFocus();
        } else if (btnsig1.isFocused()) {
            alButton.requestFocus();
        } else if (alButton.isFocused()) {
            enButton.requestFocus();
        } else if (enButton.isFocused()) {
            tname.requestFocus();
        }
    }

    private void focusPrevious() {
        if (tname.isFocused()) {
            enButton.requestFocus();
        } else if (tpass.isFocused()) {
            tname.requestFocus();
        } else if (btncon.isFocused()) {
            tpass.requestFocus();
        } else if (btnsig1.isFocused()) {
            btncon.requestFocus();
        } else if (alButton.isFocused()) {
            btnsig1.requestFocus();
        } else if (enButton.isFocused()) {
            alButton.requestFocus();
        }

    }

    @FXML
    void loginaction(ActionEvent event) {
        handleLogin();
    }

    private void handleLogin() {
        // Placeholder method
    }

    public void changeLanguage() {
        ToggleGroup languageToggleGroup = new ToggleGroup();
        alButton.setToggleGroup(languageToggleGroup);
        enButton.setToggleGroup(languageToggleGroup);

        languageToggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle == alButton) {
                Locale currentLocale = new Locale("sq", "AL");
                ResourceBundle bundle = ResourceBundle.getBundle("translations.AL_SQ", currentLocale);
                l.setText(bundle.getString("l.label"));
                btncon.setText(bundle.getString("login.button.text"));
                btnsig1.setText(bundle.getString("signup.button.text"));
                tname.setPromptText(bundle.getString("Enter.your.username.placeholder"));
                tpass.setPromptText(bundle.getString("Enter.your.password.placeholder"));
                usernameLabel.setText(bundle.getString("username.label"));
                passLabel.setText(bundle.getString("password.label"));
                dha.setText(bundle.getString("d.label"));
            } else if (newToggle == enButton) {
                Locale currentLocale = new Locale("en", "US");
                ResourceBundle bundle = ResourceBundle.getBundle("translations.US_EN", currentLocale);
                l.setText(bundle.getString("l.label"));
                btncon.setText(bundle.getString("login.button.text"));
                btnsig1.setText(bundle.getString("signup.button.text"));
                tname.setPromptText(bundle.getString("Enter.your.username.placeholder"));
                tpass.setPromptText(bundle.getString("Enter.your.password.placeholder"));
                usernameLabel.setText(bundle.getString("username.label"));
                passLabel.setText(bundle.getString("password.label"));
                dha.setText(bundle.getString("d.label"));
            }
        });

        // Vendosa gjuhën fillestare si Shqip
        languageToggleGroup.selectToggle(alButton);
    }

    public void login() {
        PreparedStatement st = null;
        ResultSet rs = null;
        Connection con = ConnexionDB.Connection();
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
        } catch (SQLException | IOException e) {
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
        Parent root;
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
