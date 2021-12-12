import java.io.*;
import java.io.FileReader;

class Serie {
    private String nome;
    private String formato;
    private String duracao;
    private String pais;
    private String idioma;
    private String emissora;
    private String transmissao;
    private int temporada;
    private int episodios;

    public Serie() {
        nome = "";
        formato = "";
        duracao = "";
        pais = "";
        idioma = "";
        emissora = "";
        transmissao = "";
        temporada = ' ';
        episodios = ' ';
    }

    public Serie(String nome, String formato, String duracao, String pais, String idioma, String emissora,
            String transmissao, int temporada, int episodios) {
        this.nome = nome;
        this.formato = formato;
        this.duracao = duracao;
        this.pais = pais;
        this.idioma = idioma;
        this.emissora = emissora;
        this.transmissao = transmissao;
        this.temporada = temporada;
        this.episodios = episodios;
    }

    //clone
    public Serie clone() {
        Serie temp = new Serie();

        temp.nome = this.nome;
        temp.formato = this.formato;
        temp.duracao = this.duracao;
        temp.pais = this.pais;
        temp.idioma = this.idioma;
        temp.emissora = this.emissora;
        temp.transmissao = this.transmissao;
        temp.temporada = this.temporada;
        temp.episodios = this.episodios;

        return temp;
    }

    //SET
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public void setEmissora(String emissora) {
        this.emissora = emissora;
    }

    public void setTransmissao(String transmissao) {
        this.transmissao = transmissao;
    }

    public void setTemporada(int temporada) {
        this.temporada = temporada;
    }

    public void setEpisodios(int episodios) {
        this.episodios = episodios;
    }

    //GET
    public String getNome() {
        return nome;
    }

    public String getFormato() {
        return formato;
    }

    public String getDuracao() {
        return duracao;
    }

    public String getPais() {
        return pais;
    }

    public String getIdioma() {
        return idioma;
    }

    public String getEmissora() {
        return emissora;
    }

    public String getTransmissao() {
        return transmissao;
    }

    public int getTemporada() {
        return temporada;
    }

    public int getEpisodios() {
        return episodios;
    }

    //Print
    public void printSerie() {
        System.out.println(this.nome + " " + this.formato + " " + this.duracao + " " + this.pais + " " + this.idioma
                + " " + this.emissora + " " + this.transmissao + " " + this.temporada + " " + this.episodios);
    }

    public String removeTag(String line) {
        String newLine = "";
        int i = 0;
        while (i < line.length()) {
            if (line.charAt(i) == '<') {
                i++;
                while (line.charAt(i) != '>') {
                    i++;
                }
            } else if (line.charAt(i) == '&') {
                i++;
                while (line.charAt(i) != ';')
                    i++;
            } else {
                newLine += line.charAt(i);
            }
            i++;
        }
        return newLine;
    }

    //pega o nome da serie
    public String pegaNome(String entrada) {
        String aux = "";
        for (int i = 0; i < entrada.length(); i++) {
            if (entrada.charAt(i) == '_') {
                aux += ' ';
            } else {
                aux += entrada.charAt(i);
            }
        }
        aux = aux.substring(0, aux.length() - 5);
        return aux;
    }

