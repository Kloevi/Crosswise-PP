import logic.GUIConnector;
import logic.Token;

public class FakeGUI implements GUIConnector {

    private Integer a;
    public FakeGUI(Integer a) {
        this.a = a;
    }
    @Override
    public void showPlayerHand(int ID, Token[] hand) {
    }

}
