/**
 * Reverse Polish Notation (RPN) calculator.
 *
 * This program evaluates RPN expressions using a stack-based approach.
 * It supports basic arithmetic operations: addition (+), subtraction (-),
 * multiplication (*), division (/), and exponentiation (^).
 * 
 * @author Caeden C
 * @version 4/1/24
 */

import java.util.*;
public class RPNCalculator {
    public static double evaluateRPN(String expression) {
        String[] tokens = expression.split("\\s+"); 
        Stack<Double> stack = new Stack<>(); 

        for (String token : tokens) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token)) {
                if (stack.size() < 2) {
                    System.out.println("Error: Insufficient operands for operator " + token);
                    return Double.NaN;
                }
                double operand2 = stack.pop();
                double operand1 = stack.pop();
                double result = applyOperator(token, operand1, operand2);
                stack.push(result);
            } else {
                System.out.println("Error: Invalid token " + token);
                return Double.NaN;
            }
        }
        if (stack.size() == 1) {
            return stack.pop();
        } else {
            System.out.println("Error: Invalid expression");
            return Double.NaN;
        }
    }
    public static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isOperator(String token) {
        return token.matches("[+\\-*/^]");
    }

    public static double applyOperator(String operator, double operand1, double operand2) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 == 0) {
                    System.out.println("Error: Division by zero");
                    return Double.NaN;
                }
                return operand1 / operand2;
            case "^":
                return Math.pow(operand1, operand2);
            default:
                System.out.println("Error: Unknown operator " + operator);
                return Double.NaN;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an RPN expression: ");
        String expression = scanner.nextLine();

        double result = evaluateRPN(expression);
        if (!Double.isNaN(result)) {
            System.out.println("Result: " + result);
        }

        scanner.close();
    }
}
