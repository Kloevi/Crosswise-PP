package logic;

import java.util.HashSet;
import java.util.Set;

public class Player {
    private String name;

    private final boolean isActive;

    private final boolean isAI;

    private final boolean isVerticalTeam;

    private final int ID;

    private Token[] hand = new Token[Constants.HAND_SIZE];

    /**
     * Konstruktor für Default Player
     */
    public Player(int ID, String name, boolean isActive, boolean isAI, boolean isVerticalTeam) {
        this.ID = ID;
        this.name = name;
        this.isActive = isActive;
        this.isAI = isAI;
        this.isVerticalTeam = isVerticalTeam;
    }

    /**
     * Konstruktor für bereits existierenden Player
     */
    public Player(int ID, String name, boolean isActive, boolean isAI, boolean isVerticalTeam, int[] hand) {
        this(ID, name, isActive, isAI, isVerticalTeam);
        System.arraycopy(hand, 0, this.hand, 0, Constants.HAND_SIZE);
        Integer a = getID();
    }

    public int getID() {
        return ID;
    }

    public Token[] getHand() {
        return hand;
    }

    public boolean isVerticalTeam() {
        return isVerticalTeam;
    }

    public Integer tokenAmountInHand(Token token) {
        Integer counter = 0;
        for (Token handToken:hand) {
            if (handToken == token) {
                counter++;
            }
        }
        return counter;
    }

    public Set<Integer> getHandSymbolTokenPositions() {
        Token[] handCopy = this.getHand();
        Set<Integer> returnSet = new HashSet<>();
        for (int i = 0; i < handCopy.length; i++) {
            if (handCopy[i].getValue() <= Constants.UNIQUE_SYMBOL_TOKENS) {
                returnSet.add(i);
            }
        }
        return returnSet;
    }

    public void setHand(Token[] hand) {
        this.hand = hand;
    }
}


