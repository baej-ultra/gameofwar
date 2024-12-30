package com.blazi;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class Player {

    private final String playerName;
    private LinkedList<Card> army;
    private LinkedList<Card> battlefront;

    public Player(String playerName) {
        this(playerName, new LinkedList<>());
    }

    public Player(String playerName, LinkedList<Card> army) {
        this.playerName = playerName;
        this.army = army;
        this.battlefront = new LinkedList<>();
    }

    public String getPlayerName() {
        return playerName;
    }

    public boolean hasArmy() {
        return !army.isEmpty();
    }

    public void recruitSoldier(Card soldier) {
        army.add(soldier);
    }

    public List<Card> surrenderFront() {
        List<Card> buffer = new LinkedList<>(battlefront);
        battlefront.clear();
        return buffer;
    }

    public Card getFront() {
        return battlefront.getFirst();
    }

    public void takeBattlefront(List<Card> enemyUnits) {
        army.addAll(army.size(), enemyUnits);
        army.addAll(army.size(), battlefront);
        battlefront.clear();
    }

    public Card sendNewToWarFront() {
        try {
            battlefront.push(army.pop());
        } catch (NoSuchElementException e) {
            System.out.println("%s has no more soldiers!");
            return null;
        }
        return battlefront.getFirst();
    }

    public int frontCount() {
        return battlefront.size();
    }

    public void printArmy() {
        Card.printDeck(army, "%s's army".formatted(playerName),4);
    }
}
