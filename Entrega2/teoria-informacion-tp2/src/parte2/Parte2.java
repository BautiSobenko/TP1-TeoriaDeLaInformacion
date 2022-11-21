package parte2;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static java.lang.Math.log;

public class Parte2 {

    public static void main(String[] args) {

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
        public static void cargaDatosCanalUno(double[] A, double[][] matrizSaliendoai){

            A[0]=0.2;
            A[1]=0.1;
            A[2]=0.3;
            A[3]=0.3;
            A[4]=0.1;

            matrizSaliendoai[0][0]=0.3;
            matrizSaliendoai[1][0]=0.4;
            matrizSaliendoai[2][0]=0.3;
            matrizSaliendoai[3][0]=0.3;
            matrizSaliendoai[4][0]=0.3;

            matrizSaliendoai[0][1]=0.3;
            matrizSaliendoai[1][1]=0.4;
            matrizSaliendoai[2][1]=0.3;
            matrizSaliendoai[3][1]=0.4;
            matrizSaliendoai[4][1]=0.4;

            matrizSaliendoai[0][2]=0.4;
            matrizSaliendoai[1][2]=0.2;
            matrizSaliendoai[2][2]=0.4;
            matrizSaliendoai[3][2]=0.3;
            matrizSaliendoai[4][2]=0.3;

        }

    public static void cargaDatosCanalDos(double[] A, double[][] matrizSaliendoai){

        A[0]=0.25;
        A[1]=0.33;
        A[2]=0.27;
        A[3]=0.15;

        matrizSaliendoai[0][0]=0.2;
        matrizSaliendoai[1][0]=0.3;
        matrizSaliendoai[2][0]=0.3;
        matrizSaliendoai[3][0]=0.3;

        matrizSaliendoai[0][1]=0.3;
        matrizSaliendoai[1][1]=0.3;
        matrizSaliendoai[2][1]=0.2;
        matrizSaliendoai[3][1]=0.3;

        matrizSaliendoai[0][2]=0.2;
        matrizSaliendoai[1][2]=0.2;
        matrizSaliendoai[2][2]=0.2;
        matrizSaliendoai[3][2]=0.3;

        matrizSaliendoai[0][3]=0.3;
        matrizSaliendoai[1][3]=0.2;
        matrizSaliendoai[2][3]=0.3;
        matrizSaliendoai[3][3]=0.1;
    }

    public static void cargaDatosCanalTres(double[] A, double[][] matrizSaliendoai){

        A[0]=0.15;
        A[1]=0.1;
        A[2]=0.2;
        A[3]=0.25;
        A[4]=0.14;
        A[5]=0.16;

        matrizSaliendoai[0][0]=0.2;
        matrizSaliendoai[1][0]=0.3;
        matrizSaliendoai[2][0]=0.2;
        matrizSaliendoai[3][0]=0.3;
        matrizSaliendoai[4][0]=0.2;
        matrizSaliendoai[5][0]=0.2;

        matrizSaliendoai[0][1]=0.3;
        matrizSaliendoai[1][1]=0.3;
        matrizSaliendoai[2][1]=0.2;
        matrizSaliendoai[3][1]=0.3;
        matrizSaliendoai[4][1]=0.3;
        matrizSaliendoai[5][1]=0.3;

        matrizSaliendoai[0][2]=0.2;
        matrizSaliendoai[1][2]=0.3;
        matrizSaliendoai[2][2]=0.3;
        matrizSaliendoai[3][2]=0.2;
        matrizSaliendoai[4][2]=0.3;
        matrizSaliendoai[5][2]=0.3;

        matrizSaliendoai[0][3]=0.3;
        matrizSaliendoai[1][3]=0.1;
        matrizSaliendoai[2][3]=0.3;
        matrizSaliendoai[3][3]=0.2;
        matrizSaliendoai[4][3]=0.2;
        matrizSaliendoai[5][3]=0.2;
    }

        public static double[] calculaB(double[] A, double[][] matrizSaliendoai,int canal){
            int i,j,cantSimbolosSalida;

            if(canal==1)
                cantSimbolosSalida=3;
            else
                if(canal==2)
                    cantSimbolosSalida=4;
                else
                    cantSimbolosSalida=4;

            double[] B = new double[cantSimbolosSalida];

            for (i=0;i<cantSimbolosSalida;i++)
                B[i]=0;

            for(i=0;i<B.length;i++){
                for(j=0;j<A.length;j++){
                    B[i] += matrizSaliendoai[j][i] * A[j];
                }
            }

            return B;
        }


        public static double[][] calculaMatrizSaliendoBj(double[] A, double[] B, double[][] matrizSaliendoai){

            double[][] matrizSaliendoBj = new double[A.length][B.length];
            int i,j;

            for (i=0;i<A.length;i++){
                for(j=0;j<B.length;j++){
                    matrizSaliendoBj[i][j] = matrizSaliendoai[i][j] * A[i] / B[j];
                }
            }

            return matrizSaliendoBj;

        }

        public static double calculaEntropiaAPriori(double[] A){
            int i;
            double res=0;

            for (i=0;i<A.length;i++){
                res += A[i] * log(1/A[i]) /log(2);
            }

            return res;

        }

        public static double calculaEntropiaPosteriori(double[][] matrizSaliendoBj,int simboloSalida,int canal){

            double res=0;
            int cantSimbolosEntrada;
            int salida = simboloSalida-1;
            int i;

            if(canal==1)
                cantSimbolosEntrada=5;
            else
                if(canal==2)
                    cantSimbolosEntrada=4;
                else
                    cantSimbolosEntrada=6;

            for (i=0;i<cantSimbolosEntrada;i++){
                res += matrizSaliendoBj[i][salida] * log(1/matrizSaliendoBj[i][salida]) / log(2);
            }

            return res;
        }

        public static double calculaEquivocacion(double[][] matrizSaliendoBj,int canal,double[] B){

            int i;
            double res=0;

            for(i=0;i<B.length;i++){
                res += B[i] * calculaEntropiaPosteriori(matrizSaliendoBj,i+1,canal);
            }

            return res;
        }


    public static double calculaEntropiaPosterioriB(double[][] matrizSaliendoAi,int simboloEntrada,int canal){

        double res=0;
        int cantSimbolosSalida;
        int entrada = simboloEntrada-1;
        int i;
        if(canal==1)
            cantSimbolosSalida=3;
        else
            if(canal==2)
                cantSimbolosSalida=4;
            else
                cantSimbolosSalida=4;

        for (i=0;i<cantSimbolosSalida;i++){
            res += matrizSaliendoAi[entrada][i] * log(1/matrizSaliendoAi[entrada][i]) / log(2);
        }

        return res;
    }

    public static double calculaEquivocacionB(double[][] matrizSaliendoAi,int canal,double[] A){

        int i;
        double res=0;

        for(i=0;i<A.length;i++){
            res += A[i] * calculaEntropiaPosterioriB(matrizSaliendoAi,i+1,canal);
        }

        return res;
    }


    public static void ejecutaCanalUno(PrintWriter pw) {

        double[] A = new double[5];
        double[][] matrizSaliendoai = new double[5][3];
        double[] B;
        double infoMutua,infoMutuaB,entropiaAfin,entropiaAFinB;

        cargaDatosCanalUno(A,matrizSaliendoai);
        B = calculaB(A,matrizSaliendoai,1);
        double[][] matrizSaliendoBj = calculaMatrizSaliendoBj(A,B,matrizSaliendoai);
        infoMutua = calculaEntropiaAPriori(A) - calculaEquivocacion(matrizSaliendoBj,1,B);
        entropiaAfin = calculaEntropiaAPriori(B)+calculaEquivocacion(matrizSaliendoBj,1,B);

        infoMutuaB = calculaEntropiaAPriori(B) - calculaEquivocacionB(matrizSaliendoai,1,A);
        entropiaAFinB = calculaEntropiaAPriori(A) + calculaEquivocacionB(matrizSaliendoai,1,A);

            pw.println("++++++++ Canal 1 ++++++++");
            pw.println("Entropia a priori de la fuente: H(A) = " + calculaEntropiaAPriori(A));
            pw.println("Entropia a priori de la salida del canal: H(B) = "+calculaEntropiaAPriori(B));
            pw.println("");
            pw.println("Equivocacion: H(A/B) = "+calculaEquivocacion(matrizSaliendoBj,1,B));
            pw.println("Informacion Mutua: I(A,B) = " + infoMutua);
            pw.println("Entropia Afin: H(A,B) = H(B)+H(A/B) = "+  entropiaAfin);
            pw.println("");
            pw.println("Equivocacion: H(B/A) = "+calculaEquivocacionB(matrizSaliendoai,1,A));
            pw.println("Informacion mutua: I(B,A) = " + infoMutuaB);
            pw.println("Entropia Afin: H(A,B) = H(A)+H(B/A) = " + entropiaAFinB);
            pw.println("");
            pw.println("Entropia a posteriori recibido B1: H(A/b1) = "+calculaEntropiaPosteriori(matrizSaliendoBj,1,1));
            pw.println("Entropia a posteriori recibido B2: H(A/b2) = "+calculaEntropiaPosteriori(matrizSaliendoBj,2,1));
            pw.println("Entropia a posteriori recibido B3: H(A/b3) = "+calculaEntropiaPosteriori(matrizSaliendoBj,3,1));
            pw.println("");
    }

    public static void ejecutaCanalDos(PrintWriter pw) {

        double[] A = new double[4];
        double[][] matrizSaliendoai = new double[4][4];
        double[] B;
        double infoMutua,infoMutuaB,entropiaAfin,entropiaAFinB;

        cargaDatosCanalDos(A,matrizSaliendoai);
        B = calculaB(A,matrizSaliendoai,2);
        double[][] matrizSaliendoBj = calculaMatrizSaliendoBj(A,B,matrizSaliendoai);
        infoMutua = calculaEntropiaAPriori(A) - calculaEquivocacion(matrizSaliendoBj,2,B);
        entropiaAfin = calculaEntropiaAPriori(B)+calculaEquivocacion(matrizSaliendoBj,2,B);

        infoMutuaB = calculaEntropiaAPriori(B) - calculaEquivocacionB(matrizSaliendoai,2,A);
        entropiaAFinB = calculaEntropiaAPriori(A) + calculaEquivocacionB(matrizSaliendoai,2,A);

            pw.println("++++++++ Canal 2 ++++++++");
            pw.println("Entropia a priori de la fuente: H(A) = " + calculaEntropiaAPriori(A));
            pw.println("Entropia a priori de la salida del canal: H(B) = "+calculaEntropiaAPriori(B));
            pw.println("");
            pw.println("Equivocacion: H(A/B) = "+calculaEquivocacion(matrizSaliendoBj,2,B));
            pw.println("Informacion Mutua: I(A,B) = " + infoMutua);
            pw.println("Entropia Afin: H(A,B) = H(B)+H(A/B) = "+  entropiaAfin);
            pw.println("");
            pw.println("Equivocacion: H(B/A) = "+calculaEquivocacionB(matrizSaliendoai,2,A));
            pw.println("Informacion mutua: I(B,A) = " + infoMutuaB);
            pw.println("Entropia Afin: H(A,B) = H(A)+H(B/A) = " + entropiaAFinB);
            pw.println("");
            pw.println("Entropia a posteriori recibido B1: H(A/b1) = "+calculaEntropiaPosteriori(matrizSaliendoBj,1,2));
            pw.println("Entropia a posteriori recibido B2: H(A/b2) = "+calculaEntropiaPosteriori(matrizSaliendoBj,2,2));
            pw.println("Entropia a posteriori recibido B3: H(A/b3) = "+calculaEntropiaPosteriori(matrizSaliendoBj,3,2));
            pw.println("Entropia a posteriori recibido B4: H(A/b4) = "+calculaEntropiaPosteriori(matrizSaliendoBj,4,2));
            pw.println("");
    }

    public static void ejecutaCanalTres(PrintWriter pw) {

        double[] A = new double[6];
        double[][] matrizSaliendoai = new double[6][4];
        double[] B;
        double infoMutua,infoMutuaB,entropiaAfin,entropiaAFinB;

        cargaDatosCanalTres(A,matrizSaliendoai);
        B = calculaB(A,matrizSaliendoai,3);
        double[][] matrizSaliendoBj = calculaMatrizSaliendoBj(A,B,matrizSaliendoai);
        infoMutua = calculaEntropiaAPriori(A) - calculaEquivocacion(matrizSaliendoBj,3,B);
        entropiaAfin = calculaEntropiaAPriori(B)+calculaEquivocacion(matrizSaliendoBj,3,B);

        infoMutuaB = calculaEntropiaAPriori(B) - calculaEquivocacionB(matrizSaliendoai,3,A);
        entropiaAFinB = calculaEntropiaAPriori(A) + calculaEquivocacionB(matrizSaliendoai,3,A);

            pw.println("++++++++ Canal 3 ++++++++");
            pw.println("Entropia a priori de la fuente: H(A) = " + calculaEntropiaAPriori(A));
            pw.println("Entropia a priori de la salida del canal: H(B) = "+calculaEntropiaAPriori(B));
            pw.println("");
            pw.println("Equivocacion: H(A/B) = "+calculaEquivocacion(matrizSaliendoBj,3,B));
            pw.println("Informacion Mutua: I(A,B) = " + infoMutua);
            pw.println("Entropia Afin: H(A,B) = H(B)+H(A/B) = "+  entropiaAfin);
            pw.println("");
            pw.println("Equivocacion: H(B/A) = "+calculaEquivocacionB(matrizSaliendoai,3,A));
            pw.println("Informacion mutua: I(B,A) = " + infoMutuaB);
            pw.println("Entropia Afin: H(A,B) = H(A)+H(B/A) = " + entropiaAFinB);
            pw.println("");
            pw.println("Entropia a posteriori recibido B1: H(A/b1) = "+calculaEntropiaPosteriori(matrizSaliendoBj,1,3));
            pw.println("Entropia a posteriori recibido B2: H(A/b2) = "+calculaEntropiaPosteriori(matrizSaliendoBj,2,3));
            pw.println("Entropia a posteriori recibido B3: H(A/b3) = "+calculaEntropiaPosteriori(matrizSaliendoBj,3,3));
            pw.println("Entropia a posteriori recibido B4: H(A/b4) = "+calculaEntropiaPosteriori(matrizSaliendoBj,4,3));
            pw.println("");
    }

}
