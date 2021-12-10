import java.io.*;
import java.io.FileReader;
import java.util.Date;
import java.io.RandomAccessFile;

class Serie {
    // declaração dos atributos
    private String name;
    private String format;
    private String duration;
    private String country;
    private String language;
    private String broadcaster;
    private String streaming;
    private int seasons;
    private int episodes;

    // construtor primário
    public Serie() {
        name = "";
        format = "";
        duration = "";
        country = "";
        language = "";
        broadcaster = "";
        streaming = "";
        seasons = 0;
        episodes = 0;
    }

    // construtor secundário
    public Serie(String name, String format, String duration, String country, String language, String broadcaster,
            String streaming, int seasons, int episodes) {
        this.name = name;
        this.format = format;
        this.duration = duration;
        this.country = country;
        this.language = language;
        this.broadcaster = broadcaster;
        this.streaming = streaming;
        this.seasons = seasons;
        this.episodes = episodes;
    }

    // método para setar o atributo name
    public void setName(String name) {
        this.name = name;
    }

    // método para setar o atributo formato
    public void setFormat(String format) {
        this.format = format;
    }

    // método para setar o atributo duration
    public void setDuration(String duration) {
        this.duration = duration;
    }

    // método para setar o atributo country
    public void setCountry(String country) {
        this.country = country;
    }

    // método para setar o atributo language
    public void setLanguage(String language) {
        this.language = language;
    }

    // método para setar o atributo broadcaster
    public void setBroadcaster(String broadcaster) {
        this.broadcaster = broadcaster;
    }

    // método para setar o atributo streaming
    public void setStreaming(String streaming) {
        this.streaming = streaming;
    }

    // método para setar o atributo seasons
    public void setSeasons(int seasons) {
        this.seasons = seasons;
    }

    // método para setar o atributo episodes
    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    // método para retornar o atributo name
    public String getName() {
        return this.name;
    }

    // método para retornar o atributo format
    public String getFormat() {
        return this.format;
    }

    // método para retornar o atributo duration
    public String getDuration() {
        return this.duration;
    }

    // método para retornar o atributo country
    public String getCountry() {
        return this.country;
    }

    // método para retornar o atributo language
    public String getLanguage() {
        return this.language;
    }

    // método para retornar o atributo broadcaster
    public String getBroadcaster() {
        return this.broadcaster;
    }

    // método para retornar o atributo streaming
    public String getStreaming() {
        return this.streaming;
    }

    // método para retornar o atributo seasons
    public int getSeasons() {
        return this.seasons;
    }

    // método para retornar o atributo episodes
    public int getEpisodes() {
        return this.episodes;
    }

    // método para clonar a classe
    public Serie clone() {
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
        return resp;
    }

    // método para printar a classe
    public void printClass() {
        System.out
                .println(this.name + " " + this.format + " " + this.duration + " " + this.country + " " + this.language
                        + " " + this.broadcaster + " " + this.streaming + " " + this.seasons + " " + this.episodes);
    }

