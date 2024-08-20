/**
 * Area of a hexagon.
 *
 * This program calculates the area of a regular hexagon
 * based on the length of its side provided by the user.
 * 
 * Formula used:
 * Area = (3 * sqrt(3) / 2) * side^2
 *
 * @author (Caeden Clark)
 * @version (260-60 2/17/23)
 */

import java.util.Scanner;
import java.lang.Math;

public class Area_of_a_hexagon {
    public static void main(String[] args) {
        // Create a Scanner object to capture input from the user
        Scanner input = new Scanner(System.in);
        
        // Prompt the user to enter the length of the side of the hexagon
        System.out.print("Enter the side: ");
        double side = input.nextDouble();
        
        // Calculate the area of the hexagon using the formula
        double area = (3 * Math.sqrt(3) / 2) * Math.pow(side, 2);
        
        // Print the calculated area of the hexagon to the console
        System.out.println("The area of the hexagon is " + area + ".");
        
        // Close the scanner
        input.close();
    }
}
