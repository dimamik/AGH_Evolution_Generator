package visual.cellsView;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.objects.AbstractPositionedObject;
import logic.objects.ObjectStates;
import logic.position.Vector2d;

import static config.Config.MAX_SIZE;
import static logic.objects.ObjectStates.EMPTY_CELL;

public class ViewCell extends Rectangle {
    public Vector2d position;

    public ViewCell(Vector2d position) {
        super(360 / MAX_SIZE, 360 / MAX_SIZE);
        this.position = position;

        setFill(Color.LIGHTGRAY);
        setStroke(Color.BLACK);
        setStrokeWidth(0.2);
    }

    public void updateCell(AbstractPositionedObject object) {
        //TODO perform some updates !
        System.out.println("CELL IS GOING TO BE UPDATED");
        if (object.getState() == EMPTY_CELL) {
            setFill(Color.LIGHTGRAY);
        } else if (object.getState() == ObjectStates.ANIMAL) {
            setFill(Color.BLUE);
        } else if (object.getState() == ObjectStates.GRASS) {
            setFill(Color.GREEN);
        }

    }
}
