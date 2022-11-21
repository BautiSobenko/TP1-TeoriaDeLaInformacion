package parte1.huffman;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MetodosCodigoHuffman {

    private static int cantPalabras;
    private static int cantSimbolos;

    public MetodosCodigoHuffman(int cantPalabras, int cantSimbolos) {
        MetodosCodigoHuffman.cantPalabras = cantPalabras;
        MetodosCodigoHuffman.cantSimbolos = cantSimbolos;
    }

    public static Map<String, Double> probabilidades(Map<String, Integer> frecPal){
        Map<String, Double> prob = new HashMap<>();

        for (Map.Entry<String, Integer> frec : frecPal.entrySet()) {
            prob.put( frec.getKey() , (double) frec.getValue() / MetodosCodigoHuffman.cantPalabras );
        }

        return prob;
    }

    public static Map<String, Double> calculoInformacion(Map<String, Double> probabilidades){

        Map<String, Double> informaciones = new HashMap<>();

        for (Map.Entry<String, Double> prob : probabilidades.entrySet()) {
            informaciones.put( prob.getKey() , Math.log10(1 / prob.getValue()) / Math.log10(2) );
        }

        return informaciones;
    }

    public static double calculoEntropia(Map<String, Double> informaciones, Map<String, Double> probabilidades){
        double entropia = 0;

        for (Map.Entry<String, Double> info : informaciones.entrySet()) {

            entropia += info.getValue() * probabilidades.get( info.getKey() );

        }

        return entropia;
    }

    public static double rendimiento(double entropia, double longitudMedia){
        return entropia / longitudMedia;
    }

    public static double redundancia(double rendimiento){
        return 1 - rendimiento;
    }

    public static double calculoLongMedia(Map<String, Double> probabilidades, Huffman huffman){
        double longMedia = 0;

        Map<String, String> huffmanCodes = huffman.getHuffmanCodes();

        for (Map.Entry<String, Double> prob : probabilidades.entrySet()) {
            longMedia += prob.getValue() * huffmanCodes.get( prob.getKey() ).length();
        }

        return longMedia;
    }

    public static double getTasaCompresion(){

        File fileOriginal = new File("DatosTP2.txt");
        double tamOriginal = fileOriginal.length();

        File fileHuffman = new File("resultados/primera-parte/compresion.huf");
        double tamHuffman = fileHuffman.length();

        System.out.println("Original" +tamOriginal);
        System.out.println(tamHuffman);

        return tamOriginal / tamHuffman;

    }

    public static int getCantPalabras() {
        return cantPalabras;
    }

    public static void setCantPalabras(int cantPalabras) {
        MetodosCodigoHuffman.cantPalabras = cantPalabras;
    }

    public static int getCantSimbolos() {
        return cantSimbolos;
    }

    public static void setCantSimbolos(int cantSimbolos) {
        MetodosCodigoHuffman.cantSimbolos = cantSimbolos;
    }
}
