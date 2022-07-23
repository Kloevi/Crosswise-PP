package logic;

import gui.JavaFXGUI;
import javafx.scene.layout.GridPane;
import logic.ErrorHandling.CrosswiseExceptionHandler;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PubLogicTest {



    @Test
    public void gridTest() {
        GridPane a = new GridPane();
        JavaFXGUI w = new JavaFXGUI(a);


    }

    @Test
    public void sandbox() {
        int[][] list = new int[][] {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
        printMatrix(list);
        int[][] reverse = swapMatrix(list);
        System.out.println();
        printMatrix(reverse);

    }

    @Test
    public void generateGameLogic() {
        GameLogic logic = new GameLogic();
        System.out.println(logic.getDrawPile());
        System.err.println("----");
        System.out.println(logic.getHand());
    }

    @Test
    public void generateGameTest() throws CrosswiseExceptionHandler {

        Player[] players = new Player[Constants.PLAYER_NUMBER];
        players[0] = new Player("Tom", true, false, true);
        players[1] = new Player("Jacob", true, false, false);
        players[2] = new Player("Jonas", true, false, true);
        players[3]= new Player("Simon", true, false, false);
        Game game = new Game(players);

        int[][] listaaaa = new int[][] {{3,3,2,1,5,2},{0,4,5,0,2,5},{3,1,1,4,5,6},{6,2,0,0,6,6},{5,5,5,3,6,0},{1,6,1,3,1,2}};
        Token[][] list = intToTokenMatrix(listaaaa);
        GameBoard newBoard = new GameBoard(list);
        game.setGameBoard(newBoard);
        printGrid(game);

        Map<Integer, Map<Token, Integer>> testoccurrenceMap = game.getOccurrencesOfTokensWithChangedToken(game.getGameBoard().getGameGrid());

        Position pos = new Position(1, 0);

        Map<Integer, Integer> pointsMap = game.calculateCurrentOverallPointsWithChangedToken(pos, Token.None);

        /*
        Position pos2 = new Position(2, 3);
        Calculation calc = game.calculateChangeWithMove(players[0], pos2, Token.Star);
        System.out.println(calc.getPointsChange());
        */

        /*
        Set<TokenMove> tokenMoves = game.createPossibleSymbolTokenMoves(Token.Sun, players[0]);
        for (TokenMove tokenMove : tokenMoves) {
            System.out.println("----");
            System.out.println(tokenMove.getPrimaryMovePosition().getXCoordinate() + "/" +
                    tokenMove.getPrimaryMovePosition().getYCoordinate());
            System.out.println(tokenMove.getRelativeChange());
            System.out.println(tokenMove.getToken());
        }
        */

        players[0].setHand(new Token[] {Token.Pentagon, Token.Pentagon, Token.Star, Token.Star});
        TokenMove bestMove = game.calculateAIMove(players[0]);
        System.out.println(bestMove.getToken());
        System.out.println(bestMove.getPrimaryMovePosition().getXCoordinate());
        System.out.println(bestMove.getPrimaryMovePosition().getYCoordinate());
        System.out.println(bestMove.getRelativeChange());

        /*
        TokenMove pentagon = new TokenMove(new Position(3, 1), 1, Token.Pentagon,
                false, false);
        TokenMove star = new TokenMove(new Position(2, 3), 1, Token.Star,
                false, false);

        System.out.println(game.isBetterMove(pentagon, star, players[0]));
        */

    }
    public Token[][] intToTokenMatrix(int[][] grid) {
        Token[][] intMatrix = new Token[Constants.GAMEGRID_ROWS][Constants.GAMEGRID_COLUMNS];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                intMatrix[i][j] = Token.getTokenFromValue(grid[i][j]);
            }
        }
        return intMatrix;
    }

    public static void printGrid(Game game) {
        GameBoard gameBoard = game.getGameBoard();
        Token[][] grid = gameBoard.getGameGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j].getValue() + " ");
            }
            System.out.println();
        }
    }



    @Test
    public void sandbox2() {
        int[][] list = new int[][] {{3,3,2,1,5,2},{0,4,5,0,2,5},{3,1,1,4,5,6},{6,2,0,0,6,6},{5,5,5,3,6,0},{1,6,1,3,1,2}};

        Map<Integer, Map<Integer, Integer>> newOccurrenceMap = getOccurrenceMap(list);

        Map<Integer, Integer> pointsMap = calculatePoints(newOccurrenceMap);
        System.out.println("---------");
        System.out.println(emptyFields(list));
    }

    @Test
    public void sandbox3() {
        int[][] list = new int[][] {{3,3,2,1,5,2},{0,4,5,0,2,5},{3,1,1,4,5,6},{6,2,0,0,6,6},{5,5,5,3,6,0},{1,6,1,3,1,2}};
        boolean verticalTeamWin = false;
        boolean horizontalTeamWin = false;
        boolean fromVerticalSide = true;
        Token[] hand = new Token[] {Token.Star, Token.Triangle, Token.Star, Token.Star};




        Map<Integer, Map<Integer, Integer>> newOccurrenceMap = getOccurrenceMap(list);

        Map<Integer, Integer> pointsMap = calculatePoints(newOccurrenceMap);


        System.out.println("---------");

        Set<Position> posSet = emptyFields(list);





        Map<Integer , Map<Token, Position>> bestPositions = new HashMap<>();




        System.out.println(bestPositions);

    }

    @Test
    public void sandbox4() {
        Token token = Token.Star;
        System.out.println(token.getValue());
    }






    public Integer calculateChangeOfSingleLine(Map<Integer, Integer> occurrences, Integer tokenInt) {
        Map<Integer, Integer> newOccurrences = new HashMap<>(Map.copyOf(occurrences));
        if (occurrences.containsKey(tokenInt)) {
            int count = newOccurrences.get(tokenInt);
            newOccurrences.put(tokenInt, count+1);
        } else {
            newOccurrences.put(tokenInt, 1);
        }
        return calculate(newOccurrences);
    }


    //TODO Position
    public Set<Position> emptyFields(int[][] grid) {
        Set<Position> positions = new HashSet<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0) {
                    positions.add(new Position(i, j));
                }
            }
        }
        return positions;
    }

    public int[][] swapMatrix(int[][] input) {
        int[][] swap = new int[input.length][input.length];
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                swap[j][i] = input[i][j];
            }
        }
        return swap;
    }

    public Integer teamDiff(Map <Integer, Integer> pointsMap) {
        int curr = 0;
        for (Map.Entry<Integer, Integer> entry : pointsMap.entrySet()) {
            if (entry.getKey() > 0) {
                curr = curr + entry.getValue();
            } else {
                curr = curr - entry.getValue();
            }
        }
        return curr;
    }

    public Map<Integer, Integer> calculatePoints(Map<Integer, Map<Integer, Integer>> occurrenceMap) {
        Map<Integer, Integer> PointMap = new HashMap<>();

        for (Map.Entry<Integer, Map<Integer, Integer>> entry : occurrenceMap.entrySet()) {
            PointMap.put(entry.getKey(), calculate(entry.getValue()));
        }
        for (Map.Entry<Integer, Integer> entry : PointMap.entrySet()) {
            System.out.println(entry);
        }

        return PointMap;
    }

    public Integer calculate (Map<Integer, Integer> map) {
        int curr = 0;
        if (map.size() == Constants.GAMEGRID_ROWS) {
            return 6;
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == Constants.GAMEGRID_ROWS) {
                return -1;
            } else if (entry.getValue() > 1) {
                curr = curr + entry.getValue() * 2 - 3;
            }
        }
        return curr;
    }

    public Map<Integer, Map<Integer, Integer>> getOccurrenceMap(int[][] grid) {
        Map<Integer, Map<Integer, Integer>> endResult = new HashMap<>();

        for (int i = 0; i < grid.length; i++) {
            endResult.put(i+1, calculateLine(grid[i]));
        }
        int[][] reverseArray = swapMatrix(grid);

        for (int i = 0; i < reverseArray.length; i++) {
            endResult.put(-i-1, calculateLine(reverseArray[i]));
        }
        return endResult;
    }

    public Map<Integer, Integer> calculateLine(int[] line) {
        Map<Integer,Integer> map = new HashMap<>();
        Arrays.stream(line).forEach(x -> map.put(x , map.computeIfAbsent(x, s -> 0) + 1));
        map.remove(0);
        return map;
    }

    public void printMatrix(int[][] input) {
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                System.out.print(input[i][j] + " ");
            }
        }
    }

    public int[][] gridToIntMatrix(Token[][] grid) {
        int[][] intMatrix = new int[Constants.GAMEGRID_ROWS][Constants.GAMEGRID_COLUMNS];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                intMatrix[i][j] = grid[i][j].getValue();
            }
        }
        return intMatrix;
    }



}
