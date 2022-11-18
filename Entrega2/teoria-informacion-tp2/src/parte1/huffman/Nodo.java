package parte1.huffman;

public class Nodo implements Comparable<Nodo>{

    private final int frecuencia;
    private Nodo izq = null;
    private Nodo der = null;

    public Nodo(Nodo izq, Nodo der) {
        this.frecuencia = izq.getFrecuencia() + der.getFrecuencia();
        this.izq = izq;
        this.der = der;
    }

    public Nodo(int frecuencia) {
        this.frecuencia = frecuencia;
        this.izq = null;
        this.der = null;
    }

    @Override
    public int compareTo(Nodo nodo) {
        return Integer.compare(this.frecuencia, nodo.getFrecuencia());
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public Nodo getIzq() {
        return izq;
    }

    public void setIzq(Nodo izq) {
        this.izq = izq;
    }

    public Nodo getDer() {
        return der;
    }

    public void setDer(Nodo der) {
        this.der = der;
    }
}
