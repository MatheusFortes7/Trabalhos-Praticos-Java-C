#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <err.h>

#define MAX_FIELD_SIZE 100
//CLASSE SERIE
typedef struct {
    char nome[MAX_FIELD_SIZE];
    char formato[MAX_FIELD_SIZE];
    char duracao[MAX_FIELD_SIZE];
    char pais[MAX_FIELD_SIZE];
    char idioma[MAX_FIELD_SIZE];
    char emissora[MAX_FIELD_SIZE];
    char transmissao[MAX_FIELD_SIZE];
    int num_temporadas;
    int num_episodios;
} Serie;

char *remove_line_break(char *line) {
    while (*line != '\r' && *line != '\n') line++;
    *line = '\0';
    return line;
}

char *freadline(char *line, int max_size, FILE *file) {
    return remove_line_break(fgets(line, max_size, file));
}

char *readline(char *line, int max_size) {
    return freadline(line, max_size, stdin);
}

void print_serie(Serie *serie) {
    printf("%s %s %s %s %s %s %s %d %d\n",
        serie->nome,
        serie->formato,
        serie->duracao,
        serie->pais,
        serie->idioma,
        serie->emissora,
        serie->transmissao,
        serie->num_temporadas,
        serie->num_episodios
    );
}

// Retorna o tamanho em bytes de um arquivo.
long tam_arquivo(FILE *file) {
    fseek(file, 0L, SEEK_END);
    long size = ftell(file);
    rewind(file);
    return size;
}

// Retorna todo o conteúdo do arquivo numa string.
char *ler_html(char filename[]) {
    FILE *file = fopen(filename, "r");
    if (!file) {
        fprintf(stderr, "Falha ao abrir arquivo %s\n", filename);
        exit(1);
    }
    long tam = tam_arquivo(file);
    char *html = (char *) malloc(sizeof(char) * (tam + 1));
    fread(html, 1, tam, file);
    fclose(file);
    html[tam] = '\0';
    return html;
}

/**
 * @brief Extrai os textos de uma tag html.
 * 
 * @param html Ponteiro para o caractere que abre a tag '<'.
 * @param texto Ponteiro para onde o texto deve ser colocado.
 * 
 * @return Ponteiro para o texto extraído.
 */
char *extrair_texto(char *html, char *texto) {
    char *start = texto;
    int contagem = 0;
    while (*html != '\0') {
        if (*html == '<') {
            if (
                (*(html + 1) == 'p') ||
                (*(html + 1) == 'b' && *(html + 2) == 'r') ||
                (*(html + 1) == '/' && *(html + 2) == 'h' && *(html + 3) == '1') ||
                (*(html + 1) == '/' && *(html + 2) == 't' && *(html + 3) == 'h') ||
                (*(html + 1) == '/' && *(html + 2) == 't' && *(html + 3) == 'd')
            ) break;
            else contagem++;
        }
        else if (*html == '>') contagem--;
        else if (contagem == 0 && *html != '"') {
            if (*html == '&') html = strchr(html, ';');
            else if (*html != '\r' && *html != '\n') *texto++ = *html;
        }
        html++;
    }
    *texto = '\0';
    return *start == ' ' ? start + 1 : start;
}

/**
 * @brief Lê o HTML da série e popula os campos da struct.
 * 
 * @param serie Struct Serie que vai receber os dados.
 * @param html String contendo todo o HTML do arquivo.
 */
void ler_serie(Serie *serie, char *html) {
    char texto[MAX_FIELD_SIZE];

    char *ptr = strstr(html, "<h1");
    extrair_texto(ptr, texto);

    char *parenteses_ptr = strchr(texto, '(');
    if (parenteses_ptr != NULL) *(parenteses_ptr - 1) = '\0';

    strcpy(serie->nome, texto);

    ptr = strstr(ptr, "<table class=\"infobox_v2\"");

    ptr = strstr(ptr, "Formato");
    ptr = strstr(ptr, "<td");
    strcpy(serie->formato, extrair_texto(ptr, texto));

    ptr = strstr(ptr, "Duração");
    ptr = strstr(ptr, "<td");
    strcpy(serie->duracao, extrair_texto(ptr, texto));

    ptr = strstr(ptr, "País de origem");
    ptr = strstr(ptr, "<td");
    strcpy(serie->pais, extrair_texto(ptr, texto));

    ptr = strstr(ptr, "Idioma original");
    ptr = strstr(ptr, "<td");
    strcpy(serie->idioma, extrair_texto(ptr, texto));

    ptr = strstr(ptr, "Emissora de televisão original");
    ptr = strstr(ptr, "<td");
    strcpy(serie->emissora, extrair_texto(ptr, texto));

    ptr = strstr(ptr, "Transmissão original");
    ptr = strstr(ptr, "<td");
    strcpy(serie->transmissao, extrair_texto(ptr, texto));

    ptr = strstr(ptr, "N.º de temporadas");
    ptr = strstr(ptr, "<td");
    sscanf(extrair_texto(ptr, texto), "%d", &serie->num_temporadas);

    ptr = strstr(ptr, "N.º de episódios");
    ptr = strstr(ptr, "<td");
    sscanf(extrair_texto(ptr, texto), "%d", &serie->num_episodios);
}

