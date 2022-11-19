package parte1.shannonFano;

import parte1.Codigo;

import java.util.*;


public class ShannonFano {

    private Arbol arbol = new Arbol();
    private String palabra;
    private Probabilidad prob;
    private ArrayList<Character> caracteres2;
    private String[] codigos = new String[30];
    private double[] informacion;
    private double[] probabilidades;
    private double[] frecuencias;
    private double longMedia;
    private double entropia;

    public ShannonFano(String palabra) {
        this.palabra = palabra;
        this.prob = new Probabilidad(this.palabra);
        this.caracteres2 = this.prob.getCaracteres();

        // Creo nodo raiz
        Nodo raiz = new Nodo();
        raiz.setCaracteres(caracteres2);
        arbol.setRaiz(raiz);
    }

    // Agrega hijo izq y der a Nodo

    public void agregoHijos(Nodo nodo, ArrayList<Character> parteIzq, ArrayList<Character> parteDer){
        Nodo nodoIzq = new Nodo();
        Nodo nodoDer = new Nodo();
        nodo.setNodoDer(nodoDer);
        nodo.setNodoIzq(nodoIzq);
        nodoIzq.setCaracteres(parteIzq);
        nodoDer.setCaracteres(parteDer);
    }

    // Metodo para crear el arbol de caracteres

    public void crearArbol( ArrayList<Character> caracteres, Nodo nodo){
        if (caracteres.size() != 1){
            ArrayList<Character> parteIzq = new ArrayList<>();
            ArrayList<Character> parteDer = new ArrayList<>();
            double limite = prob.indiceMitadDeProbabilidad(caracteres);
            for (int i = 0; i < caracteres.size(); i++) {
                if (i < limite) {
                    parteIzq.add(caracteres.get(i));
                } else {
                    parteDer.add(caracteres.get(i));
                }
            }
            agregoHijos(nodo,parteIzq,parteDer);
            crearArbol(parteIzq , nodo.getNodoIzq());
            crearArbol(parteDer,  nodo.getNodoDer());
        }
    }

    // Codigo para cada caracter

    public void obtenerCodigo( Character caracter, Nodo nodo, String[] codigo , int i){
        if (nodo.getCaracteres().size() != 1){
            boolean estaIzq = nodo.getNodoIzq().getCaracteres().contains(caracter);
            if (estaIzq){
                codigo[i] += '0';
                obtenerCodigo(caracter,nodo.getNodoIzq(), codigo , i);
            }else{
                codigo[i] += '1';
                obtenerCodigo(caracter,nodo.getNodoDer(), codigo, i);
            }
        }

    }

    // Genera codigo de todos los caracteres.

    public void generarTodosLosCodigos(){
        for ( int i = 0 ; i < caracteres2.size() ; i++){
            this.codigos[i] = "";
            obtenerCodigo(caracteres2.get(i),this.arbol.getRaiz(),codigos , i);
        }
    }



    public void printearResultados(){
        System.out.printf("\n---- CODIGOS SHANNON FANO ----\n\n");
        System.out.printf("CARACTER ----- PROBABILIDAD ------ CODIGO ----- INFORMACION ----- ENTROPIA TOTAL ----- LONG MEDIA\n");
        this.setInformacion();
        this.setProbabilidades();
        this.setEntropia();
        this.setLongMedia();
        for (int i = 0 ; i < caracteres2.size() ; i++){
            System.out.printf("\n- " + this.caracteres2.get(i).toString()+" -\t\t\t");
            System.out.printf("%1.3f\t\t\t\t",this.prob.getCaracXProbabilidad().get(this.caracteres2.get(i)));
            System.out.printf("%s " , this.codigos[i]);
            System.out.printf("\t\t\t %5.3f\t\t"      , this.getInformacion()[i]);
            if ( i == 0){
                System.out.printf("\t %1.3f\t\t\t" , this.entropia);
                System.out.printf("\t %1.3f\t\t\t" , this.longMedia);
            }
            else{
                System.out.printf("\t\t- \t\t\t\t\t- ");
            }
        }
    }

    // Informacion

    public double[] getInformacion(){
        return this.informacion;
    }

    public void setInformacion(){
        Codigo.setCantSimbolos(2);
        this.informacion = Codigo.calculoInformacion(this.prob.getProbabilidad());
    }

    // Probabilidades

    public double[] getProbabilidades(){
        return this.probabilidades;
    }
    public void setProbabilidades(){
        this.probabilidades = this.prob.getProbabilidad();
    }

    // Entropia

    public double getEntropia() {
        return entropia;
    }

    public void setEntropia() {
        this.entropia = Codigo.calculoEntropia(this.informacion , this.probabilidades);
    }


    //Longitud media

    public double getLongMedia(){
        return this.longMedia;
    }
    public void setLongMedia(){
        double acum = 0;
        for (int i = 0 ; i < probabilidades.length ; i++){
            acum += this.probabilidades[i] * this.codigos[i].length();
        }
        this.longMedia = acum;
    }

    //Informacion

    public void setInformacion(double[] informacion) {
        this.informacion = informacion;
    }

    public void setProbabilidades(double[] probabilidades) {
        this.probabilidades = probabilidades;
    }

    // Rendimiento  y redundancia.

    public double getRendimiento(){
        return entropia / this.longMedia;
    }

    public double getRedundancia(){
        return  1 - this.getRendimiento();
    }

    public static void main(String[] args) {
       ShannonFano shannonFano = new ShannonFano("Hola como andas todo re piola");
       shannonFano.crearArbol(shannonFano.caracteres2 , shannonFano.arbol.getRaiz());
       shannonFano.generarTodosLosCodigos();
       shannonFano.printearResultados();
       shannonFano.setInformacion();
    }
}

