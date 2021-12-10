import java.io.*;
import java.io.FileReader;
import java.util.Date;
import java.io.RandomAccessFile;
import java.lang.Exception;

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

class No {
    public Serie elemento; // Conteudo do no.
    public No esq, dir; // Filhos da esq e dir.
    public int nivel; // Numero de niveis abaixo do no

    /**
     * Construtor da classe.
     * 
     * @param elemento Conteudo do no.
     */
    public No(Serie elemento) {
        this(elemento, null, null, 1);
    }

    /**
     * Construtor da classe.
     * 
     * @param elemento Conteudo do no.
     * @param esq      No da esquerda.
     * @param dir      No da direita.
     */
    public No(Serie elemento, No esq, No dir, int nivel) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
        this.nivel = nivel;
    }

    /**
     * Cálculo do número de níveis a partir de um vértice
     */
    public void setNivel() {
        this.nivel = 1 + Math.max(getNivel(esq), getNivel(dir));
    }

    /**
     * Retorna o número de níveis a partir de um vértice
     * 
     * @param no nó que se deseja o nível.
     */
    public static int getNivel(No no) {
        return (no == null) ? 0 : no.nivel;
    }
}

class AVL {
    private No raiz; // Raiz da arvore.

    /**
     * Construtor da classe.
     */
    public AVL() {
        raiz = null;
    }

    /**
     * Metodo publico iterativo para pesquisar elemento.
     * 
     * @param x Elemento que sera procurado.
     * @return <code>true</code> se o elemento existir, <code>false</code> em caso
     *         contrario.
     */
    public boolean pesquisar(String x) {
        return pesquisar(x, raiz);
    }

    /**
     * Metodo privado recursivo para pesquisar elemento.
     * 
     * @param x Elemento que sera procurado.
     * @param i No em analise.
     * @return <code>true</code> se o elemento existir, <code>false</code> em caso
     *         contrario.
     */
    private boolean pesquisar(String x, No i) {
        boolean resp;
        if (i == null) {
            System.out.println(" NAO");
            resp = false;

        } else if (x.compareTo(i.elemento.getName()) == 0) {
            System.out.println(" SIM");
            resp = true;

        } else if (x.compareTo(i.elemento.getName()) < 0) {
            System.out.print(" esq");
            resp = pesquisar(x, i.esq);

        } else {
            System.out.print(" dir");
            resp = pesquisar(x, i.dir);
        }
        return resp;
    }

    /**
     * Metodo publico iterativo para exibir elementos.
     */
    public void caminharCentral() {
        System.out.print("[ ");
        caminharCentral(raiz);
        System.out.println("]");
    }

    /**
     * Metodo privado recursivo para exibir elementos.
     * 
     * @param i No em analise.
     */
    private void caminharCentral(No i) {
        if (i != null) {
            caminharCentral(i.esq); // Elementos da esquerda.
            System.out.print(i.elemento + " "); // Conteudo do no.
            caminharCentral(i.dir); // Elementos da direita.
        }
    }

    /**
     * Metodo publico iterativo para exibir elementos.
     */
    public void caminharPre() {
        System.out.print("[ ");
        caminharPre(raiz);
        System.out.println("]");
    }

    /**
     * Metodo privado recursivo para exibir elementos.
     * 
     * @param i No em analise.
     */
    private void caminharPre(No i) {
        if (i != null) {
            System.out.print(i.elemento + "(fator " + (No.getNivel(i.dir) - No.getNivel(i.esq)) + ") "); // Conteudo do
                                                                                                         // no.
            caminharPre(i.esq); // Elementos da esquerda.
            caminharPre(i.dir); // Elementos da direita.
        }
    }

    /**
     * Metodo publico iterativo para exibir elementos.
     */
    public void caminharPos() {
        System.out.print("[ ");
        caminharPos(raiz);
        System.out.println("]");
    }

    /**
     * Metodo privado recursivo para exibir elementos.
     * 
     * @param i No em analise.
     */
    private void caminharPos(No i) {
        if (i != null) {
            caminharPos(i.esq); // Elementos da esquerda.
            caminharPos(i.dir); // Elementos da direita.
            System.out.print(i.elemento + " "); // Conteudo do no.
        }
    }

    /**
     * Metodo publico iterativo para inserir elemento.
     * 
     * @param x Elemento a ser inserido.
     * @throws Exception Se o elemento existir.
     */
    public void inserir(Serie x) {
        raiz = inserir(x, raiz);
    }

    /**
     * Metodo privado recursivo para inserir elemento.
     * 
     * @param x Elemento a ser inserido.
     * @param i No em analise.
     * @return No em analise, alterado ou nao.
     * @throws Exception Se o elemento existir.
     */
    private No inserir(Serie x, No i) {
        if (i == null) {
            i = new No(x);

        } else if (x.getName().compareTo(i.elemento.getName()) < 0) {
            i.esq = inserir(x, i.esq);

        } else if (x.getName().compareTo(i.elemento.getName()) > 0) {
            i.dir = inserir(x, i.dir);

        } else {
            System.out.println("Erro ao inserir!");
        }

        return balancear(i);
    }

