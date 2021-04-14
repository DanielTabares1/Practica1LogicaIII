package Controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    int filas, columnas, minas; //se definen variables de filas, columnas y minas


    //se establece conección con la interfaz gráfica
    @FXML
    private TextField filas_id;
    @FXML
    private TextField columnas_id;
    @FXML
    private TextField minas_id;
    @FXML
    private Button dimensionesButton;

    //acción sobre el botón fácil
    public void facil_action() throws IOException {
        activarBotones(false);
        filas = 8;
        columnas = 8;
        minas = 10;
        lanzarJuego(new Stage());
    }

    //acción sobre el botón medio
    public void medio_action() throws IOException {
        activarBotones(false);
        filas = 16;
        columnas = 16;
        minas = 40;
        lanzarJuego(new Stage());
    }

    //acción sobre el botón difícil
    public void dificil_action() throws IOException {
        activarBotones(false);
        filas = 16;
        columnas = 30;
        minas = 99;
        lanzarJuego(new Stage());
    }

    //acción sobre el botón personalizado
    public void personalizado_action() {
        activarBotones(true); //activa los campos y botón para dimensiones personalizadas
    }

    //activa o desactiva los botones de facil. medio y dificil
    public void activarBotones(boolean b) {
        filas_id.setDisable(!b);
        columnas_id.setDisable(!b);
        dimensionesButton.setDisable(!b);
    }

    //acción del botón iniciar (personalizado)
    public void iniciar_action() throws IOException {
        System.out.println("acción");
        filas = Integer.parseInt(filas_id.getText());
        columnas = Integer.parseInt(columnas_id.getText());
        minas = Integer.parseInt(minas_id.getText());
        lanzarJuego(new Stage());
    }

    //lanza el juego en una nueva ventana
    public void lanzarJuego(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/juego.fxml"));
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.setTitle("Jueguito");
        primaryStage.show();
        juegoController.setValores(filas, columnas, minas);
        Implementacion.setValores(filas, columnas, minas);
    }


}
