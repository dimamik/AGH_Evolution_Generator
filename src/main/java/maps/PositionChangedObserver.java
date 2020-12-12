package maps;

import objects.AbstractPositionedObject;
import position.Vector2d;

public interface PositionChangedObserver {
    /**
     * Method for maps to be updated on the position of PositionedObject
     *
     * @param previousPosition - previous position of PositionedObject
     * @param newPosition      - new position of PositionedObject
     * @param object           - PositionedObject
     */
    void positionChanged(Vector2d previousPosition, AbstractPositionedObject object);
}
