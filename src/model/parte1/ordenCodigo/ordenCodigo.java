package model.parte1.ordenCodigo;

import model.utlils.Escritura;

public class ordenCodigo {

    private double totalApariciones;
    private double probabilidades[] = new double[3];
    private double entropiaInicial;

    public ordenCodigo( int probabilidadesTotales[]) {
        this.totalApariciones = 0;
        char[] simbolos = {'a', 'b' ,'c'};
        int orden = 1;
        double probTotal[] = new double[1];
        double entropia[] = new double[1];
        probTotal[0] = 0;
        entropia[0] = 0;
        this.probabilidades(probabilidadesTotales);
        this.probabilidadOrdenK(simbolos, orden , probTotal , entropia);
        entropiaInicial = entropia[0];
        orden = 20;
        probTotal[0] = 0;
        entropia[0] = 0;
        this.probabilidadOrdenK(simbolos, orden , probTotal , entropia);
        System.out.printf("entropia total" + entropia[0]);
        Escritura.resultadoIncisoB(entropiaInicial , entropia[0] );
    }

    public void probabilidades(int aparicionesTotales[]){

        for (int i = 0 ; i < aparicionesTotales.length; i++){
            this.probabilidades[i] = aparicionesTotales[i]/10000.;
            System.out.printf("\n Probabilidades: " + this.probabilidades[i]);
        }

    }

    public void probabilidadOrdenK(char[] simbolos, int orden , double probTotal[] , double entropia[])
    {
        int cantSimbolos = simbolos.length;
        probabilidadesOrdenKRecursivo(simbolos, "", cantSimbolos, orden , probTotal,entropia);
    }

    public void probabilidadesOrdenKRecursivo(char[] simbolos,
                                              String pal,
                                              int cantSimbolos, int orden, double[] probTotal, double[] entropia)
    {

        double prob =0;
        double prob_palabra = 0;
        if (orden == 0)
        {
            for (int i = 0; i < pal.length() ; i++){
                if (pal.charAt(i) == 'a') {
                    prob_palabra = this.probabilidades[0];
                }
                else if(pal.charAt(i) == 'b') {
                    prob_palabra = this.probabilidades[1];
                }
                else if(pal.charAt(i) == 'c') {
                    prob_palabra = this.probabilidades[2];
                }
                if (prob == 0){
                    prob += prob_palabra;
                }
                else {
                    prob *= prob_palabra;
                }
            }
            probTotal[0] += prob;
            entropia[0] += prob * (Math.log10(1 / prob) / Math.log10(3));
            return;
        }

        for (int i = 0; i < cantSimbolos; ++i)
        {
            String nuevaPal = pal + simbolos[i];
            probabilidadesOrdenKRecursivo(simbolos, nuevaPal,
                    cantSimbolos, orden - 1 , probTotal , entropia);
        }
    }
}
