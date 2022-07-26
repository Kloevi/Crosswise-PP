package logic;



public class GameData {
    private final Player[] players;

    private final int currPlayer;

    private final int[][] field;

    private final int[] usedActionTiles;

    public GameData(Player[] players, int currPlayer, int[][] field, int[] usedActionTiles) {
        this.players = players;
        this.currPlayer = currPlayer;
        this.field = field;
        this.usedActionTiles = usedActionTiles;
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getCurrPlayer() {
        return currPlayer;
    }

    public int[][] getField() {
        return field;
    }

    public int[] getUsedActionTiles() {
        return usedActionTiles;
    }
}
