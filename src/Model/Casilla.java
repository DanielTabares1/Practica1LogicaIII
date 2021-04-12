package Model;

public class Casilla {
    //Atributos
    int fila, col;
    boolean mina;

    //Constructor
    public Casilla(int fila, int col, boolean mina) {
        this.fila = fila;
        this.col = col;
        this.mina = mina;
    }
    //Getters & Setters
    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isMina() {
        return mina;
    }

    public void setMina(boolean mina) {
        this.mina = mina;
    }
}
