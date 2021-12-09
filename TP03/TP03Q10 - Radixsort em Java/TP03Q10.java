import java.io.*;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Date;

class Serie extends Lista{
    //declaração dos atributos
    private String name;
    private String format;
    private String duration;
    private String country;
    private String language;
    private String broadcaster;
    private String streaming;
    private int seasons;
    private int episodes;
    public int numDoidao;
    //construtor primário
    public Serie(){
        name = "";
        format = "";
        duration = "";
        country = "";
        language = "";
        broadcaster = "";
        streaming = "";
        seasons = 0;
        episodes = 0;
        // numDoidao = 0;
        
    }
    //construtor secundário
    public Serie(String name, String format, String duration, String country, String language, String broadcaster, String streaming, int seasons, 
    int episodes, int numDoidao){
        this.name = name;
        this.format = format;
        this.duration = duration;
        this.country = country;
        this.language = language;
        this.broadcaster = broadcaster;
        this.streaming = streaming;
        this.seasons = seasons;
        this.episodes = episodes;
        // this.numDoidao = numDoidao;
    }
    //método para setar o atributo name
    public void setName(String name){
        this.name = name;
    }
    //método para setar o atributo formato
    public void setFormat(String format){
        this.format = format;
    }
    //método para setar o atributo duration
    public void setDuration(String duration){
        this.duration = duration;
    }
    //método para setar o atributo country
    public void setCountry(String country){
        this.country = country;
    }
    //método para setar o atributo language
    public void setLanguage(String language){
        this.language = language;
    }
    //método para setar o atributo broadcaster
    public void setBroadcaster(String broadcaster){
        this.broadcaster = broadcaster;
    }
    //método para setar o atributo streaming
    public void setStreaming(String streaming){
        this.streaming = streaming;
    }
    //método para setar o atributo seasons
    public void setSeasons(int seasons){
        this.seasons = seasons;
    }
    //método para setar o atributo episodes
    public void setEpisodes(int episodes){
        this.episodes = episodes;
    }
    //método para setar o atributo numDoidao
    // public void setNumDoidao(int numDoidao){
    //     this.numDoidao = numDoidao;
    // }
    //método para retornar o atributo name
    public String getName(){ 
        return this.name; 
    }
    //método para retornar o atributo format
    public String getFormat(){ 
        return this.format; 
    }
    //método para retornar o atributo duration
    public String getDuration(){ 
        return this.duration; 
    }
    //método para retornar o atributo country
    public String getCountry(){ 
        return this.country; 
    }
    //método para retornar o atributo language
    public String getLanguage(){ 
        return this.language; 
    }
    //método para retornar o atributo broadcaster
    public String getBroadcaster(){ 
        return this.broadcaster; 
    }
    //método para retornar o atributo streaming
    public String getStreaming(){ 
        return this.streaming; 
    }
    //método para retornar o atributo seasons
    public int getSeasons(){ 
        return this.seasons; 
    }
    //método para retornar o atributo episodes
    public int getEpisodes(){ 
        return this.episodes; 
    }

