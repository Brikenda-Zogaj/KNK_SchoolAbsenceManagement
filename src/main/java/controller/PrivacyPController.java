package controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class PrivacyPController implements Initializable {


    @FXML
    private VBox root;

    @FXML
    private Label titleLabel, introLabel, infoLabel, collectionLabel, purposeLabel, cookiesLabel,
            disclosureLabel, securityLabel, rightsLabel, changesLabel, contactLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
