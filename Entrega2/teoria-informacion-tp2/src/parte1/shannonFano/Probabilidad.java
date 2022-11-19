package parte1.shannonFano;

import java.util.*;

public class Probabilidad {
    Map<Character,Double> caracXProbabilidad = new TreeMap<Character, Double>();
    String palabra;
    double [] frecuencias;

    public Probabilidad( String palabra) {
        this.palabra = palabra;
        this.setcaracXProbabilidad();
    }

    // Calculos frecuencias y dps probabilidad para cada caracter

    public void setcaracXProbabilidad() {
        for (int i = 0; i < this.palabra.length(); i++) {
            char caracter = this.palabra.charAt(i);
            double prob = !this.caracXProbabilidad.containsKey(caracter) ? 0: this.caracXProbabilidad.get(caracter);
            this.caracXProbabilidad.put(caracter,prob + 1.);
            //Esto ordena por valor
            this.caracXProbabilidad = MapUtil.sortByValue(this.caracXProbabilidad);
        }
        //Guardo frecuencias y seteo probabilidades
        setFrecuencias( new ArrayList<>(this.caracXProbabilidad.values()));
        caracXProbabilidad.replaceAll( (key,value) -> value / this.palabra.length());
        System.out.printf(caracXProbabilidad.toString());

    }

    // Cant de elementos que se recorre por grupo
    public double indiceMitadDeProbabilidad(ArrayList<Character> caracteres){
        double probTotal = 0;
        double total = 0;
        int i = 0;
        for (Character caracter : caracteres)
            probTotal += this.caracXProbabilidad.get(caracter);

        Iterator<Character> it = caracteres.iterator();
        while ( it.hasNext() && total < probTotal / 2 ){
            Character key = it.next();
            total += caracXProbabilidad.get(key);
            i++;
        }
        return i;
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

    public ArrayList<Character> getCaracteres(){
        return new ArrayList<>(this.caracXProbabilidad.keySet());
    }

    public Map<Character, Double> getCaracXProbabilidad() {
        return caracXProbabilidad;
    }

    public double[] getFrecuencias() {
        return frecuencias;
    }

}
