package model.parte1;

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
            	matrizApariciones[BuscaIndice(datos.charAt(i-1))][BuscaIndice(datos.charAt(i))] += 1;
            	aparicionesTotales[BuscaIndice(datos.charAt(i))] += 1;
			}

            //Probabilidades condicionales
            for(int i=0 ; i<3 ; i++) {
            	for(int j=0 ; j<3 ; j++) {
            		matrizEstados[i][j] = (double) matrizApariciones[i][j] / aparicionesTotales[i];
            	}
            }

			boolean esMemoriaNula = isMemoriaNula(matrizEstados);
			Escritura.resultadoIncisoA(matrizEstados, esMemoriaNula);

			if( esMemoriaNula )
				System.out.println("Fuente de memoria nula\n");
			else{
				System.out.println("Fuente de memoria no nula");

				boolean esErgodica = isErgodica(matrizEstados);

				if( !esErgodica ){
					System.out.println("Fuente no ergodica\n");
					Escritura.resultadoIncisoC(esErgodica,null,null,null);
				}
				else{
					System.out.println("Fuente ergodica\n");

					//Calculo de vector estacionario

					// a * (m[0][0]-1) +    b * m[0][1]     +    c * m[0][2]     =    0
					// a *  m[1][0]    +    b * (m[1][1]-1) +    c * m[1][2]     =    0
					// a * m[2][0]     +    b * m[2][1]     +    c * (m[2][2]-1) =    0
					// a               +    b               +    c               =    1

					double[][] matrizCoefSistEc = new double[3][3]; //Inicializo el sistema de ec
					double[][] matrizOriginalSistEc = new double[3][3];
					double[]   resultadosSistEc = new double[3];

					// Creacion de Matriz de Coeficientes

					int cantVar = 3;

					for(int j = 0 ; j < cantVar ; j++) {
						matrizCoefSistEc[0][j] = 1.00; // a + b + c + ... + n
						matrizOriginalSistEc[0][j] = 1.00;
					}
					for(int i = 1 ; i < cantVar ; i++){
						for(int j = 0 ; j < cantVar ; j++) {
							matrizCoefSistEc[i][j] = matrizEstados[i][j];
							matrizOriginalSistEc[i][j] = matrizEstados[i][j];
							if(i == j){ //Diagonal
								matrizCoefSistEc[i][j] -= 1.00;
								matrizCoefSistEc[i][j] -= 1.00;
							}
						}
					}

					// Creacion de Vector Resultado

					resultadosSistEc[0] = 1.;
					for (int i = 1 ; i < cantVar ; i++)
						resultadosSistEc[i] = 0.;

					//Resolucion del sistema utilizando Eliminacion de Gauss

					EliminacionGauss gauss = new EliminacionGauss();

					double[] vectorEstacionario = gauss.resolver(matrizCoefSistEc,resultadosSistEc);

					double entropia = entropia(matrizEstados, vectorEstacionario);

					Escritura.resultadoIncisoC(esErgodica,matrizOriginalSistEc,vectorEstacionario,entropia);

				}

			}
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

	public static boolean isMemoriaNula(double[][] matrizEstados){
		boolean isMemoriaNula = false;
		int j;
		int i = 0;

		while(i<3 && !isMemoriaNula) {
			j = 0;
			while(j<2 && !isMemoriaNula) {
				if(matrizEstados[i][j] != matrizEstados[i][j+1])
					j++;
				else
					isMemoriaNula = true;
			}
			i++;
		}
		return isMemoriaNula;
	}

	private static boolean isErgodica(double[][] matrizEstados) {

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
