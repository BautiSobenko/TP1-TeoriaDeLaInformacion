package parte1;

import parte1.huffman.Huffman;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Escritura {

    public static void resultadoParte1(Huffman huffman, int cantSimbolos,  int longMaxPalabraFuente, int longMaxPalabraCodigo, double tasaCompresion, double rendimiento, double redundancia) {

        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./resultados/primera-parte/prop-codigo-huffman.txt");
            PrintWriter pw = new PrintWriter(fichero);

            pw.printf("Tasa compresion: %.4f \n" , tasaCompresion);
            pw.printf("Rendimiento: %.4f \n" , rendimiento);
            pw.printf("Redundancia: %.4f \n" , redundancia);

            pw.close();

            fichero = new FileWriter("./resultados/primera-parte/compresion.huf");
            pw = new PrintWriter(fichero);

            //Escritura de tabla de codificacion en archivo binario

            StringBuilder tablaCod = huffman.tablaCodificacion();

            pw.write(cantSimbolos);
            pw.write(longMaxPalabraFuente);
            pw.write(longMaxPalabraCodigo);

            byte data;
            int limite;

            for (int i = 0; i < tablaCod.length(); i++) {
                if (i + 8 <= tablaCod.length())
                    limite = 8;
                else
                    limite = tablaCod.length() - i;

                data = 0b00000000;
                //Lectura del octeto o lo que quede del octeto (codigo.length - i)
                for (int j = 0; j < limite; j++) {
                    if (tablaCod.charAt(i) == '1') {
                        data |= (0b00000001 << (7 - j));
                    }
                    i++;
                }
                i--; //Con el i++ de la ultima iteracion del for del octeto me salteo una posicion de i

                //Print en file binario del octeto
                pw.write(data);
            }

            //Escritura de codificacion del archivo original en archivo binario

            String codigo = huffman.getCodigo();

            for (int i = 0; i < codigo.length(); i++) {
                if (i + 8 <= codigo.length())
                    limite = 8;
                else
                    limite = codigo.length() - i;

                data = 0b00000000;
                //Lectura del octeto o lo que quede del octeto (codigo.length - i)
                for (int j = 0; j < limite; j++) {
                    if (codigo.charAt(i) == '1') {
                        data |= (0b00000001 << (7 - j));
                    }
                    i++;
                }
                i--; //Con el i++ de la ultima iteracion del for del octeto me salteo una posicion de i

                //Print en file binario del octeto
                pw.write(data);

            }

            pw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