    // método para tratar a linha, deixar apenas números e converter o retorno de
    // String para Integer
    public int justInt(String line) {
        String resp = "";
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) >= '0' && line.charAt(i) <= '9') { // caso o caracter seja um número ele é concatenado a
                                                                  // variável resp
                resp += line.charAt(i);
            } else { // caso seja outro caracter, o i recebe o valor da condição de parada e o método
                     // de repetição é encerrado
                i = line.length();
            }
        }
        return Integer.parseInt(resp); // conversão da string resp para número inteiro a ser retornado
    }

    // método para a remoção das tags da linha lida do arquivo para retornar apenas
    // o que é desejado
    public String removeTags(String line) {
        String resp = "";
        int i = 0;
        while (i < line.length()) { // enquanto i for menor que o tamanho da String linha
            if (line.charAt(i) == '<') { // é testado para verificar se o contador i ainda está dentro das tags
                i++;
                while (line.charAt(i) != '>')
                    i++; // ao encontrar o sinal de fechamento das tags o laço de repetição é encerrado
            } else if (line.charAt(i) == '&') { // mesmo tratamento de cima mas para outras exceções presentes em alguns
                                                // outros arquivos
                i++;
                while (line.charAt(i) != ';')
                    i++;
            } else { // o que estiver fora das tags é concatenado a String resp a ser retornada
                resp += line.charAt(i);
            }
            i++;
        }
        // System.out.println(resp);
        return resp;
    }

    // método para tratar o nome do arquivo e retornar o nome da série
    public String searchName(String fileName) {
        String resp = "";
        for (int i = 0; i < fileName.length(); i++) {
            if (fileName.charAt(i) == '_') { // caso o caracter na posição i seja igual ao '_' a variável resp recebe um
                                             // espaço em branco
                resp += ' ';
            } else { // caso não tenha espaço em branco o caracter é concatenado à string resp
                resp += fileName.charAt(i);
            }
        }
        return resp.substring(0, resp.length() - 5); // retorno da substring resp retirando os 5 últimos caracteres
                                                     // relacionados à extensão do arquivo
    }

    // método para leitura do arquivo .html e tratamento das linhas
    public void readClass(String fileName) {
        String file = "/tmp/series/" + fileName;
        try {
            FileReader fileReader = new FileReader(file); // declaração da variável fileReader que será recebida pelo
                                                          // bufferedReader

            BufferedReader br = new BufferedReader(fileReader); // declaração do bufferedReader para leitura do arquivo

            // set nome da série
            this.name = searchName(fileName);

            // set Formato da série
            while (!br.readLine().contains("Formato"))
                ;
            this.format = removeTags(br.readLine()).trim();

            // set duração da série
            while (!br.readLine().contains("Duração"))
                ;
            this.duration = removeTags(br.readLine()).trim();

            // set país da série
            while (!br.readLine().contains("País de origem"))
                ;
            this.country = removeTags(br.readLine()).trim();

            // set idioma da série
            while (!br.readLine().contains("Idioma original"))
                ;
            this.language = removeTags(br.readLine()).trim();

            // set emissora da série
            while (!br.readLine().contains("Emissora de televisão"))
                ;
            this.broadcaster = removeTags(br.readLine()).trim();

            // set transmissão original da série
            while (!br.readLine().contains("Transmissão original"))
                ;
            this.streaming = removeTags(br.readLine()).trim();

            // set temporadas da série
            while (!br.readLine().contains("N.º de temporadas"))
                ;
            this.seasons = justInt(removeTags(br.readLine()));

            // set episódios da série
            while (!br.readLine().contains("N.º de episódios"))
                ;
            this.episodes = justInt(removeTags(br.readLine()));

            // fechamento do bufferedReader
            br.close();
            // Tratamento de exceções
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch (IOException e) {
            System.out.println("Error reading file '" + fileName + "'");
        }

    }
}

class Celula {
	public Serie elemento; // Elemento inserido na celula.
	public Celula prox; // Aponta a celula prox.


	/**
	 * Construtor da classe.
	 */
	public Celula() {
		this(null);
	}

	/**
	 * Construtor da classe.
	 * @param elemento int inserido na celula.
	 */
	public Celula(Serie elemento) {
      this.elemento = elemento;
      this.prox = null;
	}
}

class Lista {
	private Celula primeiro;
	private Celula ultimo;
    private int qt;

	/**
	 * Construtor da classe que cria uma lista sem elementos (somente no cabeca).
	 */
	public Lista() {
		primeiro = new Celula();
		ultimo = primeiro;
        qt = 0;
	}

    public int getQuantity() {
        return qt;
    }
	/**
	 * Insere um elemento na primeira posicao da lista.
    * @param x int elemento a ser inserido.
	 */
	public void inserirInicio(Serie x) {
		Celula tmp = new Celula(x);
      tmp.prox = primeiro.prox;
		primeiro.prox = tmp;
		if (primeiro == ultimo) {                 
			ultimo = tmp;
		}
        qt++;
      tmp = null;
	}