    //pega os inteiros da String
    public int transformaINT(String line) {
        String aux = "";

        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) >= '0' && line.charAt(i) <= '9') {
                aux += line.charAt(i);
            } else {
                i = line.length();
            }
        }
        //converte o aux para int resp
        return Integer.parseInt(aux); 
    }

    public void lerSerie(String entrada) {
        String arquivo = "/tmp/series/" + entrada;
        try {
            FileReader fileReader = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(fileReader);

            this.nome = pegaNome(entrada);
            

            //set Formato da série
            while(!br.readLine().contains("Formato"));
            this.formato = removeTag(br.readLine()).trim();
            

            //set duração da série
            while(!br.readLine().contains("Duração"));
            this.duracao = removeTag(br.readLine()).trim();
            

            //set país da série
            while(!br.readLine().contains("País de origem"));
            this.pais = removeTag(br.readLine()).trim();

            //set idioma da série
            while(!br.readLine().contains("Idioma original"));
            this.idioma = removeTag(br.readLine()).trim();
            

            //set emissora da série
            while(!br.readLine().contains("Emissora de televisão"));
            this.emissora = removeTag(br.readLine()).trim();
            

            //set transmissão original da série
            while(!br.readLine().contains("Transmissão original"));
            this.transmissao = removeTag(br.readLine()).trim();
            

            //set temporadas da série
            while(!br.readLine().contains("N.º de temporadas"));
            this.temporada = transformaINT(removeTag(br.readLine()));
            

            //set episódios da série
            while(!br.readLine().contains("N.º de episódios"));
            this.episodios = transformaINT(removeTag(br.readLine()));
            

            br.close();

        } catch (FileNotFoundException e) {
            MyIO.println("ERROR: Unable to open file '" + entrada + "'");
        } catch (IOException e) {
            MyIO.println("ERROR: Error reading file '" + entrada + "'");
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

class Pilha {
	private Celula topo;

	/**
	 * Construtor da classe que cria uma fila sem elementos.
	 */
	public Pilha() {
		topo = null;
	}

	/**
	 * Insere elemento na pilha (politica FILO).
	 * 
	 * @param x int elemento a inserir.
	 */
	public void inserir(Serie x) {
		Celula tmp = new Celula(x);
		tmp.prox = topo;
		topo = tmp;
		tmp = null;
	}

	/**
	 * Remove elemento da pilha (politica FILO).
	 * 
	 * @return Elemento removido.
	 * @trhows Exception Se a sequencia nao contiver elementos.
	 */
	public Serie remover() throws Exception {
		if (topo == null) {
			throw new Exception("Erro ao remover!");
		}
		Serie resp = topo.elemento;
		Celula tmp = topo;
		topo = topo.prox;
		tmp.prox = null;
		tmp = null;
		return resp;
	}

	/**
	 * Mostra os elementos separados por espacos, comecando do topo.
	 */
	public void mostrar() {
		//System.out.print("[ ");
		for (Celula i = topo; i != null; i = i.prox) {
			System.out.println(i.elemento.getNome() + " " + i.elemento.getFormato() + " " + i.elemento.getDuracao() + " " + i.elemento.getPais() + " " + i.elemento.getIdioma()
            + " " + i.elemento.getEmissora() + " " + i.elemento.getTransmissao() + " " + i.elemento.getTemporada() + " " + i.elemento.getEpisodios());
		}
		//System.out.println("] ");
	}

	public void mostraPilha() {
		mostraPilha(topo);
	}

	private void mostraPilha(Celula i) {
		if (i != null) {
			mostraPilha(i.prox);
			System.out.println("" + i.elemento);
		}
	}

}

public class TP02Q06{
    //fim
    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }
    
    public static void alterPile(String entrada, Serie series, Pilha pilha) throws Exception{
        //RESOLVER PROBLEMA NA HR DO PRINT
        if (entrada.charAt(0) == 'I') { //faz o stack
            series.lerSerie(entrada.substring(2));
            pilha.inserir(series.clone()); 
            
            // Insere na pilha

        } else if (entrada.charAt(0) == 'R') { // Desempilhar
            series = pilha.remover();
            MyIO.println("(R) " + series.getNome()); 
            
            // imprime o remove
        }
    }

    public static void main(String[] args) throws Exception{
        String[] entrada = new String[1000];
        Serie series = new Serie(); 
        Pilha pilha = new Pilha();
        int aux= 0, i, stop;

        //primeira parte
        do {
            entrada[aux] = MyIO.readLine();
        } while (isFim(entrada[aux++]) == false);

        for (i = 0; i < (aux - 1); i++) {
            series.lerSerie(entrada[i]);
            pilha.inserir(series.clone());
        }

        //segunda parte
        aux = 0;
        stop = MyIO.readInt(); //total de alterações
        
        do {
            entrada[aux] = MyIO.readLine();
            aux++;
        } while (aux < stop);

        //faxz as alteracoes
        for (i = 0; i < aux; i++) {
            alterPile(entrada[i], series.clone(), pilha);
        }

        pilha.mostrar();

    }
}