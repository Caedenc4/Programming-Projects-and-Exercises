/**
 * 12.1 Array Index Exception
 * 
 * This program creates an array of 100 random integers (between 1 and 1000).
 * It allows the user to enter an index to view the value at that position in the array.
 * 
 * Key Features:
 * - If the user enters an index out of range, the program will inform them that it's "Out of Bounds."
 * - If the user enters invalid input (like a non-integer), the program will prompt them to enter a valid index.
 * - The user can exit the program by typing `-1` or `q`.
 * 
 * This program demonstrates how to handle common errors when working with arrays in Java.
 * 
 * @author Caeden Clark
 * @version 2/5/2024
 */

import java.util.Random; import java.util.Scanner;
public class ArrayIndexException {
    public static void main(String[] args) {
        int[] arr = createRandomArray();
        queryArray(arr);
    }
    // Function to create an array of 100 random integers
    public static int[] createRandomArray() {
        Random rand = new Random();
        int[] arr = new int[100];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rand.nextInt(1000) + 1;
        }
        return arr;
    }
    // Function to query values in the array
    public static void queryArray(int[] arr) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Enter the index of the array (-1 to exit, 'q' to quit): ");
                String input = scanner.nextLine();
                // Check if user wants to exit
                if (input.equals("-1")) {
                    System.out.println("Exiting the program.");
                    break;
                }
                // Check if user wants to quit
                if (input.equalsIgnoreCase("q")) {
                    System.out.println("Exiting the program.");
                    break;
                }
                int index = Integer.parseInt(input);
                // Check if index is out of bounds
                if (index < 0 || index >= arr.length) {
                    System.out.println("Out of Bounds");
                    continue;
                }
                // Display corresponding element value
                System.out.println("The value at index " + index + " is " + arr[index]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid index (an integer), -1 to exit, or 'q' to quit.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
