package logic.maps;

import logic.objects.AbstractPositionedObject;
import logic.position.Vector2d;

import java.util.Optional;

/**
 * Interface representing Map with default map methods
 */
public interface IMap {
    /**
     * Adds AbstractPositionedObject to Map
     *
     * @param object - object to add
     */
    void addObject(AbstractPositionedObject object);

    /**
     * Removes AbstractPositionedObject from Map
     *
     * @param position - position where object was last registered on the map
     * @param object   - object to remove
     */
    void removeObject(Vector2d position, AbstractPositionedObject object);

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
