public class TP01Q13 {
    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static String aplicaCiframento(String s, int count, String resposta) {
        if (count < s.length()) {
            resposta = resposta + (char) (s.charAt(count) + 3);
            resposta = aplicaCiframento(s, count + 1, resposta);
        }

        return resposta;
    }

    public static void main(String[] args) {
        String[] entrada = new String[1000];
        int numEntrada = 0;

        // Leitura da entrada padrao
        do {
            entrada[numEntrada] = MyIO.readLine();
        } while (isFim(entrada[numEntrada++]) == false);
        numEntrada--; // Desconsiderar ultima linha contendo a palavra FIM

        // Para cada linha de entrada, gerando uma de saida contendo o numero de letras
        // maiusculas da entrada
        for (int i = 0; i < numEntrada; i++) {
            MyIO.println(aplicaCiframento(entrada[i],0,""));
        }
    }
}
