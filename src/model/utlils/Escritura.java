package model.utlils;

import model.parte2.codigos.Codigo;
import model.parte2.huffman.Huffman;

import java.io.*;

public class Escritura {

    public static void escribeIncisoA(double[][] matrizEstados, boolean esMemoriaNula){

        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./resultados/primer-parte/IncisoA.txt");
            PrintWriter pw = new PrintWriter(fichero);
            pw.println("Inciso A:\n");
            pw.println("# Probabilidades condicionales: \n");
            for( int i = 0 ; i < 3 ; i++ ){
                for( int j = 0 ; j < 3 ; j++ ){
                    pw.printf("Probabilidad (%c | %c): %.4f \n", buscaSimbolo(j),buscaSimbolo(i),matrizEstados[i][j]);
                }
            }
            pw.println("\n# Determinacion del tipo de fuente: \n");

            if (esMemoriaNula)
                pw.println("Fuente de memoria nula");
            else
                pw.println("Fuente de memoria no nula");

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

    public static void escribeIncisoC(boolean esErgodica, double[] vectorEstacionario, Double entropia){

        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./resultados/primer-parte/IncisoC.txt");
            PrintWriter pw = new PrintWriter(fichero);
            pw.println("Inciso C:\n");
            pw.println("# Determinacion fuente ergodica o no ergodica: \n");

            if( !esErgodica ){
                pw.println("La fuente no es ergodica\n");
            }else{
                pw.println("La fuente resulta ergodica\n");
                pw.println("# Vector estacionario: \n");
                for(int i = 0 ; i < vectorEstacionario.length ; i++){
                    pw.printf("X%d %.4f \n",i,vectorEstacionario[i]);
                }
                pw.printf("\n# Entropia de la fuente: %.4f", entropia);
            }
            pw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void escribeIncisoA(double cantInformacion, double entropia){
        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./resultados/segunda-parte/IncisoA.txt");
            PrintWriter pw = new PrintWriter(fichero);
            pw.println("Inciso A:\n");
            pw.printf("# Cantidad de informacion: %.4f Unidades de orden 3\n", cantInformacion);
            pw.printf("# Entropia: %.4f Unidades de orden 3\n", entropia);

            pw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void escribeIncisoB(Codigo codigo){
        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./resultados/segunda-parte/IncisoB.txt");
            PrintWriter pw = new PrintWriter(fichero);
            pw.println("Inciso B:\n");
            pw.println("# Tipo de codigo obtenido: \n");
            pw.println("Codigo bloque: " + codigo.esCodigoBloque());
            pw.println("Codigo no singular: " + codigo.esNoSingular());
            pw.println("Codigo univocamente decodificable: " + codigo.esUnivocamenteDecodificable());
            pw.println("Codigo instantaneo: " + codigo.esInstantaneo());

            pw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void escribeIncisoC(double kraft, double longMedia, boolean esCompacto){
        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./resultados/segunda-parte/IncisoC.txt");
            PrintWriter pw = new PrintWriter(fichero);
            pw.println("Inciso C:\n");
            pw.printf("# Inecuacion de Kraft: %.2f <= 1.00 \n", kraft);
            pw.printf("# Inecuacion de McMillan: %.2f <= 1.00 \n", kraft);
            pw.printf("# Longitud media del codigo: %.2f Unidades de orden 3\n", longMedia);
            if( esCompacto )
                pw.println("# Codigo compacto");
            else
                pw.println("# Codigo no compacto");

            pw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void escribeIncisoD(double rendimiento, double redundancia){

        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./resultados/segunda-parte/IncisoD.txt");
            PrintWriter pw = new PrintWriter(fichero);
            pw.println("Inciso D:\n");
            pw.printf("# Rendimiento: %.2f %c \n", rendimiento*100,'%');
            pw.printf("# Redundancia:  %.2f %c \n", redundancia*100,'%');

            pw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void escribeIncisoE1(Huffman huffman){

        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./resultados/segunda-parte/IncisoE1.txt");
            PrintWriter pw = new PrintWriter(fichero);
            pw.println("Inciso D: Codificacion de simbolos\n");
            huffman.getHuffmanCodes().forEach((pal, codigo) ->
                    pw.println(pal + ": " + codigo)
            );

            pw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void escribeIncisoE2(Huffman huffman, String tamPalabra){

        FileWriter fichero = null;
        try{
            fichero = new FileWriter("./resultados/segunda-parte/IncisoE2-"+tamPalabra+".txt");
            PrintWriter pw = new PrintWriter(fichero);

            pw.print(huffman.getCodigo());

            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




    }





}
