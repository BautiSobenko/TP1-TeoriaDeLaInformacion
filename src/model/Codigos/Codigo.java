package model.Codigos;

import java.util.ArrayList;

public class Codigo {

    ArrayList<String> combinaciones = new ArrayList<String>();
    ArrayList<Integer> frecuencias = new ArrayList<Integer>();
    ArrayList<Integer> probabilidades = new ArrayList<>();

    // @param palabrasCodigo: paso array con todas las palabras REPETIDAS dividida en x caracteres
    // calcula la cantidad de frecuencias por palabra y las setea en frecuencias
    // agrega a combinaciones las palabras unicas.

    public void calculoFrecYCombinaciones( ArrayList<String> listaPalabras){

    }

    public ArrayList<String> getCombinaciones() {
        return combinaciones;
    }

    public void setCombinaciones(ArrayList<String> codigo) {
        this.combinaciones = codigo;
    }

    public ArrayList<Integer> getFrecuencias() {
        return frecuencias;
    }

    public void setFrecuencias(ArrayList<Integer> frecuencias) {
        this.frecuencias = frecuencias;
    }

    public ArrayList<Integer> getProbabilidades() {
        return probabilidades;
    }

    public void setProbabilidades(ArrayList<Integer> probabilidades) {
        this.probabilidades = probabilidades;
    }
}
