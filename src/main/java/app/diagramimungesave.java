package app;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.JOptionPane;

public class diagramimungesave extends Application {
    private XYChart.Series<Number, Number> series;
    private ObservableList<XYChart.Data<Number, Number>> dataList;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();


        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Ditet e javes");
        yAxis.setLabel("Mungesat");


        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Mungesat gjate javes");


        series = new XYChart.Series<>();
        series.setName("Mungesat");
        dataList = createData();
        series.getData().addAll(dataList);

        lineChart.getData().add(series);


        FadeTransition f = new FadeTransition(Duration.seconds(4), lineChart);
        f.setFromValue(0);
        f.setToValue(1);
        f.play();


        for (XYChart.Data<Number, Number> data : series.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                JOptionPane.showMessageDialog(null, "Dita e javes --> " + data.getXValue() + "\nNumri i mungesave --> " + data.getYValue());
            });
        }


        Label dayLabel = new Label("Dita e javes (1-5):");
        TextField dayField = new TextField();
        Label absenceLabel = new Label("Numri i mungesave:");
        TextField absenceField = new TextField();
        Button addButton = new Button("Shto Mungesat");

        addButton.setOnAction(e -> {
            try {
                int day = Integer.parseInt(dayField.getText());
                int absences = Integer.parseInt(absenceField.getText());
                if (day < 1 || day > 5) {
                    JOptionPane.showMessageDialog(null, "Ju lutem shkruani nje dite te vlefshme (1-5).");
                } else {
                    addData(day, absences);
                    dayField.clear();
                    absenceField.clear();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Ju lutem shkruani numra te vlefshem.");
            }
        });

        VBox formBox = new VBox(10, dayLabel, dayField, absenceLabel, absenceField, addButton);
        formBox.setAlignment(Pos.CENTER);


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
        root.setCenter(lineChart);
        root.setRight(formBox);

        Scene scene = new Scene(root, 800, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private ObservableList<XYChart.Data<Number, Number>> createData() {
        return FXCollections.observableArrayList(
                new XYChart.Data<>(1, 20),  // Hene
                new XYChart.Data<>(2, 18),  // Marte
                new XYChart.Data<>(3, 12),  // Merkure
                new XYChart.Data<>(4, 16),  // Enjete
                new XYChart.Data<>(5, 10)   // Premte
        );
    }

    private void addData(int day, int absences) {
        XYChart.Data<Number, Number> newData = new XYChart.Data<>(day, absences);
        series.getData().add(newData);
        newData.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            JOptionPane.showMessageDialog(null, "Dita e javes --> " + newData.getXValue() + "\nNumri i mungesave --> " + newData.getYValue());
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
