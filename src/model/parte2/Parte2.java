package model.parte2;

import model.parte2.codigos.Codigo;
import model.parte2.huffman.Huffman;
import model.utlils.Escritura;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class Parte2 {

	public static void main(String[] args) {

		try {

			String path = "DatosTP1.txt";
			File file = new File(path);
			BufferedReader archivo = new BufferedReader(new FileReader(file));
			String datos = archivo.readLine();
			int len = datos.length();

			int tamanioPalabra = 7;
			int i = 0;
			ArrayList<String> listaPal = new ArrayList<String>(); //Guardo todas las Strings formadas
			String formada;

			while (len - i >= tamanioPalabra) { // Mientras no me quede resto sigo. Si i+tamanio > len crashea por el substring.
				formada = datos.substring(i, i + tamanioPalabra);
				listaPal.add(formada);
				i += tamanioPalabra; //Avanzo a la siguiente palabra
			}

			// Guardo en combinaciones las combinaciones unicas

			ArrayList<String> combinaciones = (ArrayList<String>) listaPal.stream().distinct().collect(Collectors.toList());

			Codigo codigo = new Codigo(combinaciones);
			Escritura.resultadoIncisoB(codigo, Integer.toString(tamanioPalabra));

			int[] frecuencias = new int[combinaciones.size()];
			for (i = 0; i < combinaciones.size(); i++) {
				frecuencias[i] = Collections.frequency(listaPal, combinaciones.get(i));
			}

			//Probabilidades

			float[] probabilidades = new float[combinaciones.size()];
			int frecTotal = 0;

			for (i = 0; i < frecuencias.length; i++) {
				frecTotal += frecuencias[i];
			}

			for (i = 0; i < frecuencias.length; i++) {
				probabilidades[i] = ((float) frecuencias[i]) / frecTotal;
			}

			//Cantidad de informacion

			double[] informaciones = calculoInformacion(combinaciones ,probabilidades);
			double cantInformacion = cantidadInformacion(informaciones);

			//Entropia

			double entropia = calculoEntropia(informaciones, probabilidades);

			Escritura.resultadoIncisoA(cantInformacion, entropia, Integer.toString(tamanioPalabra));

			//Kraft

			int numSimbolosDistintos = 3;
			float kraft = (float) (Math.pow(numSimbolosDistintos, (-tamanioPalabra)) * combinaciones.size());

			//Longitud Media

			double longMedia = calculoLongMedia(probabilidades, tamanioPalabra);

			//Condicion codigo compacto

			boolean esCompacto = longMedia <= tamanioPalabra;

			Escritura.resultadoIncisoC(kraft, longMedia, esCompacto, Integer.toString(tamanioPalabra));

			// Rendimiento y redundancia

			double rendimiento = rendimiento(entropia,longMedia);
			double redundancia = redundancia(rendimiento);

			Escritura.resultadoIncisoD(rendimiento, redundancia, Integer.toString(tamanioPalabra));

			//Codificacion Huffman

			Huffman huffman = new Huffman(listaPal);
			huffman.encode();

			Escritura.resultadoIncisoE1(huffman, Integer.toString(tamanioPalabra));

			Escritura.resultadoIncisoE2(huffman, Integer.toString(tamanioPalabra));

		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static double cantidadInformacion(double[] informaciones){
		double informacionFuente = 0;
		for (int i = 0; i < informaciones.length; i++) {
			informacionFuente += informaciones[i];
		}
		return informacionFuente;
	}

	public static double[] calculoInformacion( ArrayList<String> combinaciones, float[] probabilidades){
		double[] informaciones = new double[combinaciones.size()];

		for (int i = 0; i < probabilidades.length; i++) {
			informaciones[i] = Math.log10(1 / probabilidades[i]) / Math.log10(3);
		}

		return informaciones;

	}

	public static double calculoEntropia(double[] informaciones, float[] probabilidades){
		double entropia = 0;

		for (int i = 0; i < probabilidades.length; i++) {
			entropia += informaciones[i] * probabilidades[i];
		}

		return entropia;
	}

	public static double calculoLongMedia(float[] probabilidades, int tamanioPalabra){
		double sum = 0;

		for (float probabilidad : probabilidades) {
			sum += probabilidad;
		}

		return sum * tamanioPalabra;
	}

	public static double rendimiento(double entropia, double longitudMedia){
			return entropia / longitudMedia;
	}

	public static double redundancia(double rendimiento){
		return 1 - rendimiento;
	}

}
