package parte2;

import static java.lang.Math.log;

public class Parte2 {


        private double[] A = new double[5];
        private double[][] matrizSaliendoai = new double[5][3];


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
        public static double[] calculaB(double[] A, double[][] matrizSaliendoai){

            double[] B = new double[3];
            int i,j;

            for (i=0;i<3;i++)
                B[i]=0;

            for(i=0;i<3;i++){
                for(j=0;j<A.length;j++){
                    B[i] += matrizSaliendoai[j][i] * A[j];
                }
            }

            return B;
        }


        public static double[][] calculaMatrizSaliendoBj(double[] A, double[] B, double[][] matrizSaliendoai){

            double[][] matrizSaliendoBj = new double[5][3];
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
                cantSimbolosEntrada=0;

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

        public static double calculaInformacionMutua(double[][] matrizSaliendoBj,int canal,double[] B,double[] A){

            return calculaEntropiaAPriori(A) - calculaEquivocacion(matrizSaliendoBj,canal,B);

        }


    public static void main(String[] args) {

        double[] A = new double[5];
        double[][] matrizSaliendoai = new double[5][3];
        double[] B = new double[3];
        double entropiaAPriori,entropiaAPosteriori,equivocacion,infoMutua;

        cargaDatosCanalUno(A,matrizSaliendoai);
        B = calculaB(A,matrizSaliendoai);
        double[][] matrizSaliendoBj = calculaMatrizSaliendoBj(A,B,matrizSaliendoai);
        entropiaAPriori = calculaEntropiaAPriori(A);
        entropiaAPosteriori = calculaEquivocacion(matrizSaliendoBj,1,B);
        infoMutua = calculaInformacionMutua(matrizSaliendoBj,1,B,A);

        System.out.println("informacion mutua = "+infoMutua);

    }



}
