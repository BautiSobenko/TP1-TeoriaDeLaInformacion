package model.parte1.ordenCodigo;

import model.utlils.Escritura;

public class ordenCodigo {

    private double totalApariciones;
    private double probabilidades[] = new double[3];
    private double entropiaInicial;

    public ordenCodigo( int probabilidadesTotales[]) {
        this.totalApariciones = 0;
        char[] set1 = {'a', 'b' ,'c'};
        int k = 1;
        double probTotal[] = new double[1];
        double entropia[] = new double[1];
        probTotal[0] = 0;
        entropia[0] = 0;
        this.probabilidades(probabilidadesTotales);
        this.probabilidadOrdenK(set1, k , probTotal , entropia);
        entropiaInicial = entropia[0];
        k = 20;
        probTotal[0] = 0;
        entropia[0] = 0;
        this.probabilidadOrdenK(set1, k , probTotal , entropia);
        System.out.printf("entropia total" + entropia[0]);
        Escritura.resultadoIncisoB(entropiaInicial , entropia[0] );
    }

    public void probabilidades(int aparicionesTotales[]){

        for (int i = 0 ; i < aparicionesTotales.length; i++){
            this.probabilidades[i] = aparicionesTotales[i]/10000.;
            System.out.printf("\n Probabilidades: " + this.probabilidades[i]);
        }

    }

    public void probabilidadOrdenK(char[] set, int k , double probTotal[] , double entropia[])
    {
        int n = set.length;
        probabilidadesOrdenKRecursivo(set, "", n, k , probTotal,entropia);
    }


    public void probabilidadesOrdenKRecursivo(char[] set,
                                              String prefix,
                                              int n, int k , double probTotal[] ,double entropia[])
    {

        double prob =0;
        double prob_palabra = 0;
        if (k == 0)
        {
            for (int i = 0 ; i < prefix.length() ; i++){
                if (prefix.charAt(i) == 'a') {
                    prob_palabra = this.probabilidades[0];
                }
                else if(prefix.charAt(i) == 'b') {
                    prob_palabra = this.probabilidades[1];
                }
                else if(prefix.charAt(i) == 'c') {
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

        for (int i = 0; i < n; ++i)
        {
            String newPrefix = prefix + set[i];
            probabilidadesOrdenKRecursivo(set, newPrefix,
                    n, k - 1 , probTotal , entropia);
        }
    }
}
