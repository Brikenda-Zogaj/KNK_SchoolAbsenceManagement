package controller;

import app.main;


import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class maincontroller implements Initializable {

    public Button profileId;
    @FXML
    private ImageView Exit;

    @FXML
    private Label Menu;

    @FXML
    private Label MenuClose;

    @FXML
    private AnchorPane slider;
    @FXML
    private Button raportet;
    @FXML
    void raportet(ActionEvent event) throws Exception {
        // Mbyll dritaren aktuale
        raportet.getScene().getWindow().hide();

        // Krijo një dritare të re për klasën p
        Stage primaryStage = new Stage();
        main pWindow = new main(); // Krijo objektin e klases p
        pWindow.start(primaryStage); // Thirr metoden start në objektin e klases p
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        profileId.setOnAction(actionEvent -> direct3());

        Exit.setOnMouseClicked(event -> {
            System.exit(0);
        });
        slider.setTranslateX(-176);
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-176);

            slide.setOnFinished((ActionEvent e)-> {
                Menu.setVisible(false);
                MenuClose.setVisible(true);
            });
        });

        MenuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-176);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)-> {
                Menu.setVisible(true);
                MenuClose.setVisible(false);
            });
        });

    }
    public void direct3() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Profile.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the stage from the button and set the new scene
        Stage stage = (Stage) profileId.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}