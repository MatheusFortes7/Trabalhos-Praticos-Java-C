public class TP01Q11 {
    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static boolean verificarPalindromo(String s, int count, int z) {
        Boolean resp = false;

        if(z < (s.length()/2)){ //divide a palavra pelo meio, pois se a metade da palavra for igual seu palindromo, ela ja e consideradda um palindromo.
            if (s.charAt(z) == s.charAt(s.length() - z -1)){ //se a letra no lugar "z", for igual a ultima letra em diante da palavra, entrara no if
                return verificarPalindromo(s, count + 1, z + 1);
            } else { // se a letra for diferente entrara no else
                return verificarPalindromo(s, count, z + 1);
            }
        } 
            
        if (count == s.length()/2){
            resp = true;
        } else {
            resp = false;
        }
        

        return resp; //retornando um valor booleano
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
            if(verificarPalindromo(entrada[i], 0,0)){
                MyIO.println("SIM"); // se a funcao retornar true
            } else {
                MyIO.println("NAO"); // se a funcao retornar um false
            }
        }
    }
}