#define MAX_LINE_SIZE 250
#define PREFIXO "/tmp/series/"
// #define PREFIXO "../entrada e saida/tp02/series/"

int isFim(char line[]) {
    return line[0] == 'F' && line[1] == 'I' && line[2] == 'M';
}

//==================================LISTA============================================
int contador;
#define bool   short
#define true   1
#define false  0

//TIPO CELULA ===================================================================
typedef struct CelulaDupla {
	Serie elemento;        // Elemento inserido na celula.
	struct CelulaDupla* prox; // Aponta a celula prox.
   struct CelulaDupla* ant;  // Aponta a celula anterior.
} CelulaDupla;

CelulaDupla* novaCelulaDupla(Serie elemento) {
   CelulaDupla* nova = (CelulaDupla*) malloc(sizeof(CelulaDupla));
   nova->elemento = elemento;
   nova->ant = nova->prox = NULL;
   return nova;
}

//LISTA PROPRIAMENTE DITA =======================================================
CelulaDupla* primeiro;
CelulaDupla* ultimo;


/**
 * Cria uma lista dupla sem elementos (somente no cabeca).
 */
void start () {
   primeiro = novaCelulaDupla(-1);
   ultimo = primeiro;
}


/**
 * Insere um elemento na primeira posicao da lista.
 * @param x int elemento a ser inserido.
 */
void inserirInicio(Serie x) {
   CelulaDupla* tmp = novaCelulaDupla(x);

   tmp->ant = primeiro;
   tmp->prox = primeiro->prox;
   primeiro->prox = tmp;
   if (primeiro == ultimo) {                    
      ultimo = tmp;
   } else {
      tmp->prox->ant = tmp;
   }
   tmp = NULL;
}


/**
 * Insere um elemento na ultima posicao da lista.
 * @param x int elemento a ser inserido.
 */
void inserirFim(Serie x) {
   ultimo->prox = novaCelulaDupla(x);
   ultimo->prox->ant = ultimo;
   ultimo = ultimo->prox;
}


/**
 * Remove um elemento da primeira posicao da lista.
 * @return resp int elemento a ser removido.
 */
Serie removerInicio() {
   if (primeiro == ultimo) {
      errx(1, "Erro ao remover (vazia)!");
   }

   CelulaDupla* tmp = primeiro;
   primeiro = primeiro->prox;
   Serie resp = primeiro->elemento;
   tmp->prox = primeiro->ant = NULL;
   free(tmp);
   tmp = NULL;
   return resp;
}


/**
 * Remove um elemento da ultima posicao da lista.
 * @return resp int elemento a ser removido.
 */
Serie removerFim() {
   if (primeiro == ultimo) {
      errx(1, "Erro ao remover (vazia)!");
   } 
   Serie resp = ultimo->elemento;
   ultimo = ultimo->ant;
   ultimo->prox->ant = NULL;
   free(ultimo->prox);
   ultimo->prox = NULL;
   return resp;
}


/**
 *  Calcula e retorna o tamanho, em numero de elementos, da lista.
 *  @return resp int tamanho
 */
int tamanho() {
   int tamanho = 0; 
   CelulaDupla* i;
   for(i = primeiro; i != ultimo; i = i->prox, tamanho++);
   return tamanho;
}


/**
 * Insere um elemento em uma posicao especifica considerando que o 
 * primeiro elemento valido esta na posicao 0.
 * @param x int elemento a ser inserido.
 * @param pos int posicao da insercao.
 * @throws Exception Se <code>posicao</code> invalida.
 */
void inserir(Serie x, int pos) {

   int tam = tamanho();

   if(pos < 0 || pos > tam){
      errx(1, "Erro ao remover (posicao %d/%d invalida!", pos, tam);
   } else if (pos == 0){
      inserirInicio(x);
   } else if (pos == tam){
      inserirFim(x);
   } else {
      // Caminhar ate a posicao anterior a insercao
      CelulaDupla* i = primeiro;
      int j;
      for(j = 0; j < pos; j++, i = i->prox);

      CelulaDupla* tmp = novaCelulaDupla(x);
      tmp->ant = i;
      tmp->prox = i->prox;
      tmp->ant->prox = tmp->prox->ant = tmp;
      tmp = i = NULL;
   }
}


