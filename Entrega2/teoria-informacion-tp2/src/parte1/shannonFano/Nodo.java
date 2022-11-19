package parte1.shannonFano;

import java.util.ArrayList;
import java.util.Set;

public class Nodo {
    ArrayList<Character> caracteres ;
    double total;
    Nodo nodoIzq;
    Nodo nodoDer;

    Nodo(){}

    public ArrayList<Character> getCaracteres() {
        return caracteres;
    }

    public void setCaracteres(ArrayList<Character> caracteres) {
        this.caracteres = caracteres;
    }

    public Nodo getNodoIzq() {
        return nodoIzq;
    }

    public void setNodoIzq(Nodo nodoIzq) {
        this.nodoIzq = nodoIzq;
    }

    public Nodo getNodoDer() {
        return nodoDer;
    }

    public void setNodoDer(Nodo nodoDer) {
        this.nodoDer = nodoDer;
    }
}

