package app;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import javax.swing.JOptionPane;

public class diagramiklasave extends Application {
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();


        PieChart pc = new PieChart();
        pc.setTitle("Klasat");
        pc.setData(createData());


        FadeTransition f = new FadeTransition(Duration.seconds(4), pc);
        f.setFromValue(0);
        f.setToValue(1);
        f.play();


        for (PieChart.Data data : pc.getData()) {
            data.nameProperty().set(data.getName() + " : " + (int) data.getPieValue() + " klasa");
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                JOptionPane.showMessageDialog(null, "Klasa --> " + data.getName() + "\nNumri i klasave -->" + (int) data.getPieValue());
            });
        }


        Button backButton = new Button("←");
        backButton.setOnAction(e -> {
            try {

                Parent mainPage = FXMLLoader.load(getClass().getResource("/Fxml/b.fxml"));
                Scene mainScene = new Scene(mainPage, 790, 500);
                primaryStage.setScene(mainScene);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });


        HBox topBox = new HBox(backButton);
        topBox.setAlignment(Pos.CENTER_LEFT);
        root.setTop(topBox);
        root.setCenter(pc);


        Scene scene = new Scene(root, 700, 400);
        primaryStage.setScene(scene);


        primaryStage.show();
    }

    private ObservableList<PieChart.Data> createData() {
        return FXCollections.observableArrayList(
                new PieChart.Data("Klasa 10", 20),
                new PieChart.Data("Klasa 11", 18),
                new PieChart.Data("Klasa 12", 12)

        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}