package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/*
Esta clase controla lo relacionado a la ventana principal, recibe los datos del usuario
para determinar la dificultad y carga la vista del juego en la misma ventana
 */
public class Controller {

    int filas, columnas, minas; //se definen variables para filas, columnas y minas

    //se establece conección con los componentes de la interfaz gráfica
    @FXML
    private TextField filas_id;          //campo de texto para filas personalizadas
    @FXML
    private TextField columnas_id;       //campo de texto para columnas personalizadas
    @FXML
    private TextField minas_id;          //campo de texto para minas personalizadas
    @FXML
    private Button dimensionesButton;   //botón para iniciar el juego personalizado
    @FXML
    private AnchorPane rootPane;        //contenedor principal(ventana)

    //método que detecta click sobre el botón fácil
    public void facil_action() throws IOException {
        activarBotones(false); //desactiva los botones personalizados en la vista
        filas = 8;                //asigna los valores de dificultad facil a filas columnas y minas
        columnas = 8;
        minas = 10;
        lanzarJuego2();           //se lanza el juego con los valores establecidos
    }

    //método que detecta click sobre el botón medio
    public void medio_action() throws IOException {
        activarBotones(false); //se desactivan los elementos de juego personalizado
        filas = 16;              //se establecen los valoresde dificultad media para filas, columnas y minas
        columnas = 16;
        minas = 40;
        lanzarJuego2();          //se inicia el juego con los valores de dificultad media
    }

    //método que detecta acción sobre el botón difícil
    public void dificil_action() throws IOException {
        activarBotones(false); //se desactivan los componentes del juego personalizado
        filas = 16;               //se establecen los valores de dificultad dificil para filas, columnas y minas
        columnas = 30;
        minas = 99;
        lanzarJuego2();           //se lanza el juego con los valores para juego dificil
    }

    //método que detecta acción sobre el botón personalizado
    public void personalizado_action() {
        activarBotones(true); //activa los campos y botón para dimensiones personalizadas
    }

    //método que activa o desactiva los botones de facil, medio y dificil
    public void activarBotones(boolean b) {
        //activa o desactiva los componentes de juego personalizado según el valor de b (true o false)
        filas_id.setDisable(!b);
        columnas_id.setDisable(!b);
        minas_id.setDisable(!b);
        dimensionesButton.setDisable(!b);
    }

    //método que detecta acción sobre botón iniciar (personalizado)
    public void iniciar_action() throws IOException {
        //todo----controlar y validar valores ingresados por el usuario

        //se establecen los valores ingresados por el usuario
        //con un try - catch se controla que los valores ingresados sean numéricos
        try {
            filas = Integer.parseInt(filas_id.getText());
            columnas = Integer.parseInt(columnas_id.getText());
            minas = Integer.parseInt(minas_id.getText());
        } catch (NumberFormatException e) {
            //si se detecta un formato diferente se muestra un mensaje de información
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Los valores ingresados deben ser numéricos.");
            alert.setHeaderText("Formato de dato incorrecto");
            alert.show();
        }

        //se establecen valores mínimos y máximos de filas, columnas y minas para asegurar una buena jugabilidad
        if (filas < 3 || columnas < 3 || filas > 20 || columnas > 32 || minas < 5) {
            //si se sobrepasan dichos límites se muestra un mensaje informativo
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Los datos deben estar dentro de los siguientes límites:" +
                    "\nFilas: entre 3 y 20\nColumnas: entre 3 y 32\nminas: mínimo 5");
            alert.show();
        } else {
            //para la cantidad máxima de minas se establece que debe haber almenos una casilla que no sea mina
            if (minas > (filas * columnas) - 1) { //si la cantidad de minas sobrepasa el límite
                int valor = (filas * columnas) - 1;
                //se muestra un mensaje de alerta con el valor máximo posible para las dimensiones especificadas
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "El valor máximo de minas para las dimensiones\n" +
                        "ingresadas es de " + valor);
                alert.show();
            } else { //si no hay problema con las condiciones establecidas se lanza el juego
                lanzarJuego2();
            }
        }
    }

    //*****************POSIBLEMENTE SOBRA******************
    //método que lanza el juego en una nueva ventana
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

    //método que lanza el juego carganado la escena en la misma ventana
    public void lanzarJuego2() throws IOException {
        //se carga en un nuevo panel el archivo de vista del juego (juego.fxml)
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/juego.fxml"));

        //se muestra el panel recién creado en el contenedor principal de la ventana
        rootPane.getChildren().setAll(pane);

        //se carga en la clase que controla el juego los valores de filas, columnas y minas
        juegoController.setValores(filas, columnas, minas);

        //se carga en la clase encargada de crear la matriz los valores de filas, columnas y minas
        Implementacion.setValores(filas, columnas, minas);
    }


}