    //método para retornar o atributo numDoidao
    // public int getNumDoidao(){
    //     return this.numDoidao;
    // }
    //método para clonar a classe
    public Serie clone(){
        Serie resp = new Serie();
        resp.name = this.name;
        resp.format = this.format;
        resp.duration = this.duration;
        resp.country = this.country;
        resp.language = this.language;
        resp.broadcaster = this.broadcaster;
        resp.streaming = this.streaming;
        resp.seasons = this.seasons;
        resp.episodes = this.episodes;
        // resp.numDoidao = this.numDoidao;
        return resp;
    }
    //método para printar a classe
    public void printClass(){
        System.out.println(this.name + " " + this.format + " " + this.duration + " " + this.country + " " + this.language + " " + this.broadcaster + " " +
        this.streaming + " " + this.seasons + " " + this.episodes);

        // System.out.println(this.numDoidao);
    }
    //método para tratar a linha, deixar apenas números e converter o retorno de String para Integer
    public int justInt(String line){
        String resp = "";
        for(int i = 0; i < line.length(); i++){
            if(line.charAt(i) >= '0' && line.charAt(i) <= '9'){ //caso o caracter seja um número ele é concatenado a variável resp
                resp += line.charAt(i);
            } else { //caso seja outro caracter, o i recebe o valor da condição de parada e o método de repetição é encerrado
                i = line.length();
            }
        }
        return Integer.parseInt(resp); //conversão da string resp para número inteiro a ser retornado
    }
    //método para a remoção das tags da linha lida do arquivo para retornar apenas o que é desejado
    public String removeTags(String line){
        String resp = "";
        int i = 0;
        while(i < line.length()){ //enquanto i for menor que o tamanho da String linha
            if(line.charAt(i) == '<'){ // é testado para verificar se o contador i ainda está dentro das tags
                i++;
                while(line.charAt(i) != '>') i++; //ao encontrar o sinal de fechamento das tags o laço de repetição é encerrado
            } else if(line.charAt(i) == '&'){ //mesmo tratamento de cima mas para outras exceções presentes em alguns outros arquivos
                i++;
                while(line.charAt(i) != ';') i++;
            } else { //o que estiver fora das tags é concatenado a String resp a ser retornada
                resp += line.charAt(i);
            }
            i++;
        }
        //System.out.println(resp);
        return resp;
    }
    //método para tratar o nome do arquivo e retornar o nome da série
    public String searchName(String fileName){
        String resp = "";
        for(int i = 0; i < fileName.length(); i++){
            if(fileName.charAt(i)  == '_'){ //caso o caracter na posição i seja igual ao '_' a variável resp recebe um espaço em branco
                resp += ' ';
            } else { //caso não tenha espaço em branco o caracter é concatenado à string resp
                resp += fileName.charAt(i);
            }
        }
        return resp.substring(0, resp.length()-5); //retorno da substring resp retirando os 5 últimos caracteres relacionados à extensão do arquivo
    }
    //método para leitura do arquivo .html e tratamento das linhas
    public void readClass(String fileName) throws Exception{
        String file = "/tmp/series/" + fileName;
        try {
            FileReader fileReader = new FileReader(file); //declaração da variável fileReader que será recebida pelo bufferedReader

            BufferedReader br = new BufferedReader(fileReader); //declaração do bufferedReader para leitura do arquivo
            
            //set nome da série
            this.name = searchName(fileName);
            

            //set Formato da série
            while(!br.readLine().contains("Formato"));
            this.format = removeTags(br.readLine()).trim();
            

            //set duração da série
            while(!br.readLine().contains("Duração"));
            this.duration = removeTags(br.readLine()).trim();
            

            //set país da série
            while(!br.readLine().contains("País de origem"));
            this.country = removeTags(br.readLine()).trim();

            //set idioma da série
            while(!br.readLine().contains("Idioma original"));
            this.language = removeTags(br.readLine()).trim();
            

            //set emissora da série
            while(!br.readLine().contains("Emissora de televisão"));
            this.broadcaster = removeTags(br.readLine()).trim();
            

            //set transmissão original da série
            while(!br.readLine().contains("Transmissão original"));
            this.streaming = removeTags(br.readLine()).trim();
            

            //set temporadas da série
            while(!br.readLine().contains("N.º de temporadas"));
            this.seasons = justInt(removeTags(br.readLine()));
            

            //set episódios da série
            while(!br.readLine().contains("N.º de episódios"));
            this.episodes = justInt(removeTags(br.readLine()));

            // this.numDoidao = (this.episodes * 1000) + this.seasons;
            
            
            //fechamento do bufferedReader
            br.close();         
        //Tratamento de exceções
        } catch(FileNotFoundException e) {
            System.out.println("Unable to open file '" + fileName + "'");                
        } catch(IOException e) {
            System.out.println("Error reading file '" + fileName + "'");
        }
        
    }
} 

class Lista {
    private Serie[] array;
    private int n;
 
 
    /**
     * Construtor da classe.
     */
    public Lista () {
       this(70);
    }
 
 
    /**
     * Construtor da classe.
     * @param tamanho Tamanho da lista.
     */
    public Lista (int tamanho){
       array = new Serie[tamanho];
       n = 0;
    }
 
 
    /**
     * Insere um elemento na primeira posicao da lista e move os demais
     * elementos para o fim da lista.
     * @param x int elemento a ser inserido.
     * @throws Exception Se a lista estiver cheia.
     */
    public void inserirInicio(Serie x) throws Exception {
 
       //validar insercao
       if(n >= array.length){
          throw new Exception("Erro ao inserir!");
       } 
 
       //levar elementos para o fim do array
       for(int i = n; i > 0; i--){
          array[i] = array[i-1];
       }
 
       array[0] = x;
       n++;
    }
 
 
    /**
     * Insere um elemento na ultima posicao da lista.
     * @param x int elemento a ser inserido.
     * @throws Exception Se a lista estiver cheia.
     */
    public void inserirFim(Serie x) throws Exception {
 
       //validar insercao
       if(n >= array.length){
          throw new Exception("Erro ao inserir!");
       }
 
       array[n] = x;
       n++;
    }
 
 
    /**
     * Insere um elemento em uma posicao especifica e move os demais
     * elementos para o fim da lista.
     * @param x int elemento a ser inserido.
     * @param pos Posicao de insercao.
     * @throws Exception Se a lista estiver cheia ou a posicao invalida.
     */
    public void inserir(Serie x, int pos) throws Exception {
 
       //validar insercao
       if(n >= array.length || pos < 0 || pos > n){
          throw new Exception("Erro ao inserir!");
       }
 
       //levar elementos para o fim do array
       for(int i = n; i > pos; i--){
          array[i] = array[i-1];
          TP03Q10.contador++;
       }
 
       array[pos] = x;
       n++;
    }
 
 
    /**
     * Remove um elemento da primeira posicao da lista e movimenta 
     * os demais elementos para o inicio da mesma.
     * @return resp int elemento a ser removido.
     * @throws Exception Se a lista estiver vazia.
     */
    public Serie removerInicio() throws Exception {
 
       //validar remocao
       if (n == 0) {
          throw new Exception("Erro ao remover!");
       }
 
       Serie resp = array[0];
       n--;
 
       for(int i = 0; i < n; i++){
          array[i] = array[i+1];
          TP03Q10.contador++;
       }
 
       return resp;
    }
 
 
    /**
     * Remove um elemento da ultima posicao da lista.
     * @return resp int elemento a ser removido.
     * @throws Exception Se a lista estiver vazia.
     */
    public Serie removerFim() throws Exception {
 
       //validar remocao
       if (n == 0) {
          throw new Exception("Erro ao remover!");
       }
 
       return array[--n];
    }
 
 
    /**
     * Remove um elemento de uma posicao especifica da lista e 
     * movimenta os demais elementos para o inicio da mesma.
     * @param pos Posicao de remocao.
     * @return resp int elemento a ser removido.
     * @throws Exception Se a lista estiver vazia ou a posicao for invalida.
     */
    public Serie remover(int pos) throws Exception {
 
       //validar remocao
       if (n == 0 || pos < 0 || pos >= n) {
          throw new Exception("Erro ao remover!");
       }
 
       Serie resp = array[pos];
       n--;
 
       for(int i = pos; i < n; i++){
          array[i] = array[i+1];
          TP03Q10.contador++;
       }
 
       return resp;
    }
 
 
    /**
     * Mostra os elementos da lista separados por espacos.
     */
    public void mostrar (){
       //System.out.print("[ ");
       for(int i = 0; i < n; i++){
          array[i].printClass();
       }
       //System.out.println("]");
    }
 
 
    /**
     * Procura um elemento e retorna se ele existe.
     * @param x int elemento a ser pesquisado.
     * @return <code>true</code> se o array existir,
     * <code>false</code> em caso contrario.
     */
    public boolean pesquisar(Serie x) {
       boolean retorno = false;
       for (int i = 0; i < n && retorno == false; i++) {
          retorno = (array[i] == x);
       }
       return retorno;
    }

    
     int getMaior(){
        Serie maior = array[0];
        for (int i = 0; i < n; i++) {
            if(getNumDoidao(maior) < getNumDoidao(array[i])) {
                maior = array[i];
                TP03Q10.contador++;
            }

        }
        return getNumDoidao(maior);
    }
	
