import java.io.RandomAccessFile;

public class TP01Q09 {
    public static void main(String[] args) throws Exception {
        int n = MyIO.readInt();
        RandomAccessFile saida = new RandomAccessFile("teste.txt", "rw");
        for (int i = 0; i < n; i++) {
            double valor = MyIO.readDouble();
            saida.writeDouble(valor);
        }
        int z = (int) saida.getFilePointer();
        saida.close();

        saida = new RandomAccessFile("teste.txt", "r");

        for (int i = 8; i < z; i += 8) {
            saida.seek(z - i);
            double aux = saida.readDouble();
            if (aux % 1 == 0) {
                MyIO.println((int) aux);
            } else
                MyIO.println(aux);
        }
    }
}