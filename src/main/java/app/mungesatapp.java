package app;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class mungesatapp extends Application{
    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Fxml/LogIn.fxml"));
        Scene scene=new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
    public static void mungesatapp(String[] args){
        launch();
    }
}