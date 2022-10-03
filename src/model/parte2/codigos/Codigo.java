package model.parte2.codigos;

import java.util.ArrayList;
import java.util.TreeSet;

public class Codigo {

    ArrayList<String> codigo;

    public Codigo(ArrayList<String> codigo) {
        this.codigo = codigo;
    }


    public boolean esCodigoBloque(){
        // verifo que cada codigo dentro del array sea del mismo tamano
        // si cumple la condicion devuelvo true.

        int length = this.codigo.get(0).length();
        for (String s : this.codigo) {
            if (length != s.length()) {
                return false;
            }
        }
        return true;
    }

    public boolean esNoSingular() {
        // Verifico el tamano del tree set con el codigo original
        // como en el tree set no puede haber elementos repetidos
        // si el tamanio es igual es pq es no singular

        int i = 0, length = this.codigo.size();
        TreeSet<String> noRepetidos = new TreeSet<String>(this.codigo);

        return noRepetidos.size() == this.codigo.size();
    }

    public boolean esUnivocamenteDecodificable(){
        return this.esCodigoBloque() && this.esNoSingular();
    }

    public boolean esInstantaneo() {

        for (String palabra : this.codigo) {
            ArrayList<String> prefijos = this.obtenerPrefijos(palabra);
            //Chequea cada prefijo con todas las palabras
            for (String prefijo : prefijos){
                for (String s: this.codigo){
                    if (s.equals(prefijo) ){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private ArrayList<String> obtenerPrefijos( String palabra){
        ArrayList<String> prefijos = new ArrayList<>();
        for (int i = 0; i < palabra.length(); i++) {
            prefijos.add(palabra.substring(0, i + 1));
        }
        // Elimino el ultimo elemento que seria la palabra completa.
        prefijos.remove(prefijos.size()-1);
        return prefijos;
    }
}
