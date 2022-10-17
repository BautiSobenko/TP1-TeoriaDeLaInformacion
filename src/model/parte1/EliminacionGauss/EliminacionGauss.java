package model.parte1.EliminacionGauss;

import java.util.Scanner;

public class EliminacionGauss {

        public double[] resolver(double[][] coeficientes, double[] rtados) {
            int N = rtados.length;

            for (int k = 0; k < N; k++) {

                //Encuentro fila pivote
                int max = k;
                for (int i = k + 1; i < N; i++)
                    if (Math.abs(coeficientes[i][k]) > Math.abs(coeficientes[max][k]))
                        max = i;

                /** swap fila en matriz coeficientes **/
                double[] temp = coeficientes[k];
                coeficientes[k] = coeficientes[max];
                coeficientes[max] = temp;

                /** swap valores en vector de resultados **/
                double t = rtados[k];
                rtados[k] = rtados[max];
                rtados[max] = t;

                /** pivot within A and B **/
                for (int i = k + 1; i < N; i++)
                {
                    double factor = coeficientes[i][k] / coeficientes[k][k];
                    rtados[i] -= factor * rtados[k];
                    for (int j = k; j < N; j++)
                        coeficientes[i][j] -= factor * coeficientes[k][j];
                }
            }

            printMatrizEscalonada(coeficientes, rtados);

            /** Sustitucion hacia atras **/
            double[] solucion = new double[N];
            for (int i = N - 1; i >= 0; i--)
            {
                double sum = 0.0;
                for (int j = i + 1; j < N; j++)
                    sum += coeficientes[i][j] * solucion[j];
                solucion[i] = (rtados[i] - sum) / coeficientes[i][i];
            }

            printSolucion(solucion);

            return solucion;

        }

        public void printMatrizEscalonada(double[][] A, double[] B)
        {
            int N = B.length;
            System.out.println("\nMatriz Escalonada : ");
            for (int i = 0; i < N; i++)
            {
                for (int j = 0; j < N; j++)
                    System.out.printf("%.3f ", A[i][j]);
                System.out.printf("| %.3f\n", B[i]);
            }
            System.out.println();
        }


        public void printSolucion(double[] sol)
        {
            int N = sol.length;
            System.out.println("Resultados del sistema de ecuaciones = Vector estacionario");
            for (int i = 0; i < N; i++)
                System.out.printf(i+")  %.7f\n", sol[i]);
            System.out.println();
        }

}
