package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.awt.*;


public class juegoController {

    @FXML
    private Pane panelPrincipal;

    public double hPanel, wPanel, hBoton, wBoton;
    public static int minas, filas, columnas;

    public void comenzar_action() {
        hPanel = panelPrincipal.getHeight();
        wPanel = panelPrincipal.getWidth();

        hBoton = Math.floor(hPanel / filas);
        wBoton = Math.floor(wPanel / columnas);

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
                    click(id);
                });
            }
        }
    }

    public static void setValores(int f, int c, int m) {
        filas = f;
        columnas = c;
        minas = m;
    }

    public void click(int id) {
        int f = id / columnas + 1;
        int c = id % columnas + 1;



    }

    public  void disenoBoton(Button b){
        b.setDisable(true);
        b.setStyle("-fx-background-color: indianred;" +
                "-fx-border-color: black");
    }

}
