package visual;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import objects.AbstractPositionedObject;
import objects.ObjectStates;

import java.util.Optional;

public class Cell extends Rectangle {
    public int x, y;

    private VisualRectangularMap visualRectangularMap;

    public Cell(int x, int y, VisualRectangularMap visualRectangularMap, Optional<AbstractPositionedObject> object) {
        super(30, 30);
        this.x = x;
        this.y = y;

        if (object.isEmpty()){
            setFill(Color.LIGHTGRAY);
        }
        else if (object.get().getState() == ObjectStates.ANIMAL){
            setFill(Color.BLUE);
        }
        else if (object.get().getState() == ObjectStates.GRASS){
            setFill(Color.GREEN);
        }

        this.visualRectangularMap = visualRectangularMap;

        setStroke(Color.BLACK);
    }

}