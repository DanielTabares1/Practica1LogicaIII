import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View/sample.fxml"));
        primaryStage.setTitle("Buscaminas Beta v.1.0.0");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
        Image ico = new Image("images/buscaminasicono.jpg");
        primaryStage.getIcons().add(ico);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
