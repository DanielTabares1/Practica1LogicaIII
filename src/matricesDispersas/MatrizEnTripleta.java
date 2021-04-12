package matricesDispersas;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import matricesDispersas.Tripleta;

/**
 * MatrizEnTripleta dispersa representada en forma de tripletas.
 *
 * @author camilo
 */
public class MatrizEnTripleta {

    private Tripleta v[], aux[];

    public MatrizEnTripleta(Tripleta t) {
        int n;
        n = (Integer) t.retornaValor();
        v = new Tripleta[n + 2];
        v[0] = t;
    }

    public MatrizEnTripleta(int f, int c) {
        Tripleta t = new Tripleta(f, c, 0);
        v = new Tripleta[2];
        v[0] = t;
    }

    public boolean esVacia() {
        Tripleta t = retornaTripleta(0);
        if ((Integer) t.retornaValor() == 0) {
            return true;
        }
        return false;
    }

    public int numeroFilas() {
        return v[0].retornaFila();
    }

    public int numeroColumnas() {
        return v[0].retornaColumna();
    }

    public int numeroTripletas() {
        return (int) v[0].retornaValor();
    }

    public Tripleta retornaTripleta(int i) {
        return v[i];
    }

    public void asignaNumeroTripletas(int datos) {
        System.out.println("Antiguo número de tripletas: " + numeroTripletas());
        System.out.println("Nuevo número de tripletas: " + datos);
        int antiguo;
        antiguo = numeroTripletas();
        aux = v;
        Tripleta ti, tj;
        ti = aux[0];
        tj = new Tripleta(ti.retornaFila(), ti.retornaColumna(), datos);
        v = new Tripleta[datos + 2];
        v[0] = tj;
        for (int i = 1; i < datos; i++) {
            v[i] = aux[i];
        }

    }

    /**
     * Muestra todos los elementos de la matriz.
     */
    public void muestraMatriz() {
        int p, f, c, i;
        double val;
        p = numeroTripletas();
        f = v[0].retornaFila();
        c = v[0].retornaColumna();
        val = (int) v[0].retornaValor();
        System.out.println("\n\nCabeza: " + Integer.toString(f) + ", " + Integer.toString(c) + ", " + Double.toString(val));
        for (i = 1; i <= p; i++) {
            if (v[i] == null) {
                System.err.println("Nulo en i: " + i);
                return;
            }
            f = v[i].retornaFila();
            c = v[i].retornaColumna();
            val = (Double) v[i].retornaValor();
            //Necesaria modificación para GUI.
            System.out.println(Integer.toString(f) + ", " + Integer.toString(c) + ", " + Double.toString(val));
        }
    }

    public void asignaTripleta(Tripleta t, int i) {
        if (t == null) {
            System.err.println("Tripleta vacía.");
            return;
        }
        /*
         if (i > numeroTripletas()) {
         System.err.println("No existen tantas tripletas como: " + i + ", n = " + numeroTripletas());
         return;
         }
         */
        int c = t.retornaColumna();
        int f = t.retornaFila();
        Tripleta nt;
        if (t.retornaValor() instanceof Double) {
            double val = (Double) t.retornaValor();
            nt = new Tripleta(f, c, val);
        } else {
            nt = new Tripleta(f, c, (Integer) t.retornaValor());
        }
        v[i] = nt;
    }

    /**
     * Inserta la tripleta t en su lugar correspondiente. La matriz está
     * ordenada por filas y luego por columnas de menor a mayor.
     *
     * Tripleta a insertar.
     */
    public void insertaTripleta(Tripleta ti) {
        int i, j, datos;
        Tripleta t, tx;
        tx = retornaTripleta(0);
        datos = (Integer) tx.retornaValor();
        i = 1;
        t = retornaTripleta(i);
        while (i <= datos && t.retornaFila() < ti.retornaFila()) {
            i = i + 1;
            t = retornaTripleta(i);
        }
        while (i <= datos && t.retornaFila() == ti.retornaFila() && t.retornaColumna() < ti.retornaColumna()) {
            i = i + 1;
            t = retornaTripleta(i);
        }
        j = datos;
        datos = datos + 1;
        asignaNumeroTripletas(datos);
        while (j >= i) {
            v[j + 1] = v[j];
            j = j - 1;
        }
        v[i] = ti;
    }

    private int comparar(int numero1, int numero2) {
        if (numero1 < numero2) {
            return -1;
        }
        if (numero1 > numero2) {
            return 1;
        }
        return 0;
    }
}
