package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class Parte2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
		
		String path = "DatosTP1.txt";
		File file = new File(path);
		BufferedReader archivo = new BufferedReader(new FileReader(file));
        String datos = archivo.readLine();
        int len = datos.length();
		
		int tamanioPalabra = 3; //
		int i = 0;
		ArrayList<String> listaPal = new ArrayList<String>(); //Guardo todas las Strings formadas
		String formada;
		
		while(len-i >= tamanioPalabra) { // Mientras no me quede resto sigo. Si i+tamanio > len crashea por el substring.
			formada = datos.substring(i, i+tamanioPalabra);
			listaPal.add(formada);
			i += tamanioPalabra; //Avanzo a la siguiente palabra
		}
		
		int n = listaPal.size();
		
		// Guardo en combinaciones las combinaciones unicas
		
		ArrayList<String> combinaciones = (ArrayList<String>) listaPal.stream().distinct().collect(Collectors.toList());

		int[] frecuencias = new int[combinaciones.size()];
		
		for(i=0;i<combinaciones.size();i++) {
		   frecuencias[i] = Collections.frequency(listaPal,combinaciones.get(i));
		   System.out.println("Frecuencia de "+combinaciones.get(i)+" es:"+ frecuencias[i]);
		}

		// Calculo de PROBABILIDADES de las palabras codigo

		float [] probabilidades = new float[combinaciones.size()];
		int frecTotal = 0;

		for(i = 0 ; i < frecuencias.length ; i++){
			frecTotal += frecuencias[i];
		}

		System.out.printf(" -- PROBABILIDADES --\n");
		for (i = 0; i < frecuencias.length ; i++){
			probabilidades[i] = ((float)frecuencias[i]) / frecTotal;
			System.out.println("Probabilidad de "+combinaciones.get(i)+" es:"+ probabilidades[i]);
		}

		// Calculo de INFORMACIONES

		double[] informaciones = new double[combinaciones.size()];

		System.out.printf(" -- INFORMACION --\n");
		for (i = 0; i < frecuencias.length ; i++){
			informaciones[i] = Math.log10(1 / probabilidades[i]);
			System.out.println("Informacion de "+ combinaciones.get(i)+" es:"+ informaciones[i]);
		}
		// Calculo de ENTROPIA

		double entropia = 0;

		System.out.printf(" -- ENTROPIA --\n");
		for (i = 0; i < frecuencias.length ; i++){
			entropia += informaciones[i]*probabilidades[i];
		}

		System.out.println("Entropia es " + entropia);
		///Cual seria la definicion de entropia en este caso?
		///Cual es la probablidad de cada simbolo? Lo tenemos que calcular?
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
