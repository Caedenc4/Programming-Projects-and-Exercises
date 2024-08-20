/**
 * InfixCalculator
 * 
 * This program converts a mathematical expression from infix notation 
 * (e.g., "3 + 5 * 2") to postfix notation (RPN, e.g., "3 5 2 * +") 
 * and then calculates the result.
 * 
 * Features:
 * - Supports basic arithmetic operations: +, -, *, /, and ^.
 * - Handles parentheses to enforce correct order of operations.
 * - Detects and reports errors like mismatched parentheses.
 * 
 * Example:
 * - Input: "3 + 5 * 2"
 * - Output: "RPN of 3 + 5 * 2 is 3 5 2 * +" and "Expression value = 13.0"
 * 
 * Author: Caeden C
 * Version: 4/10/24
 */

import java.util.*;
class Token {
    private String value;

    public Token(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public boolean isOperator() {
        return "+-*/^".contains(value);
    }

    public int getPrecedence() {
        if (value.equals("+") || value.equals("-")) return 1;
        if (value.equals("*") || value.equals("/")) return 2;
        if (value.equals("^")) return 3;
        return 0; 
    }

    public boolean isOpenParenthesis() {
        return value.equals("(");
    }

    public boolean isCloseParenthesis() {
        return value.equals(")");
    }
}

class InfixToPostfix {
    private Stack<Token> operatorStack;
    private StringBuilder postfix;

    public InfixToPostfix() {
        operatorStack = new Stack<>();
        postfix = new StringBuilder();
    }

    public String convert(String infix) {
        String[] tokens = infix.split("\\s+"); 
        for (String tokenStr : tokens) {
            Token token = new Token(tokenStr);
            if (!token.isOperator() && !token.isOpenParenthesis() && !token.isCloseParenthesis()) {
                postfix.append(token.getValue()).append(" "); 
            } else if (token.isOpenParenthesis()) {
                operatorStack.push(token);
            } else if (token.isCloseParenthesis()) {
                while (!operatorStack.isEmpty() && !operatorStack.peek().isOpenParenthesis()) {
                    postfix.append(operatorStack.pop().getValue()).append(" ");
                }
                if (!operatorStack.isEmpty()) {
                    operatorStack.pop(); // Discard the open parenthesis
                } else {
                    return "Error: Mismatched parentheses";
                }
            } else { // Operator
                while (!operatorStack.isEmpty() && !operatorStack.peek().isOpenParenthesis()
                        && token.getPrecedence() <= operatorStack.peek().getPrecedence()) {
                    postfix.append(operatorStack.pop().getValue()).append(" ");
                }
                operatorStack.push(token);
            }
        }
        while (!operatorStack.isEmpty()) {
            if (operatorStack.peek().isOpenParenthesis() || operatorStack.peek().isCloseParenthesis()) {
                return "Error: Mismatched parentheses";
            }
            postfix.append(operatorStack.pop().getValue()).append(" ");
        }
        return postfix.toString().trim();
    }
}

class PostfixEval {
    public double evaluate(String postfix) {
        Stack<Double> operandStack = new Stack<>();
        String[] tokens = postfix.split("\\s+");
        for (String tokenStr : tokens) {
            Token token = new Token(tokenStr);
            if (!token.isOperator()) {
                operandStack.push(Double.parseDouble(token.getValue()));
            } else {
                double operand2 = operandStack.pop();
                double operand1 = operandStack.pop();
                double result = performOperation(operand1, operand2, token.getValue());
                operandStack.push(result);
            }
        }
        return operandStack.pop();
    }

    private double performOperation(double operand1, double operand2, String operator) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 != 0) {
                    return operand1 / operand2;
                } else {
                    throw new ArithmeticException("Error: Division by zero");
                }
            case "^":
                return Math.pow(operand1, operand2);
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }
}

public class InfixCalculator  {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an infix expression: ");
        String infix = scanner.nextLine();

        InfixToPostfix converter = new InfixToPostfix();
        String postfix = converter.convert(infix);

        if (postfix.startsWith("Error")) {
            System.out.println(postfix);
        } else {
            System.out.println("RPN of " + infix + " IS " + postfix);
            PostfixEval evaluator = new PostfixEval();
            double result = evaluator.evaluate(postfix);
            System.out.println("Expression value = " + result);
        }

        scanner.close();
    }
}
