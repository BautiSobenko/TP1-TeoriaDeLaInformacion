package model;

public class Pruebas {

	public static void main(String[] args) {
		
		String  primer = "abcabcab";
		int i=0;
		String formada = "";
		while(primer.length()-i >= 3) {
			formada = primer.substring(i, i+3);
			System.out.println(formada);
			i += 3;
		}
		
	}

}
