import java.util.Random;

class TP01Q04 {
    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    // funcao que percorrerá a string trocando seus caracteres seguindo o enunciado
    public static String trocarLetras(String s, Random gerador) {
        String resp = "";
        char aleatorio, aleatorio2;

        aleatorio = ((char)('a' + (Math.abs(gerador.nextInt() % 26)))); // chave para alterar aleatoriamente um carcatere
        aleatorio2 = ((char)('a' + (Math.abs(gerador.nextInt() % 26))));

        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == aleatorio2){ //FALTA ESSA PARTE AQUI Ó <------
                resp = resp + (char)(aleatorio);
            } else {
                resp = resp + (char)(s.charAt(i));
            }
        }
        return resp;
    }

    public static void main(String[] args) {
        String[] entrada = new String[1000];
        int numEntrada = 0;
        Random gerador = new Random();
        gerador.setSeed(4);

        // Leitura da entrada padrao
        do {
            entrada[numEntrada] = MyIO.readLine();
        } while (isFim(entrada[numEntrada++]) == false);
        numEntrada--; // Desconsiderar ultima linha contendo a palavra FIM

        // Para cada linha de entrada, gerando uma de saida contendo o numero de letras
        // maiusculas da entrada
        for (int i = 0; i < numEntrada; i++) {
            MyIO.println(trocarLetras(entrada[i], gerador));
        }
    }
}