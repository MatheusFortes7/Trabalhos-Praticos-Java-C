#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>


#define MAX_FIELD_SIZE 100
//CLASSE SERIE
typedef struct
{
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

char *remove_line_break(char *line)
{
   while (*line != '\r' && *line != '\n')
      line++;
   *line = '\0';
   return line;
}

char *freadline(char *line, int max_size, FILE *file)
{
   return remove_line_break(fgets(line, max_size, file));
}

char *readline(char *line, int max_size)
{
   return freadline(line, max_size, stdin);
}

void print_serie(Serie *serie)
{
   printf("%s %s %s %s %s %s %s %d %d\n",
          serie->nome,
          serie->formato,
          serie->duracao,
          serie->pais,
          serie->idioma,
          serie->emissora,
          serie->transmissao,
          serie->num_temporadas,
          serie->num_episodios);
}

// Retorna o tamanho em bytes de um arquivo.
long tam_arquivo(FILE *file)
{
   fseek(file, 0L, SEEK_END);
   long size = ftell(file);
   rewind(file);
   return size;
}

// Retorna todo o conteúdo do arquivo numa string.
char *ler_html(char filename[])
{
   FILE *file = fopen(filename, "r");
   if (!file)
   {
      fprintf(stderr, "Falha ao abrir arquivo %s\n", filename);
      exit(1);
   }
   long tam = tam_arquivo(file);
   char *html = (char *)malloc(sizeof(char) * (tam + 1));
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
char *extrair_texto(char *html, char *texto)
{
   char *start = texto;
   int contagem = 0;
   while (*html != '\0')
   {
      if (*html == '<')
      {
         if (
             (*(html + 1) == 'p') ||
             (*(html + 1) == 'b' && *(html + 2) == 'r') ||
             (*(html + 1) == '/' && *(html + 2) == 'h' && *(html + 3) == '1') ||
             (*(html + 1) == '/' && *(html + 2) == 't' && *(html + 3) == 'h') ||
             (*(html + 1) == '/' && *(html + 2) == 't' && *(html + 3) == 'd'))
            break;
         else
            contagem++;
      }
      else if (*html == '>')
         contagem--;
      else if (contagem == 0 && *html != '"')
      {
         if (*html == '&')
            html = strchr(html, ';');
         else if (*html != '\r' && *html != '\n')
            *texto++ = *html;
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
void ler_serie(Serie *serie, char *html)
{
   char texto[MAX_FIELD_SIZE];

   char *ptr = strstr(html, "<h1");
   extrair_texto(ptr, texto);

   char *parenteses_ptr = strchr(texto, '(');
   if (parenteses_ptr != NULL)
      *(parenteses_ptr - 1) = '\0';

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

int isFim(char line[])
{
   return line[0] == 'F' && line[1] == 'I' && line[2] == 'M';

}

//==================================LISTA============================================
#define MAXTAM 70
#define bool short
#define true 1
#define false 0
int contador = 0;


Serie array[MAXTAM]; // Elementos da pilha
int n;               // Quantidade de array.

void start()
{
   n = 0;
}

/**
 * Insere um elemento na primeira posicao da lista e move os demais
 * elementos para o fim da 
 * @param x int elemento a ser inserido.
 */
void inserirInicio(Serie x)
{
   //validar insercao
   if (n >= MAXTAM)
   {
      printf("Erro ao inserir!");
      exit(1);
   }

   //levar elementos para o fim do array
   for (int i = n; i > 0; i--)
   {
      array[i] = array[i - 1];
      contador++;
   }

   array[0] = x;
   n++;
}

/**
 * Insere um elemento na ultima posicao da 
 * @param x int elemento a ser inserido.
 */
void inserirFim(Serie x)
{

   //validar insercao
   if (n >= MAXTAM)
   {
      printf("Erro ao inserir!");
      exit(1);
   }

   array[n] = x;
   n++;
}

/**
 * Insere um elemento em uma posicao especifica e move os demais
 * elementos para o fim da 
 * @param x int elemento a ser inserido.
 * @param pos Posicao de insercao.
 */
void inserir(Serie x, int pos)
{
   //validar insercao
   if (n >= MAXTAM || pos < 0 || pos > n)
   {
      printf("Erro ao inserir!");
      exit(1);
   }

   //levar elementos para o fim do array
   for (int i = n; i > pos; i--)
   {
      array[i] = array[i - 1];
      contador++;
   }

   array[pos] = x;
   n++;
}

/**
 * Remove um elemento da primeira posicao da lista e movimenta 
 * os demais elementos para o inicio da mesma.
 * @return resp int elemento a ser removido.
 */
Serie removerInicio()
{
   Serie resp;

   //validar remocao
   if (n == 0)
   {
      printf("Erro ao remover!");
      exit(1);
   }

   resp = array[0];
   n--;

   for (int i = 0; i < n; i++)
   {
      array[i] = array[i + 1];
      contador++;
   }

   return resp;
}

/**
 * Remove um elemento da ultima posicao da 
 * @return resp int elemento a ser removido.
 */
Serie removerFim()
{

   //validar remocao
   if (n == 0)
   {
      printf("Erro ao remover!");
      exit(1);
   }

   return array[--n];
}

/**
 * Remove um elemento de uma posicao especifica da lista e 
 * movimenta os demais elementos para o inicio da mesma.
 * @param pos Posicao de remocao.
 * @return resp int elemento a ser removido.
 */
Serie remover(int pos)
{
   Serie resp;

   //validar remocao
   if (n == 0 || pos < 0 || pos >= n)
   {
      printf("Erro ao remover!");
      exit(1);
   }

   resp = array[pos];
   n--;

   for (int i = pos; i < n; i++)
   {
      array[i] = array[i + 1];
      contador++;
   }

   return resp;
}

/**
 * Mostra os array separados por espacos.
 */
void mostrar()
{
   for (int i = 0; i < n; i++)
   {
      printf("%s %s %s %s %s %s %s %d %d\n",
             array[i].nome,
             array[i].formato,
             array[i].duracao,
             array[i].pais,
             array[i].idioma,
             array[i].emissora,
             array[i].transmissao,
             array[i].num_temporadas,
             array[i].num_episodios);
   }
}

/**
 * Procura um array e retorna se ele existe.
 * @param x int elemento a ser pesquisado.
 * @return <code>true</code> se o array existir,
 * <code>false</code> em caso contrario.
 */
/*bool pesquisar(Serie x) {
   bool retorno = false;
   for (int i = 0; i < n && retorno == false; i++) {
      retorno = (array[i] == x);
   }
   return retorno;
}*/

//========================CODIGO=DE=ORDENAÇÃO=========================================
void swap(int i, int j)
{
   Serie aux = array[i];
   array[i] = array[j];
   array[j] = aux;
}

void selecao()
{
   for (int i = 0; i < (n - 1); i++)
   {
      int menor = i;
      for (int j = (i + 1); j < n; j++) {
         if (strcmp(array[menor].pais, array[j].pais) > 0) { 
            menor = j;
            contador++;
         } else if(strcmp(array[menor].pais, array[j].pais) == 0){
            if (strcmp(array[menor].nome, array[j].nome) > 0)
            {
               menor = j;
               contador++;
            }
            
         }
      }
      swap(menor, i);
   }
}

//================================MAIN==============================================
int main(){
   Serie serie;
   clock_t t;
    size_t tam_prefixo = strlen(PREFIXO);
    char line[MAX_LINE_SIZE];
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

   selecao();
   mostrar();
   t = clock() - t;
   FILE *arq;
   
   arq = fopen("747358_selecaoRecusriva.txt", "a");

   fprintf(arq, "747358 \t %ld \t %d", t, contador);;

   fclose(arq);

   return EXIT_SUCCESS;
}