	public void sort() {
		for (int i = 1; i < n; i++) {
			Serie tmp = array[i];
			int j = i - 1;

			while ((j >= 0) && (array[j].getName().compareTo(tmp.getName()) > 0)){
				array[j + 1] = array[j];

                TP03Q10.contador++;
				j--;
			}
			array[j + 1] = tmp;
		}
	}
	

    int getNumDoidao(Serie x){
        return (x.getEpisodes() * 1000) + x.getSeasons();
    }

    void countingSort(int exp){
        int tamCount = getMaior() + 1;
        int[] count = new int[tamCount];
        Serie[] ordenado = new Serie [n];
 
        for(int i = 0; i < tamCount; count[i] = 0, i++);
        TP03Q10.contador++;
        for (int i = 0; i < n; count[(getNumDoidao(array[i]) / exp)%10]++, i++);
        TP03Q10.contador++;
        for(int i = 1; i < tamCount; count[i] += count[i-1], i++);
        TP03Q10.contador++;
        for(int i = n-1; i >= 0; ordenado[count[(getNumDoidao(array[i]) / exp)%10]-1] = array[i], count[(getNumDoidao(array[i]) / exp)%10]--, i--);
        TP03Q10.contador++;
        for(int i = 0; i < n; array[i] = ordenado[i], i++);
        TP03Q10.contador++;
     }

     void radixSort(){
         int m = getMaior();

         for (int exp = 1; m/exp > 0; exp *= 10){
            countingSort(exp);
            TP03Q10.contador++;
        }
     }
    
    
    
 }



class TP03Q10{
    public static int contador = 0; 
 
    public static long now(){
        return new Date().getTime();
    }

    public static boolean isFim(String s) {
        return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static void main(String[] args) throws Exception {
        Serie serie = new Serie();
        String[] input = new String[1000];
        Lista lista = new Lista();
        int numInput = 0;
        long inicio=0, fim=0;
        double diferenca=0;

        inicio = now();

        do{
            input[numInput] = MyIO.readLine();
        }while(isFim(input[numInput++]) == false);
        numInput--;//Desconsiderar a palavra FIM
        

        for(int i = 0; i < numInput;i++){
            try{
                serie = new Serie();
                serie.readClass(input[i]);
                //serie.printClass();
                lista.inserirFim(serie); 
            }catch(Exception e){
            }
        }
        //lista.mostrar();
        //System.out.println("----------------");
        //System.out.println("ordenado");
        //System.out.println("----------------");
	lista.sort();
    lista.radixSort();
    lista.mostrar();

    fim = now();
        diferenca = (fim - inicio) / 1000.0;

        RandomAccessFile Arq = new RandomAccessFile("747358_radixsort.txt", "rw");

        Arq.writeChars("747358" + "\t" + diferenca + "\t" + TP03Q10.contador);

        Arq.close();
    }
}
