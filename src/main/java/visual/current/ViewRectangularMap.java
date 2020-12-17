package visual.current;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.position.Vector2d;
import visual.cellsView.ViewCell;

import static config.Config.HEIGHT;
import static config.Config.WIDTH;


public class ViewRectangularMap extends Parent {
    private final VBox rows = new VBox();

    private final ViewCell[][] arrayOfCells;

    public ViewRectangularMap(EventHandler<? super MouseEvent> handler) {
        arrayOfCells = new ViewCell[WIDTH][HEIGHT];
        for (int y = 0; y < HEIGHT; y++) {
            HBox row = new HBox();
            for (int x = 0; x < WIDTH; x++) {

                arrayOfCells[x][y] = new ViewCell(new Vector2d(x, y));
                arrayOfCells[x][y].setOnMouseClicked(handler);
                row.getChildren().add(arrayOfCells[x][y]);
            }

            rows.getChildren().add(row);
        }
        getChildren().add(rows);
    }


    public ViewCell[][] getArrayOfCells() {
        return arrayOfCells;
    }
}
