import Model.Casilla;
import matricesDispersas.MatrizEnTripleta;
import matricesDispersas.Tripleta;

import java.util.Random;
import java.util.Vector;

public class Implementacion {

    public static void main(String[] args) {

        // se reciben datos y crea matriz
            //todo--------------recibir datos de la interfaz

            /*
            después de recibir los datos creamos el tablero y se pueden presentar los siguientes casos:
            -si oprime una celda con mina pierde, se muestra el tablero completo
            -una celda con cero revela todas las celdas con cero a su alrededor y los numeros adyacentes a estas
            -una celda con numero se muestra ella solita
            -todas las celdas con numero están destapadas => gana, se muestra el tablero...
             */

        int filas = 8, columnas = 8, minas = 10;
        Tripleta t = new Tripleta(filas, columnas, 0);
        MatrizEnTripleta m = new MatrizEnTripleta(t);
        Random r = new Random();

        //se generan las minas de forma aleatoria
        for (int i = 0; i < minas; i++) {
            int f = r.nextInt(filas) + 1;
            int c = r.nextInt(columnas) + 1;
            Tripleta tx = new Tripleta(f, c, -1);

            while (m.existe(f, c)) {
                f = r.nextInt(filas) + 1;
                c = r.nextInt(columnas) + 1;
                tx = new Tripleta(f, c, -1);
            }
            m.insertaTripleta(tx);
        }
        m.mostrarMatrizCuadricula();

        //se actualizan las posiciones adyacentes a las minas
        for (int i = 1; i <= filas; i++) {
            for (int j = 1; j <= columnas; j++) {
                if (!m.existe(i, j)) {
                    int n = minasAlRededor(m, i, j);
                    if (n != 0) {
                        Tripleta x = new Tripleta(i, j, n);
                        m.insertaTripleta(x);
                    }
                }
            }
        }
        System.out.println("Matriz con todos los valores");
        m.mostrarMatrizCuadricula();

        //lo siguiente es: el juego
    }

    //método que cuenta la cantidad de minas al rededor de una casilla
    public static int minasAlRededor(MatrizEnTripleta m, int fila, int columna) {
        int suma = 0;
        if (m.existe(fila - 1, columna - 1) && (int) m.retornaTripleta(m.buscarPosicion(fila-1,columna-1)).retornaValor() == -1 ) {
            suma++;
        }    //sup izq
        if (m.existe(fila - 1, columna) && (int) m.retornaTripleta(m.buscarPosicion(fila-1,columna)).retornaValor() == -1 ) {
            suma++;
        }    //sup
        if (m.existe(fila - 1, columna + 1) && (int) m.retornaTripleta(m.buscarPosicion(fila-1,columna+1)).retornaValor() == -1 ) {
            suma++;
        }   //sup der
        if (m.existe(fila, columna - 1) && (int) m.retornaTripleta(m.buscarPosicion(fila,columna-1)).retornaValor() == -1 ) {
            suma++;
        }          //izq
        if (m.existe(fila, columna + 1) && (int) m.retornaTripleta(m.buscarPosicion(fila,columna+1)).retornaValor() == -1 ) {
            suma++;
        }          //der
        if (m.existe(fila + 1, columna - 1) && (int) m.retornaTripleta(m.buscarPosicion(fila+1,columna-1)).retornaValor() == -1 ) {
            suma++;
        }   //inf izq
        if (m.existe(fila + 1, columna) && (int) m.retornaTripleta(m.buscarPosicion(fila+1,columna)).retornaValor() == -1 ) {
            suma++;
        }              //inf
        if (m.existe(fila + 1, columna + 1) && (int) m.retornaTripleta(m.buscarPosicion(fila+1,columna+1)).retornaValor() == -1 ) {
            suma++;
        }   //inf der
        return suma;
    }

}
