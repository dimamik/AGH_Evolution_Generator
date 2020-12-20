package logic.simulation;

import logic.maps.MapCell;
import logic.maps.RectangularMap;
import logic.objects.animal.Animal;
import logic.objects.animal.Family;
import logic.objects.emptyCellObject.EmptyCellObject;
import logic.objects.grass.GenerateGrass;
import logic.position.Vector2d;
import logic.random.RandomGenerator;
import logic.statistics.MapStatistics;
import logic.statistics.MapStatisticsPropertyGetter;
import visual.map.cells.cellsViewModel.CellsWrapper;
import visual.map.cells.cellsViewModel.ViewObserver;

import java.util.LinkedList;
import java.util.Optional;

import static config.Config.*;
import static logic.objects.animal.PairAnimals.pairAnimalsFromFamilyGroup;

public class MapSimulation implements ViewObserver {
    private final RectangularMap rectangularMap;
    private final MapStatistics mapStatistics;
    private final MapStatisticsPropertyGetter mapStatisticsPropertyGetter;
    private final LinkedList<CellsWrapper> listOfViewObservers;

    public MapSimulation() {
        mapStatistics = new MapStatistics(this);
        mapStatisticsPropertyGetter = new MapStatisticsPropertyGetter(mapStatistics);
        this.rectangularMap = new RectangularMap(mapStatistics);
        this.listOfViewObservers = new LinkedList<>();
        addAnimalsOnStart();
    }


    /**
     * Adds animals on Start of Simulation
     */
    private void addAnimalsOnStart() {
        for (int i = 0; i < ANIMALS_ON_START; i++) {
            rectangularMap.addObject(new Animal(RandomGenerator.getRandomPosition(WIDTH - 1, HEIGHT - 1), START_ENERGY, RandomGenerator.generateRandomGenome()));
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
                pairAnimalsFromFamilyGroup(familyGroupOptional.get(), rectangularMap,
                        getMapStatistics().getDayOfAnimation());
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
        getMapStatistics().setDayOfAnimation(getMapStatistics().getDayOfAnimation() + 1);
        mapStatistics.decreaseSumEnergyByDayPrice();
    }

    // Accessors and mutators

    public MapStatisticsPropertyGetter getMapStatisticsGetter() {
        return mapStatisticsPropertyGetter;
    }

    public RectangularMap getRectangularMap() {
        return rectangularMap;
    }

    public MapStatistics getMapStatistics() {
        return mapStatistics;
    }

    public long getDayInMapSimulation() {
        return getMapStatistics().getDayOfAnimation();
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
