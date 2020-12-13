package logic.objects;

import logic.maps.PositionChangedObserver;
import logic.position.Vector2d;

public interface PositionChangedPublisher {

    /**
     * Adds Observer to observer list
     *
     * @param observer observer to add
     */
    void addObserver(PositionChangedObserver observer);

    /**
     * Removes observer from observers list
     *
     * @param observer observer to remove
     */
    void removeObserver(PositionChangedObserver observer);

    /**
     * Updates Position Changed Observers
     *
     * @param previousPosition previous position
     * @param newPosition      new position
     */
    void updatePositionChangedObservers(Vector2d previousPosition, Vector2d newPosition);
}