	/**
	 * Insere um elemento na ultima posicao da lista.
    * @param x int elemento a ser inserido.
	 */
	public void inserirFim(Serie x) {
		ultimo.prox = new Celula(x);
		ultimo = ultimo.prox;
        qt++;
	}


	/**
	 * Remove um elemento da primeira posicao da lista.
    * @return resp int elemento a ser removido.
	 * @throws Exception Se a lista nao contiver elementos.
	 */
	public Serie removerInicio() {
		if (primeiro == ultimo) {
			//throw new Exception("Erro ao remover (vazia)!");
		}

      Celula tmp = primeiro;
		primeiro = primeiro.prox;
		Serie resp = primeiro.elemento;
        tmp.prox = null;
        tmp = null;
        qt--;
		return resp;
	}


	/**
	 * Remove um elemento da ultima posicao da lista.
    * @return resp int elemento a ser removido.
	 * @throws Exception Se a lista nao contiver elementos.
	 */
	public Serie removerFim() {
		if (primeiro == ultimo) {
			//throw new Exception("Erro ao remover (vazia)!");
		} 

		// Caminhar ate a penultima celula:
        Celula i;
        for(i = primeiro; i.prox != ultimo; i = i.prox);

        Serie resp = ultimo.elemento; 
        ultimo = i; 
        i = ultimo.prox = null;
        qt--;

	    return resp;
	}


	/**
    * Insere um elemento em uma posicao especifica considerando que o 
    * primeiro elemento valido esta na posicao 0.
    * @param x int elemento a ser inserido.
	 * @param pos int posicao da insercao.
	 * @throws Exception Se <code>posicao</code> invalida.
	 */
   public void inserir(Serie x, int pos){

      int tamanho = tamanho();

      if(pos < 0 || pos > tamanho){
			//throw new Exception("Erro ao inserir posicao (" + pos + " / tamanho = " + tamanho + ") invalida!");
      } else if (pos == 0){
         inserirInicio(x);
      } else if (pos == tamanho){
         inserirFim(x);
      } else {
		   // Caminhar ate a posicao anterior a insercao
         Celula i = primeiro;
         for(int j = 0; j < pos; j++, i = i.prox);
		
         Celula tmp = new Celula(x);
         tmp.prox = i.prox;
         i.prox = tmp;
         tmp = i = null;
         qt++;
      }
   }


	/**
    * Remove um elemento de uma posicao especifica da lista
    * considerando que o primeiro elemento valido esta na posicao 0.
	 * @param posicao Meio da remocao.
    * @return resp int elemento a ser removido.
	 * @throws Exception Se <code>posicao</code> invalida.
	 */
	public Serie remover(int pos) {
      Serie resp = null;
      int tamanho = tamanho();

		if (primeiro == ultimo){
			//throw new Exception("Erro ao remover (vazia)!");

      } else if(pos < 0 || pos >= tamanho){
			//throw new Exception("Erro ao remover (posicao " + pos + " / " + tamanho + " invalida!");
      } else if (pos == 0){
         resp = removerInicio();
      } else if (pos == tamanho - 1){
         resp = removerFim();
      } else {
		   // Caminhar ate a posicao anterior a insercao
         Celula i = primeiro;
         for(int j = 0; j < pos; j++, i = i.prox);
		
         Celula tmp = i.prox;
         resp = tmp.elemento;
         i.prox = tmp.prox;
         tmp.prox = null;
         i = tmp = null;
      }
        qt--;
		return resp;
	}

