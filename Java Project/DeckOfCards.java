/**
 * code tha simulates dealing and sorting a hand of cards from a 52-card deck.
 * The code shuffles the deck, deals a hand of 5 cards, and sorts the hand.
 * The user can deal multiple hands until there are not enough cards left in the deck.
 *
 * @author (Caeden Clark)
 * @version (4/11/2023)
 */

import java.util.*;

public class DeckOfCards {
    public static void main(String[] args) {
        Deck deck = new Deck(); // Create a new deck of 52 cards
        deck.shuffle(); // Shuffle the deck

        // Deal the first hand of 5 cards
        Card[] hand = deck.dealHand(5);
        System.out.println("Your hand:");
        printHand(hand);
        
        // Sort and print the hand
        sortHand(hand);
        System.out.println("Your hand (sorted):");
        printHand(hand);

        // Allow the user to deal more hands if there are enough cards left
        Scanner scanner = new Scanner(System.in);
        while (deck.cardsLeft() >= 5) {
            System.out.println("Deal another hand? (y/n)");
            String input = scanner.nextLine();
            if (!input.equalsIgnoreCase("y")) {
                break;
            }
            deck.shuffle(); // Shuffle before dealing a new hand
            hand = deck.dealHand(5); // Deal a new hand
            System.out.println("Your hand:");
            printHand(hand);
            sortHand(hand);
            System.out.println("Your hand (sorted):");
            printHand(hand);
        }
        scanner.close();
    }

    // Prints the hand of cards
    public static void printHand(Card[] hand) {
        for (Card card : hand) {
            System.out.println(getCardName(card.getNumber()));
        }
        System.out.println();
    }

    // Sorts the hands
    public static void sortHand(Card[] hand) {
        Arrays.sort(hand, Comparator.comparingInt(Card::getRank));
    }

    // Returns the name of the card based on its number
    public static String getCardName(int cardnum) {
        String rankStr;
        String suitStr;
        switch (cardnum % 13) {
            case 0:  rankStr = "Ace"; break;
            case 10: rankStr = "Jack"; break;
            case 11: rankStr = "Queen"; break;
            case 12: rankStr = "King"; break;
            default: rankStr = Integer.toString((cardnum % 13) + 1); break;
        }
        switch (cardnum / 13) {
            case 0:  suitStr = "Clubs"; break;
            case 1:  suitStr = "Diamonds"; break;
            case 2:  suitStr = "Hearts"; break;
            default: suitStr = "Spades"; break;
        }
        return rankStr + " of " + suitStr;
    }
}

class Card {
    private int number;

    public Card(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public int getRank() {
        return (number % 13) + 1;
    }
}

class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<Card>();
        for (int i = 0; i < 52; i++) {
            cards.add(new Card(i));
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card[] dealHand(int numCards) {
        Card[] hand = new Card[numCards];
        for (int i = 0; i < numCards; i++) {
            hand[i] = cards.remove(0);
        }
        return hand;
    }

    public int cardsLeft() {
        return cards.size();
    }
}
