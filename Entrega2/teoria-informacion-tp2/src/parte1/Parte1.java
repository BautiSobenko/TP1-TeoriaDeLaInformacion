package parte1;

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
                String word = in.nextLine();
                String[] palabras = word.split(" ");
                for ( String pal : palabras ) {
                    cantTotalPalabras++;
                    if( pal.length() > longMaxPalFuente )
                        longMaxPalFuente = pal.length();

                    Integer frec = frecPal.get(pal);
                    frecPal.put(pal, frec != null ? frec + 1 : 1);
                }
                for (int i = 0; i < word.length(); i++) {
                    simbolos.add(word.charAt(i));
                    // Si el carácter en [i] es un espacio (' ') aumentamos el contador
                    if (Character.isWhitespace(word.charAt(i))){
                        cantTotalPalabras++;
                        Integer frec = frecPal.get(" ");
                        frecPal.put(" ", frec != null ? frec + 1 : 1);
                    }
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


        frecPal.forEach( (pal, frec) -> {
            System.out.println(pal + ": " + frec);
        });

        Huffman huffman = new Huffman(frecPal);
        huffman.encode();
        System.out.println("Long max de palabra fuente: " + longMaxPalFuente);
        System.out.println("Long max de palabra codigo: " + huffman.longMaxPalabraCod());
        Escritura.resultadoParte1(huffman, cantTotalPalabras, longMaxPalFuente, huffman.longMaxPalabraCod());

        Codigo codigo = new Codigo(cantTotalPalabras, cantSimbolos);

        double[] probabilidades = Codigo.probabilidades(frecPal);
        double[] cantInformaciones = Codigo.calculoInformacion(probabilidades);
        double entropia = Codigo.calculoEntropia(cantInformaciones, probabilidades);
        double longMedia = Codigo.calculoLongMedia(probabilidades, frecPal);
        double rendimiento = Codigo.rendimiento(entropia, longMedia);
        double redundancia = Codigo.redundancia(rendimiento);
        System.out.println(rendimiento);
        System.out.println(redundancia);


    }

}
