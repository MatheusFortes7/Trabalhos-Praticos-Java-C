class TP01Q01 {
    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static boolean verificarPalindromo(String s) {
        Boolean resp = false;
        for (int i = 0; i < s.length(); i++) { // corre a string inteira, comparando os caracteres em ordem inversa com a string original 
            if (s.charAt(i) == s.charAt(s.length() - i -1)){
                resp = true;
            } else {
                resp = false;
                i = s.length(); // terminando a funcao, com a condição de parada
            }
        }

        return resp;
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
            if(verificarPalindromo(entrada[i])){
                MyIO.println("SIM"); // se a funcao retornar true
            } else {
                MyIO.println("NAO"); // se a funcao retornar um false
            }
        }
    }
}