    /**
     * Metodo publico iterativo para remover elemento.
     * 
     * @param x Elemento a ser removido.
     * @throws Exception Se nao encontrar elemento.
     */
    public void remover(Serie x) {
        raiz = remover(x, raiz);
    }

    /**
     * Metodo privado recursivo para remover elemento.
     * 
     * @param x Elemento a ser removido.
     * @param i No em analise.
     * @return No em analise, alterado ou nao.
     * @throws Exception Se nao encontrar elemento.
     */
    private No remover(Serie x, No i) {

        if (i == null) {
            System.out.println("Erro ao remover!");

        } else if (x.getName().compareTo(i.elemento.getName()) < 0) {
            i.esq = remover(x, i.esq);

        } else if (x.getName().compareTo(i.elemento.getName()) > 0) {
            i.dir = remover(x, i.dir);

            // Sem no a direita.
        } else if (i.dir == null) {
            i = i.esq;

            // Sem no a esquerda.
        } else if (i.esq == null) {
            i = i.dir;

            // No a esquerda e no a direita.
        } else {
            i.esq = maiorEsq(i, i.esq);
        }

        return balancear(i);
    }

    /**
     * Metodo para trocar o elemento "removido" pelo maior da esquerda.
     * 
     * @param i No que teve o elemento removido.
     * @param j No da subarvore esquerda.
     * @return No em analise, alterado ou nao.
     */
    private No maiorEsq(No i, No j) {

        // Encontrou o maximo da subarvore esquerda.
        if (j.dir == null) {
            i.elemento = j.elemento; // Substitui i por j.
            j = j.esq; // Substitui j por j.ESQ.

            // Existe no a direita.
        } else {
            // Caminha para direita.
            j.dir = maiorEsq(i, j.dir);
        }
        return j;
    }

    private No balancear(No no) {
        if (no != null) {
            int fator = No.getNivel(no.dir) - no.getNivel(no.esq);

            // Se balanceada
            if (Math.abs(fator) <= 1) {
                no.setNivel();

                // Se desbalanceada para a direita
            } else if (fator == 2) {

                int fatorFilhoDir = No.getNivel(no.dir.dir) - No.getNivel(no.dir.esq);

                // Se o filho a direita tambem estiver desbalanceado
                if (fatorFilhoDir == -1) {
                    no.dir = rotacionarDir(no.dir);
                }
                no = rotacionarEsq(no);

                // Se desbalanceada para a esquerda
            } else if (fator == -2) {

                int fatorFilhoEsq = No.getNivel(no.esq.dir) - No.getNivel(no.esq.esq);

                // Se o filho a esquerda tambem estiver desbalanceado
                if (fatorFilhoEsq == 1) {
                    no.esq = rotacionarEsq(no.esq);
                }
                no = rotacionarDir(no);

            } else {
                System.out.println("Erro no No(" + no.elemento + ") com fator de balanceamento (" + fator + ") invalido!");
            }
        }

        return no;
    }

    private No rotacionarDir(No no) {
        //System.out.println("Rotacionar DIR(" + no.elemento + ")");
        No noEsq = no.esq;
        No noEsqDir = noEsq.dir;

        noEsq.dir = no;
        no.esq = noEsqDir;

        no.setNivel(); // Atualizar o nivel do no
        noEsq.setNivel(); // Atualizar o nivel do noEsq

        return noEsq;
    }

    private No rotacionarEsq(No no) {
        //System.out.println("Rotacionar ESQ(" + no.elemento + ")");
        No noDir = no.dir;
        No noDirEsq = noDir.esq;

        noDir.esq = no;
        no.dir = noDirEsq;

        no.setNivel(); // Atualizar o nivel do no
        noDir.setNivel(); // Atualizar o nivel do noDir
        return noDir;
    }
}

class TP04Q03 {
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
        AVL arvore = new AVL();
        int n = 0, i, stop;

        // ! INICIO DA LEITURA
        // Recebe a primeira parte da entrada
        do {
            entrada[n] = MyIO.readLine();
        } while (isFim(entrada[n++]) == false);
        // Salva as series na lista
        for (i = 0; i < (n - 1); i++) {
            series.readClass(entrada[i]);
            arvore.inserir(series.clone());
        }

        n = 0;
        // Recebe a primeira parte da entrada
        do {
            entrada[n] = MyIO.readLine();
        } while (isFim(entrada[n++]) == false);

        for (i = 0; i < (n - 1); i++) {
            System.out.print("raiz");
            arvore.pesquisar(entrada[i]);
        }
        // ! FIM DA LEITURA
    }
}