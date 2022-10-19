package prueba;

import model.parte1.FuenteInformacion.FuenteInformacion;
import model.parte1.ordenCodigo.ordenCodigo;
import model.parte2.codigos.Codigo;
import model.parte2.huffman.Huffman;
import model.utlils.Escritura;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        System.out.printf("Trabajo practico Integrador N1 \n" + "Grupo 10\n" + "Comenzando ejecucion del programa" + "\n ---------- \n");

        // ----    RESOLUCION DE PRIMERA PARTE ----
        try {
            String path = "DatosTP1.txt";
            File file = new File(path);
            BufferedReader archivo = new BufferedReader(new FileReader(file));
            String datos = archivo.readLine();
            int len = datos.length();

            int[][] matrizApariciones = new int[3][3];    //Matriz de apariciones si el simbolo previo a X es A | B | C
            int[] aparicionesTotales = new int[3];       //Apariciones totales por simbolo
            double[][] matrizEstados = new double[3][3]; //Matriz de transicion de estados (probabilidades)

            //Inicializacion de contadores
            for (int i = 0; i < 3; i++) {
                aparicionesTotales[i] = 0;
                for (int j = 0; j < 3; j++) {
                    matrizApariciones[i][j] = 0;
                }
            }

            for (int i = 1; i < len; i++) {
                matrizApariciones[FuenteInformacion.buscaIndice(datos.charAt(i - 1))][FuenteInformacion.buscaIndice(datos.charAt(i))] += 1;
                aparicionesTotales[FuenteInformacion.buscaIndice(datos.charAt(i))] += 1;
            }

            //Probabilidades condicionales
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    matrizEstados[i][j] = (double) matrizApariciones[i][j] / aparicionesTotales[i];
                }
            }

            boolean esMemoriaNula = FuenteInformacion.isMemoriaNula(matrizEstados, 0.05);
            Escritura.resultadoIncisoA(matrizEstados, esMemoriaNula);

            if (esMemoriaNula) {
                //ordenCodigo codigo = new ordenCodigo(aparicionesTotales);
            } else {
                boolean esErgodica = FuenteInformacion.isErgodica(matrizEstados);

                if (!esErgodica) {
                    Escritura.resultadoIncisoC(esErgodica, null, null, null);
                } else {

                    //Calculo de vector estacionario

                    // a * (m[0][0]-1) +    b * m[0][1]     +    c * m[0][2]     =    0
                    // a *  m[1][0]    +    b * (m[1][1]-1) +    c * m[1][2]     =    0
                    // a * m[2][0]     +    b * m[2][1]     +    c * (m[2][2]-1) =    0
                    // a               +    b               +    c               =    1

                    double[][] matrizCoefSistEc = new double[3][3]; //Inicializo el sistema de ec
                    // Creacion de Matriz de Coeficientes

                    int cantVar = 3;

                    for (int j = 0; j < cantVar; j++) {
                        matrizCoefSistEc[0][j] = 1.00; // a + b + c + ... + n
                    }
                    for (int i = 1; i < cantVar; i++) {
                        for (int j = 0; j < cantVar; j++) {
                            matrizCoefSistEc[i][j] = matrizEstados[i][j];
                            if (i == j) { //Diagonal
                                matrizCoefSistEc[i][j] -= 1.00;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ----    RESOLUCION DE SEGUNDA PARTE ----

        int[] carac = {3,5,7};
        for (int j : carac) {
            try {

                String path = "DatosTP1.txt";
                File file = new File(path);
                BufferedReader archivo = new BufferedReader(new FileReader(file));
                String datos = archivo.readLine();
                int len = datos.length();

                int tamanioPalabra = j;
                int i = 0;
                ArrayList<String> listaPal = new ArrayList<String>(); //Guardo todas las Strings formadas
                String formada;

                while (len - i >= tamanioPalabra) { // Mientras no me quede resto sigo. Si i+tamanio > len crashea por el substring.
                    formada = datos.substring(i, i + tamanioPalabra);
                    listaPal.add(formada);
                    i += tamanioPalabra; //Avanzo a la siguiente palabra
                }

                // Guardo en combinaciones las combinaciones unicas

                ArrayList<String> combinaciones = (ArrayList<String>) listaPal.stream().distinct().collect(Collectors.toList());

                Codigo codigo = new Codigo(combinaciones);
                // DESPRINTEAR ESTO
                Escritura.resultadoIncisoB(codigo, Integer.toString(tamanioPalabra));

                int[] frecuencias = new int[combinaciones.size()];
                for (i = 0; i < combinaciones.size(); i++) {
                    frecuencias[i] = Collections.frequency(listaPal, combinaciones.get(i));
                }

                //Probabilidades

                float[] probabilidades = new float[combinaciones.size()];
                int frecTotal = 0;

                for (i = 0; i < frecuencias.length; i++) {
                    frecTotal += frecuencias[i];
                }

                for (i = 0; i < frecuencias.length; i++) {
                    probabilidades[i] = ((float) frecuencias[i]) / frecTotal;
                }

                //Cantidad de informacion

                double[] informaciones = Codigo.calculoInformacion(combinaciones, probabilidades);
                double cantInformacion = Codigo.cantidadInformacion(informaciones);

                //Entropia

                double entropia = Codigo.calculoEntropia(informaciones, probabilidades);
                // DESPRINTEAR ESTO
                Escritura.resultadoIncisoA(cantInformacion, entropia, Integer.toString(tamanioPalabra));

                //Kraft

                int numSimbolosDistintos = 3;
                float kraft = (float) (Math.pow(numSimbolosDistintos, (-tamanioPalabra)) * combinaciones.size());

                //Longitud Media

                double longMedia = Codigo.calculoLongMedia(probabilidades, tamanioPalabra);

                //Condicion codigo compacto

                boolean esCompacto = longMedia <= tamanioPalabra;

                Escritura.resultadoIncisoC(kraft, longMedia, esCompacto, Integer.toString(tamanioPalabra));

                // Rendimiento y redundancia

                double rendimiento = Codigo.rendimiento(entropia, longMedia);
                double redundancia = Codigo.redundancia(rendimiento);

                Escritura.resultadoIncisoD(rendimiento, redundancia, Integer.toString(tamanioPalabra));

                //Codificacion Huffman

                Huffman huffman = new Huffman(listaPal);
                huffman.encode();

                Escritura.resultadoIncisoE1(huffman, Integer.toString(tamanioPalabra));

                Escritura.resultadoIncisoE2(huffman, Integer.toString(tamanioPalabra));


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.printf("Termino la ejecucion del programa. Los resultados se encuentran en la carpeta RESULTADOS \n");
        System.out.printf("Integrantes:\n ------ Bautista Sobemko \n ------ Rojas Agustin \n ------ Matarazzo Tomas");
    }
}
