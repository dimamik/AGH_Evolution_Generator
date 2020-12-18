package visual.map.cells.cellsView;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.objects.AbstractPositionedObject;
import logic.objects.ObjectStates;
import logic.objects.animal.Animal;
import logic.objects.emptyCellObject.EmptyCellObject;
import logic.position.Vector2d;

import static config.Config.MAX_SIZE;
import static config.Config.START_ENERGY;
import static logic.objects.ObjectStates.ANIMAL;
import static logic.objects.ObjectStates.EMPTY_CELL;

public class CellView extends Rectangle {
    public Vector2d position;
    public AbstractPositionedObject currentObject;
    public boolean isDominant;


    public CellView(Vector2d position) {
        super(360 / (double) MAX_SIZE, 360 / (double) MAX_SIZE);
        this.position = position;
        this.isDominant = false;
        currentObject = new EmptyCellObject(position);
        setFill(Color.LIGHTGRAY);
        setStroke(Color.BLACK);
        setStrokeWidth(0.2);
    }

    public AbstractPositionedObject getCurrentObject() {
        return currentObject;
    }

    public void setCurrentObject(AbstractPositionedObject currentObject) {
        this.currentObject = currentObject;
    }

    public void updateCell(AbstractPositionedObject object) {
        setCurrentObject(object);
        if (object.getState() == EMPTY_CELL) {
            setFill(Color.LIGHTGRAY);
        } else if (object.getState() == ObjectStates.ANIMAL) {
            Animal animal = (Animal) object;
            if (animal.getEnergy() >= START_ENERGY / 2 + START_ENERGY / 4)
                setFill(Color.rgb(0, 200, 0));
            else if (animal.getEnergy() < START_ENERGY / 2 + START_ENERGY / 4 && (animal.getEnergy() > 5))
                setFill(Color.rgb(216, 133, 0));
            else {
                setFill(Color.rgb(237, 0, 0));
            }
            if (isDominant) {
                setFill(Color.GOLD);
            }
        } else if (object.getState() == ObjectStates.GRASS) {
            setFill(
                    Color.rgb(0, 86, 24)
            );
        }
    }

    public void makeCellDominant(boolean show) {
        if (show)
            setFill(Color.GOLD);
    }

    public void makeCellSelected() {
        if (currentObject.getState() == ANIMAL) {
            Animal animal = (Animal) currentObject;
            setFill(Color.BLUE);
            if (animal.getEnergy() <= 0)
                setFill(Color.GREY);
        }

    }
}
