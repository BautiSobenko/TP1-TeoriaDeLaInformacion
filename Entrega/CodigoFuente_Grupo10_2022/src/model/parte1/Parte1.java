package model.parte1;

import model.parte1.FuenteInformacion.FuenteInformacion;
import model.utlils.Escritura;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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
            	matrizApariciones[FuenteInformacion.buscaIndice(datos.charAt(i-1))][FuenteInformacion.buscaIndice(datos.charAt(i))] += 1;
            	aparicionesTotales[FuenteInformacion.buscaIndice(datos.charAt(i))] += 1;
			}

            //Probabilidades condicionales
            for(int i=0 ; i<3 ; i++) {
            	for(int j=0 ; j<3 ; j++) {
            		matrizEstados[i][j] = (double) matrizApariciones[i][j] / aparicionesTotales[i];
            	}
            }

			boolean esMemoriaNula = FuenteInformacion.isMemoriaNula(matrizEstados, 0.05);
			Escritura.resultadoIncisoA(matrizEstados, esMemoriaNula);

			if( esMemoriaNula ) {
				System.out.println("\nFuente de memoria nula\n");
			}
			else{
				System.out.println("\nFuente de memoria no nula");
				boolean esErgodica = FuenteInformacion.isErgodica(matrizEstados);

				if( !esErgodica ){
					System.out.println("Fuente no ergodica\n");
					Escritura.resultadoIncisoC(esErgodica,null,null,null);
				}
				else{
					System.out.println("Fuente ergodica");

					//Calculo de vector estacionario

					// a * (m[0][0]-1) +    b * m[0][1]     +    c * m[0][2]     =    0
					// a *  m[1][0]    +    b * (m[1][1]-1) +    c * m[1][2]     =    0
					// a * m[2][0]     +    b * m[2][1]     +    c * (m[2][2]-1) =    0
					// a               +    b               +    c               =    1

					double[][] matrizCoefSistEc = new double[3][3]; //Inicializo el sistema de ec
					// Creacion de Matriz de Coeficientes

					int cantVar = 3;

					for(int j = 0 ; j < cantVar ; j++) {
						matrizCoefSistEc[0][j] = 1.00; // a + b + c + ... + n
					}
					for(int i = 1 ; i < cantVar ; i++){
						for(int j = 0 ; j < cantVar ; j++) {
							matrizCoefSistEc[i][j] = matrizEstados[i][j];
							if(i == j){ //Diagonal
								matrizCoefSistEc[i][j] -= 1.00;
							}
						}
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}





}


	
	

