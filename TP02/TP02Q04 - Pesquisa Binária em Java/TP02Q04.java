import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.Date;
import java.io.RandomAccessFile;


class Serie {
    private String nome;
    private String idioma;
    private String formato;
    private String duracao;
    private String paisDeOrigem;
    private String emissora;
    private String transmissao;
    private int numEpisodios;
    private int numTemporadas;

    //Declaração series
    Serie(){
        nome = "";
        idioma = "";
        formato = "";
        duracao = "";
        paisDeOrigem = "";
        emissora = "";
        transmissao = "";
        numTemporadas = 0;
        numEpisodios = 0;
    }

    Serie(String nome, String idioma, String formato, String duracao, String paisDeOrigem, String emissora, String transmissao, int numEpisodios, int numTemporadas){
        this.nome = nome;
        this.idioma = idioma;
        this.formato = formato;
        this.duracao = duracao;
        this.paisDeOrigem = paisDeOrigem;
        this.emissora = emissora;
        this.transmissao = transmissao;
        this.numEpisodios = numEpisodios;
        this.numTemporadas = numTemporadas;
    }

    // Get-Set Nome 
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getNome(){
        return this.nome;
    }

    // Get-Set Idioma 
    public void setIdioma(String idioma){
        this.idioma = idioma;
    }
    public String getIdioma(){
        return this.idioma;
    }

    // Get-Set Formato 
    public void setFormato(String formato){
        this.formato = formato;
    }
    public String getFormato(){
        return this.formato;
    }

    // Get-Set Duracao 
    public void setDuracao(String duracao){
        this.duracao = duracao;
    }
    public String getDuracao(){
        return this.duracao;
    }

    // Get-Set Pais de Origem 
    public void setPaisDeOrigem(String paisDeOrigem){
        this.paisDeOrigem = paisDeOrigem;
    }
    public String getPaisDeOrigem(){
        return this.paisDeOrigem;
    }

    // Get-Set Emissora 
    public void setEmissora(String emissora){
        this.emissora = emissora;
    }
    public String getEmissora(){
        return this.emissora;
    }

    // Get-Set Transmissao 
    public void setTransmissao (String transmissao){
        this.transmissao = transmissao;
    }
    public String getTransmissao(){
        return this.transmissao;
    }

    // Get-Set NumEpisodios 
    public void setNumEpisodios(int numEpisodios){
        this.numEpisodios = numEpisodios;
    }
    public Integer setNumEpisodios(){
        return this.numEpisodios;
    }

    // Get-Set NumTemporadas 
    public void setNumTemporadas(int numTemporadas){
        this.numTemporadas = numTemporadas;
    }
    public Integer setNumTemporadas(){
        return this.numTemporadas;
    }

     String removeTags(String line){
        String newline="";
        int i = 0;
        while(i<line.length()){
            if(line.charAt(i)== '<'){
                i++;
                while(line.charAt(i) != '>')i++;
            }else{
                newline += line.charAt(i);
            }
            i++;
        }

        newline = newline.replace("&#160;", "");
        newline = newline.replace("&nbsp;", "");
        newline = newline.replace("(lista de episódios)", "");
        newline = newline.replace("+ Redemption", "");
        newline = newline.replace("+ 1 especial", "");
        newline = newline.replace("Marvel's ", "");
        newline = newline.replace(": Uma Vida Eterna", "");
        newline = newline.replace("The Simpsons", "Os Simpsons");
        newline = newline.trim();
        return newline;
    }

