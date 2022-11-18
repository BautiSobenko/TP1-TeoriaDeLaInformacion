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
        int frecTotal = 0;
        try {
            Scanner in = new Scanner(new FileReader(path));
            while(in.hasNext()) {
                String word = in.next();
                frecTotal++;
                Integer frec = frecPal.get(word);
                frecPal.put(word, frec != null ? frec + 1 : 1);
            }
            in.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println(frecTotal);
        System.out.println(frecPal.size());

        Huffman huffman = new Huffman(frecPal);
        huffman.encode();
        huffman.getCodigo();
        Escritura.resultadoParte1(huffman);

    }



}
