package visual.map;

import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.objects.AbstractPositionedObject;
import logic.position.Vector2d;
import visual.map.cells.cellsView.CellView;
import visual.window.MapAndStatisticsWindow;

import java.util.LinkedList;

import static config.Config.HEIGHT;
import static config.Config.WIDTH;


public class ViewRectangularMap extends Parent {
    private final VBox rows = new VBox();
    private final CellView[][] arrayOfCells;
    private final LinkedList<MapAndStatisticsWindow> listenersOfCellChange;

    public ViewRectangularMap() {
        arrayOfCells = new CellView[WIDTH][HEIGHT];
        listenersOfCellChange = new LinkedList<>();
        for (int y = 0; y < HEIGHT; y++) {
            HBox row = new HBox();
            for (int x = 0; x < WIDTH; x++) {
                arrayOfCells[x][y] = new CellView(new Vector2d(x, y));
                int finalX = x;
                int finalY = y;
                arrayOfCells[x][y].setOnMouseClicked(
                        event -> {
                            notifyAllListeners(arrayOfCells[finalX][finalY].getCurrentObject());
                        }
                );
                row.getChildren().add(arrayOfCells[x][y]);
            }

            rows.getChildren().add(row);
        }
        getChildren().add(rows);
    }

    public void addNewCellListener(MapAndStatisticsWindow listener) {
        listenersOfCellChange.add(listener);
    }

    private void notifyAllListeners(AbstractPositionedObject object) {
        for (MapAndStatisticsWindow listener : listenersOfCellChange) {
            listener.cellWasClicked(object);
        }
    }

    public CellView[][] getArrayOfCells() {
        return arrayOfCells;
    }
}
