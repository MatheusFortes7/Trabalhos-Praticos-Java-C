class TP01Q06{
 
 public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    
    public static String isVogal(String s) {
        String z = s.toLowerCase();
        boolean resp = false;
        for(int i = 0; i < z.length(); i++){
            if(z.charAt(i) != 'a' || z.charAt(i) != 'e' || z.charAt(i) != 'i' || z.charAt(i) != 'o' || z.charAt(i) != 'u'){
                i = z.length();
                resp = false;
            } else {
                resp = true;
            }
        }
        if (resp == false){
            return "NAO";
        } else {
            return "SIM";
        }
    }

    public static String isConsoante(String s){
        String z = s.toLowerCase();
        boolean resp = false;
        for(int i = 0; i < z.length();i++){
            if ((int) s.charAt(i) > 64 && (int) s.charAt(i)< 91){
                if(z.charAt(i) == 'a' || z.charAt(i) == 'e' || z.charAt(i) == 'i' || z.charAt(i) == 'o' || z.charAt(i) == 'u'){
                    i = z.length();
                    resp = false;
                } else {
                    resp = true;
                }
            }
        }
        if (resp == false){
            return "NAO";
        } else {
            return "SIM";
        }
    }

    public static String isInteiro (String s) {
        boolean resp = false;
        for(int i =0; i < s.length(); i++){
            if((int)s.charAt(i) > 47 && (int)s.charAt(i) < 58){
                resp = true;
            } else {
                resp = false;
                i = s.length();
            }
        }
        if(resp == true){
            return "SIM";
        } else {
            return "NAO";
        }
        
        
    }

    public static String isReal (String s){
        boolean resp = false;
        for(int i =0; i < s.length(); i++){
            if((int)s.charAt(i) > 47 && (int)s.charAt(i) < 58 || s.charAt(i) == 46){
                resp = true;
            } else {
                resp = false;
                i = s.length();
            }
        }
        if(resp == true){
           return "SIM";
        } else {
            return "NAO";
        }
        
    }


    public static void main (String[] args){
        String[] entrada = new String[1000];
        int numEntrada = 0;

        //Leitura da entrada padrao
        do {
           entrada[numEntrada] = MyIO.readLine();
        } while (isFim(entrada[numEntrada++]) == false);
        numEntrada--;   //Desconsiderar ultima linha contendo a palavra FIM
  
        //Para cada linha de entrada, gerando uma de saida contendo o numero de letras maiusculas da entrada
        for(int i = 0; i < numEntrada; i++){
           MyIO.println(isVogal(entrada[i]) + " " + isConsoante(entrada[i]) + " " + isInteiro(entrada[i]) + " " + isReal(entrada[i]) );
        }
     }
}