package model.parte2.huffman;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Huffman {

    private Nodo root;
    private final ArrayList<String> listaPal;
    private Map<String, Integer> frecPal;
    private final Map<String, String> huffmanCodes;


    public Huffman(ArrayList<String> listaPal) {
        this.listaPal = listaPal;
        this.frecuencias();
        huffmanCodes = new HashMap<>();
    }

    //Calculo de frecuencias en un HashMap: (Key: Palabra codigo, Value: Frecuencia de aparicion)

    private void frecuencias() {
        this.frecPal = new HashMap<>();
        for(String palabra : this.listaPal) {
            Integer frec = this.frecPal.get(palabra);
            this.frecPal.put(palabra, frec != null ? frec + 1 : 1);
        }
        //frecPal.forEach((pal, frec) -> System.out.println("Palabra: " + pal + ": Frecuencia: " + frec));

    }

    public void encode() {
        //Creo pq, queue ordenados segun prioridad (menor valor, mayor prioridad)
        Queue<Nodo> queue = new PriorityQueue<>();
        //Todas las palabras codigo seran nodos hoja
        frecPal.forEach( (pal, frec) ->
                queue.add(new NodoHoja(pal, frec))
        );
        //Obtengo y saco de la pq las dos frecuencias mas bajas y añado un nuevo nodo con la suma de las frecuencias
        while(queue.size() > 1){
            queue.add(new Nodo(queue.poll(), queue.poll()));
        }
        generarCodigoHuffman(root = queue.poll(), "");
        printCodes();
    }

    private void generarCodigoHuffman(Nodo nodo, String codigo){
        if( nodo.getIzq() == null && nodo.getDer() == null ){
            this.huffmanCodes.put( ((NodoHoja) nodo).getPalabra(), codigo );
        }else{
            generarCodigoHuffman( nodo.getIzq(), codigo.concat("0") );
            generarCodigoHuffman( nodo.getDer(), codigo.concat("1") );
        }
    }

    public void escribeArchivo( String nombreArchivo ){

        try{
            FileWriter fichero = new FileWriter("./resultados/"+nombreArchivo);
            PrintWriter pw = new PrintWriter(fichero);

            pw.print(this.getCodigo());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String getCodigo(){
        StringBuilder sb = new StringBuilder();
        for(String pal : this.listaPal){
            sb.append(huffmanCodes.get(pal));
        }
        return sb.toString();
    }

    private void printCodes(){
        huffmanCodes.forEach((pal, codigo) ->
                System.out.println(pal + ": " + codigo)
        );
    }

    public double longMedia(){
        double frec;
        double longitud = 0;
        double total = 0;
        for (Map.Entry<String, String> entry : huffmanCodes.entrySet()) {
           frec =  this.frecPal.get(entry.getKey());
           longitud += frec * entry.getValue().length();
           total += frec;
        }

        return longitud/total;
    }


}
