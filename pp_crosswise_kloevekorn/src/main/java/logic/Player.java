package logic;

public class Player {
    private String name;

    private boolean isActive;

    private boolean isAI;

    private Token[] hand = new Token[4];

    /**
     * Konstruktor für Default Player
     */
    public Player(String name, boolean isActive, boolean isAI) {
        this.name = name;
        this.isActive = isActive;
        this.isAI = isAI;
    }

    /**
     * Konstruktor für bereits existierenden Player
     */
    public Player(String name, boolean isActive, boolean isAI, int[] hand) {
        this(name, isActive, isAI);
        System.arraycopy(hand, 0, this.hand, 0, Constants.HAND_SIZE);
    }
}


