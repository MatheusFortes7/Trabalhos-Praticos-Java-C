import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

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

class TP02Q01Class{

    public static boolean isFim(String s) {
        return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static void main(String[] args) {
        Serie serie = new Serie();
        String[] input = new String[1000];
        int numInput = 0;

        do{
            input[numInput] = MyIO.readLine();
        }while(isFim(input[numInput++]) == false);
        numInput--;//Desconsiderar a palavra FIM


        for(int i = 0; i < numInput;i++){
            try{
                serie.ler("/tmp/series/"+input[i]);
            }catch(Exception e){
            }
            serie.Imprimir();
        }
    }
}