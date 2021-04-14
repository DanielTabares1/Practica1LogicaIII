package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class juegoController {

    @FXML
    private Pane panelPrincipal; //contenedor de los botones

    public double hPanel, wPanel, hBoton, wBoton;   //variables del tamaño de panel  y botones
    public static int minas, filas, columnas;       //filas, columnas y # de minas de la matriz

    //llena el panel con la cantidad de botones necesarios según las dimensiones de la matriz
    public void comenzar_action() {

        //se hacen calculos para el tamaño ideal de los botones según las dimensiones de la matriz
        hPanel = panelPrincipal.getHeight();
        wPanel = panelPrincipal.getWidth();
        hBoton = Math.floor(hPanel / filas);
        wBoton = Math.floor(wPanel / columnas);


        //se crean y muestran los botones
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Button b = new Button();
                b.setLayoutY(i * hBoton);
                b.setLayoutX(j * wBoton);
                b.setPrefWidth(wBoton);
                b.setPrefHeight(hBoton);
                b.setStyle("-fx-background-color: lightskyblue;" +
                        "-fx-border-color: black");
                panelPrincipal.getChildren().add(b);
                b.setOnAction(actionEvent -> {
                    int id = panelPrincipal.getChildren().indexOf(b);
                    click(id,b);
                });
            }
        }
    }

    //recibe y define los valores de filas, columnas y minas
    public static void setValores(int f, int c, int m) {
        filas = f;
        columnas = c;
        minas = m;
    }

    //detecta el click sobre una casilla y recibe el id del botón presionado
    public void click(int id, Button b) {
        int f = id / columnas + 1; //se calcula la fila del botón según su id
        int c = id % columnas + 1; //se calcula la columna del botón según su id
        int d = Implementacion.datoCasilla(f,c);

        switch (d){
            case 0:
                break;
            case -1:
                int[][] v = Implementacion.minas();
                for (int i = 1; i <= v.length ; i++) {
                    //panelPrincipal.getChildren().get(f*c-1);
                    System.out.println((v[i-1][0]-1)*columnas+v[i-1][1]-1);
                }
                break;
            default:
                b.setText(d+"");
                disenoBotonNumero(b);break;
        }
    }

    //se cambia el estilo del botón
    public void disenoBotonNumero(Button b){
        b.setDisable(true);
        b.setStyle("-fx-background-color: #7ed960; -fx-border-color: #000000");
    }

    public void disenoBotonMina(Button b){

    }


}
