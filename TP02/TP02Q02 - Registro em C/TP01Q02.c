#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

typedef struct Serie{
    char nome[30];
    char idioma[15];
    char formato[15];
    char duracao[40];
    char paisDeOrigem[20];
    char emissora[30];
    char transmissao[30];
    int numEpisodios;
    int numTemporadas;
} Serie;

//contrutor
void Series (Serie* s){
    strcpy(s->nome, "");
    strcpy(s->idioma, "");
    strcpy(s->formato, "");
    strcpy(s->duracao, "");
    strcpy(s->paisDeOrigem, "");
    strcpy(s->emissora, "");
    strcpy(s->transmissao, "");
    s->numTemporadas = 0;
    s->numEpisodios = 0;
}

char* removeTags(char line[]){
    char newline = "";
    int i = 0;
    while (i < strlen(line)){
        if(line[i] == '<'){
            i++;
            while(line[i] != '>')i++;
        }else{
            newline += line[i];
        }
        i++;
    }

    
    return newline;
}
// strtsr == contains
void ler(char nomeArquivo, Serie* x){
    FILE *filme = fopen ("/tmp/series/","r");
    char linha[100];
    fgets(linha, 100, filme);
    while (!strstr(linha, "infobox_v2")){
        fgets(linha, 100, filme);
    }
    fgets(linha, 100, filme);
    strcpy(x->nome, removeTags(fgets(linha,100, filme)));

    fgets(linha, 100, filme);
    while (!strstr(linha, "Formato")){
        fgets(linha, 100, filme);
    }
    strcpy(x->formato, removeTags(fgets(linha,100, filme)));

    fgets(linha, 100, filme);
    while (!strstr(linha, "Duração")){
        fgets(linha, 100, filme);
    }
    strcpy(x->duracao, removeTags(fgets(linha,100, filme)));

    fgets(linha, 100, filme);
    while (!strstr(linha, "País de Origem")){
        fgets(linha, 100, filme);
    }
    strcpy(x->paisDeOrigem, removeTags(fgets(linha,100, filme)));

    fgets(linha, 100, filme);
    while (!strstr(linha, "Idioma original")){
        fgets(linha, 100, filme);
    }
    strcpy(x->idioma, removeTags(fgets(linha,100, filme)));

    fgets(linha, 100, filme);
    while (!strstr(linha, "Emissora de televisão original")){
        fgets(linha, 100, filme);
    }
    strcpy(x->emissora, removeTags(fgets(linha,100, filme)));

    fgets(linha, 100, filme);
    while (!strstr(linha, "Transmissão original")){
        fgets(linha, 100, filme);
    }
    strcpy(x->transmissao, removeTags(fgets(linha,100, filme)));

    fgets(linha, 100, filme);
    while (!strstr(linha, "N.º de temporadas")){
        fgets(linha, 100, filme);
    }
    strcpy(x->numTemporadas, removeTags(fgets(linha,100, filme)));

    fgets(linha, 100, filme);
    while (!strstr(linha, "N.º de episódios")){
        fgets(linha, 100, filme);
    }
    strcpy(x->numEpisodios, removeTags(fgets(linha,100, filme)));

    fclose(filme);
}

void imprimir(Serie *z){
    printf("%s %s %s %s %s %s %s %i %i", z->nome,z->formato,z->duracao,z->paisDeOrigem,z->idioma,z->emissora,z->transmissao,z->numTemporadas,z->numEpisodios);
}



bool isFim(char *s){
    return (strlen(s) >= 3 && s[0] == 'F' &&
        s[1] == 'I' && s[2] == 'M');
}

int main(){
    char entrada[1000][100];
    int numEntrada = 0;
    Serie teste;
    Series(&teste);
    // Leitura da entrada padrao
    do{
        scanf(" %[^\n]s", entrada[numEntrada]);
    } while (isFim(entrada[numEntrada++]) == false);
    numEntrada--; // Desconsiderar ultima linha contendo a palavra FIM

    // Para cada linha de entrada, gerando uma de saida contendo o numero de letras
    // maiusculas da entrada
    for (int i = 0; i < numEntrada; i++){
        
    }
    
    
    return 0;
}

