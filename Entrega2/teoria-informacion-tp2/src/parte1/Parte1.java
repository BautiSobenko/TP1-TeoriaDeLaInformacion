package parte1;

import parte1.huffman.Codigo;
import parte1.huffman.Huffman;

import java.io.*;
import java.util.*;

public class Parte1 {

    public static void main(String[] args) {

        String path = "DatosTP2.txt";
        File file = new File(path);
        Map< String, Integer > frecPal = new HashMap<>();
        Set<Character> simbolos = new HashSet<>();
        int cantTotalPalabras = 0;
        int longMaxPalFuente = 0;
        try {
            Scanner in = new Scanner(file);

            while(in.hasNext()) {
                String word = in.next();
                cantTotalPalabras++;
                if( word.length() > longMaxPalFuente )
                    longMaxPalFuente = word.length();
                Integer frec = frecPal.get(word);
                frecPal.put(word, frec != null ? frec + 1 : 1);
                for (int i = 0; i < word.length(); i++) {
                    simbolos.add(word.charAt(i));
                }
            }
            in.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println(cantTotalPalabras);
        simbolos.forEach(System.out::println);
        System.out.println(simbolos.size());
        int cantSimbolos = simbolos.size();

        frecPal.forEach( (pal, frec) -> System.out.println(pal + ": " + frec));


        Huffman huffman = new Huffman(frecPal);
        huffman.encode();
        System.out.println("Long max de palabra fuente: " + longMaxPalFuente);
        System.out.println("Long max de palabra codigo: " + huffman.longMaxPalabraCod());

        Codigo codigo = new Codigo(cantTotalPalabras, cantSimbolos);

        Map<String, Double> probabilidades = Codigo.probabilidades(frecPal);
        Map<String, Double> informaciones = Codigo.calculoInformacion(probabilidades);
        double entropia = Codigo.calculoEntropia(informaciones, probabilidades);
        double longMedia = Codigo.calculoLongMedia(probabilidades, huffman);
        double rendimiento = Codigo.rendimiento(entropia, longMedia);
        double redundancia = Codigo.redundancia(rendimiento);

        Escritura.resultadoParte1(huffman, cantTotalPalabras, longMaxPalFuente, huffman.longMaxPalabraCod(), 0. , rendimiento, redundancia);

        System.out.println("\nRendimiento: " + rendimiento);
        System.out.println("Redundancia: " +redundancia);
        System.out.println("Longitud Media: " + longMedia);
        System.out.println("Entropia: " + entropia);
        System.out.println("Cantidad de simbolos: " + cantSimbolos);
        System.out.println("Cantidad de palabras: " + cantTotalPalabras);







    }

}
