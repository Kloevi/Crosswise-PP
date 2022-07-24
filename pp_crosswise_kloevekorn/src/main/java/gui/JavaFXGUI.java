package gui;

import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import logic.Constants;
import logic.GUIConnector;
import logic.Token;

public class JavaFXGUI implements GUIConnector {

    private final GridPane gameFieldGrid;

    private final ImageView[][] gridImages = new ImageView[Constants.GAMEGRID_ROWS][Constants.GAMEGRID_COLUMNS];

    private GridPane handPlayer1;

    private GridPane handPlayer2;

    private GridPane handPlayer3;

    private GridPane handPlayer4;

    private Label currentPlayerLabel;

    private GridPane usedActionTokensGrid;

    private MenuItem menuPunkte;

    private MenuItem menuComputer;

    public JavaFXGUI(GridPane gameFieldGrid, GridPane handPlayer1, GridPane handPlayer2,
            GridPane handPlayer3, GridPane handPlayer4, Label currentPlayerLabel,
            GridPane usedActionTokensGrid, MenuItem menuPunkte, MenuItem menuComputer) {
        this.gameFieldGrid = gameFieldGrid;
        this.handPlayer1 = handPlayer1;
        this.handPlayer2 = handPlayer2;
        this.handPlayer3 = handPlayer3;
        this.handPlayer4 = handPlayer4;
        this.currentPlayerLabel = currentPlayerLabel;
        this.usedActionTokensGrid = usedActionTokensGrid;
        this.menuPunkte = menuPunkte;
        this.menuComputer = menuComputer;

        generateGrid();
        Token[] hand2 = new Token[] {Token.Star, Token.Sun, Token.Cross, Token.Star};
        generateHand(0, hand2);
    }

    @Override
    public void showPlayerHand(int ID, Token[] hand) {
        switch (ID) {
            case 0:
                handPlayer1.setVisible(true);
                break;
            case 1:
                handPlayer2.setVisible(true);
                break;
            case 2:
                handPlayer3.setVisible(true);
                break;
            case 3:
                handPlayer4.setVisible(true);
                break;

        }
    }


    /*
        if (isGameFinished()){
            gui.startEndSequence();
        }
        currentPlayer +1;

        playerDoesTurn


        nextPlayer





     */








    public void generateHand(int ID, Token[] hand) {
        for (int i = 0; i < Constants.HAND_SIZE; i++) {
            ImageView imgNew = new ImageView();
            String id = "playerHand" + ID + "position" + i;
            imgNew.setId(id);

            imgNew.fitHeightProperty().bind(this.handPlayer1.widthProperty().divide(Constants.HAND_SIZE));
            imgNew.fitWidthProperty().bind(this.handPlayer1.widthProperty().divide(Constants.HAND_SIZE));

            switch (ID) {
                case 0 -> handPlayer1.add(imgNew, i, 0);
                case 1 -> handPlayer2.add(imgNew, 0, i);
                case 2 -> handPlayer3.add(imgNew, i, 0);
                case 3 -> handPlayer4.add(imgNew, 0, i);
            }
            for (int j = 0; j < 4; j++) {
                ImageView view = (ImageView) handPlayer1.getChildren().get(ID);
                view.setImage(new Image(Token.getImagePathFromToken(hand[j])));
            }
        }
    }

    public void updateHand() {
        return;
    }

    public void generateGrid() {
        this.gameFieldGrid.getChildren().clear();
        for (int r = 0; r < Constants.GAMEGRID_ROWS; r++) {
            for (int c = 0; c < Constants.GAMEGRID_COLUMNS; c++) {
                ImageView imgNew = new ImageView();

                imgNew.fitHeightProperty().bind(this.gameFieldGrid.widthProperty().divide(Constants.GAMEGRID_COLUMNS));
                imgNew.fitWidthProperty().bind(this.gameFieldGrid.widthProperty().divide(Constants.GAMEGRID_ROWS));

                String id = "gridToken" + c + r;
                imgNew.setId(id);

                System.out.println(imgNew.getId());

                Image img = new Image("\\pictures\\1 - sun.png");
                imgNew.setImage(img);

                this.gridImages[r][c] = imgNew;
                this.gameFieldGrid.add(imgNew, c, r);
            }
        }


    }


}
