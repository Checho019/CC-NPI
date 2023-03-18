
import java.util.Scanner;
import java.util.HashMap;
import java.util.Stack;

public class Main {
    // Resultado esperado = 4 5 8 4 * 2 - 10 / ^ * 7 3 1 2 / ^ * +
    public static String operacion = "4 * 5 ^ ( ( 8 * 4 - 2 ) / 10 ) + 7 * 3 ^ ( 1 / 2 )";
    public static String posfija = "";
    public static Stack<String> stackOperaciones = new Stack<>();
    public static HashMap<String, Integer> valores = new HashMap<>();

    public static void main(String[] args) {

        // Llenar hashMap
        valores.put("+", 1);
        valores.put("-", 1);
        valores.put("*", 2);
        valores.put("/", 2);
        valores.put("^", 3);
        valores.put("(", 4);

        Scanner entrada = new Scanner(operacion);
        while (entrada.hasNext()) {
            String c = entrada.next();

            try {
                posfija += Integer.parseInt(c) + " ";
                System.out.println("Posfija = " + posfija);
            } catch (Exception e) {
                try {
                    validarJerarquia(c);
                } catch (Exception exc) {
                    System.out.println("Stack = " + stackOperaciones.toString());
                }
            }
        }

        // Volcar el stack ya que son las ultimas operaciones que quedan
        while (!stackOperaciones.isEmpty()) {
            posfija += stackOperaciones.pop() + " ";
        }
        System.out.println("POSFIJA FINAL: " + posfija);
    }

    public static void validarJerarquia(String c) throws Exception {
        // Caso de stack vacio
        if (stackOperaciones.isEmpty()) {
            stackOperaciones.push(c);
            throw new Exception("");
        }

        // Caso: Agregar parentesis de apertura al stack
        if (c.equals("(")) {
            stackOperaciones.push(c);
            throw new Exception("");
        }

        // caso: Parentesis de cierre entra al stack
        if (c.equals(")")) {
            while (true) {
                if (stackOperaciones.peek().equals("(")) {
                    stackOperaciones.pop();
                    throw new Exception("");
                } else {
                    posfija += stackOperaciones.pop() + " ";
                }
            }
        }

        // Caso: Agregar un valor comun y corriente
        while (true) {
            if (stackOperaciones.isEmpty()) {
                stackOperaciones.push(c);
                throw new Exception("");
            }
            String k = stackOperaciones.peek();
            if (valores.get(c) <= valores.get(k) && !k.equals("(")) {
                posfija += stackOperaciones.pop() + " ";
            } else {
                stackOperaciones.push(c);
                throw new Exception("");
            }
        }
    }
}