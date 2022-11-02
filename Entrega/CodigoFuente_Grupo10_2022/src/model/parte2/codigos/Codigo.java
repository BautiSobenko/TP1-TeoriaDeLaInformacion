package model.parte2.codigos;

import java.util.ArrayList;
import java.util.TreeSet;

public class Codigo {

    ArrayList<String> codigo;

    public Codigo(ArrayList<String> codigo) {
        this.codigo = codigo;
    }

    public static boolean esCompacto(float[] probabilidades, int tamPalabra){

        boolean isCompacto = true;
        int li;
        int i = 0;

        while( i < probabilidades.length && isCompacto){
            li = (int) Math.ceil(Math.log10(1/probabilidades[i]) / Math.log10(3));
            if( li != tamPalabra )
                isCompacto = false;
            i++;
        }

        return isCompacto;

    }

    public boolean esCodigoBloque(){
        // verifico que cada codigo dentro del array sea del mismo tamano
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

    public static double cantidadInformacion(double[] informaciones){
        double informacionFuente = 0;
        for (int i = 0; i < informaciones.length; i++) {
            informacionFuente += informaciones[i];
        }
        return informacionFuente;
    }

    public static double[] calculoInformacion( ArrayList<String> combinaciones, float[] probabilidades){
        double[] informaciones = new double[combinaciones.size()];

        for (int i = 0; i < probabilidades.length; i++) {
            informaciones[i] = Math.log10(1 / probabilidades[i]) / Math.log10(3);
        }

        return informaciones;

    }

    public static double calculoEntropia(double[] informaciones, float[] probabilidades){
        double entropia = 0;

        for (int i = 0; i < probabilidades.length; i++) {
            entropia += informaciones[i] * probabilidades[i];
        }

        return entropia;
    }

    public static double calculoLongMedia(float[] probabilidades, int tamanioPalabra){
        double sum = 0;

        for (float probabilidad : probabilidades) {
            sum += probabilidad;
        }

        return sum * tamanioPalabra;
    }

    public static double rendimiento(double entropia, double longitudMedia){
        return entropia / longitudMedia;
    }

    public static double redundancia(double rendimiento){
        return 1 - rendimiento;
    }


}
