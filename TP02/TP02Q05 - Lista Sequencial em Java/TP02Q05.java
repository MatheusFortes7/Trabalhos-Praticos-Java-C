import java.io.*;
import java.io.FileReader;

//Classe séries
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

    // Contrutora vazia
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

    // Construtora com valor
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

    // Contrutor de clone
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

    // Métodos SET
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

    // Métodos Get
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

    // Método Print
    public void printSerie() {
        System.out.println(this.nome + " " + this.formato + " " + this.duracao + " " + this.pais + " " + this.idioma
                + " " + this.emissora + " " + this.transmissao + " " + this.temporada + " " + this.episodios);
    }

    /*
     * Recebe a linha especifica e remove as tags "<>" dela, pegando apenas a parte
     * necessaria e retornando-a como String.
     */
    public String removeTag(String line) {
        String newLine = "";
        int i = 0;
        // System.out.println(line);
        while (i < line.length()) {
            // Se o char for '<' ele entra em loop até achar um '>' para ignorar.
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
                // Quando n eh tag, ele copia os elementos para outra String
                newLine += line.charAt(i);
            }
            i++;
        }
        // System.out.println(newLine);
        return newLine;
    }

    // Extrai o nome da série pela entrada
    public String forcaNome(String entrada) {
        String temp = "";
        for (int i = 0; i < entrada.length(); i++) {
            if (entrada.charAt(i) == '_') {
                temp += ' ';
            } else {
                temp += entrada.charAt(i);
            }
        }
        temp = temp.substring(0, temp.length() - 5);
        return temp;
    }

    // Extrai os inteiros da String
    public int parseINT(String line) {
        String temp = "";

        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) >= '0' && line.charAt(i) <= '9') {
                temp += line.charAt(i);
            } else {
                i = line.length();
            }
        }
        return Integer.parseInt(temp); // Converte o String temp para int resp
    }

    public void lerSerie(String entrada) {
        String arquivo = "/tmp/series/" + entrada;
        try {
            FileReader fileReader = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(fileReader);

            setNome(forcaNome(entrada));

            while (!br.readLine().contains("Formato"))
                ;
            setFormato(removeTag(br.readLine()));

            while (!br.readLine().contains("Duração"))
                ;
            setDuracao(removeTag(br.readLine()));

            while (!br.readLine().contains("País de origem"))
                ;
            setPais(removeTag(br.readLine()));

            while (!br.readLine().contains("Idioma original"))
                ;
            setIdioma(removeTag(br.readLine()));

            while (!br.readLine().contains("Emissora de televisão original"))
                ;
            setEmissora(removeTag(br.readLine()));

            while (!br.readLine().contains("Transmissão original"))
                ;
            setTransmissao(removeTag(br.readLine()));

            while (!br.readLine().contains("N.º de temporadas"))
                ;
            setTemporada(parseINT(removeTag(br.readLine())));

            while (!br.readLine().contains("N.º de episódios"))
                ;
            setEpisodios(parseINT(removeTag(br.readLine())));

            br.close();

        } catch (FileNotFoundException e) {
            MyIO.println("ERROR: Unable to open file '" + entrada + "'");
        } catch (IOException e) {
            MyIO.println("ERROR: Error reading file '" + entrada + "'");
        }
    }

}

// Classe de celula para lista
class CellSerie {

    public Object item;
    public CellSerie next;
    public CellSerie back;

    public CellSerie() {
        item = null;
        next = null;
        back = null;
    }

    public CellSerie(Object value) {
        item = value;
        next = null;
        back = null;
    }

    public CellSerie(Object value, CellSerie nextCell, CellSerie backCell) {
        item = value;
        next = nextCell;
        back = backCell;
    }
}

// Classe lista
class ClasseLista {
    private CellSerie first;
    private CellSerie last;
    private int qt;

    // Metodo contrutor de classe
    public ClasseLista() {
        first = new CellSerie();
        last = first;
        qt = 0;
    }

    // Metodos GET
    public Object getFirst() {
        Object temp = null;
        if (first != last) {
            temp = first.next.item;
        }
        return temp;
    }

    public Object getLast() {
        return last.item;
    }

    public Object getItemAt(int index) {
        Object resp = null;
        if (index == 1) {
            resp = getFirst();
        } else if (index == qt) {
            resp = getLast();
        } else if ((index > 1 && index < qt) && first != last) {
            CellSerie temp = first;
            for (int i = 0; i < index; i++, temp = temp.next)
                ;
            resp = temp.item;
        } else {
            MyIO.println("ERRO - Get Index com indice " + index + " não permitido");
        }
        return resp;
    }

    public int getQuantity() {
        return qt;
    }

    // Metodo de insercao no início (ATENÇÂO)
    public void addInicio(Object value) {
        CellSerie temp = new CellSerie(value, first.next, first);
        first.next = temp;
        if (first == last) {
            last = temp;
        } else {
            temp.next.back = temp;
        }
        qt++;
    }

    // Metodo de insercao no final
    public void addFinal(Object value) {
        last.next = new CellSerie(value, null, last);
        last = last.next;
        qt++;
    }

