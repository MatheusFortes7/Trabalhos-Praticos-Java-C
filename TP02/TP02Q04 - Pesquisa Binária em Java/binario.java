import java.io.*;
import java.util.*;

public class binario {

    public static String substituir(String nomeArq){
        String aux="";
    
        aux = nomeArq.replace(".html", "");
        for(int i = 0; i < nomeArq.length(); i++){
            if(nomeArq.charAt(i) == '_')
                aux = aux.replaceAll("_", " ");   
        }
        return aux;
    }
    
    public static boolean pesquisaBinaria(String aux[], String nomeSeq, int cont){
        boolean resp = false;
        int dir = 30, esq = 0, meio;
        
        while(esq <= dir){
            meio = (esq + dir) / 2;
            cont++;
            
            if(nomeSeq.compareTo(aux[meio]) == 0){
                resp = true;
                esq = 31;
            }else if(nomeSeq.compareTo(aux[meio]) > 0){ 
                esq = meio + 1;
            } else{
                dir = meio - 1;
            }
        }
        return resp;
    }

    public static long now(){
        return new Date().getTime();
    }


    public static void main(String args[]) throws Exception{
        String nomeArq[] = new String[33];
        String aux[] = new String[31];
        String nomeSeq="";
        long inicio=0, fim=0;
        double dif=0;
        int i=0, cont=0;
        
        inicio = now();   

        nomeArq[i] = MyIO.readLine();
        while(nomeArq[i].compareTo("FIM") != 0 ){
            aux[i] =  substituir(nomeArq[i]);
            i++;
            nomeArq[i] = MyIO.readLine();
        }

        nomeSeq = MyIO.readLine();
        while(nomeSeq.compareTo("FIM") != 0){
            if(pesquisaBinaria(aux, nomeSeq, cont) == true){
                System.out.println("SIM");
            } else{
               System.out.println("NÃO");
            }
            nomeSeq = MyIO.readLine();
        
        }
        
        fim = now();
        dif = (fim - inicio) / 1000.0;

        RandomAccessFile Arq = new RandomAccessFile("matricula_binaria.txt", "rw");

        Arq.writeChars("\t" + dif + "\t" + cont + "\t");

        Arq.close();
    }
}
