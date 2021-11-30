#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

bool isFim(char s[]){
    return (strlen(s) == 3 && s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}

bool verificarPalindromo(char s[], int count, int z){ 
    bool resp = false; 

    if (z < strlen(s)/2) { 
        if(s[z] == s[(strlen(s) - z - 1 )]){   
            return verificarPalindromo( s , count + 1, z+1);
        } else { 
            return verificarPalindromo(s, count, z+1);
        }
    } 

    if(count == strlen(s)/2){
        resp = true;
    } else {
        resp = false;
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
        if (verificarPalindromo(entrada[i],0,0)){
            printf("SIM\n"); // se a funcao retornar um true
        }
        else{
            printf("NAO\n"); // se a funcao retornar um false
        }
    }
    return 0;
}

