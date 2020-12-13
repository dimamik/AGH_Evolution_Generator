package visual;

import config.Config;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.maps.RectangularMap;
import logic.position.Vector2d;


public class VisualRectangularMap extends Parent {
    private final VBox rows = new VBox();

    public VisualRectangularMap(EventHandler<? super MouseEvent> handler, RectangularMap rectangularMap) {
        for (int y = 0; y < Config.HEIGHT; y++) {
            HBox row = new HBox();
            for (int x = 0; x < Config.WIDTH; x++) {
                Cell c = new Cell(x, y, this, rectangularMap.getObjectFromCell(new Vector2d(x, y)));
                c.setOnMouseClicked(handler);
                row.getChildren().add(c);
            }

            rows.getChildren().add(row);
        }
        getChildren().add(rows);
    }

    public Cell getCell(int x, int y) {
        return (Cell) ((HBox) rows.getChildren().get(y)).getChildren().get(x);
    }
}
