#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

bool isFim(char s[]){
    return (strlen(s) == 3 && s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}

bool verificarPalindromo(char s[]){
    bool resp = false; 
    for(int i = 0 ; i < strlen(s); i++){ //corre a string armazenando os caracteres de traz para frente
        if(s[i] == s[(strlen(s) -i -1 )]){
            resp = true;
        } else {
            resp = false;
            i = strlen(s);
        }
    }
    return resp;
}

int main(){
    char entrada[1000][100];
    int numEntrada = 0;

    // Leitura da entrada padrao
    do{
        scanf(" %[^\n]s", entrada[numEntrada]);
    } while (isFim(entrada[numEntrada++]) == false);
    numEntrada--; // Desconsiderar ultima linha contendo a palavra FIM

    // Para cada linha de entrada, gerando uma de saida contendo o numero de letras
    // maiusculas da entrada
    for (int i = 0; i < numEntrada; i++){
        if (verificarPalindromo(entrada[i])){
            printf("SIM\n"); // se a funcao retornar um true
        }
        else{
            printf("NAO\n"); // se a funcao retornar um false
        }
    }
    return 0;
}

