package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/*
Esta clase se encarga de controlar lo relacionado con la vista del juego en ejecución
y recibir las acciones del usuario en la interfaz
 */
public class juegoController {

    //se establece una relación con los componentes de la vista
    @FXML
    private Pane panelPrincipal;   //contenedor de los botones
    @FXML
    private Button cerrar_id;      //botón de cerrar
    @FXML
    private Button abrir_id;       //botón de abrir tablero
    @FXML
    private AnchorPane rootPane;   //contenedor principal (ventana)
    @FXML
    private Label label_id;

    //se definen valores generales para el tamaño de los botones y del panel principal
    public double hPanel, wPanel, hBoton, wBoton;

    //se definen variables para filas, columnas, # de minas y # de casillas desbloqueadas por el jugador
    public static int minas, filas, columnas, contador;

    /*
    Este método se encarga de crear los botones en el panel principal del juego
    los crea en forma de cuadrícula y el tamaño de cada uno se calcula deaecuerdo al
    número de filas y columnas de la matriz
     */
    public void comenzar_action() {
        //se desactiva el botón que genera los elementos de la cuadrícula
        abrir_id.setVisible(false);
        label_id.setText("¡Cuidado! hay " + minas + " minas.");

        //se hacen calculos para el tamaño ideal de los botones según las dimensiones de la matriz
        hPanel = panelPrincipal.getHeight();
        wPanel = panelPrincipal.getWidth();
        hBoton = Math.floor(hPanel / filas);
        wBoton = Math.floor(wPanel / columnas);


        //dos ciclos crean y sitúan cada botón en la posición correcta del panel principal
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Button b = new Button();   //se crea un botón nuevo
                b.setMinSize(0, 0);   //su tamaño mínimo se define en cero
                b.setLayoutY(i * hBoton);  //se define su posición y tamaño deacuerdo a los
                b.setLayoutX(j * wBoton);  //valores hallados anteriormente
                b.setPrefWidth(wBoton);
                b.setPrefHeight(hBoton);
                //se define un estilo(color de fondo y borde)
                b.setStyle("-fx-background-color: #87cefa;" +
                        "-fx-border-color: black");

                panelPrincipal.getChildren().add(b); //se agrega al panel principal del juego

                //y se agrega una acción pre definida para tomar al momento de recibir un click
                b.setOnAction(actionEvent -> {
                    int id = panelPrincipal.getChildren().indexOf(b); //se obtien el id del elemento
                    //en el panel
                    click(id, b); //se llama la función click, tomando el id y el botón mismo
                });
            }
        }
    }

    //recibe y define los valores de filas, columnas y minas
    public static void setValores(int f, int c, int m) {
        filas = f;
        columnas = c;
        minas = m;
        contador = f * c;
    }

    /*
    este método es llamado al hacer click sobre uno de los botones(casillas) del juego
    tomando dicho botón y su id se realizan cálculos para mostrar un resultado diferente
    dependiendo del valor que a este le corresponda en la matriz dispersa que controla
    el juego
     */
    public void click(int id, Button b) {
        int f = id / columnas + 1; //se calcula la fila del botón según su id
        int c = id % columnas + 1; //se calcula la columna del botón según su id

        //se obtiene el dato correspondiente en la matriz dispersa según la fila y columna del botón
        int d = Implementacion.datoCasilla(f, c);

        //casos del dato recién tomado
        switch (d) {
            case 0:      //caso cero (el elemento no está en la matriz)
                disenoBotonCero(b); //se le da al botón un diseño particular de lso elementos sin
                //algún tipo de dato particular en el juego (sin mina y sin número)

                //se llama un método particular y recursivo que buscará los elementos que rodean
                //dicha casilla descubriendo tanto números como otros "ceros"
                abrirCeros(f, c);

                //se actualiza el contador de casillas abiertas para determinar si se acaba el juego
                revisarContador();
                break;
            case -1:   //caso -1 (mina)
                panelPrincipal.setDisable(true); //se desactiva totalmente el panel del juego
                cerrar_id.setVisible(true);      //se activa un botón para cerrar la ventana

                //se crea un vector que guardará para cada mina en el juego sus datos de fila y columna
                int[][] v = Implementacion.minas();

                //un ciclo for realiza el mismo proceso para cada mina
                for (int i = 1; i <= v.length; i++) {
                    //se obtiene el botón de la posición especificada
                    Button boton = (Button) panelPrincipal.getChildren().get((v[i - 1][0] - 1) * columnas + v[i - 1][1] - 1);

                    //se establece un ícono para el botón que representa la mina
                    Image image = new Image("images/Bomba.png", hBoton, hBoton, false, true);
                    boton.setGraphic(new ImageView(image));
                    boton.setPadding(Insets.EMPTY);
                    boton.setAlignment(Pos.CENTER);

                    //se le da un estilo característico(color rojo) para hacer visible en pantalla todas las minas
                    boton.setStyle("-fx-background-color: #c30000; -fx-border-color: #000000");
                }

                //se crea y lanza un mensaje de alerta que indica el fin del juego
                Alert a = new Alert(Alert.AlertType.INFORMATION, "¡Lo sentimos!\nHas perdido la partida unu\nVuelve a intentarlo");
                a.setTitle("Usted ha pisado una mina");
                a.setHeaderText("KBOOOOOM!!!");
                ImageView ganar = new ImageView("/images/perdedor.png");
                // The standard Alert icon size is 48x48, so let's resize our icon to match
                ganar.setFitHeight(48);
                ganar.setFitWidth(48);
                // Set our new ImageView as the alert's icon
                a.getDialogPane().setGraphic(ganar);
                Image ico = new Image("images/buscaminasicono.jpg");
                Stage stage = (Stage) a.getDialogPane().getScene().getWindow();
                stage.getIcons().add(ico);
                a.show();
                break;
            default: //caso default (un número)
                b.setText(d + "");    //asigna al texto del botón el número correspondiente (cantidad de minas al rededor)
                disenoBotonNumero(b); //asigna un diseño específico a este tipo de elemento en el juego
                revisarContador();    //y revisa la cantidad de casillas abiertas para determinar si el juego termina
                break;
        }

    }

    //cambia el estilo del botón pulsado para el caso de número
    public void disenoBotonNumero(Button b) {
        contador--;            //se resta en 1 el contador de casillas abiertas
        b.setDisable(true);    //se descativa el botón para prevenir pulsarlo más de una vez
        String htext = (int) Math.floor(hBoton / 2) + ""; //se calcula la altura correcta del texto interior

        //se le da un color verde claro en el fondo, bordes y texto negros y una fuente gruesa para ver mejor el número
        String style = "-fx-background-color: #9cff81; -fx-font-family: 'Segoe UI Black'; -fx-border-color: #000000; -fx-text-fill: black; -fx-font-size: " + htext;
        b.setStyle(style);
    }

    //cambia el estilo del botón pulsado para el caso de cero
    public void disenoBotonCero(Button b) {
        contador--;  //se resta en 1 el contador de minas abiertas
        b.setDisable(true); //se descativa el botón
        //se le da un fondo verde claro y bordes negros
        b.setStyle("-fx-background-color: #9cff81; -fx-border-color: #000000");
    }

    /*
    Este método se encarga de tomar la acción sobre el botón cerrar y cargar nuevamente la vista principal
    en la misma ventana
     */
    public void cerrar_action() throws IOException {
        //obtiene el archivo de la vista principal (sample.fxml) y lo carga en un panel
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/sample.fxml"));
        rootPane.getChildren().setAll(pane); //muestra la vista sobre el contenedor principal de la ventana
    }

    /*
    este método toma la posición del botón pulsado y encuentra para cada elemento a su alrededor el valor
    correspondiente, luego si este es diferente de cero muestra dicho valor
    pero si este es un cero, se llama el mísmo  método de forma recursiva, revelando el "espacio vacío"
    en el que esta casilla estaba contenida, al igual que en el juego original de buscaminas
     */
    public void abrirCeros(int f, int c) {
        Button b; //se crea un botón

        //para cada uno de los elementos al rededor del que recibió el click, se controla que sí exista y
        if (f != 1 && c != 1) { //sup izq
            //se obtiene el botón según su posición de filas y columnas
            b = (Button) panelPrincipal.getChildren().get(indiceDe(f - 1, c - 1));

            //si el elemento no está desactivado (no ha sido clickeado o actualizado de alguna manera)
            if (!b.isDisable()) {
                //se obtiene el dato correspondiente en la matriz
                int dato = Implementacion.datoCasilla(f - 1, c - 1);

                //si el dato es diferente de cero
                if (dato != 0) {
                    //se aplica el diseño correspondiente y se actualzia su texto interior
                    disenoBotonNumero(b);
                    b.setText(dato + "");
                } else { //si el dato es un cero (no hay mina ni número)
                    disenoBotonCero(b); //se aplica un diseño específico

                    // se llama de forma recursiva este mismo método dando el comportamiento
                    //idéntico del juego original cuando se encuentra una casilla vacía
                    abrirCeros(f - 1, c - 1);
                }
            }
        }
        //los demás if en este método realizan lo mismo para las demás posiciones al rededor del botón pulsado

        if (f != 1) { //sup
            b = (Button) panelPrincipal.getChildren().get(indiceDe(f - 1, c));
            if (!b.isDisable()) {
                int dato = Implementacion.datoCasilla(f - 1, c);
                if (dato != 0) {
                    disenoBotonNumero(b);
                    b.setText(dato + "");
                } else {
                    disenoBotonCero(b);
                    abrirCeros(f - 1, c);
                }
            }
        }
        if (f != 1 && c != columnas) { //sup der
            b = (Button) panelPrincipal.getChildren().get(indiceDe(f - 1, c + 1));
            if (!b.isDisable()) {
                int dato = Implementacion.datoCasilla(f - 1, c + 1);
                if (dato != 0) {
                    disenoBotonNumero(b);
                    b.setText(dato + "");
                } else {
                    disenoBotonCero(b);
                    abrirCeros(f - 1, c + 1);
                }
            }
        }
        if (c != 1) { //izq
            b = (Button) panelPrincipal.getChildren().get(indiceDe(f, c - 1));
            if (!b.isDisable()) {
                int dato = Implementacion.datoCasilla(f, c - 1);
                if (dato != 0) {
                    disenoBotonNumero(b);
                    b.setText(dato + "");
                } else {
                    disenoBotonCero(b);
                    abrirCeros(f, c - 1);
                }
            }
        }
        if (c != columnas) { //der
            b = (Button) panelPrincipal.getChildren().get(indiceDe(f, c + 1));
            if (!b.isDisable()) {
                int dato = Implementacion.datoCasilla(f, c + 1);
                if (dato != 0) {
                    disenoBotonNumero(b);
                    b.setText(dato + "");
                } else {
                    disenoBotonCero(b);
                    abrirCeros(f, c + 1);
                }
            }
        }
        if (f != filas && c != 1) { //inf izq
            b = (Button) panelPrincipal.getChildren().get(indiceDe(f + 1, c - 1));
            if (!b.isDisable()) {
                int dato = Implementacion.datoCasilla(f + 1, c - 1);
                if (dato != 0) {
                    disenoBotonNumero(b);
                    b.setText(dato + "");
                } else {
                    disenoBotonCero(b);
                    abrirCeros(f + 1, c - 1);
                }
            }
        }
        if (f != filas) { //inf
            b = (Button) panelPrincipal.getChildren().get(indiceDe(f + 1, c));
            if (!b.isDisable()) {
                int dato = Implementacion.datoCasilla(f + 1, c);
                if (dato != 0) {
                    disenoBotonNumero(b);
                    b.setText(dato + "");
                } else {
                    disenoBotonCero(b);
                    abrirCeros(f + 1, c);
                }
            }
        }
        if (f != filas && c != columnas) { //inf der
            b = (Button) panelPrincipal.getChildren().get(indiceDe(f + 1, c + 1));
            if (!b.isDisable()) {
                int dato = Implementacion.datoCasilla(f + 1, c + 1);
                if (dato != 0) {
                    disenoBotonNumero(b);
                    b.setText(dato + "");
                } else {
                    disenoBotonCero(b);
                    abrirCeros(f + 1, c + 1);
                }
            }
        }
    }

    //este método obtiene el índice de un botón deacuerdo a sus fila y columna
    public int indiceDe(int f, int c) {
        return ((f - 1) * columnas) + (c - 1);
    }

    /*
    este método compara el número de casillas abiertas y lo compara con el número de minas
    para así determinar si el usuario ya ha abierto todas las posibles casillas diferentes de
    minas y dar el mensaje de victoria terminando con el juego
     */
    public void revisarContador() {
        //si el valor de minas es igual al del contador
        if (minas == contador) {
            panelPrincipal.setDisable(true); //se descativa el panel principal del juego
            cerrar_id.setVisible(true);      //se activa el botón para cerrar la ventana (volver a la vista principal)
            int[][] v = Implementacion.minas();//se obtiene un vector con los valores fila y columna de cada mina

            //para cada elemento del vector, se halla el botón correspondiente y se cambia su estilo
            for (int i = 1; i <= v.length; i++) {
                Button boton = (Button) panelPrincipal.getChildren().get((v[i - 1][0] - 1) * columnas + v[i - 1][1] - 1);
                //se cambia su color de fondo y bordes
                boton.setStyle("-fx-background-color: #ff6600; -fx-border-color: #000000");

                //y se establece la imagen que representa las minas
                Image image = new Image("images/Bomba.png", hBoton, hBoton, false, true);
                boton.setGraphic(new ImageView(image));
                boton.setPadding(Insets.EMPTY);
                boton.setAlignment(Pos.CENTER);
            }

            //se crea y muestra en pantalla un mensaje de vistoria
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Has ganado la partida uwu");
            a.setTitle("¡Buen Juego!");
            a.setHeaderText("Ganador!");
            ImageView ganar = new ImageView("/images/ganador.png");
            // The standard Alert icon size is 48x48, so let's resize our icon to match
            ganar.setFitHeight(48);
            ganar.setFitWidth(48);
            // Set our new ImageView as the alert's icon
            a.getDialogPane().setGraphic(ganar);
            Image ico = new Image("images/buscaminasicono.jpg");
            Stage stage = (Stage) a.getDialogPane().getScene().getWindow();
            stage.getIcons().add(ico);
            a.show();
        }
    }

}
