import java.util.Scanner;

//codigo feito em cima de um video do youtube. link: https://www.youtube.com/watch?v=_NYNw2OQOLs&ab_channel=csme

class interpreter {

    private String string;
    private int posicao;
    private int[] operandos = new int[3];
    private int count = 0;

    public interpreter() {
        this.string = "";
        this.posicao = 0;
        for (int i = 0; i < operandos.length; i++) {
            operandos[i] = 0;
        }
    }

    public void setString(String s) {
        this.string = s;
    }

    public void setPosicao(int i) {
        this.posicao = i;
    }

    public void setOperandos(int[] array) {
        for (int i = 0; i < 3; i++) {
            this.operandos[i] = array[i];
        }
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int[] getOperandos() {
        return operandos;
    }

    public int getOperandos(int num) {
        return operandos[num];
    }

    public int getPosicao() {
        return posicao;
    }

    public String getString() {
        return string;
    }

    public int getCount() {
        return count;
    }

    public void posicaoPlusPlus(int i) {
        this.posicao = posicao + i;
    }
} // fim interpreter

public class TP01Q05 {

    public static int lerExpressao(interpreter interpreter) {
        int resultado = 0;
        int aux = 0;
        String s = interpreter.getString();
        char c = s.charAt(interpreter.getPosicao());
        interpreter.posicaoPlusPlus(1);
        
        if (c == 'A') {
            resultado = interpreter.getOperandos(0);
        } else if (c == 'B') {
            resultado = interpreter.getOperandos(1);
        } else if (c == 'C') {
            resultado = interpreter.getOperandos(2);
        } else if (c == 'n') {
            interpreter.posicaoPlusPlus(3);
            if (lerExpressao(interpreter) == 0) {
                resultado = 1;
            } else if (lerExpressao(interpreter) == 1) {
                resultado = 0;
            }
            interpreter.posicaoPlusPlus(1);
        } else if (c == 'a') {
            interpreter.posicaoPlusPlus(3);
            if (lerExpressao(interpreter) == 0) {
                resultado = 0;
            } else if (lerExpressao(interpreter) == 1) {
                resultado = 1;
            }
            while (s.charAt(interpreter.getPosicao()) == ',') {
                interpreter.posicaoPlusPlus(1);
                aux = lerExpressao(interpreter);
                if (resultado == 0 || aux == 0) {
                    resultado = 0;
                } else {
                    resultado = 1;
                }
            }
            interpreter.posicaoPlusPlus(1);
        } else if (c == 'o') {
            interpreter.posicaoPlusPlus(3);
            if (lerExpressao(interpreter) == 0) {
                resultado = 0;
            } else if (lerExpressao(interpreter) == 1) {
                resultado = 1;
            }
            while (s.charAt(interpreter.getPosicao()) == ',') {
                interpreter.posicaoPlusPlus(1);
                aux = lerExpressao(interpreter);
                if (resultado == 0 || aux == 0) {
                    resultado = 1;
                } else {
                    resultado = 0;
                }
            }
            interpreter.posicaoPlusPlus(1);
        }

        return resultado;

    }

    public static String tiraEspacoString(String s) {
        String semEspaco = s;
        return semEspaco.replace(" ", "");
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        interpreter interpreter = new interpreter();

        // setando os operandos
        int numOperandos = input.nextInt();
        while (numOperandos != 0) {
            int[] operandos = new int[3];
            for (int j = 0; j < numOperandos; j++) {
                operandos[j] = input.nextInt();
            }
            interpreter.setOperandos(operandos);

            // tirando o espaço
            String espaco = "";
            String vazio = "";
            espaco = input.nextLine();
            vazio = tiraEspacoString(espaco);
            interpreter.setString(vazio);

            // setando a posição
            interpreter.setPosicao(0);

            int resultado = lerExpressao(interpreter);
            System.out.println(resultado);
            numOperandos = input.nextInt();

        }
    }
}