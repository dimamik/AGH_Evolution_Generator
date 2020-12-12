package objects;

import maps.PositionChangedObserver;
import position.Vector2d;

public interface PositionChangedPublisher {

    void addObserver(PositionChangedObserver observer);

    void removeObserver(PositionChangedObserver observer);

    void updatePositionChangedObservers(Vector2d previousPosition, Vector2d newPosition);
}
