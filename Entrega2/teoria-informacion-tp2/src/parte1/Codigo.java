package parte1;

import java.util.ArrayList;
import java.util.Map;

public class Codigo {

    private static int cantPalabras;
    private static int cantSimbolos;

    public Codigo(int cantPalabras, int cantSimbolos) {
        Codigo.cantPalabras = cantPalabras;
        Codigo.cantSimbolos = cantSimbolos;
    }

    public static double[] probabilidades(Map<String, Integer> frecPal){
        double[] probabilidades = new double[frecPal.size()];

        int i = 0;

        for (Map.Entry<String, Integer> item : frecPal.entrySet()) {
            probabilidades[i++] = (double) item.getValue() / Codigo.cantPalabras;
        }

        return probabilidades;
    }

    public static double[] calculoInformacion(double[] probabilidades){

        double[] informaciones = new double[probabilidades.length];

        int i = 0;

        for ( double probabilidad : probabilidades ) {
            informaciones[i++] = Math.log10(1 / probabilidad) / Math.log10(Codigo.cantSimbolos);
        }

        return informaciones;
    }

    public static double calculoEntropia(double[] informaciones, double[] probabilidades){
        double entropia = 0;

        for (int i = 0; i < probabilidades.length; i++) {
            entropia += informaciones[i] * probabilidades[i];
        }

        return entropia;
    }

    public static double rendimiento(double entropia, double longitudMedia){
        return entropia / longitudMedia;
    }

    public static double redundancia(double rendimiento){
        return 1 - rendimiento;
    }

    public static double calculoLongMedia(double[] probabilidades, Map<String, Integer> frecPal){
        double longMedia = 0;
        int i = 0;

        for (Map.Entry<String, Integer> item : frecPal.entrySet()) {
            longMedia += probabilidades[i++] * item.getKey().length();
        }

        return longMedia;
    }

    public static int getCantPalabras() {
        return cantPalabras;
    }

    public static void setCantPalabras(int cantPalabras) {
        Codigo.cantPalabras = cantPalabras;
    }

    public static int getCantSimbolos() {
        return cantSimbolos;
    }

    public static void setCantSimbolos(int cantSimbolos) {
        Codigo.cantSimbolos = cantSimbolos;
    }
}
