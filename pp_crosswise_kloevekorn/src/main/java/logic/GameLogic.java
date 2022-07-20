package logic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class GameLogic {

    private GUIConnector gui;

    private GameBoard gameBoard;

    private Set<Player> players = new HashSet<>();

    private Map<Integer, Token> hand = new HashMap<>();



    private Map<Token, Integer> drawPile = new HashMap<>();


    public GameLogic(/*GuiConnector gui */) {
        //this.gui = gui;
        gameBoard = new GameBoard(Constants.GAMEGRID_ROWS);
        generateDrawPile();
        generateHand();
    }


    public Map<Token, Integer> getDrawPile() {
        return drawPile;
    }

    public Map<Integer, Token> getHand() {
        return this.hand;//TODO Deep Copy
    }


    public static Map<Token, Integer> generateDrawPile() {
        Token a = Token.Sun;
        int counter = 1;
        for (int i = 1; i < Constants.UNIQUE_SYMBOL_TOKENS;i++) {
            this.drawPile.put(a.getTokenFromValue(i), Constants.GAMEGRID_ROWS + 1);
            counter++;
        }
        System.out.println(counter);
        for (int j = counter; j <= Constants.UNIQUE_ACTION_TOKENS + counter; j++) {
            this.drawPile.put(a.getTokenFromValue(j), Constants.AMOUNT_ACTION_TOKENS);
        }
    }

    public Token drawFromDrawPile() {
        if (drawPile.isEmpty()) {
            return null;
        } else {
            int tokensLeft = 0;
            for (Map.Entry<Token, Integer> tokenType : this.drawPile.entrySet()) {
                tokensLeft += tokenType.getValue();
            }

            Random random = new Random();
            int iterator = random.nextInt(tokensLeft + 1);

            for (Map.Entry<Token, Integer> tokenType : this.drawPile.entrySet()) {
                if(iterator <= tokenType.getValue()) {
                    this.drawPile.put(tokenType.getKey(), tokenType.getValue() - 1);
                    return tokenType.getKey();
                } else {
                    iterator = iterator - tokenType.getValue();
                }
            }
        }
        return null;
    }

    public void generateHand() {
        for (int i = 0; i < Constants.HAND_SIZE; i++) {
            this.hand.put(i, drawFromDrawPile());
        }
    }

    /*
    public Integer compute() {
        for (Token token:hand) {
            switch (token.getValue()) {
                case 0:
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    for (Position pos : posSet) {
                        int changeHorizontal = calculateChangeOfSingleLine(newOccurrenceMap.get(pos.getYCoordinate() + 1), token.getValue());
                        int changeVertical = calculateChangeOfSingleLine(newOccurrenceMap.get(-pos.getXCoordinate() - 1), token.getValue());
                        int change = 0;

                        if (changeHorizontal == -1) {
                            horizontalTeamWin = true;
                        } else if (changeVertical == -1) {
                            verticalTeamWin = true;
                        } else {
                            change = change + changeVertical - changeHorizontal;
                        }if (fromVerticalSide) {
                            System.err.println(token.getValue() + " " + pos.getYCoordinate() + "," + pos.getXCoordinate());
                            if (bestPositions.get(token) == null || bestPositions.get(token).get(pos) < change) {
                                Map<Position, Integer> bestPos = new HashMap<>();
                                bestPos.put(pos, change);
                                bestPositions.put(token, bestPos);
                            }
                        } else {
                            if (bestPositions.get(token) == null || bestPositions.get(token).get(pos) > change) {
                                Map<Position, Integer> bestPos = new HashMap<>();
                                bestPos.put(pos, change);
                                bestPositions.put(token, bestPos);
                            }
                        }
                    }
                    break;
                case 7:
                    //TODO
                    break;
                case 8:
                    //TODO
                    break;
                case 9:
                    //TODO
                    break;
                case 10:
                    //TODO
                    break;
            }
        }
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
    */
}
