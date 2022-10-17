package model.utlils;

import java.io.*;

public class Escritura {

    public static void escribeIncisoA(double[][] matrizEstados){

        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./results.txt");
            PrintWriter pw = new PrintWriter(fichero);
            pw.print("The first line");
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static char buscaSimbolo(int posicion){
        if( posicion == 0 )
            return 'A';

        if( posicion == 1 )
            return 'B';

        if( posicion == 2 )
            return 'C';

        return '-';
    }



}