    // Metodo de insercao em posicao indicada
    public void insertIndex(Object value, int index) {
        if (index == 0) {
            this.addInicio(value);
        } else if (index == qt) {
            this.addFinal(value);
        } else if ((index > 0 && index < qt) && first != last) {
            CellSerie temp = first;
            for (int i = 0; i < index; i++, temp = temp.next)
                ;
            CellSerie aux = new CellSerie(value);
            // temp.back.next = temp.next.back = temp;

            aux.back = temp;
            aux.next = temp.next;
            aux.back.next = aux.next.back = aux;
            aux = temp = null;

            qt++;
        } else {
            MyIO.println("ERRO: Insersao com  Indice " + index + " maior que o total de objetos");
        }
    }

    // Metodo de impressao da lista
    public void printClasseLista() {
        for (CellSerie temp = first.next; temp != last.next; temp = temp.next) {
            MyIO.println(temp.item + " ");
        }
    }

    // Metodo que remove o primeiro
    public Object removeStart() {
        Object temp = null;
        if (first != last) {
            CellSerie aux = first;
            first = first.next;
            temp = first.item;
            aux.next = first.back = null;
            qt--;
        }
        return temp;
    }

    // Metodo que removo o ultimo
    public Object removeEnd() {
        Object temp = null;
        if (first != last) {
            temp = last.item; // Obtem o objeto do last
            last = last.back;
            last.next.back = null;
            last.next = null;
            qt--;
        }
        return temp;
    }

    // Metodo que remove a posição index
    public Object removeIndex(int index) {
        Object resp = null;
        if (index == 0) {
            resp = removeStart();
        } else if (index == qt - 1) {
            resp = removeEnd();
        } else if ((index > 0 && index < qt) && (first != last)) {

            CellSerie temp = first;
            for (int i = 0; i <= index; i++, temp = temp.next)
                ;// Encontra o valor do index

            temp.back.next = temp.next;
            temp.next.back = temp.back;

            resp = temp.item;
            temp.next = temp.back = null;
            temp = null;

            qt--;
        } else {
            MyIO.println("ERRO: Remocao com Index '" + index + "' invalido");
        }
        return resp;
    }

}

// Classe principal
public class TP02Q05 {
    // Funcao que para a leitura da entrada quando recebe FIM
    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    /*
     * Funcao que realiza a insercao ou remocao a ser executada na série. Recebe a
     * string, a serie e a lista, identifica o comando a ser utilizado e, por fim,
     * realiza a acao.
     */
    public static void alteraLista(String entrada, Serie series, ClasseLista lista) {
        int temp;
        if (entrada.charAt(0) == 'I' && entrada.charAt(1) == 'I') {
             // Entrada no inicio
            series.lerSerie(entrada.substring(3)); 
            // Retira o comando
            lista.addInicio(series); 
            // Insere na lista

        } else if (entrada.charAt(0) == 'I' && entrada.charAt(1) == '*') { 
            // Entrada no valor indicado
            if (entrada.charAt(4) == ' ') { 
                // Se a posicao for de apenas um digito
                series.lerSerie(entrada.substring(5)); 
                // Retira o comando e a posicao
                temp = 4;
            } else { 
                // Se a posicao possuir 2 digitos
                series.lerSerie(entrada.substring(6));
                temp = 5;
            }
            lista.insertIndex(series, Integer.parseInt(entrada.substring(3, temp)));
            series = (Serie) lista.getItemAt(Integer.parseInt(entrada.substring(3, temp)));

        } else if (entrada.charAt(0) == 'I' && entrada.charAt(1) == 'F') { 
            // Entrada no fim
            series.lerSerie(entrada.substring(3));
            lista.addFinal(series);

        } else if (entrada.charAt(0) == 'R' && entrada.charAt(1) == 'I') {
             // Remocao no inicio
            series = (Serie) lista.removeStart(); 
            // Remove da lista
            MyIO.println("(R) " + series.getNome()); 
            // Imprime o nome da serie removida
        } else if (entrada.charAt(0) == 'R' && entrada.charAt(1) == '*') { 
            // Remocao no valor indicado
            series = (Serie) lista.removeIndex(Integer.parseInt(entrada.substring(3)));
            MyIO.println("(R) " + series.getNome());
        } else if (entrada.charAt(0) == 'R' && entrada.charAt(1) == 'F') { 
            // Remocao no fim
            series = (Serie) lista.removeEnd();
            MyIO.println("(R) " + series.getNome());
        }
    }

    public static void main(String[] args) {
        String[] entrada = new String[1000];
        Serie series = new Serie(); // Declaracao de serie
        ClasseLista lista = new ClasseLista(); // Declaracao de lista
        int n = 0, i, stop;


        // Recebe a primeira parte da entrada
        do {
            entrada[n] = MyIO.readLine();
        } while (isFim(entrada[n++]) == false);
        // Salva as series na lista
        for (i = 0; i < (n - 1); i++) {
            series.lerSerie(entrada[i]);
            lista.addFinal(series.clone());
        }

        // Recebe a segunda parte da entrada
        n = 0;
        stop = MyIO.readInt(); // Recebe o total de alterações a serem feitas
        do {
            entrada[n] = MyIO.readLine();
            n++;
        } while (n < stop);
        
        // Realiza as alteracoes
        for (i = 0; i < n; i++) {
            alteraLista(entrada[i], series.clone(), lista);
        }

        // Imprime a lista final
        n = lista.getQuantity();
        for (i = 1; i <= n; i++) {
            series = (Serie) lista.getItemAt(i);
            series.printSerie();
            
        }

    }
}