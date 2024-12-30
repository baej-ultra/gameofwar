package com.blazi;

import java.util.ArrayList;
import java.util.List;

public record Card(Suit suit, String face, int rank) {

    public enum Suit {

        CLUB, DIAMOND, HEART, SPADE;

        public char getImage() {
            return (new char[]{9827, 9830, 9829, 9824})[this.ordinal()];
        }
    }

    @Override
    public String toString() {

        int index = face.equals("10") ? 2 : 1;
        String faceString = face.substring(0, index);
        return "%s%c(%d)".formatted(faceString, suit.getImage(), rank);
    }

    public static Card getNumericCard(Suit suit, int cardNumber) {

        if (cardNumber < 2 || cardNumber > 10) {
            System.out.println("Invalid Numeric card selected");
            return null;
        }

        return new Card(suit, String.valueOf(cardNumber), cardNumber - 2);
    }

    public static Card getFaceCard(Suit suit, char abbrev) {

        int index = "JQKA".indexOf(abbrev);
        if (index > -1) {
            return new Card(suit, "" + abbrev, index + 9);
        }

        System.out.println("Invalid face value");
        return null;
    }

    public static List<Card> getStandardDeck() {

        List<Card> deck = new ArrayList<>(52);
        for (Suit suit : Suit.values()) {
            for (int i = 2; i <= 10; i++) {
                deck.add(Card.getNumericCard(suit, i));
            }
            for (char c : "JQKA".toCharArray()) {
                deck.add(Card.getFaceCard(suit, c));
            }
        }
        return deck;
    }

    public static void printDeck(List<Card> deck) {
        printDeck(deck, "Current deck", 4);
    }

    public static void printDeck(List<Card> deck, String description, int rows) {
        // Print deck in given number of rows
        System.out.println(description);
        System.out.println("-----------------");

        int count = 0;
        int cardsPerRow = (int) Math.ceil((double) deck.size() / rows);
        for (Card card : deck) {
            if (count > 0 && count % (cardsPerRow) == 0) {
                System.out.println();
            }
            System.out.print(card + " ");
            count++;
        }
        System.out.println();
    }
}
