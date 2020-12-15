package logic.simulation;

import logic.maps.MapCell;
import logic.maps.RectangularMap;
import logic.objects.animal.Animal;
import logic.objects.animal.Family;
import logic.objects.animal.Gens;
import logic.objects.emptyCellObject.EmptyCellObject;
import logic.objects.grass.GenerateGrass;
import logic.position.Vector2d;
import logic.random.RandomGenerator;
import logic.statistics.MapStatistics;
import logic.statistics.MapStatisticsGetter;
import visual.cellsView.CellsWrapper;
import visual.cellsView.ViewObserver;

import java.util.LinkedList;
import java.util.Optional;

import static config.Config.*;
import static logic.objects.animal.PairAnimals.pairAnimalsFromFamilyGroup;

public class MapSimulation implements ViewObserver {
    private final RectangularMap rectangularMap;
    private final MapStatistics mapStatistics;
    private final MapStatisticsGetter mapStatisticsGetter;
    private final LinkedList<CellsWrapper> listOfViewObservers;
    private int dayInMapSimulation;

    public MapSimulation() {
        dayInMapSimulation = 0;
        mapStatistics = new MapStatistics(this);
        mapStatisticsGetter = new MapStatisticsGetter(mapStatistics);
        this.rectangularMap = new RectangularMap(mapStatistics);
        this.listOfViewObservers = new LinkedList<>();
        addAnimalsOnStart();
    }


    /**
     * Adds animals on Start of Simulation
     */
    private void addAnimalsOnStart() {
        //TODO Gens can be generated randomly
        int[] genSeq = {0, 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 5, 5, 6, 6, 7, 7, 7, 7};
        int[] genSeq2 = {0, 0, 7, 1, 1, 7, 2, 0, 2, 0, 2, 3, 7, 7, 7, 4, 4, 4, 4, 0, 7, 0, 7, 0, 5, 5, 6, 6, 7, 7, 7, 7};

        for (int i = 0; i < ANIMALS_ON_START; i++) {
            rectangularMap.addObject(new Animal(RandomGenerator.getRandomPosition(WIDTH - 1, HEIGHT - 1), START_ENERGY, new Gens(genSeq)));
        }
    }

    /**
     * Deletes dead animals
     *
     * @return list of alive animals
     */
    public LinkedList<Animal> deleteDead() {
        LinkedList<Animal> listOfAnimals = new LinkedList<>();
        LinkedList<MapCell> listOfObjects = rectangularMap.getListOfOccupiedCells();
        for (MapCell currCell : listOfObjects) {
            listOfAnimals.addAll(currCell.getAllAnimalsAndRemoveDead(mapStatistics));
            if (currCell.isEmpty()) {
                rectangularMap.removeCellFromHashMap(currCell.getPosition());
                notifyAllObservers(currCell.getPosition(), currCell);
            }
        }
        return listOfAnimals;
    }

    /**
     * Moves all animals
     *
     * @param listOfAnimals list of animals to move
     */
    public void moveAllAnimals(LinkedList<Animal> listOfAnimals) {
        for (Animal animal : listOfAnimals) {
            animal.moveAnimal();
        }
    }

    /**
     * Animals with highest energy eat the grass if it is present in their cell
     */
    public void eatByAnimals() {
        LinkedList<MapCell> listOfObjects = rectangularMap.getListOfOccupiedCells();
        for (MapCell mapCell : listOfObjects) {
            mapCell.eatGrassByStrongestAnimal(mapStatistics);
        }
    }

    /**
     * Animals with highest energy are paired if it is possible
     */
    public void pairAnimals() {
        LinkedList<MapCell> listOfObjects = rectangularMap.getListOfOccupiedCells();
        for (MapCell mapCell : listOfObjects) {
            Optional<Family> familyGroupOptional = mapCell.pairAnimalsIfPossible();
            if (familyGroupOptional.isPresent()) {
                mapStatistics.addNewChildToStatistics();
                pairAnimalsFromFamilyGroup(familyGroupOptional.get(), rectangularMap, dayInMapSimulation);
            }
        }
    }

    /**
     * Grass is generated -> 1 in Jungles and 1 outside of Jungles
     */
    public void generateGrass() {
        if (GenerateGrass.generateGrasInJungles(rectangularMap))
            mapStatistics.addGrassToStatistics();
        if (GenerateGrass.generateGrassOutOfJungles(rectangularMap))
            mapStatistics.addGrassToStatistics();
    }

    /**
     * Energy is subtracted from every animal from the list
     *
     * @param listOfAnimals list of animals
     */
    public void subtractEnergy(LinkedList<Animal> listOfAnimals) {
        for (Animal animal : listOfAnimals) {
            animal.setEnergy(animal.getEnergy() - MOVE_ENERGY);
            if (getRectangularMap().getMapCellFromPosition(animal.getPosition()).isPresent())
                notifyAllObservers(animal.getPosition(), getRectangularMap().getMapCellFromPosition(animal.getPosition()).get());
        }
    }

    /**
     * Increases days and decreases sum in statistics
     */
    public void increaseDay() {
        dayInMapSimulation += 1;
        mapStatistics.decreaseSumEnergyByDayPrice();
    }

    // Accessors and mutators

    public MapStatisticsGetter getMapStatisticsGetter() {
        return mapStatisticsGetter;
    }

    public RectangularMap getRectangularMap() {
        return rectangularMap;
    }

    public MapStatistics getMapStatistics() {
        return mapStatistics;
    }

    public int getDayInMapSimulation() {
        return dayInMapSimulation;
    }

    @Override
    public void addNewViewObserver(CellsWrapper cellsWrapper) {
        getRectangularMap().addNewViewObserver(cellsWrapper);
        listOfViewObservers.add(cellsWrapper);
    }

    @Override
    public void notifyAllObservers(Vector2d position, MapCell mapCell) {
        if (mapCell.getBestObject().isPresent())
            for (CellsWrapper cell : listOfViewObservers) {
                cell.updateCell(position, mapCell.getBestObject().get());
            }
        else {
            for (CellsWrapper cell : listOfViewObservers) {
                cell.updateCell(position, new EmptyCellObject(position));
            }
        }
    }
}
