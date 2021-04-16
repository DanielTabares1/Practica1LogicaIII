package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

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
        int d = Implementacion.datoCasilla(f, c);

        switch (d) {
            case 0:
                //b.setDisable(true);
                //abrirCruz(f, c);

                //abrirPistas(f, c);
                break;
            case -1:
                panelPrincipal.setDisable(true);
                cerrar_id.setVisible(true);
                int[][] v = Implementacion.minas();
                for (int i = 1; i <= v.length; i++) {
                    Button boton = (Button) panelPrincipal.getChildren().get((v[i - 1][0] - 1) * columnas + v[i - 1][1] - 1);
                    boton.setText("KBOOM");
                    //boton.setDisable(true);
                    boton.setStyle("-fx-background-color: #a42200;-fx-text-fill: white; -fx-border-color: #000000");
                }
                break;
            default:
                b.setText(d + "");
                disenoBotonNumero(b);
                break;
        }
    }

    //se cambia el estilo del botón
    public void disenoBotonNumero(Button b) {
        b.setDisable(true);
        b.setStyle("-fx-background-color: #0f3b00; -fx-border-color: #000000; -fx-text-fill: white");
    }

    public void cerrar_action() {

    }

    public void abrirPistas(int f, int c) {
        int n = 8 - Implementacion.minasAlRededor(f, c);
        int[][] v = new int[n][2];
        v = localizacion(v, f, c);
        for (int i = 0; i < n; i++) {
            Button b = (Button) panelPrincipal.getChildren().get((v[i][0] - 1) * columnas + v[i][1] - 1);
            if (!b.isDisable()) {
                b.setDisable(true);
                if (Implementacion.minasAlRededor(v[i][0], v[i][1]) == 0) {
                    abrirPistas(v[i][0], v[i][1]);
                }
            }
        }

    }

    public int[][] localizacion(int[][] v, int fila, int columna) {
        int i = 0;
        if (Implementacion.m.existe(fila - 1, columna - 1) && (int) Implementacion.m.retornaTripleta(Implementacion.m.buscarPosicion(fila - 1, columna - 1)).retornaValor() != -1) {
            v[i][0] = fila - 1;
            v[i][1] = columna - 1;
            i++;
        }    //sup izq
        if (Implementacion.m.existe(fila - 1, columna) && (int) Implementacion.m.retornaTripleta(Implementacion.m.buscarPosicion(fila - 1, columna)).retornaValor() != -1) {
            v[i][0] = fila - 1;
            v[i][1] = columna;
            i++;
        }    //sup
        if (Implementacion.m.existe(fila - 1, columna + 1) && (int) Implementacion.m.retornaTripleta(Implementacion.m.buscarPosicion(fila - 1, columna + 1)).retornaValor() != -1) {
            v[i][0] = fila - 1;
            v[i][1] = columna + 1;
            i++;

        }   //sup der
        if (Implementacion.m.existe(fila, columna - 1) && (int) Implementacion.m.retornaTripleta(Implementacion.m.buscarPosicion(fila, columna - 1)).retornaValor() != -1) {
            v[i][0] = fila;
            v[i][1] = columna - 1;
            i++;
        }          //izq
        if (Implementacion.m.existe(fila, columna + 1) && (int) Implementacion.m.retornaTripleta(Implementacion.m.buscarPosicion(fila, columna + 1)).retornaValor() != -1) {
            v[i][0] = fila;
            v[i][1] = columna + 1;
            i++;

        }          //der
        if (Implementacion.m.existe(fila + 1, columna - 1) && (int) Implementacion.m.retornaTripleta(Implementacion.m.buscarPosicion(fila + 1, columna - 1)).retornaValor() != -1) {
            v[i][0] = fila + 1;
            v[i][1] = columna - 1;
            i++;

        }   //inf izq
        if (Implementacion.m.existe(fila + 1, columna) && (int) Implementacion.m.retornaTripleta(Implementacion.m.buscarPosicion(fila + 1, columna)).retornaValor() != -1) {
            v[i][0] = fila + 1;
            v[i][1] = columna;
            i++;

        }              //inf
        if (Implementacion.m.existe(fila + 1, columna + 1) && (int) Implementacion.m.retornaTripleta(Implementacion.m.buscarPosicion(fila + 1, columna + 1)).retornaValor() != -1) {
            v[i][0] = fila + 1;
            v[i][1] = columna + 1;
            i++;

        }   //inf der
        return v;
    }

    public void abrirCruz(int f, int c) {
        Button boton = (Button) panelPrincipal.getChildren().get(((f - 1) * columnas) + c - 1);
        boton.setDisable(true);
        int d = Implementacion.m.getDato(f, c);
        System.out.println(":::" + d);

        if (d == 0) {
            boton.setStyle("-fx-background-color: #4ee54e");

            if (f != 1) {
                int d1 = Implementacion.datoCasilla(f - 1, c);  //arriba
                Button boton1 = (Button) panelPrincipal.getChildren().get(d1);
                if (!boton1.isDisabled()) {
                    boton1.setDisable(true);
                    abrirCruz(f - 1, c);
                }
            }
            if (c != columnas) {
                int d2 = Implementacion.datoCasilla(f, c + 1);    //derecha
                Button boton2 = (Button) panelPrincipal.getChildren().get(d2);
                if (!boton2.isDisabled()) {
                    boton2.setDisable(true);
                    abrirCruz(f, c + 1);
                }
            }
            if (c != 1) {
                int d3 = Implementacion.datoCasilla(f, c - 1);    //izquierda
                Button boton3 = (Button) panelPrincipal.getChildren().get(d3);
                if (!boton3.isDisabled()) {
                    boton3.setDisable(true);
                    abrirCruz(f, c - 1);
                }
            }
            if (f != filas) {
                int d4 = Implementacion.datoCasilla(f + 1, c);    //abajo
                Button boton4 = (Button) panelPrincipal.getChildren().get(d4);
                if (!boton4.isDisabled()) {
                    boton4.setDisable(true);
                    abrirCruz(f + 1, c);
                }
            }

        } else {
            disenoBotonNumero(boton);
        }
    }


}
