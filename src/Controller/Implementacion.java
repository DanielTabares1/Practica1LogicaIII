package Controller;

import matricesDispersas.MatrizEnTripleta;
import matricesDispersas.Tripleta;

import java.util.Random;

public class Implementacion {

    //se define un parámetro estático de tipo Matriz en tripletas para interactuar con él desde otras clases
    public static MatrizEnTripleta m;

    //se crean parámentros para filas, columnas y # de minas
    public static int minas, filas, columnas;

    //un método principal genera la matriz correspondiente
    public static void principal() {

        //se crea la tripleta principal para crear la matriz tomando los valores de filas y columnas
        Tripleta t = new Tripleta(filas, columnas, 0);
        m = new MatrizEnTripleta(t);

        //se crea un generador de números aleatorios
        Random r = new Random();

        //un ciclo se repite tantas veces como minas hay que generar
        for (int i = 0; i < minas; i++) {

            //se establece el rango para obtener el número según el número de filas y columnas ingresadas
            int f = r.nextInt(filas) + 1;
            int c = r.nextInt(columnas) + 1;

            //se crea una tripleta con los valores de f y c obtenidos,
            //y un valor de -1 que decidimos corresponderá a las minas
            Tripleta tx = new Tripleta(f, c, -1);

            //este ciclo verifica si ya hay un elemento con dichos valores de filas y columnas
            //en dicho caso vuelve a obtener los valores para no tener la posición repetida
            while (m.existe(f, c)) {
                f = r.nextInt(filas) + 1;
                c = r.nextInt(columnas) + 1;
                tx = new Tripleta(f, c, -1);
            }

            //se inserta la tripleta en la matriz
            m.insertaTripleta(tx);
        }
        // m.mostrarMatrizCuadricula(); se muestra la matriz en consola (en caso de querer verificar similitud
        //con la que se muestra en la interfaz gráfica

        //el siguiente ciclo crea los valores correspondientes a los números en el juego
        //para cada elemento no existente calcula la cantidad de minas alrededor y define una nueva tripleta
        //con este número como valor (siempre y cuando este sea diferente de cero)
        for (int i = 1; i <= filas; i++) {
            for (int j = 1; j <= columnas; j++) {
                if (!m.existe(i, j)) {
                    int n = minasAlRededor(i, j);
                    if (n != 0) {
                        Tripleta x = new Tripleta(i, j, n);
                        m.insertaTripleta(x);
                    }
                }
            }
        }
        //System.out.println("Matriz con todos los valores");
        //m.mostrarMatrizCuadricula(); //se puede mostrar la matriz en consola para verificar la igualdad con
        //la que se muestra en la interfaz gráfica
    }

    //método que cuenta la cantidad de minas al rededor de una casilla
    public static int minasAlRededor(int fila, int columna) {
        int suma = 0; //se define un valor inicial de suma en cero

        //para cada posición, se verifica si existe cada una de las posiciones alrededor de esta
        //además se verifica si dicho valor es "-1" (una mina), en dicho caso se suma 1 al valor de "suma"

        if (m.existe(fila - 1, columna - 1) && (int) m.retornaTripleta(m.buscarPosicion(fila - 1, columna - 1)).retornaValor() == -1) {
            suma++;
            //System.out.println("f:" + fila + "c:" + columna + "sup izq");
        }    //sup izq
        if (m.existe(fila - 1, columna) && (int) m.retornaTripleta(m.buscarPosicion(fila - 1, columna)).retornaValor() == -1) {
            suma++;
            //System.out.println("f:" + fila + "c:" + columna + "sup");
        }    //sup
        if (m.existe(fila - 1, columna + 1) && (int) m.retornaTripleta(m.buscarPosicion(fila - 1, columna + 1)).retornaValor() == -1) {
            suma++;
            //System.out.println("f:" + fila + "c:" + columna + "sup der");

        }   //sup der
        if (m.existe(fila, columna - 1) && (int) m.retornaTripleta(m.buscarPosicion(fila, columna - 1)).retornaValor() == -1) {
            suma++;
            //System.out.println("f:" + fila + "c:" + columna + "izq");
        }          //izq
        if (m.existe(fila, columna + 1) && (int) m.retornaTripleta(m.buscarPosicion(fila, columna + 1)).retornaValor() == -1) {
            suma++;
            //System.out.println("f:" + fila + "c:" + columna + "der");

        }          //der
        if (m.existe(fila + 1, columna - 1) && (int) m.retornaTripleta(m.buscarPosicion(fila + 1, columna - 1)).retornaValor() == -1) {
            suma++;
            //System.out.println("f:" + fila + "c:" + columna + "inf izq");

        }   //inf izq
        if (m.existe(fila + 1, columna) && (int) m.retornaTripleta(m.buscarPosicion(fila + 1, columna)).retornaValor() == -1) {
            suma++;
            //System.out.println("f:" + fila + "c:" + columna + "inf");

        }              //inf
        if (m.existe(fila + 1, columna + 1) && (int) m.retornaTripleta(m.buscarPosicion(fila + 1, columna + 1)).retornaValor() == -1) {
            suma++;
            //System.out.println("f:" + fila + "c:" + columna + "inf der");

        }   //inf der

        //retorna el valor resultante de suma
        return suma;
    }

    //recibe y define valores de fila, columna y minas, además invoca el método principal creando la matriz para el juego
    public static void setValores(int f, int c, int m) {
        filas = f;
        columnas = c;
        minas = m;
        principal();
    }

    //obtiene el dato de una casilla al pasarle las correspondientes fila y columna (con modificación en el retorno)
    public static int datoCasilla(int f, int c) {
        return m.getDato(f, c);
    }

    //obtiene el dato de una casilla al pasarle las correspondientes fila y columna
    public static int datoBoton(int f, int c) {
        return m.getDatoBoton(f, c);
    }

    //retorna un vector con los valores fila y columna de cada mina en la matriz
    public static int[][] minas() {
        int[][] v = new int[minas][2];  //crea un vector de dimensiones minas * 2
        Tripleta t;                     //se define una tripleta
        int contador = 0;               //se establece un contador en 0

        //para cada elemento(Tripleta) guardado en la matriz
        for (int i = 1; i <= m.numeroTripletas(); i++) {
            t = m.retornaTripleta(i);           //obtenemos dicha tripleta según su índice
            if ((int) t.retornaValor() == -1) { //si el valor es "-1"(mina)
                v[contador][0] = t.retornaFila();   //agregamos los valores fila y columna al vector
                v[contador][1] = t.retornaColumna();
                contador++;                         //aumentamos el contador en 1
            }
        }
        //retornamos el vector
        return v;
    }
}