    public void ler(String nomeArquivo) throws Exception{
        InputStreamReader isr = new InputStreamReader(new FileInputStream(nomeArquivo));
        BufferedReader br = new  BufferedReader(isr);

        while(!br.readLine().contains("infobox_v2"));
        br.readLine();
        this.nome = removeTags(br.readLine());

        while(!br.readLine().contains("Formato"));
        this.formato = removeTags(br.readLine());

        while(!br.readLine().contains("Duração"));
        this.duracao = removeTags(br.readLine());

        while(!br.readLine().contains("País de origem"));
        this.paisDeOrigem = removeTags(br.readLine());

        while(!br.readLine().contains("Idioma original"));
        this.idioma = removeTags(br.readLine());

        while(!br.readLine().contains("Emissora de televisão original"));
        this.emissora = removeTags(br.readLine());

        while(!br.readLine().contains("Transmissão original"));
        this.transmissao = removeTags(br.readLine());

        while(!br.readLine().contains("N.º de temporadas"));
        this.numTemporadas = Integer.parseInt(removeTags(br.readLine()));

        while(!br.readLine().contains("N.º de episódios"));
        this.numEpisodios = Integer.parseInt(removeTags(br.readLine()));

        br.close();
    }

    public void lerNome(String nomeArquivo) throws Exception{
        InputStreamReader isr = new InputStreamReader(new FileInputStream(nomeArquivo));
        BufferedReader br = new  BufferedReader(isr);

        while(!br.readLine().contains("infobox_v2"));
        br.readLine();
        this.nome = removeTags(br.readLine());

        br.close();
    }

    public void Salvar() throws Exception{
        //criar um array de strings e ir salvando o nome delas.
        
    }

    //clone
    public Serie clone(){
        Serie resp = new Serie();
        resp.nome = this.nome;
        resp.idioma = this.idioma;
        resp.formato = this.formato;
        resp.duracao = this.duracao;
        resp.paisDeOrigem = this.paisDeOrigem;
        resp.emissora = this.emissora;
        resp.transmissao = this.transmissao;
        resp.numTemporadas = this.numTemporadas;
        resp.numEpisodios = this.numEpisodios;
        return resp;
    }

    //imprimir
    public void Imprimir(){
        System.out.println(nome + " " + formato + " " + duracao + " " + paisDeOrigem + " " + idioma + " " + emissora + " " + transmissao + " " + numTemporadas + " " + numEpisodios);
    }


}

class TP02Q04{

    public static int contador = 0; 

    public static boolean pesquisaBinaria(String input[], String nomeSeq){
        boolean resp = false;
        int dir = 30, esq = 0, meio;
        
        while(esq <= dir){
            meio = (esq + dir) / 2;
            TP02Q04.contador++;
            
            if(nomeSeq.compareTo(input[meio]) == 0){
                resp = true;
                TP02Q04.contador++;
                esq = 31;
            }else if(nomeSeq.compareTo(input[meio]) > 0){ 
                esq = meio + 1;
                TP02Q04.contador++;
            } else{
                dir = meio - 1;
            }
        }
        return resp;
    }

    public static boolean isFim(String s) {
        TP02Q04.contador = TP02Q04.contador + 4;
        return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static long now(){
        return new Date().getTime();
    }

    public static void main(String[] args) throws Exception{
        Serie serie = new Serie();
        String[] input = new String[1000];
        int numInput = 0;
        long inicio=0, fim=0;
        double dif=0;
        
        inicio = now(); 

        InputStream is = System.in;
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
    
        //ler a primeira entrada
        do{
            input[numInput] = MyIO.readLine();
        }while(isFim(input[numInput++]) == false);
        numInput--;//Desconsiderar a palavra FIM

        String[] entrada1 = new String[1000];

        for(int i = 0; i < numInput; i++){
            TP02Q04.contador++;
            input[i] = input[i].replace(".html", "");
            input[i] = input[i].replace("_", " ");
            //System.out.println(input[i]);
            
        }

        numInput = 0;

        //ler a segunda entrada
        String nomeSeq = MyIO.readLine();
        while(nomeSeq.compareTo("FIM") != 0){
            if(pesquisaBinaria(input, nomeSeq) == true){
                TP02Q04.contador++;
                System.out.println("SIM");
            } else{
               System.out.println("NÃO");
            }
            nomeSeq = MyIO.readLine();
        
        }

        
        
        fim = now();
        dif = (fim - inicio) / 1000.0;

        RandomAccessFile Arq = new RandomAccessFile("matricula_binaria.txt", "rw");

        Arq.writeChars("747358" + "\t" + dif + "\t" + TP02Q04.contador);

        Arq.close();
    }
}