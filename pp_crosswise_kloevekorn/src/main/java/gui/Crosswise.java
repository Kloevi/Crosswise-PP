package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

/**
 * Class that starts our application.
 *
 * @author mjo
 */
public class Crosswise extends Application {

    /**
     * Creating the stage and showing it. This is where the initial size and the
     * title of the window are set.
     *
     * @param stage the stage to be shown
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Crosswise.class.getResource("UserInterface.fxml"));

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        Integer v = d.width;
        Integer v1 = d.height;

        Scene scene = new Scene(fxmlLoader.load(), v, v1);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method
     *
     * @param args unused
     */
    public static void main(String... args) {
        launch(args);
    }
}
