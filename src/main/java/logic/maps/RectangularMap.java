package logic.maps;

import logic.objects.AbstractPositionedObject;
import logic.objects.ObjectStates;
import logic.objects.animal.Animal;
import logic.position.Vector2d;
import logic.statistics.MapStatistics;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;

import static config.Config.*;

public class RectangularMap implements IMap, PositionChangedObserver {
    MapStatistics mapStatistics;
    HashMap<Vector2d, MapCell> cellsHashMap;


    public RectangularMap(MapStatistics mapStatistics) {
        this.cellsHashMap = new HashMap<>();
        this.mapStatistics = mapStatistics;
    }

    @Override
    public boolean addObject(AbstractPositionedObject object) {
        if (object.getState() == ObjectStates.ANIMAL) {
            addAnimal((Animal) object);
        }
        if (!cellsHashMap.containsKey(object.getPosition())) {
            MapCell mapCell = new MapCell(object.getPosition());
            cellsHashMap.put(object.getPosition(), mapCell);
            mapCell.addObject(object);
            return true;
        } else {
            cellsHashMap.get(object.getPosition()).addObject(object);
        }
        return false;
    }

    /**
     * Help method to addObject which handles animal observation
     *
     * @param animal - animal to add
     */
    private void addAnimal(Animal animal) {
        mapStatistics.addAnimalToStatistics(animal);
        animal.addObserver(this);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return cellsHashMap.containsKey(position);
    }

    @Override
    public void removeObject(Vector2d position, AbstractPositionedObject object) {
        if (object.getState() == ObjectStates.ANIMAL) {
            removeAnimal((Animal) object);
        }
        MapCell mapCell = cellsHashMap.get(position);
        mapCell.removeObject(object);
        if (mapCell.isEmpty()) {
            cellsHashMap.remove(position);
        }
    }

    /**
     * Help method to remove animal with removing animal observation
     *
     * @param animal - animal to remove
     */
    private void removeAnimal(Animal animal) {
        mapStatistics.removeAnimalFromStatistics(animal);
        animal.removeObserver(this);
    }

    /**
     * Checks Map without Jungle for empty space
     * <p>
     * Can be replaced with return cellsHashMap.size() == WIDTH*HEIGHT; But then we can't
     * divide generated grass into two zones, so we need to check every position :(
     * With a bit of Algorithmic it can be replaced with B-Tree
     *
     * @return - true if map is full, else - false
     */
    public boolean isMapFull() {
        for (int x = 0; x <= WIDTH / JUNGLE_RATIO - 1; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (!cellsHashMap.containsKey(new Vector2d(x, y)))
                    return false;
            }
        }
        for (int x = WIDTH / JUNGLE_RATIO; x <= WIDTH - (WIDTH / JUNGLE_RATIO); x++) {
            for (int y = 0; y <= HEIGHT / JUNGLE_RATIO - 1; y++) {
                if (!cellsHashMap.containsKey(new Vector2d(x, y)))
                    return false;
            }
        }
        for (int x = WIDTH / JUNGLE_RATIO; x <= WIDTH - (WIDTH / JUNGLE_RATIO); x++) {
            for (int y = HEIGHT - (HEIGHT / JUNGLE_RATIO); y < HEIGHT; y++) {
                if (!cellsHashMap.containsKey(new Vector2d(x, y)))
                    return false;
            }
        }
        for (int x = WIDTH - (WIDTH / JUNGLE_RATIO); x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (!cellsHashMap.containsKey(new Vector2d(x, y)))
                    return false;
            }
        }
        return true;
    }

    /**
     * Checks if Jungle is Full
     *
     * @return true if Jungle is Full, else False
     */
    public boolean isJungleFull() {
        for (int x = (WIDTH / JUNGLE_RATIO); x < WIDTH - (WIDTH / JUNGLE_RATIO); x++) {
            for (int y = (HEIGHT / JUNGLE_RATIO); y < HEIGHT - (HEIGHT / JUNGLE_RATIO); y++) {
                if (!cellsHashMap.containsKey(new Vector2d(x, y))) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public Optional<AbstractPositionedObject> getObjectFromCell(Vector2d position) {
        if (cellsHashMap.containsKey(position)) {
            return cellsHashMap.get(position).getBestObject();
        } else {
            return Optional.empty();
        }
    }

    /**
     * Returns list of occupied MapCells
     *
     * @return - list of MapCells
     */
    public LinkedList<MapCell> getListOfOccupiedCells() {
        return new LinkedList<>(cellsHashMap.values());
    }

    public LinkedList<Animal> getAllAnimals(){
        LinkedList<Animal> listToReturn = new LinkedList<>();
        for (MapCell cell: getListOfOccupiedCells()){
            listToReturn.addAll(cell.getAllAnimals());
        }
        return listToReturn;
    }

    /**
     * Removing cell from HashMap
     *
     * @param position - position of the cell
     */
    public void removeCellFromHashMap(Vector2d position) {
        cellsHashMap.remove(position);
    }

    @Override
    public String toString() {
        return "RectangularMap{" +
                "currentDay=" +
                ", cellsHashMap=" + cellsHashMap +
                '}';
    }

    /**
     * Observer pattern to be notified when animal makes a move
     *
     * @param previousPosition previous position of PositionedObject
     * @param object           PositionedObject
     */
    @Override
    public void positionChanged(Vector2d previousPosition, AbstractPositionedObject object) {
        removeObject(previousPosition, object);
        addObject(object);
    }
}
