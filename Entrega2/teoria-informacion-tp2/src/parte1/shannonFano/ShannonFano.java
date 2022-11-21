package parte1.shannonFano;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class ShannonFano {

    private Arbol arbol = new Arbol();
    private String palabra;
    private Probabilidad prob;
    private ArrayList<String> caracteres2;
    private String[] codigos = new String[4000];
    private double[] informacion;
    private double[] probabilidades;
    private double longMedia;
    private double entropia;

    public ShannonFano() {
        this.prob = new Probabilidad();
        this.caracteres2 = this.prob.getCaracteres();

        // Creo nodo raiz
        Nodo raiz = new Nodo();
        raiz.setCaracteres(caracteres2);
        arbol.setRaiz(raiz);
    }

    // Agrega hijo izq y der a Nodo

    public void agregoHijos(Nodo nodo, ArrayList<String> parteIzq, ArrayList<String> parteDer){
        Nodo nodoIzq = new Nodo();
        Nodo nodoDer = new Nodo();
        nodo.setNodoDer(nodoDer);
        nodo.setNodoIzq(nodoIzq);
        nodoIzq.setCaracteres(parteIzq);
        nodoDer.setCaracteres(parteDer);
    }

    // Metodo para crear el arbol de caracteres

    public void crearArbol( ArrayList<String> caracteres, Nodo nodo){
        if (caracteres.size() != 1){
            ArrayList<String> parteIzq = new ArrayList<>();
            ArrayList<String> parteDer = new ArrayList<>();
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

    public void obtenerCodigo( String caracter, Nodo nodo, String[] codigo , int i){
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


    public int longMaxPalCodigo(){
        int max = 0;
        int i = 0;
        while( codigos[i] != null ){
            if( codigos[i].length() > max )
                max = codigos[i].length();
            i++;
        }
        return max;
    }


    public void printearResultados(){
        System.out.printf("\n---- CODIGOS SHANNON FANO ----\n\n");
        System.out.printf("CARACTER ----- PROBABILIDAD ------ CODIGO ----- INFORMACION ----- ENTROPIA TOTAL ----- LONG MEDIA ---- RENDIMIENTO --- REDUNDANCIA\n");
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
                System.out.printf("\t %1.3f\t\t\t" , this.getRendimiento());
                System.out.printf("\t %1.3f\t\t\t" , this.getRedundancia());

            }
            else{
                System.out.printf("\t\t- \t\t\t\t\t- ");
            }
        }
    }


    public double[] getInformacion(){
        return this.informacion;
    }

    public void setInformacion(){
        MetodosCodigoShannon.setCantSimbolos(2);
        this.informacion = MetodosCodigoShannon.calculoInformacion(this.prob.getProbabilidad());
    }


    public void setProbabilidades(){
        this.probabilidades = this.prob.getProbabilidad();
    }


    public void setEntropia() {
        this.entropia = MetodosCodigoShannon.calculoEntropia(this.informacion , this.probabilidades);
    }

    public String getCodificacion(){

        String path = "DatosTP2.txt";
        File file = new File(path);
        StringBuilder code = new StringBuilder();
        try {
            Scanner in = new Scanner(file);
            while(in.hasNext()) {
                String word = in.next();
                int index = this.caracteres2.lastIndexOf(word);
                code.append( this.codigos[index] );
                //System.out.println("Palabra: " + word + " Codigo: " + this.codigos[index]);
            }
            in.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return code.toString();

    }

    public void setLongMedia(){
        double acum = 0;
        for (int i = 0 ; i < probabilidades.length ; i++){
            acum += this.probabilidades[i] * this.codigos[i].length();
        }
        this.longMedia = acum;
    }


    public double getRendimiento(){
        return entropia / this.longMedia;
    }

    public double getRedundancia(){
        return  1 - this.getRendimiento();
    }

    public ArrayList<String> getCaracteres2() {
        return caracteres2;
    }

    public StringBuilder tablaCodificacion(){
        StringBuilder tabla = new StringBuilder();

        for ( String code : this.codigos ) {
            tabla.append(code);
        }

        return tabla;
    }

    public String[] getCodigos() {
        return codigos;
    }

    public Arbol getArbol() {
        return arbol;
    }

    public String getPalabra() {
        return palabra;
    }

    public Probabilidad getProb() {
        return prob;
    }

    public double[] getProbabilidades() {
        return probabilidades;
    }

    public double getLongMedia() {
        return longMedia;
    }

    public double getEntropia() {
        return entropia;
    }
}

