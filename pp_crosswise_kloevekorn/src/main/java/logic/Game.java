package logic;

import logic.ErrorHandling.CrosswiseExceptionHandler;
import logic.ErrorHandling.ErrorType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Game {


    private GameBoard gameBoard;

    private final Player[] players;

    private Player currentPlayer;

    private int[] usedActionTiles;

    private Map<Token, Integer> drawPile = new HashMap<>();

    private GUIConnector gui;

    public Game (Player[] players) {
        //TODO player generation;

        this.gameBoard = new GameBoard(Constants.GAMEGRID_ROWS);
        this.players = players;
        this.currentPlayer = this.players[0];
        this.usedActionTiles = new int[Constants.UNIQUE_ACTION_TOKENS];
        generateNewDrawPile();
        generateNewHands();
        System.out.println(drawPile);
    }

    public Game (GameBoard gameBoard, Player[] players, Player currentPlayer, int[] usedActionTiles) {
        this.gameBoard = gameBoard;
        this.players = players;
        this.currentPlayer = currentPlayer;
        this.usedActionTiles = usedActionTiles;
        generateDrawPileForExistingGame();
    }

    //------------------------------------Getter und Setter-----------------------------------------



    public GameBoard getGameBoard() {
        return gameBoard;
    }
    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int[] getUsedActionTiles() {
        return usedActionTiles;
    }

    //-----------------------------------------Methoden---------------------------------------------


    public Player nextPlayer() {
        int nextId = currentPlayer.getID() + 1;
        boolean isActivePlayer = false;
        while (!isActivePlayer) {
            nextId = nextId % Constants.PLAYER_NUMBER;
            if (players[nextId].isActive()) {
                isActivePlayer = true;
            } else {
                nextId++;
            }
        }
        return players[nextId];
    }

    public void computeTurn() throws CrosswiseExceptionHandler {


        hideCurrentPlayerHand();
        this.currentPlayer = nextPlayer();
        showCurrentPlayerHand();
    }


    public void showCurrentPlayerHand() throws CrosswiseExceptionHandler {
        gui.showPlayerHand(this.currentPlayer.getID(), this.currentPlayer.getHand());
    }

    public void hideCurrentPlayerHand() {
        gui.hidePlayerHand(this.currentPlayer.getID());

    }

    public void generateNewHands() {
        for (Player player:this.players) {
            generateHand(player);
        }
    }

    public void generateHand(Player player) {
        Token[] hand = new Token[Constants.HAND_SIZE];
        for (int i = 0; i < Constants.HAND_SIZE; i++) {
            hand[i] = drawFromDrawPile();
        }
        player.setHand(hand);
    }

    private Token drawFromDrawPile() {
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

    public void generateNewDrawPile() {
        int counter = 1;
        for (int i = 1; i < Constants.UNIQUE_SYMBOL_TOKENS;i++) {
            this.drawPile.put(Token.getTokenFromValue(i), Constants.GAMEGRID_ROWS + 1);
            counter++;
        }
        for (int j = counter; j <= Constants.UNIQUE_ACTION_TOKENS + counter; j++) {
            this.drawPile.put(Token.getTokenFromValue(j), Constants.AMOUNT_ACTION_TOKENS);
        }
    }

    public void generateDrawPileForExistingGame() {
        generateNewDrawPile();
        Map<Integer, Map<Token, Integer>> occurrenceMap = getOccurrencesOfTokens();
        for (Map.Entry<Integer, Map<Token, Integer>> entry : occurrenceMap.entrySet()) {
            for (Map.Entry<Token, Integer> entry1 : entry.getValue().entrySet()) {
                this.drawPile.put(entry1.getKey(), this.drawPile.get(entry1.getKey()) - 1);
            }
        }
        //TODO auf Funktionalität überprüfen
    }


    //------------------------------------------AI--------------------------------------------------


    public TokenMove calculateAIMove(Player player)
            throws CrosswiseExceptionHandler {
        List<TokenMove> bestMovePerToken = new ArrayList<>();
        Token[] playerHand = player.getHand();

        for (Token token : playerHand) {
            System.out.println("current Token: " + token);
            bestMovePerToken.add(calculateBestTokenMove(token, player));
        }
        int counter = 0;
        for (TokenMove tokenMove : bestMovePerToken) {
            System.out.println("HandStein: " + counter++);
            System.out.println(tokenMove.getToken());
            System.out.println(tokenMove.getPrimaryMovePosition().getXCoordinate() + "/" +
                    tokenMove.getPrimaryMovePosition().getYCoordinate());
            System.out.println(tokenMove.getRelativeChange());
        }

        Integer bestToken = null;
        for (int i = 0; i < playerHand.length; i++) {
            if (bestMovePerToken.get(i) != null) {
                if (bestToken == null) {
                    System.out.println("First: " + i);
                    bestToken = i;
                } else {
                    System.out.println("Others" + i);
                    if (isBetterMove(bestMovePerToken.get(i), bestMovePerToken.get(bestToken), player)) {
                        bestToken = i;
                    }
                }
            }
        }
        if (bestToken == null) {
            throw new CrosswiseExceptionHandler(ErrorType.noTokensOnHand);
        } else {
            return bestMovePerToken.get(bestToken);
        }
    }

    public boolean isBetterMove(TokenMove newMove, TokenMove currentBestMove, Player player) {
        //Vergleich auf Siegchance
        if (newMove.isGameWinning()) {
            return true;
        } else if (currentBestMove.isGameWinning()) {
            return false;
        }
        //Vergleich auf Verhinderung einer Niederlage
        if (newMove.isPreventingLoss()){
            return true;
        } else if (currentBestMove.isPreventingLoss()) {
            return false;
        }
        //Vergleich auf Änderung der Punkte
        int difference = newMove.getRelativeChange() - currentBestMove.getRelativeChange();
        if (player.isVerticalTeam()) {
            if (difference < 0) {
                return false;
            }
            if (difference > 0) {
                return true;
            }
        } else {
            if (difference > 0) {
                return false;
            }
            if (difference < 0) {
                return true;
            }
        }
        //Vergleich auf Aktionsstein gegenüber Symbolstein
        int compareTokenValue = compareTypeOfToken(newMove.getToken(), currentBestMove.getToken());
        if (compareTokenValue == 1) {
            return true;
        }
        if (compareTokenValue == -1) {
            return false;
        }
        //Vergleich auf häufigstes Hand Vorkommen
        int tokenAmountInHandDifference = player.tokenAmountInHand(newMove.getToken()) -
                player.tokenAmountInHand(currentBestMove.getToken());
        if (tokenAmountInHandDifference > 0) {
            return true;
        }
        if (tokenAmountInHandDifference < 0) {
            return false;
        }

        //Vergleich auf Anzahl des Tokens auf Brett (nur bei Symbolsteinen)
        if (newMove.getToken().getValue() <= Constants.UNIQUE_SYMBOL_TOKENS) {
            int differenceOccurrencesOnBoard =
                    countNumberOfTokenOnGrid(newMove.getToken()) - countNumberOfTokenOnGrid(
                            currentBestMove.getToken());
            if (differenceOccurrencesOnBoard > 0) {
                return false;
            }
            if (differenceOccurrencesOnBoard < 0) {
                return true;
            }
        }
        //Vergleich auf Ordinalität von Token
        int differenceOrdinalityOfToken = newMove.getToken().getValue() -
                currentBestMove.getToken().getValue();
        if (differenceOrdinalityOfToken > 0) {
            return false;
        }
        if (differenceOrdinalityOfToken < 0) {
            return true;
        }
        //Vergleich auf vertikale Position des Tokens
        int differenceVerticalPosition = newMove.getPrimaryMovePosition().getXCoordinate() -
                currentBestMove.getPrimaryMovePosition().getXCoordinate();
        if (differenceVerticalPosition < 0) {
             return true;
        }
        if (differenceVerticalPosition > 0) {
            return false;
        }
        //Vergleich auf horizontale Position des Tokens
        int differenceHorizontalPosition = newMove.getPrimaryMovePosition().getYCoordinate() -
                currentBestMove.getPrimaryMovePosition().getYCoordinate();
        if (differenceHorizontalPosition < 0) {
            return true;
        }
        if (differenceHorizontalPosition > 0) {
            return false;
        }
        if (newMove.getToken().getValue() > Constants.UNIQUE_SYMBOL_TOKENS && newMove.getToken().getValue() < (Constants.UNIQUE_SYMBOL_TOKENS + Constants.UNIQUE_ACTION_TOKENS)) {
            //Falls SpezialStein: Vergleich auf zweite vertikale Position des Tokens
            int differenceVerticalPosition2 = newMove.getSecondaryMovePosition().getXCoordinate()
                    - currentBestMove.getSecondaryMovePosition().getXCoordinate();
            if (differenceVerticalPosition2 < 0) {
                return true;
            }
            if (differenceVerticalPosition2 > 0) {
                return false;
            }
            //Falls Replacer, wird der neue Zug als besser bewertet
            if (newMove.getToken().getValue() == Constants.UNIQUE_ACTION_TOKENS + Constants.UNIQUE_SYMBOL_TOKENS) {
                return true;
                //TODO möglicherweise bessere AI Logik hier
            }
            //Falls SpezialStein: Vergleich auf zweite horizontale Position des Tokens
            int differenceHorizontalPosition2 = newMove.getSecondaryMovePosition().getYCoordinate()
                    - currentBestMove.getSecondaryMovePosition().getYCoordinate();
            return differenceHorizontalPosition2 < 0;
        }
        //Falls der exakt gleiche Zug verglichen wird
        return false;
    }

    private Integer countNumberOfTokenOnGrid(Token token) {
        Integer counter = 0;
        Token[][] grid = gameBoard.getGameGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; i < grid[0].length; i++) {
                if (grid[i][j] == token){
                    counter++;
                }
            }
        }
        return counter;
    }


    /**
     * Vergleicht zwei Token-Arten miteinander
     *
     * @param newToken Neues Token
     * @param currentToken Momentan bestes Token
     * @return 0 = Arten sind gleich, 1 = neues Token ist Symbol und momentanes Token ist Aktion,
     * -1 = neues Token ist Aktion und momentanes Token ist Symbol
     */
    private Integer compareTypeOfToken(Token newToken, Token currentToken) {
        int range = Constants.UNIQUE_SYMBOL_TOKENS;
        int nValue = newToken.getValue();
        int cValue = currentToken.getValue();
        if (nValue <= range && cValue <= range ||
                nValue > range && cValue > range) {
            return 0;
        } else if (nValue <= range) {
            return 1;
        } else {
            return -1;
        }
    }

    //---------------------------------------Best Move Calculation----------------------------------

    private TokenMove calculateBestTokenMove(Token token, Player player)
            throws CrosswiseExceptionHandler {

        Set<TokenMove> tokenMovesPerToken = createPossibleMoves(token, player);
        TokenMove bestMove = null;
        if (tokenMovesPerToken == null) {
            throw new CrosswiseExceptionHandler(ErrorType.noPossibleMoves);
        } else {
            for (TokenMove tokenMove : tokenMovesPerToken) {
                if (bestMove == null) {
                    bestMove = tokenMove;
                } else {
                    if (isBetterMove(tokenMove, bestMove, player)) {
                        bestMove = tokenMove;
                    }
                }
            }
        }
        return bestMove;
    }


    private Set<TokenMove> createPossibleMoves(Token token, Player player) {
        return switch (token.getValue()) {
            case 1, 2, 3, 4, 5, 6 -> createPossibleSymbolTokenMoves(token, player);
            case 7 -> createPossibleRemoverTokenMoves(player);
            case 8 -> createPossibleMoverTokenMoves(player);
            case 9 -> createPossibleSwapperTokenMoves(player);
            case 10 -> createPossibleReplacerTokenMoves(player);
            default -> null;
        };
    }


    //----------------------------------Calculation Symbol Token Moves------------------------------

    public Set<TokenMove> createPossibleSymbolTokenMoves(Token token, Player player) {
        Set<TokenMove> tokenMoves = new HashSet<>();
        Set<Position> emptyFields = emptyFields();
        for (Position position : emptyFields) {
            Calculation currentCalculation = calculateChangeWithMove(player, getGridCopyWithAddedToken(position, token));
            //TODO Prevent Loss
            tokenMoves.add(new TokenMove(position, currentCalculation.getPointsChange(), token, currentCalculation.isGameWinning(), isMovePreventingLoss()));
        }
        return tokenMoves;
    }

    private Set<Position> emptyFields() {
        Set<Position> positions = new HashSet<>();
        Token[][] grid = this.gameBoard.getGameGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].getValue() == 0) {
                    positions.add(new Position(i, j));
                }
            }
        }
        return positions;
    }

    /**
     * Berechnet Punkte Veränderung insgesamt
     * @return Punkte Differenz auf das ganze Feld bezogen
     */
    public Calculation calculateChangeWithMove(Player player, Token[][] newGrid) {
        Map<Integer, Integer> pointsMap = calculateCurrentOverallPointsWithChangedToken(newGrid);

        int curr = 0;
        boolean isWinning = false;
        boolean isCreatingLoss = false;
        for (Map.Entry<Integer, Integer> entry : pointsMap.entrySet()) {
            if (entry.getValue() == -1) {
                if (entry.getKey() > 0) {
                    if (player.isVerticalTeam()) {
                        isWinning = true;
                    } else {
                        isCreatingLoss = true;
                    }
                } else {
                    if (player.isVerticalTeam()) {
                        isCreatingLoss = true;
                    } else {
                        isWinning = true;
                    }
                }
            }
            if (entry.getKey() > 0) {
                curr = curr + entry.getValue();
            } else {
                curr = curr - entry.getValue();
            }
        }
        return new Calculation(curr, isCreatingLoss, isWinning);
    }




    /**
     * Berechnet Punkte für jede Linie
     *
     * @return Map (Linie, Punkte)
     */
    public Map<Integer, Integer> calculateCurrentOverallPointsWithChangedToken(Token [][] newGrid) {
        Map<Integer, Map<Token, Integer>> occurrenceMap = getOccurrencesOfTokensWithChangedToken(newGrid);

        Map<Integer, Integer> PointMap = new HashMap<>();

        for (Map.Entry<Integer, Map<Token, Integer>> entry : occurrenceMap.entrySet()) {
            PointMap.put(entry.getKey(), calculate(entry.getValue()));
        }
        return PointMap;
    }

    public Token[][] getGridCopyWithAddedToken(Position position, Token token) {
        Token[][] originalGrid = this.gameBoard.getGameGrid();

        Token[][] grid = new Token[Constants.GAMEGRID_ROWS][Constants.GAMEGRID_COLUMNS];
        for (int i = 0; i < Constants.GAMEGRID_ROWS; i++) {
            for (int j = 0; j < Constants.GAMEGRID_COLUMNS; j++) {
                grid[i][j] = originalGrid[i][j];
            }
        }
        grid[position.getXCoordinate()][position.getYCoordinate()] = token;

        return grid;
    }

    /**
     * Berrechnet Punkte für einzelne Linie
     *
     * @param map Map(Token, Anzahl Vorkommnisse)
     * @return Points per Line
     */
    public Integer calculate (Map<Token, Integer> map) {
        int curr = 0;
        if (map.size() == Constants.GAMEGRID_ROWS) {
            return 6;
        }
        for (Map.Entry<Token, Integer> entry : map.entrySet()) {
            if (entry.getValue() == Constants.GAMEGRID_ROWS) {
                return -1;
            } else if (entry.getValue() > 1) {
                curr = curr + entry.getValue() * 2 - 3;
            }
        }
        return curr;
    }

    public Map<Integer, Map<Token, Integer>> getOccurrencesOfTokensWithChangedToken(Token[][] grid) {
        Map<Integer, Map<Token, Integer>> occurrenceMap = new HashMap<>();

        for (int i = 0; i < grid.length; i++) {
            occurrenceMap.put(-i-1, calculateOccurencesPerLine(grid[i]));
        }
        Token[][] reverseArray = swapMatrix(grid);

        for (int i = 0; i < reverseArray.length; i++) {
            occurrenceMap.put(i+1, calculateOccurencesPerLine(reverseArray[i]));
        }
        return occurrenceMap;
    }

    private Map<Token, Integer> calculateOccurencesPerLine(Token[] tokens) {
        Map<Token,Integer> map = new HashMap<>();
        Arrays.stream(tokens).forEach(x -> map.put(x , map.computeIfAbsent(x, s -> 0) + 1));
        map.remove(Token.None);
        return map;
    }

    public Token[][] swapMatrix(Token[][] input) {
        Token[][] swap = new Token[input.length][input.length];
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                swap[j][i] = input[i][j];
            }
        }
        return swap;
    }

    private boolean isMovePreventingLoss(/*Player player, Position position, Token token*/) {
        /*Map<Integer, Map<Token, Integer>> occurrenceMap = getOccurrencesOfTokens();
        for (Map.Entry<Integer, Map<Token, Integer>> entry : occurrenceMap.entrySet()) {
            if (entry.getValue().size() == 1) {
                for (Map.Entry<Token, Integer> entry2 : entry.getValue().entrySet()) {
                    if (entry2.getValue() == Constants.GAMEGRID_ROWS - 1) {
                        System.out.println("FAILLLL");
                        return true;
                    }
                }
            }
        }
        //TODO Method change
        System.out.println("yay");

         */
        return false;
    }

    //-------------------------------Calculation Removes Token Moves--------------------------------

    public Set<TokenMove> createPossibleRemoverTokenMoves(Player player) {
        Set<TokenMove> tokenMoves = new HashSet<>();
        Set<Position> occupiedFields = occupiedFields();
        for (Position position : occupiedFields) {
            Calculation currentCalculation = calculateChangeWithMove(player, getGridCopyWithAddedToken(position, Token.None));
            //TODO Prevent Loss
            tokenMoves.add(new TokenMove(position, currentCalculation.getPointsChange(), Token.Remover, false, isMovePreventingLoss()));

        }
        return tokenMoves;
    }


    public Set<Position> occupiedFields() {
        Set<Position> positions = new HashSet<>();
        Token[][] grid = this.gameBoard.getGameGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].getValue() != 0) {
                    positions.add(new Position(i, j));
                }
            }
        }
        return positions;
    }

    //-----------------------------------Calculation Mover Token Moves------------------------------

    public Set<TokenMove> createPossibleMoverTokenMoves(Player player) {
        Set<TokenMove> tokenMoves = new HashSet<>();
        Set<Position> emptyFields = emptyFields();
        Set<Position> occupiedFields = occupiedFields();
        for (Position occupiedPosition : occupiedFields) {
            for (Position emptyPosition : emptyFields) {
                Calculation currentCalculation = calculateChangeWithMove(player,
                        getGridCopyWithSwappedTokens(emptyPosition, getTokenAtPosition(occupiedPosition),
                                occupiedPosition, Token.None));
                //TODO Prevent Loss
                tokenMoves.add(
                        new TokenMove(emptyPosition, occupiedPosition, currentCalculation.getPointsChange(), Token.Mover,
                                currentCalculation.isGameWinning(), isMovePreventingLoss()));
            }
        }
        return tokenMoves;
    }

    public Token getTokenAtPosition(Position position) {
        Token[][] originalGrid = this.gameBoard.getGameGrid();
        return originalGrid[position.getXCoordinate()][position.getYCoordinate()];
    }

    public Token[][] getGridCopyWithSwappedTokens(Position swap1pos, Token swap1, Position swap2pos, Token swap2) {
        Token[][] originalGrid = this.gameBoard.getGameGrid();

        Token[][] grid = new Token[Constants.GAMEGRID_ROWS][Constants.GAMEGRID_COLUMNS];
        for (int i = 0; i < Constants.GAMEGRID_ROWS; i++) {
            for (int j = 0; j < Constants.GAMEGRID_COLUMNS; j++) {
                grid[i][j] = originalGrid[i][j];
            }
        }
        grid[swap1pos.getXCoordinate()][swap1pos.getYCoordinate()] = swap1;
        grid[swap2pos.getXCoordinate()][swap2pos.getYCoordinate()] = swap2;

        return grid;
    }

    //-------------------------------Calculation Swapper Token Moves--------------------------------

    public Set<TokenMove> createPossibleSwapperTokenMoves(Player player) {
        Set<TokenMove> tokenMoves = new HashSet<>();
        Set<Position> occupiedFields = occupiedFields();
        //TODO Hälfte mit Hälfte vll?
        for (Position pos1 : occupiedFields) {
            for (Position pos2 : occupiedFields) {
                if (!pos1.equals(pos2)) {
                    Calculation currentCalculation = calculateChangeWithMove(player,
                            getGridCopyWithSwappedTokens(pos1, getTokenAtPosition(pos2), pos2,
                                    getTokenAtPosition(pos1)));
                    //TODO Prevent Loss
                    tokenMoves.add(new TokenMove(pos1, pos2, currentCalculation.getPointsChange(),
                            Token.Swapper, currentCalculation.isGameWinning(), isMovePreventingLoss()));
                }
            }
        }
        return tokenMoves;
    }

    //------------------------------Calculation Replacer Token Moves--------------------------------

    public Set<TokenMove> createPossibleReplacerTokenMoves(Player player) {
        Set<TokenMove> tokenMoves = new HashSet<>();
        Token[] hand = player.getHand();
        Set<Integer> handSymbolTokenSet = player.getHandSymbolTokenPositions();
        Set<Position> occupiedFields = occupiedFields();
        for (Position occupiedField : occupiedFields) {
            for (Integer handPosition : handSymbolTokenSet) {
                Calculation currentCalculation = calculateChangeWithMove(player,
                        getGridCopyWithAddedToken(occupiedField, hand[handPosition]));
                //TODO Prevent Loss
                tokenMoves.add(new TokenMove(occupiedField, new Position(handPosition), currentCalculation.getPointsChange(),
                        Token.Replacer, currentCalculation.isGameWinning(), isMovePreventingLoss()));
            }
        }
        return tokenMoves;
    }

    //----------------------------------------------------------------------------------------------

    /**
     * Berechnet Punkte für jede Linie
     *
     * @return Map (Linie, Punkte)
     */
    private Map<Integer, Integer> calculateCurrentOverallPoints() {
        Map<Integer, Map<Token, Integer>> occurrenceMap = getOccurrencesOfTokens();
        Map<Integer, Integer> PointMap = new HashMap<>();

        for (Map.Entry<Integer, Map<Token, Integer>> entry : occurrenceMap.entrySet()) {
            PointMap.put(entry.getKey(), calculate(entry.getValue()));
        }
        return PointMap;
    }

    public Map<Integer, Map<Token, Integer>> getOccurrencesOfTokens() {
        Map<Integer, Map<Token, Integer>> occurrenceMap = new HashMap<>();
        Token[][] grid = this.gameBoard.getGameGrid();

        for (int i = 0; i < grid.length; i++) {
            occurrenceMap.put(-i-1, calculateOccurencesPerLine(grid[i]));
        }
        Token[][] reverseArray = swapMatrix(grid);

        for (int i = 0; i < reverseArray.length; i++) {
            occurrenceMap.put(i+1, calculateOccurencesPerLine(reverseArray[i]));
        }
        return occurrenceMap;
    }
}


