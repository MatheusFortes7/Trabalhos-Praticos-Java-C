import java.io.*;
import java.net.*;

class TP01Q08{
   public static boolean isFim(String s) {
      return (s.length() >= 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
   }

   public static String Html(String end) {
      URL url;
      InputStream is = null;
      BufferedReader br;
      String resp = "", line;

      try {
         url = new URL(end);
         is = url.openStream();
         br = new BufferedReader(new InputStreamReader(is));

         while ((line = br.readLine()) != null) {
            resp += line + "\n";
         }
      } catch (MalformedURLException mue) {
         mue.printStackTrace();
      } catch (IOException ioe) {
         ioe.printStackTrace();
      }
      try {
         is.close();
      } catch (IOException ioe) {
      }
      return resp;
   }

   public static int Tag(String html, String tag) {
      int qtd = 0;
      if (tag == "<table>") {
         for (int i = 0; i < html.length(); i++) {
            if (html.charAt(i) == '<') {
               if (html.charAt(i + 1) == 't' && html.charAt(i + 2) == 'a' && html.charAt(i + 3) == 'b'
                     && html.charAt(i + 4) == 'l' && html.charAt(i + 5) == 'e' && html.charAt(i + 6) == '>') {
                  qtd++;
               }
            }
         }
      } else {
         for (int i = 0; i < html.length(); i++) {
            if (html.charAt(i) == '<') {
               if (html.charAt(i + 1) == 'b' && html.charAt(i + 2) == 'r' && html.charAt(i + 3) == '>') {
                  qtd++;
               }
            }
         }
      }
      return qtd;
   }

   public static void Contador(String html, String titulo) {
      int[] qtd = new int[25];
      qtd[23] = Tag(html, "<br>");
      qtd[24] = Tag(html, "<table>");
      for (int i = 0; i < html.length(); i++) {
         if (html.charAt(i) >= 97 && html.charAt(i) <= 122 && (html.charAt(i) != 97 && html.charAt(i) != 101
               && html.charAt(i) != 105 && html.charAt(i) != 111 && html.charAt(i) != 117)) {
            qtd[22]++;
         } else {
            if (html.charAt(i) == 'a')
               qtd[0]++;
            else {
               if (html.charAt(i) == 'e')
                  qtd[1]++;
               else {
                  if (html.charAt(i) == 'i')
                     qtd[2]++;
                  else {
                     if (html.charAt(i) == 'o')
                        qtd[3]++;
                     else {
                        if (html.charAt(i) == 'u')
                           qtd[4]++;
                        else {
                           if (html.charAt(i) == '\u00E1') 
                              qtd[5]++;
                           else {
                              if (html.charAt(i) == '\u00E9')
                                 qtd[6]++;
                              else {
                                 if (html.charAt(i) == '\u00ED')
                                    qtd[7]++;
                                 else {
                                    if (html.charAt(i) == '\u00F3')
                                       qtd[8]++;
                                    else {
                                       if (html.charAt(i) == '\u00FA')
                                          qtd[9]++;
                                       else {
                                          if (html.charAt(i) == '\u00E0')
                                             qtd[10]++;
                                          else {
                                             if (html.charAt(i) == '\u00E8')
                                                qtd[11]++;
                                             else {
                                                if (html.charAt(i) == '\u00EC')
                                                   qtd[12]++;
                                                else {
                                                   if (html.charAt(i) == '\u00F2')
                                                      qtd[13]++;
                                                   else {
                                                      if (html.charAt(i) == '\u00F9')
                                                         qtd[14]++;
                                                      else {
                                                         if (html.charAt(i) == '\u00E3')
                                                            qtd[15]++;
                                                         else {
                                                            if (html.charAt(i) == '\u00F5')
                                                               qtd[16]++;
                                                            else {
                                                               if (html.charAt(i) == '\u00E2')
                                                                  qtd[17]++;
                                                               else {
                                                                  if (html.charAt(i) == '\u00EA')
                                                                     qtd[18]++;
                                                                  else {
                                                                     if (html.charAt(i) == '\u00EE')
                                                                        qtd[19]++;
                                                                     else {
                                                                        if (html.charAt(i) == '\u00F4')
                                                                           qtd[20]++;
                                                                        else {
                                                                           if (html.charAt(i) == '\u00FB')
                                                                              qtd[21]++;
                                                                        }
                                                                     }
                                                                  }
                                                               }
                                                            }
                                                         }
                                                      }
                                                   }
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      if (qtd[24] > 0) { 
         qtd[0] = qtd[0] - qtd[24];
         qtd[1] = qtd[1] - qtd[24];
         qtd[22] = qtd[22] - 3 * qtd[24];
      }
      if (qtd[23] > 0) {
         qtd[22] = qtd[22] - 2 * qtd[23];
      }

      System.out.println("a(" + (qtd[0]) + ") e(" + (qtd[1]) + ") i(" + qtd[2] + ") o(" + qtd[3] + ") u(" + qtd[4]+ ") "+ (char)'\u00E1' +"(" + qtd[5] + ") "+ (char)'\u00E9' +"(" + qtd[6] + ") "+ (char)'\u00ED' +"(" + qtd[7] + ") "+ (char)'\u00F3' +"(" + qtd[8] + ") "+ (char)'\u00FA' +"(" + qtd[9] + ") "+ (char)'\u00E0' +"(" + qtd[10]+ ") "+ (char)'\u00E8' +"(" + qtd[11] + ") "+ (char)'\u00EC' +"(" + qtd[12] + ") "+ (char)'\u00F2' +"(" + qtd[13] + ") "+ (char)'\u00F9' +"(" + qtd[14] + ") "+ (char)'\u00E3' +"(" + qtd[15] + ") "+ (char)'\u00F5' +"("+ qtd[16] + ") "+ (char)'\u00E2' +"(" + qtd[17] + ") "+ (char)'\u00EA' +"(" + qtd[18] + ") "+ (char)'\u00EE' +"(" + qtd[19] + ") "+ (char)'\u00F4' +"(" + qtd[20] + ") "+ (char)'\u00FB' +"(" + qtd[21]+ ") consoante(" + qtd[22] + ") <br>(" + qtd[23] + ") <table>(" + qtd[24] + ") " + titulo);
   }

   public static void main(String[] args) {
      String[] x = new String[100];
      String[] fim = new String[50];
      String[] titulo = new String[50];
      int numEntrada = 0;

      //Ler entrada
      do {
         x[numEntrada] = MyIO.readLine();
      } while (isFim(x[numEntrada++]) == false);
      numEntrada--;//Desconsiderar a palavra FIM

      String html;

      for (int i = 0, j = 0; i < numEntrada; i += 2, j++) {
         titulo[j] = x[i];
         fim[j] = x[i + 1];
         html = Html(fim[j]);
         Contador(html, titulo[j]);
      }
   }
}
