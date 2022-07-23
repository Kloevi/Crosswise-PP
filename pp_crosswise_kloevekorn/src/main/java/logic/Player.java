package logic;

public class Player {
    private String name;

    private final boolean isActive;

    private final boolean isAI;

    private final boolean isVerticalTeam;

    private Token[] hand = new Token[Constants.HAND_SIZE];

    /**
     * Konstruktor für Default Player
     */
    public Player(String name, boolean isActive, boolean isAI, boolean isVerticalTeam) {
        this.name = name;
        this.isActive = isActive;
        this.isAI = isAI;
        this.isVerticalTeam = isVerticalTeam;
    }

    /**
     * Konstruktor für bereits existierenden Player
     */
    public Player(String name, boolean isActive, boolean isAI, boolean isVerticalTeam, int[] hand) {
        this(name, isActive, isAI, isVerticalTeam);
        System.arraycopy(hand, 0, this.hand, 0, Constants.HAND_SIZE);
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

    public void setHand(Token[] hand) {
        this.hand = hand;
    }
}


