package gui;

import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import logic.Constants;
import logic.GUIConnector;

public class JavaFXGUI implements GUIConnector {

    private final GridPane gameFieldGrid;

    private final ImageView[][] gridImages;






    public JavaFXGUI(GridPane gameFieldGrid
    ) {
        this.gameFieldGrid = gameFieldGrid;

        this.gridImages = new ImageView[Constants.GAMEGRID_ROWS][Constants.GAMEGRID_COLUMNS];

        generateGrid();
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

                this.gridImages[r][c] = imgNew;
                this.gameFieldGrid.add(imgNew, c, r);
            }
        }


    }


}
