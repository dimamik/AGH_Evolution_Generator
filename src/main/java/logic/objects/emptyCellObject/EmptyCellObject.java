package logic.objects.emptyCellObject;


import logic.objects.AbstractPositionedObject;
import logic.objects.ObjectStates;
import logic.position.Vector2d;

import static logic.objects.ObjectStates.EMPTY_CELL;


public class EmptyCellObject extends AbstractPositionedObject {

    public EmptyCellObject(Vector2d position) {
        super(position);
    }

    @Override
    public void setPosition(Vector2d position) {
        super.setPosition(position);
    }

    @Override
    public ObjectStates getState() {
        return EMPTY_CELL;
    }
}
