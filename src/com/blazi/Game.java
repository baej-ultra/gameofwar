package com.blazi;

import java.util.*;

public class Game {

    public void startGame() {
        LinkedList<Card> deck = new LinkedList<>(Card.getStandardDeck());
        shuffleDeck(deck,true);

        // Create players
        Player player1 = new Player("Igor");
        Player player2 = new Player("Eddie");

        // Deal cards
        dealCards(deck, player1, player2);

        // Print decks
        player1.printArmy();
        System.out.println();
        player2.printArmy();
        System.out.println();

        // Battle loop
        while (player1.hasArmy() && player2.hasArmy()) {
            battle(player1, player2);
        }

        // Results
        if (player1.hasArmy()) {
            System.out.printf("%s won!%n", player1.getPlayerName());
        } else {
            System.out.printf("%s won!%n", player2.getPlayerName());
        }
    }

    private static void dealCards(LinkedList<Card> deck, Player... players) {
        while(!deck.isEmpty()){
            for (var player : players) {
                try {
                    player.recruitSoldier(deck.pop());
                } catch (NoSuchElementException e) {
                    System.out.println("Deck's finished");
                }
            }
        }
    }

    private static void shuffleDeck(LinkedList<Card> deck, boolean cut) {
        // Shuffle the deck and cut if needed
        Collections.shuffle(deck);
        if(cut) {
            int indexHalf = (deck.size() / 2);
            Collections.rotate(deck, indexHalf);
        }
    }

    private static void takeOver(Player p1, Player p2) {
        System.out.println(p1.getPlayerName() + " takes " + (p1.frontCount()+ p2.frontCount()));
        p1.takeBattlefront(p2.surrenderFront());
    }

    private static void printBattlefield(Player p1, Player p2) {
        System.out.printf("%s: %s vs %s: %s%n",p1.getPlayerName(), p1.getFront(),p2.getPlayerName(), p2.getFront());
    }

    private static void battle(Player p1, Player p2) {
        // Send to front at the start of the round
        p1.sendNewToWarFront();
        p2.sendNewToWarFront();
        printBattlefield(p1, p2);
        // Check result
        if (p1.getFront().rank() > p2.getFront().rank()) {
            takeOver(p1,p2);
        } else if (p1.getFront().rank() < p2.getFront().rank()) { // P2 wins
            takeOver(p2, p1);
        } else { // Send additional on war
            System.out.println("War!");
            if (p1.hasArmy() && p2.hasArmy()) {
                p1.sendNewToWarFront();
                p2.sendNewToWarFront();
            }
        }

    }
}
