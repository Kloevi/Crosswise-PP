import logic.GUIConnector;
import logic.Token;

public class FakeGUI implements GUIConnector {

    public FakeGUI() {
    }
    @Override
    public void showPlayerHand(int ID, Token[] hand) {
    }
    @Override
    public void hidePlayerHand(int ID){
    }
    @Override
    public void setAnimationSpeed(float speed){
    }

}
