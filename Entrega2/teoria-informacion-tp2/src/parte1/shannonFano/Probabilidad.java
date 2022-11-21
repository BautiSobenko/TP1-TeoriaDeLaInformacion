package parte1.shannonFano;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Probabilidad {

    Map<String,Double> caracXProbabilidad = new TreeMap<String, Double>();
    String palabra;
    private int cantTotalPalabras = 0;
    private static int longMaxPalFuente;
    private static int cantSimbolos;

    public Probabilidad( String palabra) {
        this.palabra = palabra;
//        this.setcaracXProbabilidad();
        this.lecturaDelArchivo();
    }

    public void lecturaDelArchivo(){
        String path = "DatosTP2.txt";
        File file = new File(path);
        Set<Character> simbolos = new HashSet<>();
        longMaxPalFuente = 0;
        try {
            Scanner in = new Scanner(file);
            while(in.hasNext()) {
                String word = in.next();
                cantTotalPalabras++;
                if( word.length() > longMaxPalFuente )
                    longMaxPalFuente = word.length();
                Double frec = caracXProbabilidad.get(word);
                caracXProbabilidad.put(word, frec != null ? frec + 1 : 1);
                for (int i = 0; i < word.length(); i++) {
                    simbolos.add(word.charAt(i));
                }
                this.caracXProbabilidad = MapUtil.sortByValue(this.caracXProbabilidad);
            }
            Probabilidad.cantSimbolos = simbolos.size();
            in.close();
            setFrecuencias(new ArrayList<>(this.caracXProbabilidad.values()));
            caracXProbabilidad.replaceAll( (key,value) -> value / cantTotalPalabras);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Cant de elementos que se recorre por grupo
    public double indiceMitadDeProbabilidad(ArrayList<String> caracteres){
        double probTotal = 0;
        double total = 0;
        int i = 0;

        for (String caracter : caracteres) {
            //System.out.printf("%10f",this.caracXProbabilidad.get(caracter));
            probTotal += this.caracXProbabilidad.get(caracter);
        }

        Iterator<String> it = caracteres.iterator();
        while ( it.hasNext() && total < probTotal / 2 ){
            String key = it.next();
            total += caracXProbabilidad.get(key);
            i++;
        }
        return i;
    }

    public static int getCantSimbolos() {
        return cantSimbolos;
    }

    // Calculos auxiliares

    // Paso de prob de hashmap a vector
    public double[] getProbabilidad(){
        double[] valores = new double[caracXProbabilidad.size()];
        ArrayList<Double> valor = new ArrayList<>(this.caracXProbabilidad.values());
        for (int i = 0 ; i < valores.length ; i++){
            valores[i] = valor.get(i);
        }
        return valores;
    }

    public void setFrecuencias(ArrayList<Double> valores) {
        double[] frecuencias = new double[valores.size()];
        for ( int i = 0 ; i < frecuencias.length ; i++){
            frecuencias[i] = valores.get(i);
        }
    }

    public ArrayList<String> getCaracteres(){
        return new ArrayList<>(this.caracXProbabilidad.keySet());
    }

    public Map<String, Double> getCaracXProbabilidad() {
        return caracXProbabilidad;
    }

    public static int getLongMaxPalFuente() {
        return longMaxPalFuente;
    }
}
