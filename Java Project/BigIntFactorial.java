/**
 * BigIntFactorial
 *
 * This program calculates the factorial of an integer using the BigInteger class to handle large numbers.
 * Features:
 * - Prompts the user for an integer and calculates its factorial.
 * - Checks for non-negative input (factorials are undefined for negative numbers).
 * - Formats the result with commas for readability.
 * - Determines if the result fits in an int or requires BigInteger.
 * - Runs in a loop, allowing repeated calculations.
 * 
 * Example:
 * - Input: 5 -> Output: "5! is 120 and requires int"
 * - Input: 20 -> Output: "20! is 2,432,902,008,176,640,000 and requires BigInteger"
 * 
 * Author: Caeden Clark
 * Version: 3/20/2024
 */

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Scanner;
public class BigIntFactorial {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NumberFormat formatter = NumberFormat.getInstance();
        while (true) {
            System.out.print("Find Factorial of what integer? ");
            int n = scanner.nextInt();
            
            if (n < 0) {
                System.out.println("Factorial is not defined for negative numbers.");
                continue;
            }
            BigInteger result = factorial(BigInteger.valueOf(n));
            String resultString = formatter.format(result);
            String dataType = "int";
            if (resultString.length() > 10) {
                dataType = "BigInteger";
            }
            
            System.out.println(n + "! is " + resultString + " and requires " + dataType);
            
        }
    }
    public static BigInteger factorial(BigInteger n) {
        if (n.equals(BigInteger.ZERO) || n.equals(BigInteger.ONE)) {
            return BigInteger.ONE;
        } else {
            return n.multiply(factorial(n.subtract(BigInteger.ONE)));
        }
    }
}