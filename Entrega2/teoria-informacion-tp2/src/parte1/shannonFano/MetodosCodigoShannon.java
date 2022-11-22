package parte1.shannonFano;

import java.io.File;
import java.util.Map;


public class MetodosCodigoShannon {

    private static int cantPalabras;
    private static int cantSimbolos;

    public MetodosCodigoShannon(int cantPalabras, int cantSimbolos) {
        MetodosCodigoShannon.cantPalabras = cantPalabras;
        MetodosCodigoShannon.cantSimbolos = cantSimbolos;
    }


    public static double[] calculoInformacion(double[] probabilidades){

        double[] informaciones = new double[probabilidades.length];

        int i = 0;

        for ( double probabilidad : probabilidades ) {
            informaciones[i++] = Math.log10(1 / probabilidad) / Math.log10(MetodosCodigoShannon.cantSimbolos);
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

    public static double getTasaCompresion(){

        File fileOriginal = new File("DatosTP2.txt");
        double tamOriginal = fileOriginal.length();

        File fileShannon = new File("resultados/primera-parte/compresion.fan");
        double tamShannon = fileShannon.length();

        return tamOriginal / tamShannon;

    }

    public static void setCantSimbolos(int cantSimbolos) {
        MetodosCodigoShannon.cantSimbolos = cantSimbolos;
    }
}

