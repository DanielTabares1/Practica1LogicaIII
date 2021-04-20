import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/*
La clase Main se encarga únicamente de cargar y lanzar la ventana principal
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //se obtiene el archivo correspondiente a la ventana principal (sample.fxml)
        Parent root = FXMLLoader.load(getClass().getResource("View/sample.fxml"));

        //determina titulo, dimesiones e ícono de la ventana, y la muestra en pantalla
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
