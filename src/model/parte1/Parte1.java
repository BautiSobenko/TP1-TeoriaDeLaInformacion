package model.parte1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import Jama.Matrix;

public class Parte1 {

	public static void main(String[] args) {

		try {
			String path = "DatosTP1.txt";
			File file = new File(path);
			BufferedReader archivo = new BufferedReader(new FileReader(file));
            String datos = archivo.readLine();
            int len = datos.length();
            
            int[][] matrizApariciones = new int[3][3];    //Matriz de apariciones si el simbolo previo a X es A | B | C
            int[]  aparicionesTotales = new int[3];       //Apariciones totales por simbolo
            double[][]  matrizEstados = new double[3][3]; //Matriz de transicion de estados (probabilidades)

			//Inicializacion de contadores
            for(int i=0 ; i<3 ; i++) {
				aparicionesTotales[i] = 0;
            	for(int j=0 ; j<3 ; j++) {
            		matrizApariciones[i][j] = 0;
            	}
            }

			for(int i=1 ; i<len ; i++) {
            	matrizApariciones[BuscaIndice(datos.charAt(i-1))][BuscaIndice(datos.charAt(i))] += 1;
            	aparicionesTotales[BuscaIndice(datos.charAt(i))] += 1;
			}
            
            //Calculo de probabilidades condicionales
            for(int i=0 ; i<3 ; i++) {
            	for(int j=0 ; j<3 ; j++) {
            		matrizEstados[i][j] = (double) matrizApariciones[i][j] / aparicionesTotales[i];
            	}
            }
            
            //Comenzamos con calculo de vector estacionario

			// a*(m[0][0]-1) +    b*m[0][1]     +    c*m[0][2]     =    0
			// a*m[1][0]     +    b*(m[1][1]-1) +    c*m[1][2]     =    0
			// a*m[2][0]     +    b*m[2][1]     +    c*(m[2][2]-1) =    0
			// a             +    b             +    c             =    1

			double[][] sistemaEc = new double[4][3]; //Inicializo el sistema de ec
            double[]   resultados = new double[4];
            
            for(int i=0 ; i<3 ; i++) {
            	resultados[i] = 0;
            	sistemaEc[3][i] = 1; // a + b + c -> { 1, 1, 1 } , Ultima fila con 1's
            }
            resultados[3] = 1;

			for(int i=0 ; i<3 ; i++) {
            	for(int j=0 ; j<3 ; j++) {
            		sistemaEc[i][j] = matrizEstados[i][j];
            		if(i == j) //Diagonal
            			sistemaEc[i][j] -= 1.00;
            	}
            }

			System.out.println("Matriz del sistema");
			for(int x=0 ; x < sistemaEc.length ; x++) {
            	  System.out.print("|");
            	  for (int y=0 ; y < sistemaEc[x].length ; y++) {
            	    System.out.print (sistemaEc[x][y]);
            	    if (y != sistemaEc[x].length-1) System.out.print("\t");
            	  }
            	  System.out.println("|");
			}
			System.out.println();

			System.out.println("Vector terminos independientes");
			System.out.print("[  ");
			for(int h=0; h<4; h++){
				System.out.print(resultados[h] + "  ");
			}
			System.out.println("]\n");

			//Resolucion del sistema de ecuaciones usando JAMA

			Matrix var = new Matrix(sistemaEc);
            Matrix sol = new Matrix(resultados,4);
            
            Matrix ans = var.solve(sol);

			System.out.println("Resultados del sistema de ecuaciones = Vector estacionario");
			System.out.println("A = " + ans.get(0, 0));
            System.out.println("B = " + ans.get(1, 0));
            System.out.println("C = " + ans.get(2, 0));

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static int BuscaIndice(char i) {
		
		if(i == 'A')
			return 0;     // posicion 0 si es A
		else if(i == 'B')
			return 1;     // posicion 1 si es B
		else
			return 2;     // posicion 2 si es C
	}
	
	

}
