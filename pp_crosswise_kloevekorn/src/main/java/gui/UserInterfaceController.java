package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import logic.Constants;
import logic.GUIConnector;
import logic.Game;
import logic.GameLogic;
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

    /**
     * Label that displays the welcome text. Should be deleted when creating an
     * actual project.
     */
    @FXML
    private Label welcomeText;

    @FXML
    private GridPane gameFieldGrid;


    private GameLogic gameLogic;

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
        this.guiConnector = new JavaFXGUI(this.gameFieldGrid);


        //this.gameLogic = new GameLogic(this.guiConnector);

    }



    /**
     * Called when the button on the gui is clicked. Triggers display of welcome
     * text in the label. <br> Should be deleted when creating an actual
     * project.
     */
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void clickNewGameButton() {
        //TODO extra Fenster mit Werten
        Player[] players = new Player[Constants.PLAYER_NUMBER];
        players[0] = new Player("Tom", true, false, true);
        players[1] = new Player("Jacob", true, false, false);
        players[2] = new Player("Jonas", true, false, true);
        players[3]= new Player("Simon", true, false, false);

        Game game = new Game(players);

        System.out.println(Arrays.toString(players[0].getHand()));
        Image image = new Image(Token.getImagePathFromToken(players[0].getHand()[0]));

        player1HandToken1.setImage(image);
    }


}
