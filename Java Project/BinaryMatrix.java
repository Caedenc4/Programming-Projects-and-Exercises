/**
 * PE 6-2 Binary Matrix
 *
 * This program generates an n x n matrix of random binary values (0 or 1).
 * The size of the matrix is determined by user input.
 * 
 * @author (Caeden Clark)
 * @version (3/28/2023)
 */

import java.util.Random;
import java.util.Scanner;

public class BinaryMatrix {

    /**
     * Prints an n x n matrix of random binary values (0 or 1).
     *
     * @param n the size of the matrix (n x n)
     */
    public static void printMatrix(int n) {
        Random rand = new Random();
        // Loop through rows
        for (int i = 0; i < n; i++) {
            // Loop through columns
            for (int j = 0; j < n; j++) {
                // Print a random integer (0 or 1) followed by a space
                System.out.print(rand.nextInt(2) + " ");
            }
            // Move to the next line after each row is printed
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Prompt the user to enter the size of the matrix
        System.out.print("Enter n: ");
        int n = scanner.nextInt(); // Read the user input for matrix size
        scanner.close();
        
        // print the matrix
        printMatrix(n);
    }
}
