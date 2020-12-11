package objects.maps;

import objects.AbstractPositionedObject;
import objects.ObjectStates;
import objects.animal.Animal;
import objects.animal.Family;
import objects.grass.GenerateGrass;
import position.Vector2d;
import statistics.Statistics;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;

import static config.Config.*;
import static objects.animal.PairAnimals.pairAnimalsFromFamilyGroup;

public class RectangularMap implements IMap{
    int currentDay;
    Statistics statistics;
    HashMap<Vector2d, MapCell> cellsHashMap;


    public RectangularMap() {
        this.currentDay = 0;
        this.cellsHashMap = new HashMap<>();
        statistics = new Statistics(this);
    }

    @Override
    public boolean addObject(AbstractPositionedObject object) {
        if (object.getState() == ObjectStates.ANIMAL) {
            statistics.addAnimalToStatistics((Animal) object);
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
    @Override
    public void removeObject(AbstractPositionedObject object) {
        if (object.getState() == ObjectStates.ANIMAL) {
            statistics.removeAnimalFromStatistics((Animal) object);
        }
        Vector2d position = object.getPosition();
        MapCell mapCell = cellsHashMap.get(position);
        mapCell.removeObject(object);
        if (mapCell.isEmpty()) {
            cellsHashMap.remove(position);
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return cellsHashMap.containsKey(position);
    }

    /**
     * Checks Map without Jungle for empty space
     *
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


    public void moveAnimal(Animal animal) {
        removeObject(animal);
        animal.moveAnimal();
        addObject(animal);
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
     * Function to remove all dead Animals from the map
     * And to return list of alive animals to lower the complexity
     * of moveAllAnimals method
     *
     * @return Linked List of Alive Animals
     */
    public LinkedList<Animal> deleteDead() {
        LinkedList<Animal> listOfAnimals = new LinkedList<>();
        LinkedList<MapCell> listOfObjects = new LinkedList<>(cellsHashMap.values());
        for (MapCell currCell : listOfObjects) {
            listOfAnimals.addAll(currCell.getAllAnimalsAndRemoveDead(statistics));
            if (currCell.isEmpty()) {
                cellsHashMap.remove(currCell.getPosition());
            }
        }
        return listOfAnimals;
    }

    public void moveAllAnimals(LinkedList<Animal> listOfAnimals) {
        for (Animal animal : listOfAnimals) {
            moveAnimal(animal);
        }
    }

    public void eatByAnimals() {
        LinkedList<MapCell> listOfObjects = new LinkedList<>(cellsHashMap.values());
        for (MapCell mapCell : listOfObjects) {
            mapCell.eatGrassByStrongestAnimal(statistics);
        }
    }

    public void pairAnimals() {
        LinkedList<MapCell> listOfObjects = new LinkedList<>(cellsHashMap.values());
        for (MapCell mapCell : listOfObjects) {
            Optional<Family> familyGroupOptional = mapCell.pairAnimalsIfPossible();
            if (familyGroupOptional.isPresent()) {
                statistics.addNewChildToStatistics();
                pairAnimalsFromFamilyGroup(familyGroupOptional.get(), this);
            }
        }
    }

    public void generateGrass() {
        if (GenerateGrass.generateGrasInJungles(this))
            statistics.addGrassToStatistics();
        if (GenerateGrass.generateGrassOutOfJungles(this))
            statistics.addGrassToStatistics();
    }

    public void subtractEnergy(LinkedList<Animal> listOfAnimals) {
        for (Animal animal : listOfAnimals) {
            animal.setEnergy(animal.getEnergy() - MOVE_ENERGY);
        }
    }

    //TODO Replace with simulation.newDay()
    public void newDay() {
        currentDay++;
        statistics.decreaseSumEnergyByDayPrice();
        LinkedList<Animal> listOfAnimals = deleteDead();
        moveAllAnimals(listOfAnimals);
        eatByAnimals();
        pairAnimals();
        //TODO Make Animal Observer to die by himself (reducing time complexity)
        subtractEnergy(listOfAnimals);
        generateGrass();
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public void increaseDay() {
        currentDay++;
    }

    @Override
    public String toString() {
        return "RectangularMap{" +
                "currentDay=" + currentDay +
                ", cellsHashMap=" + cellsHashMap +
                '}';
    }

    public Statistics getStatistics() {
        return statistics;
    }
}
