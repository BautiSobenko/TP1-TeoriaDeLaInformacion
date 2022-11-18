package parte1;

import parte1.huffman.Huffman;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Parte1 {

    public static void main(String[] args) {
        String path = "DatosTP2.txt";
        Map< String, Integer > frecPal = new HashMap<>();
        int cantTotalSimbolos = 0;
        int longMaxPalFuente = 0;
        try {
            Scanner in = new Scanner(new FileReader(path));
            while(in.hasNext()) {
                String word = in.next();
                cantTotalSimbolos++;
                if( word.length() > longMaxPalFuente )
                    longMaxPalFuente = word.length();
                Integer frec = frecPal.get(word);
                frecPal.put(word, frec != null ? frec + 1 : 1);
            }
            in.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println(cantTotalSimbolos);
        System.out.println(frecPal.size());

        Huffman huffman = new Huffman(frecPal);
        huffman.encode();
        System.out.println("Long max de palabra fuente: " + longMaxPalFuente);
        System.out.println("Long max de palabra codigo: " + huffman.longMaxPalabraCod());
        Escritura.resultadoParte1(huffman, cantTotalSimbolos, longMaxPalFuente, huffman.longMaxPalabraCod());

    }

}