	/**
	 * Mostra os elementos da lista separados por espacos.
	 */
	public void mostrar() {
		for (Celula i = primeiro.prox; i != null; i = i.prox) {
			System.out.print(i.elemento + " ");
        }
	}
    /**
	 * Procura um elemento e retorna se ele existe.
	 * @param x Elemento a pesquisar.
	 * @return <code>true</code> se o elemento existir,
	 * <code>false</code> em caso contrario.
	 */
	public boolean pesquisar(String x) {
		boolean resp = false;
		for (Celula i = primeiro.prox; i != null; i = i.prox) {
         if(i.elemento.getName().compareTo(x) == 0){
            resp = true;
            i = ultimo;
         }
		}

        if(resp == true){
            System.out.println(" SIM");
        } else {
            System.out.println(" NAO");
        }

		return resp;
	}

	/**
	 * Calcula e retorna o tamanho, em numero de elementos, da lista.
	 * @return resp int tamanho
	 */
   public int tamanho() {
      int tamanho = 0; 
      for(Celula i = primeiro; i != ultimo; i = i.prox, tamanho++);
      return tamanho;
   }

    public Serie getLast() {
        return ultimo.elemento;
    }

    public Serie getFirst() {
        Serie temp = null;
        if (primeiro != ultimo) {
            temp = primeiro.prox.elemento;
        }
        return temp;
    }

    public Serie getItemAt(int index) {
        Serie resp = null;
        if (index == 1) {
            resp = getFirst();
        } else if (index == qt) {
            resp = getLast();
        } else if ((index > 1 && index < qt) && primeiro != ultimo) {
            Celula temp = primeiro;
            for (int i = 0; i < index; i++, temp = temp.prox)
                ;
            resp = temp.elemento;
        } else {
            MyIO.println("ERRO - Get Index com indice " + index + " não permitido");
        }
        return resp;
    }

}


class HashIndiretoLista {
    Lista tabela[];
    int tamanho;
    final int NULO = -1;
 
    public HashIndiretoLista (){
       this(21);
    }
 
    public HashIndiretoLista (int tamanho){
       this.tamanho = tamanho;
       tabela = new Lista[tamanho];
       for(int i = 0; i < tamanho; i++){
          tabela[i] = new Lista();
       }
    }
 
    public int h(int elemento){
       return elemento % tamanho;
    }
 
    boolean pesquisar(String elemento){
        int ascii = 0;

        for (int i = 0; i < elemento.length(); i++) {
            ascii = ascii + (int) elemento.charAt(i);
        }
        int pos = h(ascii);
       return tabela[pos].pesquisar(elemento);
    }
    
    public int getValorAscii(Serie elemento) {
        int ascii = 0;
        String nome = elemento.getName();

        for (int i = 0; i < nome.length(); i++) {
            ascii = ascii + (int) nome.charAt(i);
        }

        return ascii;
    }

    public void inserirInicio (Serie elemento){
       int pos = h(getValorAscii(elemento));
       tabela[pos].inserirInicio(elemento);
    }
 
}
 

class TP04Q08 {
    public static int contador = 0;
    // Salvando os itens no arra nao dara certo pois so ordenara os paises, e nao a
    // linha inteira

    public static long now() {
        return new Date().getTime();
    }

    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static void main(String[] args) {
        String[] entrada = new String[1000];
        Serie series = new Serie(); // Declaracao de serie
        HashIndiretoLista hash = new HashIndiretoLista();
        int n = 0, i, stop;

        // ! INICIO DA LEITURA
        // Recebe a primeira parte da entrada
        do {
            entrada[n] = MyIO.readLine();
        } while (isFim(entrada[n++]) == false);
        // Salva as series na lista
        for (i = 0; i < (n - 1); i++) {
            series = new Serie();
            series.readClass(entrada[i]);
            // series.clone().printClass();
            hash.inserirInicio(series.clone());

        }
        // hash.mostrar();

        n = 0;
        // Recebe a primeira parte da entrada
        do {
            entrada[n] = MyIO.readLine();
        } while (isFim(entrada[n++]) == false);

        for (i = 0; i < (n - 1); i++) {
            hash.pesquisar(entrada[i]);
        }
        // ! FIM DA LEITURA
    }
}