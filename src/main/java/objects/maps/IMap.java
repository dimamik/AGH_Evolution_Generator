package objects.maps;

import objects.AbstractPositionedObject;
import position.Vector2d;

import java.util.Optional;

public interface IMap {
    /**
     * Adds AbstractPositionedObject to Map
     *
     * @param object - object to add
     * @return - true if added, else false
     */
    boolean addObject(AbstractPositionedObject object);

    /**
     * Removes AbstractPositionedObject from Map
     *
     * @param object - object to remove
     */
    void removeObject(AbstractPositionedObject object);

    /**
     * Returns whether a position on the map is occupied (Either by Animal/Paired
     * Animal or Grass)
     *
     * @return - if position is occupied
     */
    boolean isOccupied(Vector2d position);

    /**
     * Returns "best" object meaning this with max energy or grass
     *
     * @param position - position of cell
     * @return - Optional of object on position
     */
    Optional<AbstractPositionedObject> getObjectFromCell(Vector2d position);

}
