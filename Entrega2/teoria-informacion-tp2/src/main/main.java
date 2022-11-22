package main;

import parte1.huffman.MetodosCodigoHuffman;
import parte1.huffman.Huffman;
import parte1.shannonFano.MetodosCodigoShannon;
import parte1.shannonFano.Probabilidad;
import parte1.shannonFano.ShannonFano;
import parte1.utils.Escritura;

import java.io.*;
import java.util.*;

import static parte2.Parte2.*;

public class main {

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

        int cantSimbolos = simbolos.size();
        //frecPal.forEach( (pal, frec) -> System.out.println(pal + ": " + frec));


        Huffman huffman = new Huffman(frecPal);
        huffman.encode();
        MetodosCodigoHuffman codigo = new MetodosCodigoHuffman(cantTotalPalabras, cantSimbolos);

        Map<String, Double> probabilidades = MetodosCodigoHuffman.probabilidades(frecPal);
        Map<String, Double> informaciones = MetodosCodigoHuffman.calculoInformacion(probabilidades);
        double entropia = MetodosCodigoHuffman.calculoEntropia(informaciones, probabilidades);
        double longMedia = MetodosCodigoHuffman.calculoLongMedia(probabilidades, huffman);
        double rendimiento = MetodosCodigoHuffman.rendimiento(entropia, longMedia);
        double redundancia = MetodosCodigoHuffman.redundancia(rendimiento);


        Escritura.resultadoParte1Huffman(huffman,cantSimbolos, longMaxPalFuente, huffman.longMaxPalabraCod(), MetodosCodigoHuffman.getTasaCompresion() , rendimiento, redundancia);

        ShannonFano shannonFano = new ShannonFano();
        shannonFano.crearArbol(shannonFano.getCaracteres2() , shannonFano.getArbol().getRaiz());
        shannonFano.generarTodosLosCodigos();
        shannonFano.setInformacion();
        shannonFano.setInformacion();
        shannonFano.setProbabilidades();
        shannonFano.setEntropia();
        shannonFano.setLongMedia();
        Escritura.resultadosParte1Shannon(shannonFano, Probabilidad.getCantSimbolos(), Probabilidad.getLongMaxPalFuente(), shannonFano.longMaxPalCodigo(), MetodosCodigoShannon.getTasaCompresion() , shannonFano.getRendimiento(), shannonFano.getRedundancia());

        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./resultados/segunda-parte/canales.txt");
            PrintWriter pw = new PrintWriter(fichero);

            ejecutaCanalUno(pw);
            ejecutaCanalDos(pw);
            ejecutaCanalTres(pw);

            pw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
