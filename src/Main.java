
import java.util.Scanner;
import java.util.HashMap;
import java.util.Stack;

public class Main {
    // expected result = 4 5 8 4 * 2 - 10 / ^ * 7 3 1 2 / ^ * +
    public static String operation = "4 * 5 ^ ( ( 8 * 4 - 2 ) / 10 ) + 7 * 3 ^ ( 1 / 2 )";
    public static String postfix = "";
    public static Stack<String> generalPurposeStack = new Stack<>();
    public static HashMap<String, Integer> values = new HashMap<>();

    public static void main(String[] args) {

        // fill hashMap
        values.put("+", 1);
        values.put("-", 1);
        values.put("*", 2);
        values.put("/", 2);
        values.put("^", 3);
        values.put("(", 4);

        Scanner input = new Scanner(operation);
        while (input.hasNext()) {
            String c = input.next();

            try {
                postfix += Integer.parseInt(c) + " ";
                System.out.println("Postfix = " + postfix);
            } catch (Exception e) {
                try {
                    ValidateHierarchy(c);
                } catch (Exception exc) {
                    System.out.println("Stack = " + generalPurposeStack.toString());
                }
            }
        }

        // Dump stack
        while (!generalPurposeStack.isEmpty()) {
            postfix += generalPurposeStack.pop() + " ";
        }
        System.out.println("Postfix expression: " + postfix);

        System.out.println("Result: " + solver());

    }

    // stack handling algorithm
    public static void ValidateHierarchy(String c) throws Exception {
        // Case: empty Stack
        if (generalPurposeStack.isEmpty()) {
            generalPurposeStack.push(c);
            throw new Exception("");
        }

        // Case: opening parenthesis
        if (c.equals("(")) {
            generalPurposeStack.push(c);
            throw new Exception("");
        }

        // Case: closing parenthesis
        if (c.equals(")")) {
            while (true) {
                if (generalPurposeStack.peek().equals("(")) {
                    generalPurposeStack.pop();
                    throw new Exception("");
                } else {
                    postfix += generalPurposeStack.pop() + " ";
                }
            }
        }

        // case: add a common value
        while (true) {
            if (generalPurposeStack.isEmpty()) {
                generalPurposeStack.push(c);
                throw new Exception("");
            }
            String k = generalPurposeStack.peek();
            if (values.get(c) <= values.get(k) && !k.equals("(")) {
                postfix += generalPurposeStack.pop() + " ";
            } else {
                generalPurposeStack.push(c);
                throw new Exception("");
            }
        }
    }

    // Algorithm to solve the problem expressed in reverse Polish notation.
    public static double solver(){
        Scanner input = new Scanner(postfix);
        while (input.hasNext()){
            String c = input.next();
            if (values.containsKey(c)){
                double a = Double.parseDouble(generalPurposeStack.pop());
                double b = Double.parseDouble(generalPurposeStack.pop());
                double result = 0;

                switch (c){
                    case "+":
                        result = b + a;
                        break;
                    case "-":
                        result = b - a;
                        break;
                    case "*":
                        result = b * a;
                        break;
                    case "/":
                        result = b / a;
                        break;
                    case "^":
                        result = Math.pow(b,a);
                        break;
                }

                generalPurposeStack.push(result + "");
            } else {
                generalPurposeStack.push(c);
            }
        }
        return Double.parseDouble(generalPurposeStack.pop());
    }
}