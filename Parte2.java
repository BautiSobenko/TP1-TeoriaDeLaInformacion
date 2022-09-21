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
		
		// Guardo las Frecuencias
		int[] frecuencias = new int[combinaciones.size()];
		
		for(i=0;i<combinaciones.size();i++) {
		   frecuencias[i] = Collections.frequency(listaPal,combinaciones.get(i));
			System.out.printf(combinaciones.get(i) + 'frecuencia de la palabra' + frecuencias[i]);
		   // System.out.println("Frecuencia de "+combinaciones.get(i)+" es:"+Collections.frequency(listaPal,combinaciones.get(i)));
		}
			System.out.printf(f);
		
		///Cual seria la definicion de entropia en este caso?
		///Cual es la probablidad de cada simbolo? Lo tenemos que calcular?
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
