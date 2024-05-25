package app;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class studentatapp extends Application{

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Fxml/Nxenesit.fxml"));
        Scene scene=new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
    public static void studentatapp(String[] args){
        launch();
    }
}