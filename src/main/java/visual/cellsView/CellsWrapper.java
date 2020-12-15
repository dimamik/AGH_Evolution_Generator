package visual.cellsView;

import logic.objects.AbstractPositionedObject;
import logic.position.Vector2d;

import static config.Config.HEIGHT;
import static config.Config.WIDTH;


public class CellsWrapper {

    private final CellViewModel[][] arrayOfCells;

    public CellsWrapper() {
        arrayOfCells = new CellViewModel[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                this.arrayOfCells[x][y] = new CellViewModel();
            }
        }
    }

    public void addEventListeners(ViewCell[][] listOfCells) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                int finalX = x;
                int finalY = y;
                arrayOfCells[x][y].positionedObjectObjectPropertyProperty()
                        .addListener((observable, oldValue, newValue) -> listOfCells[finalX][finalY].updateCell(newValue));
            }
        }
    }

    public void updateCell(Vector2d cellPosition, AbstractPositionedObject object) {
        arrayOfCells[cellPosition.getX()][cellPosition.getY()].setPositionedObjectObjectProperty(object);
    }

}