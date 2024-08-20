/**
 * TextAnalyzer
 * 
 * This program reads a text file, processes its content to generate a concordance, and outputs the results.
 * A concordance is a list of all the unique words in the text, along with the number of occurrences and 
 * the line numbers where each word appears.
 * 
 * Key Features:
 * - The program uses a Binary Search Tree (BST) to store words and their associated metadata.
 * - The user can specify whether the word matching should be case-sensitive or not.
 * - The output can be directed to either the console or a file.
 * - The concordance is displayed in alphabetical order with each word, its frequency, and the line numbers where it appears.
 * 
 * Input:
 * - The program prompts the user for the file path, case sensitivity preference, and output destination.
 * 
 * Output:
 * - The output is a formatted concordance listing words, their counts, and line numbers.
 * 
 * Example Output:
 *   hello           2 occurrence(s) on line(s): 1 3
 *   world           1 occurrence(s) on line(s): 2
 * 
 * Usage:
 * - Run the program and follow the prompts to specify the input file, case sensitivity, and output preference.
 * 
 * @author (Caeden Clark)
 * @version (may 1, 2024)
 */

import java.io.*;
import java.util.*;

class BSTNode {
    String word;
    int count;
    List<Integer> lineNumbers;
    BSTNode left, right;

    public BSTNode(String word, int lineNumber) {
        this.word = word;
        this.count = 1;
        this.lineNumbers = new ArrayList<>();
        this.lineNumbers.add(lineNumber);
        this.left = this.right = null;
    }
}

class BST {
    BSTNode root;

    public void insert(String word, int lineNumber) {
        root = insertRec(root, word, lineNumber);
    }

    private BSTNode insertRec(BSTNode root, String word, int lineNumber) {
        if (root == null) {
            return new BSTNode(word, lineNumber);
        }

        int cmp = word.compareToIgnoreCase(root.word);
        if (cmp < 0) {
            root.left = insertRec(root.left, word, lineNumber);
        } else if (cmp > 0) {
            root.right = insertRec(root.right, word, lineNumber);
        } else {
            root.count++;
            if (!root.lineNumbers.contains(lineNumber)) {
                root.lineNumbers.add(lineNumber);
            }
        }
        return root;
    }

    public void printConcordance(PrintWriter writer) {
        writer.println("Concordance of the input file:");
        printConcordanceRec(root, writer);
    }

    private void printConcordanceRec(BSTNode root, PrintWriter writer) {
        if (root == null) return;

        printConcordanceRec(root.left, writer);
        
        // Print word, count, and line numbers in a clear and readable format
        writer.printf("%-15s %3d occurrence(s) on line(s): %s%n",
                      root.word, root.count, lineNumbersFormatted(root.lineNumbers));
        
        printConcordanceRec(root.right, writer);
    }

    private String lineNumbersFormatted(List<Integer> lineNumbers) {
        return String.join(" ", lineNumbers.stream().map(String::valueOf).toArray(String[]::new));
    }
}

public class TextAnalyzer {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter file path: ");
        String filePath = scanner.nextLine();

        System.out.print("Case Sensitive? [Y]es or [N]o: ");
        boolean caseSensitive = scanner.nextLine().equalsIgnoreCase("Y");

        System.out.print("Output of results: [F]ile or [C]onsole: ");
        String outputPreference = scanner.nextLine().toUpperCase();

        String outputFilePath = filePath.replace(".txt", "_CND.txt");

        String text = readInputFile(filePath);
        int lineNumber = 1;
        BST bst = new BST();
        for (String line : text.split("\\R")) {
            for (String word : extractWords(line, caseSensitive)) {
                bst.insert(word, lineNumber);
            }
            lineNumber++;
        }

        PrintWriter writer;
        if (outputPreference.equals("F")) {
            writer = new PrintWriter(new FileWriter(outputFilePath));
        } else {
            writer = new PrintWriter(System.out, true);
        }

        bst.printConcordance(writer);
        writer.flush();
        writer.close();
    }

    private static String readInputFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: The file at " + filePath + " was not found.");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Error: An I/O error occurred while reading the file.");
            System.exit(1);
        }
        return content.toString();
    }

    private static List<String> extractWords(String text, boolean caseSensitive) {
        List<String> words = new ArrayList<>();
        String[] tokens = text.split("\\s+");
        for (String token : tokens) {
            token = token.replaceAll("^[^a-zA-Z0-9']+", "").replaceAll("[^a-zA-Z0-9']+$", "");
            if (!token.isEmpty()) {
                words.add(caseSensitive ? token : token.toLowerCase());
            }
        }
        return words;
    }
}
