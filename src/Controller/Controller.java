package Controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
/*
Esta clase controla lo relacionado a la ventana principal, recibe los datos del usuario
para determinar la dificultad del juego y carga la vista del juego en la misma ventana
 */
public class Controller {

    int filas, columnas, minas; //se definen variables para filas, columnas y minas


    //se establece conección con la interfaz gráfica
    @FXML
    private TextField filas_id;
    @FXML
    private TextField columnas_id;
    @FXML
    private TextField minas_id;
    @FXML
    private Button dimensionesButton;
    @FXML
    private AnchorPane rootPane;

    //acción sobre el botón fácil
    public void facil_action() throws IOException {
        activarBotones(false); //desactiva los botones personalizados en la vista
        filas = 8;                //asigna valores de nivel facil a filas columnas y minas
        columnas = 8;
        minas = 10;
        lanzarJuego2();           //se lanza el juego con los valores establecidos
    }

    //acción sobre el botón medio
    public void medio_action() throws IOException {
        activarBotones(false); //se desactivan los elementos de juego personalizado
        filas = 16;              //se establecen los valroes de filas, columnas y minas
        columnas = 16;
        minas = 40;
        lanzarJuego2();          //se inicia el juego con los valores de dificultad media
    }

    //acción sobre el botón difícil
    public void dificil_action() throws IOException {
        activarBotones(false); //se desactivan los componentes del juego personalizado
        filas = 16;              //se establecen los valores para filas, columnas y minas
        columnas = 30;
        minas = 99;
        lanzarJuego2();             //se lanza el juego con los valores para juego dificil
    }

    //acción sobre el botón personalizado
    public void personalizado_action() {
        activarBotones(true); //activa los campos y botón para dimensiones personalizadas
    }

    //activa o desactiva los botones de facil. medio y dificil
    public void activarBotones(boolean b) {
        //activa o desactiva los componentes de juego personalizado según el valor de b
        filas_id.setDisable(!b);
        columnas_id.setDisable(!b);
        minas_id.setDisable(!b);
        dimensionesButton.setDisable(!b);
    }

    //acción del botón iniciar (personalizado)
    public void iniciar_action() throws IOException {
        //todo----controlar y validar valores ingresados por el usuario

        //se establecen los valores ingresados por el usuario
        filas = Integer.parseInt(filas_id.getText());
        columnas = Integer.parseInt(columnas_id.getText());
        minas = Integer.parseInt(minas_id.getText());
        lanzarJuego2(); //se lanza el juego con los valores recibidos
    }

    //lanza el juego en una nueva ventana
    public void lanzarJuego(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/juego.fxml"));
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.setTitle("Buscaminas Beta v.1.0.0");
        primaryStage.show();
        Image ico = new Image("images/buscaminasicono.jpg");
        primaryStage.getIcons().add(ico);

        juegoController.setValores(filas, columnas, minas);
        Implementacion.setValores(filas, columnas, minas);
    }

    public void lanzarJuego2() throws IOException {
        //se carga en el panel principal el archivo de vista del juego (juego.fxml)
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/juego.fxml"));
        rootPane.getChildren().setAll(pane);
        //se asignan los valores de filas, columnas y minas en las clases definidas para
        //la generación de la matriz y el controlador de la vista del juego
        juegoController.setValores(filas, columnas, minas);
        Implementacion.setValores(filas, columnas, minas);
    }


}
