package logic;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class GameLogic {

    private final GUIConnector gui;

    private Game game;

    public GameLogic (GUIConnector gui) {
        this.gui = gui;
    }

    public void setupNewGame(Player[] players) {
        this.game = new Game(players);
    }

    public void saveGame(File file) throws com.google.gson.JsonParseException {

        //TODO toJson
        try {
            Gson gson = new Gson();
            gson.toJson(getGameData());
        } catch (com.google.gson.JsonParseException ex) {
            //Exception handling
        }
    }


    public void loadGame(File file) throws FileNotFoundException {
        try {
            Reader r = new FileReader(file);
            Gson gson = new Gson();
            GameData gameData = gson.fromJson(r, GameData.class);
            //Hand of players to Token
            Player[] players = gameData.getPlayers();
            Player currPlayer = players[gameData.getCurrPlayer()];
            GameBoard field = new GameBoard(gameData.getField());
            int[] usedActionTiles = gameData.getUsedActionTiles();

            this.game = new Game(field, players, currPlayer, usedActionTiles);


        } catch (FileNotFoundException | com.google.gson.JsonParseException ex) {
            //error handling
        }
    }

    private GameData getGameData() {
        //Exception, wenn kein Spiel läuft
        Player[] players = this.game.getPlayers();
        int currentPlayer = this.game.getCurrentPlayer().getID();

        //turn grid
        int[][] field = this.game.getGameBoard().getIntArrayGameBoard();

        int[] usedActionTiles = this.game.getUsedActionTiles();

        return new GameData(players, currentPlayer, field, usedActionTiles);
    }
}
