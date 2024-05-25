package controller;

import app.diagramiklasave;
import app.diagramimungesave;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class maincontroller implements Initializable {

    public Button profile;
    public Button home;
    public Button mungesat;
    public Button studentat;


    public Button student;
    public Button absence;
    public Button klasa;

    @FXML
    private ImageView Exit;

    @FXML
    private Label Menu;
    @FXML
    private Label m;

    @FXML
    private Label titulli;

    @FXML
    private RadioButton al;
    @FXML
    private RadioButton en;

    @FXML
    private Label MenuClose;

    @FXML
    private AnchorPane slider;
    @FXML
    private Label student1;
    @FXML
    private Label absence1;
    @FXML
    private Label klasa1;

    @FXML
    void absence(ActionEvent event) throws Exception {
        // Mbyll dritaren aktuale
        absence.getScene().getWindow().hide();

        // Krijo një dritare të re për klasën diagramimungesave
        Stage primaryStage = new Stage();
        diagramimungesave diagramimungesaveWindow = new diagramimungesave(); // Krijo objektin e klases diagramimungesave
        diagramimungesaveWindow.start(primaryStage); // Thirr metoden start në objektin e klases diagramimungesave
    }
    @FXML
    void klasa(ActionEvent event) throws Exception{
       klasa.getScene().getWindow().hide();
        Stage primaryStage = new Stage();
        diagramiklasave diagramiklasaveWindow= new diagramiklasave();
        diagramiklasaveWindow.start(primaryStage);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changeLanguage();
        profile.setOnAction(actionEvent -> direct3());

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
        Stage stage = (Stage) profile.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    void languageaction(ActionEvent event) {
        handlelanguage();
    }

    private void handlelanguage() {
        // Placeholder method
    }
    public void changeLanguage() {
        ToggleGroup languageToggleGroup = new ToggleGroup();
        al.setToggleGroup(languageToggleGroup);
        en.setToggleGroup(languageToggleGroup);

        languageToggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle == al) {
                Locale currentLocale = new Locale("sq", "AL");
                ResourceBundle bundle = ResourceBundle.getBundle("translations.AL_SQ", currentLocale);
                home.setText(bundle.getString("home.button.text"));
                studentat.setText(bundle.getString("studentat.button.text"));
                mungesat.setText(bundle.getString("mungesat.button.text"));
                profile.setText(bundle.getString("profile.button.text"));
                m.setText(bundle.getString("mm.label"));
                titulli.setText(bundle.getString("titulli.label"));
                student1.setText(bundle.getString("student.button.text"));
                absence1.setText(bundle.getString("absence.button.text"));
                klasa1.setText(bundle.getString("klasa.button.text"));

            } else if (newToggle == en) {
                Locale currentLocale = new Locale("en", "US");
                ResourceBundle bundle = ResourceBundle.getBundle("translations.US_EN", currentLocale);
                home.setText(bundle.getString("home.button.text"));
                studentat.setText(bundle.getString("studentat.button.text"));
                mungesat.setText(bundle.getString("mungesat.button.text"));
                profile.setText(bundle.getString("profile.button.text"));
                m.setText(bundle.getString("mm.label"));
                titulli.setText(bundle.getString("titulli.label"));
                student1.setText(bundle.getString("student.button.text"));
                absence1.setText(bundle.getString("absence.button.text"));
                klasa1.setText(bundle.getString("klasa.button.text"));
            }
        });

        // Vendosa gjuhën fillestare si Shqip
        languageToggleGroup.selectToggle(al);
    }

    public void Studentat(ActionEvent event) {
        // Your code here
    }

}