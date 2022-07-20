package logic;

import java.util.HashMap;
import java.util.Map;

public class Game {

    private GameBoard gameBoard;

    private Player[] players = new Player[Constants.PLAYER_NUMBER];

    private Player currentPlayer;

    private int[] usedActionTiles;

    private Map<Token, Integer> drawPile = new HashMap<>();

    public Game (Player[] players) {
        this.gameBoard = new GameBoard(Constants.GAMEGRID_ROWS);
        this.players = players;
        this.currentPlayer = this.players[0];
        this.usedActionTiles = new int[Constants.UNIQUE_ACTION_TOKENS];
        GameLogic.generateDrawPile();
    }


}


