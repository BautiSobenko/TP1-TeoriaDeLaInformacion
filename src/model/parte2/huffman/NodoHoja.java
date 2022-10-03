package model.parte2.huffman;

public class NodoHoja extends Nodo{

    private final String palabra;

    public NodoHoja(String palabra, int frecuencia) {
        super(frecuencia);
        this.palabra = palabra;
    }

    public String getPalabra() {
        return palabra;
    }
}