/**
 * Remove um elemento de uma posicao especifica da lista
 * considerando que o primeiro elemento valido esta na posicao 0.
 * @param posicao Meio da remocao.
 * @return resp int elemento a ser removido.
 * @throws Exception Se <code>posicao</code> invalida.
 */
Serie remover(int pos) {
   Serie resp;
   int tam = tamanho();

   if (primeiro == ultimo){
      errx(1, "Erro ao remover (vazia)!");
   } else if(pos < 0 || pos >= tam){
      errx(1, "Erro ao remover (posicao %d/%d invalida!", pos, tam);
   } else if (pos == 0){
      resp = removerInicio();
   } else if (pos == tam - 1){
      resp = removerFim();
   } else {
      // Caminhar ate a posicao anterior a insercao
      CelulaDupla* i = primeiro->prox;
      int j;
      for(j = 0; j < pos; j++, i = i->prox);

      i->ant->prox = i->prox;
      i->prox->ant = i->ant;
      resp = i->elemento;
      i->prox = i->ant = NULL;
      free(i);
      i = NULL;
   }

   return resp;
}


/**
 * Mostra os elementos da lista separados por espacos.
 */
void mostrar() {
   CelulaDupla* i;
   for (i = primeiro->prox; i != NULL; i = i->prox) {
      printf("%s ", i->elemento.nome);
      printf("%s ", i->elemento.formato);
      printf("%s ", i->elemento.duracao);
      printf("%s ", i->elemento.pais);
      printf("%s ", i->elemento.idioma);
      printf("%s ", i->elemento.emissora);
      printf("%s ", i->elemento.transmissao);
      printf("%d ", i->elemento.num_temporadas);
      printf("%d ", i->elemento.num_episodios);

   }
}


/**
 * Mostra os elementos da lista de forma invertida 
 * e separados por espacos.
 */
void mostrarInverso() {
   
   CelulaDupla* i;
   for (i = ultimo; i != primeiro; i = i->ant){
      printf("%s ", i->elemento.nome);
      printf("%s ", i->elemento.formato);
      printf("%s ", i->elemento.duracao);
      printf("%s ", i->elemento.pais);
      printf("%s ", i->elemento.idioma);
      printf("%s ", i->elemento.emissora);
      printf("%s ", i->elemento.transmissao);
      printf("%d ", i->elemento.num_temporadas);
      printf("%d ", i->elemento.num_episodios);
   }
   
}

CelulaDupla getCelulaIndex(int pos){
    int tmp = 0;
    CelulaDupla i;
    for( i.elemento = primeiro->prox->elemento; tmp < pos ; i.elemento = i.prox->elemento, tmp++);
    return i;
}

void swap(int i, int primeiro) {
    Serie aux = getCelulaIndex(i).elemento;
    getCelulaIndex(i).elemento = getCelulaIndex(primeiro).elemento;
    getCelulaIndex(primeiro).elemento = aux;
}

void quicksortRec(int *array, int esq, int dir) {
    int i = esq, j = dir;
    Serie pivo = getCelulaIndex((esq+dir)/2).elemento;
    while (i <= j) {
        CelulaDupla aux = getCelulaIndex(i);
        while (strcmp(aux.elemento.pais, pivo.pais) > 0){
            i++;
            aux.elemento = aux.prox->elemento;
        }
        CelulaDupla index = getCelulaIndex(j);
        while (strcmp(index.elemento.pais, pivo.pais)> 0) {
            j--;
            index.elemento = index.prox->elemento;
        }
        if (i <= j) {
            swap(i, j);
            i++;
            j--;
        }
    }
    if (esq < j)  quicksortRec(array, esq, j);
    if (i < dir)  quicksortRec(array, i, dir);
}
//=============================================================================
void quicksort(int *array, int n) {
    quicksortRec(array, 0, n-1);
}


//========================CODIGO=DE=ORDENAÇÃO=========================================

//=============================================================================


//================================MAIN==============================================
int main() {
    Serie serie;
    size_t tam_prefixo = strlen(PREFIXO);
    char line[MAX_LINE_SIZE];
    clock_t t;
    t = clock();
    strcpy(line, PREFIXO);
    readline(line + tam_prefixo, MAX_LINE_SIZE);

    while (!isFim(line + tam_prefixo)) {
        char *html = ler_html(line);
        ler_serie(&serie, html);
        free(html);
        inserirFim(serie);
        readline(line + tam_prefixo, MAX_LINE_SIZE);
    }
    
    
    
    mostrar();
    t = clock() - t;

    FILE *arq;
   
   arq = fopen("747358_countingsort.txt", "a");

   fprintf(arq, "747358 \t %ld \t %d", t, contador);;

   fclose(arq);

    return EXIT_SUCCESS;
}


