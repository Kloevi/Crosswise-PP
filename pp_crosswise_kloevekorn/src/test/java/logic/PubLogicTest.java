package logic;

import gui.JavaFXGUI;
import javafx.scene.layout.GridPane;
import org.junit.Test;

public class PubLogicTest {



    @Test
    public void gridTest() {
        GridPane a = new GridPane();
        JavaFXGUI w = new JavaFXGUI(a);

        w.generateGrid();
    }
}
