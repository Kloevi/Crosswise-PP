package logic;

public class GameBoard {

    private Token[][] gameGrid;

    private int size;

    public GameBoard(int size) {
        this.size = size;
        gameGrid = new Token[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameGrid[i][j] = Token.None;
            }
        }
    }



}
