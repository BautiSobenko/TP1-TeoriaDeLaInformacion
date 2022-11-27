package parte1.utils;

import parte1.huffman.Huffman;
import parte1.huffman.MetodosCodigoHuffman;
import parte1.shannonFano.MetodosCodigoShannon;
import parte1.shannonFano.ShannonFano;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Escritura {

    public static void resultadoParte1Huffman(Huffman huffman, int cantSimbolos, int longMaxPalFuente, int longMaxPalCodigo, double tasaCompresion, double rendimiento, double redundancia) {

        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./resultados/primera-parte/resultados-huffman.txt");
            PrintWriter pw = new PrintWriter(fichero);

            pw.printf("Tasa compresion: %.4f \n" , tasaCompresion);
            pw.printf("Rendimiento: %.4f \n" , rendimiento);
            pw.printf("Redundancia: %.4f \n" , redundancia);
            pw.printf("Cantidad de simbolos: %d \n", cantSimbolos);
            pw.printf("Longitud maxima de palabra fuente: %d \n", longMaxPalFuente);
            pw.printf("Longitud maxima de palabra codigo: %d \n", longMaxPalCodigo );


            pw.close();

            RandomAccessFile fileHuf = new RandomAccessFile("./resultados/primera-parte/compresion.huf", "rw");

            //Escritura de tabla de codificacion en archivo binario

            Map<String, String> tablaCodigo = huffman.getHuffmanCodes();

                /*
                Tabla de decodificacion compuesta por:
                    Codigo (2 bytes)
                    Longitud de bytes a leer ( 1 byte = Numero al multiplicar: 2 (tam char)  * long pal )
                    Palabra ( 2 bytes (char)  * long pal ) -> Alojada en forma dinamica
                 */

            int pos = 0;

            fileHuf.seek(pos);
            fileHuf.writeInt(cantSimbolos);
            pos += 4;

            fileHuf.seek(pos);
            fileHuf.writeInt(longMaxPalCodigo);
            pos += 4;

            fileHuf.seek(pos);
            fileHuf.writeInt(longMaxPalFuente);
            pos += 4;


            for (Map.Entry<String, String> entry : tablaCodigo.entrySet()) {

                String codigoTabla = entry.getValue();

                fileHuf.seek(pos);
                fileHuf.writeUTF(codigoTabla);
                pos += 2 * longMaxPalCodigo;

                String palabraTabla = entry.getKey();

                fileHuf.seek(pos);
                fileHuf.writeUTF(palabraTabla);
                pos += 2 * longMaxPalFuente;

            }

            //Escritura de codificacion del archivo original en archivo binario

            String codigoHuf = huffman.getCodigo();

            int limite;

            for (int i = 0; i < codigoHuf.length(); i++) {
                if (i + 8 <= codigoHuf.length())
                    limite = 8;
                else
                    limite = codigoHuf.length() - i;

                byte data = 0b00000000;
                //Lectura del octeto o lo que quede del octeto (codigo.length - i)
                for (int j = 0; j < limite; j++) {
                    if (codigoHuf.charAt(i) == '1') {
                        data |= (0b00000001 << (7 - j));
                    }
                    i++;
                }
                i--; //Con el i++ de la ultima iteracion del for del octeto me salteo una posicion de i

                //Print en file binario del octeto
                fileHuf.seek(pos);
                fileHuf.writeByte(data);
                pos += 1;

            }

            fileHuf.close();

            Escritura.palabrasCodigoHuffman(huffman);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toBinary(byte n, int len)
    {
        String binary = "";
        for (long i = (1L << len - 1); i > 0; i = i / 2) {
            binary += (n & i) != 0 ? "1" : "0";
        }
        return binary;
    }
    public static String toBinary(char n, int len)
    {
        String binary = "";
        for (long i = (1L << len - 1); i > 0; i = i / 2) {
            binary += (n & i) != 0 ? "1" : "0";
        }
        return binary;
    }

    public static void decodificacionShannon()  {

        RandomAccessFile fileSha = null;
        try {
            fileSha = new RandomAccessFile("./resultados/primera-parte/compresion.fan", "rw");

            Map<String, String> tablaCodigo = new HashMap<>();

            int pos = 0;

            fileSha.seek(pos);
            int cantSimbolos = fileSha.readInt();
            pos += 4;

            fileSha.seek(pos);
            int longMaxPalCodigo = fileSha.readInt();
            pos += 4;

            fileSha.seek(pos);
            int longMaxPalFuente = fileSha.readInt();
            pos += 4;


            for (int i = 0 ; i < cantSimbolos ; i++) {

                fileSha.seek(pos);
                String codigoTabla = fileSha.readUTF();
                pos += 2 * longMaxPalCodigo;

                fileSha.seek(pos);
                String palabraTabla = fileSha.readUTF();
                pos += 2 * longMaxPalFuente;

                tablaCodigo.put(palabraTabla, codigoTabla);

            }

            FileWriter decod = new FileWriter("./resultados/primera-parte/descompresion-shannon.txt");
            PrintWriter pw = new PrintWriter(decod);

            ArrayList<Character> bytes = new ArrayList<>();
            while( true ) {

                try {
                    fileSha.seek(pos);
                    bytes.add(fileSha.readChar());
                    pos += 2;

                } catch (IOException e) {
                    break;
                }
            }

            StringBuilder bytesStr = new StringBuilder();
            for( int i = 0; i < bytes.size() ; i++){


                for (int j = 0; j < 16; j++) {
                    if (((bytes.get(i) >> (15 - j)) & 0x1) == 1) {
                        bytesStr.append("1");
                    } else {
                        bytesStr.append("0");
                    }
                }
            }

            String codigoStr = bytesStr.toString();

            while( codigoStr.length() > 4 ){

                //System.out.println(codigoStr.length());

                for (Map.Entry<String, String> entry : tablaCodigo.entrySet()) {
                    String codigoTabla = entry.getValue();

                    if( codigoStr.startsWith(codigoTabla) ){
                        pw.printf( entry.getKey() + " ");
                        codigoStr = codigoStr.substring(codigoTabla.length());
                        break;
                    }
                }

            }

            pw.close();
            fileSha.close();

        } catch (IOException e) {
                throw new RuntimeException(e);
        }


    }

    public static void decodificacionHuffman()  {

        RandomAccessFile fileHuf = null;
        try {
            fileHuf = new RandomAccessFile("./resultados/primera-parte/compresion.huf", "rw");

            Map<String, String> tablaCodigo = new HashMap<>();

            int pos = 0;

            fileHuf.seek(pos);
            int cantSimbolos = fileHuf.readInt();
            pos += 4;

            fileHuf.seek(pos);
            int longMaxPalCodigo = fileHuf.readInt();
            pos += 4;

            fileHuf.seek(pos);
            int longMaxPalFuente = fileHuf.readInt();
            pos += 4;


            for (int i = 0 ; i < cantSimbolos ; i++) {

                fileHuf.seek(pos);
                String codigoTabla = fileHuf.readUTF();
                pos += 2 * longMaxPalCodigo;

                fileHuf.seek(pos);
                String palabraTabla = fileHuf.readUTF();
                pos += 2 * longMaxPalFuente;

                tablaCodigo.put(palabraTabla, codigoTabla);

            }

            FileWriter decod = new FileWriter("./resultados/primera-parte/descompresion-huffman.txt");
            PrintWriter pw = new PrintWriter(decod);

            ArrayList<Character> bytes = new ArrayList<>();
            while( true ) {

                try {
                    fileHuf.seek(pos);
                    bytes.add(fileHuf.readChar());
                    pos += 2;

                } catch (IOException e) {
                    break;
                }
            }

            StringBuilder bytesStr = new StringBuilder();
            for( int i = 0; i < bytes.size() ; i++){


                for (int j = 0; j < 16; j++) {
                    if (((bytes.get(i) >> (15 - j)) & 0x1) == 1) {
                        bytesStr.append("1");
                    } else {
                        bytesStr.append("0");
                    }
                }
            }

            String codigoStr = bytesStr.toString();

            while( codigoStr.length() > 4 ){

                //System.out.println(codigoStr.length());

                for (Map.Entry<String, String> entry : tablaCodigo.entrySet()) {
                    String codigoTabla = entry.getValue();

                    if( codigoStr.startsWith(codigoTabla) ){
                        pw.printf( entry.getKey() + " " );
                        codigoStr = codigoStr.substring(codigoTabla.length());
                        break;
                    }
                }

            }

            fileHuf.close();
            pw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static void resultadosParte1Shannon(ShannonFano shannonFano,int cantSimbolos, int longMaxPalFuente, int longMaxPalCodigo, double tasaCompresion, double rendimiento, double redundancia ){

        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./resultados/primera-parte/resultados-shannon.txt");
            PrintWriter pw = new PrintWriter(fichero);

            pw.printf("Tasa compresion: %.4f \n" , tasaCompresion);
            pw.printf("Rendimiento: %.4f \n" , rendimiento);
            pw.printf("Redundancia: %.4f \n" , redundancia);
            pw.printf("Cantidad de simbolos: %d\n", cantSimbolos);
            pw.printf("Longitud maxima de palabra fuente: %d\n", longMaxPalFuente);
            pw.printf("Longitud maxima de palabra codigo: %d\n", longMaxPalCodigo );

            pw.close();

            fichero = new FileWriter("./resultados/primera-parte/compresion.fan");
            pw = new PrintWriter(fichero);

            //Escritura de tabla de codificacion en archivo binario

            RandomAccessFile fileSha = new RandomAccessFile("./resultados/primera-parte/compresion.fan", "rw");

            //Escritura de tabla de codificacion en archivo binario

            Map<String, String> tablaCodigo = shannonFano.getCodigoShannon();

                /*
                Tabla de decodificacion compuesta por:
                    Codigo (2 bytes)
                    Longitud de bytes a leer ( 1 byte = Numero al multiplicar: 2 (tam char)  * long pal )
                    Palabra ( 2 bytes (char)  * long pal ) -> Alojada en forma dinamica
                 */

            int pos = 0;

            fileSha.seek(pos);
            fileSha.writeInt(cantSimbolos);
            pos += 4;

            fileSha.seek(pos);
            fileSha.writeInt(longMaxPalCodigo);
            pos += 4;

            fileSha.seek(pos);
            fileSha.writeInt(longMaxPalFuente);
            pos += 4;


            for (Map.Entry<String, String> entry : tablaCodigo.entrySet()) {

                String codigoTabla = entry.getValue();

                fileSha.seek(pos);
                fileSha.writeUTF(codigoTabla);
                pos += 2 * longMaxPalCodigo;

                String palabraTabla = entry.getKey();

                fileSha.seek(pos);
                fileSha.writeUTF(palabraTabla);
                pos += 2 * longMaxPalFuente;

            }

            //Escritura de codificacion del archivo original en archivo binario

            String codigoSha = shannonFano.getCodificacion();

            int limite;

            for (int i = 0; i < codigoSha.length(); i++) {
                if (i + 8 <= codigoSha.length())
                    limite = 8;
                else
                    limite = codigoSha.length() - i;

                byte data = 0b00000000;
                //Lectura del octeto o lo que quede del octeto (codigo.length - i)
                for (int j = 0; j < limite; j++) {
                    if (codigoSha.charAt(i) == '1') {
                        data |= (0b00000001 << (7 - j));
                    }
                    i++;
                }
                i--; //Con el i++ de la ultima iteracion del for del octeto me salteo una posicion de i

                //Print en file binario del octeto
                fileSha.seek(pos);
                fileSha.writeByte(data);
                pos += 1;

            }

            fileSha.close();

            Escritura.palabrasCodigoShannon(shannonFano);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void palabrasCodigoHuffman(Huffman huffman){

        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./resultados/primera-parte/palabras-codigo-huffman.txt");
            PrintWriter pw = new PrintWriter(fichero);

            pw.println("Palabras codigo asociadas a Huffman \n");

            huffman.getHuffmanCodes().forEach( (pal, code ) -> {

                pw.printf("%s -> %s \n", pal, code);

            } );

            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void palabrasCodigoShannon(ShannonFano shannonFano){

        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./resultados/primera-parte/palabras-codigo-shannon.txt");
            PrintWriter pw = new PrintWriter(fichero);

            pw.println("Palabras codigo asociadas a Shannon-Fano \n");

            ArrayList<String> palabras = shannonFano.getCaracteres2();
            String[] palCodigo = shannonFano.getCodigos();

            for ( int i = 0 ; i < palabras.size() ; i++ ){
                pw.printf("%s -> %s \n", palabras.get(i), palCodigo[i]);
            }

            pw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

}
