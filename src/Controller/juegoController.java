package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import matricesDispersas.MatrizEnTripleta;
import matricesDispersas.Tripleta;

public class juegoController {

    @FXML
    private Pane panelPrincipal; //contenedor de los botones
    @FXML
    private Button cerrar_id;
    @FXML
    private Button abrir_id;

    public double hPanel, wPanel, hBoton, wBoton;   //variables del tamaño de panel  y botones
    public static int minas, filas, columnas;       //filas, columnas y # de minas de la matriz

    //llena el panel con la cantidad de botones necesarios según las dimensiones de la matriz
    public void comenzar_action() {

        abrir_id.setVisible(false);

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
                b.setStyle("-fx-background-color: #87cefa;" +
                        "-fx-border-color: black");
                panelPrincipal.getChildren().add(b);
                b.setOnAction(actionEvent -> {
                    int id = panelPrincipal.getChildren().indexOf(b);
                    click(id, b);
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
        int d = Implementacion.datoCasilla(f, c);   //Todo corregir d porque arroja un dato equivocado para la posicion (filas, columnas)



        if (d==-1){
            int[][] v = Implementacion.minas();
            for (int[] ints : v) {
                Button boton = (Button) panelPrincipal.getChildren().get(deCordenadaAId(ints[0], ints[1], columnas));
                boton.setText("KBOOM");
                boton.setStyle("-fx-background-color: #a42200;-fx-text-fill: white; -fx-border-color: #000000");
                boton.setDisable(true);
            }
            panelPrincipal.setDisable(true);
            cerrar_id.setVisible(true);
        }
        else if (d==0){
            b.setDisable(true);
            disenoBotonNumero(b);
            int libres = diferentesdeMina(id, f, c);
            MatrizEnTripleta w = obtenerCasillasAlrededor(f, c, libres);
            for (int i = 1; i <= w.numeroTripletas(); i++) {
                Tripleta z = w.retornaTripleta(i);
                Button u = (Button) panelPrincipal.getChildren().get((int) z.retornaValor());
                if (!u.isDisable()) {
                    click(panelPrincipal.getChildren().indexOf(u), u);
                }
            }
        }
        else {
            b.setText(d + "");
            disenoBotonNumero(b);
        }
    }
    //se cambia el estilo del botón
    public void disenoBotonNumero(Button b) {
        b.setDisable(true);
        b.setStyle("-fx-background-color: #0f3b00; -fx-border-color: #000000; -fx-text-fill: white");
    }
    public void cerrar_action () {}
    public MatrizEnTripleta obtenerCasillasAlrededor ( int posFila, int posColumna, int diferentesdeCero){
        Tripleta t = new Tripleta(diferentesdeCero, 2, 0);
        MatrizEnTripleta libres = new MatrizEnTripleta(t);
        for (int i = 0; i < 8; i++) {
            int tmpPosFila = posFila;
            int tmpPosColumna = posColumna;
            switch (i) {
                case 0:
                    tmpPosFila--;
                    tmpPosColumna--;
                    break; //Izquierda Arriba
                case 1:
                    tmpPosFila--;
                    break; //Arriba
                case 2:
                    tmpPosFila--;
                    tmpPosColumna++;
                    break; //Arriba Derecha
                case 3:
                    tmpPosColumna--;
                    break; //Izquierda
                case 4:
                    tmpPosColumna++;
                    break; //Derecha
                case 5:
                    tmpPosFila++;
                    tmpPosColumna--;
                    break; //Abajo Izquierda
                case 6:
                    tmpPosFila++;
                    break; //Abajo
                case 7:
                    tmpPosColumna++;
                    tmpPosFila++;
                    break; //Derecha Abajo
            }
            if (tmpPosFila > 0 && tmpPosFila <= filas
                    && tmpPosColumna > 0 && tmpPosColumna <= columnas) {
                Tripleta w = new Tripleta(tmpPosFila, tmpPosColumna, deCordenadaAId(tmpPosFila, tmpPosColumna, columnas));
                libres.insertaTripleta(w);
            }

        }
        return libres;
    }
    public int deCordenadaAId ( int fila, int columna, int totalcolumnas){
        return (fila - 1) * totalcolumnas + columna - 1;
    }
    public int diferentesdeMina ( int id, int fila, int columna){
        int f = id / columnas + 1; //se calcula la fila del botón según su id
        int c = id % columnas + 1; //se calcula la columna del botón según su id
        int i = 0;
        if (!(fila - 1 <= 0 || columna - 1 <= 0 || fila - 1 > filas || columna - 1 > columnas) && Implementacion.datoCasilla(f - 1, c - 1) != -1) {
            i++;
        }    //sup izq
        if (!(fila - 1 <= 0 || columna <= 0 || fila - 1 > filas || columna > columnas) && Implementacion.datoCasilla(f - 1, c) != -1) {
            i++;
        }    //sup
        if (!(fila - 1 <= 0 || columna + 1 <= 0 || fila - 1 > filas || columna + 1 > columnas) && Implementacion.datoCasilla(f - 1, c + 1) != -1) {
            i++;

        }   //sup der
        if (!(fila <= 0 || columna - 1 <= 0 || fila > filas || columna - 1 > columnas) && Implementacion.datoCasilla(f, c - 1) != -1) {
            i++;
        }          //izq
        if (!(fila <= 0 || columna + 1 <= 0 || fila > filas || columna + 1 > columnas) && Implementacion.datoCasilla(f, c + 1) != -1) {
            i++;

        }          //der
        if (!(fila + 1 <= 0 || columna - 1 <= 0 || fila + 1 > filas || columna - 1 > columnas) && Implementacion.datoCasilla(f + 1, c - 1) != -1) {
            i++;

        }   //inf izq
        if (!(fila + 1 <= 0 || columna <= 0 || fila + 1 > filas || columna > columnas) && Implementacion.datoCasilla(f + 1, c) != -1) {
            i++;

        }              //inf
        if (!(fila + 1 <= 0 || columna + 1 <= 0 || fila + 1 > filas || columna + 1 > columnas) && Implementacion.datoCasilla(f + 1, c + 1) != -1) {
            i++;

        }   //inf der
        return i;
    }
}




