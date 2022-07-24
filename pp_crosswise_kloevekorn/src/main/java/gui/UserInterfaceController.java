package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import logic.Constants;
import logic.GUIConnector;
import logic.Game;
import logic.Player;
import logic.Token;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Main class for the user interface.
 *
 * @author mjo, cei
 */
public class UserInterfaceController implements Initializable {


    @FXML
    private GridPane gameFieldGrid;

    @FXML
    private GridPane handPlayer1;

    @FXML
    private GridPane handPlayer2;

    @FXML
    private GridPane handPlayer3;

    @FXML
    private GridPane handPlayer4;

    @FXML
    private Label currentPlayerLabel;

    @FXML
    private GridPane usedActionTokensGrid;

    @FXML
    private MenuItem menuPunkte;

    @FXML
    private MenuItem menuComputer;




    private GUIConnector guiConnector;

    @FXML
    private GridPane player1Hand;
    @FXML
    private ImageView player1HandToken1;

    /**
     * This is where you need to add code that should happen during
     * initialization and then change the java doc comment.
     *
     * @param location  probably not used
     * @param resources probably not used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.guiConnector = new JavaFXGUI(this.gameFieldGrid,
                handPlayer1,
                handPlayer2,
                handPlayer3,
                handPlayer4,
                currentPlayerLabel,
                usedActionTokensGrid,
                menuPunkte,
                menuComputer);


        //this.gameLogic = new GameLogic(this.guiConnector);

    }


    @FXML
    protected void clickNewGameButton() {
        //TODO extra Fenster mit Werten
        Player[] players = new Player[Constants.PLAYER_NUMBER];
        players[0] = new Player(0, "Tom", true, false, true);
        players[1] = new Player(1,"Jacob", true, false, false);
        players[2] = new Player(2,"Jonas", true, false, true);
        players[3]= new Player(3,"Simon", true, false, false);

        Game game = new Game(players /*TODO guiConnector*/);
        game.setUpNewGame();

        System.out.println(Arrays.toString(players[0].getHand()));
        Image image = new Image(Token.getImagePathFromToken(players[0].getHand()[0]));

        player1HandToken1.setImage(image);
    }


}
