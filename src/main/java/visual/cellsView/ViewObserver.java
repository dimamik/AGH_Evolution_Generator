package visual.cellsView;

import logic.maps.MapCell;
import logic.position.Vector2d;

public interface ViewObserver {
    void addNewViewObserver(CellsWrapper cellsWrapper);

    void notifyAllObservers(Vector2d position, MapCell mapCell);

}
