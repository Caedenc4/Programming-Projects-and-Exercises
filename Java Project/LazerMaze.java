/**
 * LazerMaze game where the player must locate mirrors on a grid by shooting at them or guessing their locations.
 *
 * @author Caeden Clark
 * @version 5/2/23
 */

import java.util.Scanner;
import java.util.Random;

public class LazerMaze {
    private static final int GRID_SIZE = 5;
    private static final int DEFAULT_NUM_MIRRORS = 3;
    private char[][] grid;
    private int mirrorsRemaining;
    private int shotsTaken = 0;
    private int correctGuesses = 0;
    private int incorrectGuesses = 0;
    private long startTime;

    public LazerMaze(int numMirrors) {
        this.grid = new char[GRID_SIZE][GRID_SIZE];
        this.mirrorsRemaining = numMirrors;
        this.startTime = System.currentTimeMillis();
        placeMirrors(numMirrors);
    }

    // Places the mirrors randomly on the grid
    private void placeMirrors(int numMirrors) {
        Random rand = new Random();
        for (int i = 0; i < numMirrors; i++) {
            int row, col;
            do {
                row = rand.nextInt(GRID_SIZE);
                col = rand.nextInt(GRID_SIZE);
            } while (this.grid[row][col] != 0); // Avoid placing multiple mirrors in the same spot

            this.grid[row][col] = (rand.nextBoolean() ? '\\' : '/');
        }
    }

    // Displays the grid
    public void displayMap(boolean showAll) {
        System.out.println("   A  B  C  D  E");
        for (int row = 0; row < GRID_SIZE; row++) {
            System.out.print(row + 1 + " ");
            for (int col = 0; col < GRID_SIZE; col++) {
                char c = '.';
                if (showAll || this.grid[row][col] == 'X' || this.grid[row][col] == 'H') {
                    c = this.grid[row][col];
                }
                System.out.print(" " + c + " ");
            }
            System.out.println();
        }
    }

    // Simulates shooting at a grid location
    public void shoot(int row, int col) {
        this.shotsTaken++;
        char mirror = this.grid[row][col];
        if (mirror == '/' || mirror == '\\') {
            System.out.println("Hit!");
            this.grid[row][col] = 'H';
            this.mirrorsRemaining--;
            this.correctGuesses++;
        } else {
            System.out.println("Miss!");
            this.grid[row][col] = 'X';
            this.incorrectGuesses++;
        }
    }

    // Simulates guessing the location of a mirror
    public void guess(int row, int col) {
        if (grid[row][col] == '/' || grid[row][col] == '\\') {
            System.out.println("Correct!");
            this.grid[row][col] = 'H';
            this.mirrorsRemaining--;
            this.correctGuesses++;
        } else {
            System.out.println("Miss!");
            this.grid[row][col] = 'X';
            this.incorrectGuesses++;
        }
    }

    // Displays the game statistics
    public void displayStats() {
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        int minutes = (int) (totalTime / 1000 / 60);
        int seconds = (int) ((totalTime / 1000) % 60);
        System.out.println("Game over!");
        System.out.println("Time taken: " + minutes + " minutes " + seconds + " seconds");
        System.out.println("Number of shots: " + this.shotsTaken);
        System.out.println("Number of correct guesses: " + this.correctGuesses);
        System.out.println("Number of incorrect guesses: " + this.incorrectGuesses);
    }

    public static void main(String[] args) {
        System.out.println("Welcome to LazerMaze!");
        System.out.println("                     ");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of mirrors (2-8, default " + DEFAULT_NUM_MIRRORS + "): ");
        int numMirrors = scanner.nextInt();
        while (numMirrors < 2 || numMirrors > 8) {
            System.out.print("Invalid input. Enter number of mirrors (2-8, default " + DEFAULT_NUM_MIRRORS + "): ");
            numMirrors = scanner.nextInt();
        }
        LazerMaze game = new LazerMaze(numMirrors);
        while (game.mirrorsRemaining > 0) {
            game.displayMap(false);
            System.out.print("[S]hoot, [G]uess, [M]ap, [H]elp, or [Q]uit? ");
            String input = scanner.next().toUpperCase();
            int row, col;
            String colInput;
            switch (input) {
                case "S":
                    System.out.print("Enter row (1-5): ");
                    row = scanner.nextInt() - 1;
                    System.out.print("Enter column (A-E): ");
                    colInput = scanner.next().toUpperCase();
                    col = colInput.charAt(0) - 'A';
                    game.shoot(row, col);
                    break;
                case "G":
                    System.out.print("Enter row (1-5): ");
                    row = scanner.nextInt() - 1;
                    System.out.print("Enter column (A-E): ");
                    colInput = scanner.next().toUpperCase();
                    col = colInput.charAt(0) - 'A';
                    game.guess(row, col);
                    break;
                case "M":
                    game.displayMap(true);
                    break;
                case "H":
                    System.out.println("Instructions:");
                    System.out.println(" - To shoot, select a row and column on the grid.");
                    System.out.println(" - To guess a mirror's location, select a row and column.");
                    System.out.println(" - To display the map with mirrors revealed, enter 'M'.");
                    System.out.println(" - To quit the game, enter 'Q'.");
                    break;
                case "Q":
                    game.displayStats();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input.");
                    break;
            }
        }
        game.displayMap(true);
        System.out.println("Congratulations, you win!");
        game.displayStats();
    }
}
