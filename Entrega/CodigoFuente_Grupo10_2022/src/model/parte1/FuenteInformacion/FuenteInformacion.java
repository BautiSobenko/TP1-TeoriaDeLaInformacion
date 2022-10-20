package model.parte1.FuenteInformacion;

public class FuenteInformacion {

    public static int buscaIndice(char i) {

        if(i == 'A')
            return 0;     // posicion 0 si es A
        else if(i == 'B')
            return 1;     // posicion 1 si es B
        else
            return 2;     // posicion 2 si es C
    }


    public static double entropia(double[][] matrizEstados,double[] vectorEstacionario ) {

        double entropia = 0;
        double aux;
        for (int i = 0; i < 3; i++) {
            aux = 0;
            for (int j = 0; j < 3; j++) {
                aux += matrizEstados[j][i] * (Math.log10(1 / matrizEstados[j][i]) / Math.log10(3));
            }
            aux *= vectorEstacionario[i];
            entropia += aux;
        }

        return entropia;

    }

    public static boolean isMemoriaNula(double[][] matrizEstados , double error){
        boolean isMemoriaNula = false;
        int j;
        int i = 0;

        while(i<3 && !isMemoriaNula) {
            j = 0;
            while(j<2 && !isMemoriaNula) {
                if(matrizEstados[i][j] < matrizEstados[i][j+1] - error || matrizEstados[i][j] > matrizEstados[i][j+1] + error)
                    j++;
                else
                    isMemoriaNula = true;
            }
            i++;
        }
        return isMemoriaNula;
    }


    public static boolean isErgodica(double[][] matrizEstados) {

        boolean Es = true;
        int i=0;
        int j;

        while(i<3 && Es) {
            j=0;
            while(j<3 && Es) {
                if(matrizEstados[i][j]==0 && i!=j)
                    Es=false;
                j+=1;
            }
            i+=1;
        }
        return Es;
    }
}
