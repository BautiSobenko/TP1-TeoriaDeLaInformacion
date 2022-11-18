package parte1;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Parte1 {

    public static void main(String[] args) {
        String path = "DatosTP2.txt";
        Map< String, Integer > frecPal = new HashMap<>();
        try {
            Scanner in = new Scanner(new FileReader(path));
            while(in.hasNext()) {
                String word = in.next();
                Integer frec = frecPal.get(word);
                frecPal.put(word, frec != null ? frec + 1 : 1);
            }
            in.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        System.out.println(frecPal);

    }



}
