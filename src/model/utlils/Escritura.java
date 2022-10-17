package model.utlils;

import java.io.*;

public class Escritura {

    public static void escribeIncisoA(double[][] matrizEstados){

        File folder = new File ("./Resultados");
        folder.mkdirs();


        File file = new File("Resultados.txt");
        try {
            file.createNewFile();
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
