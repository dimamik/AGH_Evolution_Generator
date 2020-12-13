package logic.simulation;

import logic.maps.MapCell;
import logic.maps.RectangularMap;
import logic.objects.animal.Animal;
import logic.objects.animal.Family;
import logic.objects.animal.Gens;
import logic.objects.grass.GenerateGrass;
import logic.random.RandomGenerator;
import logic.statistics.MapStatistics;
import logic.statistics.MapStatisticsGetter;

import java.util.LinkedList;
import java.util.Optional;

import static config.Config.*;
import static logic.objects.animal.PairAnimals.pairAnimalsFromFamilyGroup;

public class MapSimulation {
    public RectangularMap getRectangularMap() {
        return rectangularMap;
    }

    public MapStatistics getMapStatistics() {
        return mapStatistics;
    }

    private final RectangularMap rectangularMap;
    private final MapStatistics mapStatistics;
    private final MapStatisticsGetter mapStatisticsGetter;
    private int dayInMapSimulation;

    public MapSimulation() {
        dayInMapSimulation = 0;
        mapStatistics = new MapStatistics(this);
        mapStatisticsGetter = new MapStatisticsGetter(mapStatistics);
        this.rectangularMap = new RectangularMap(mapStatistics);
        addAnimalsOnStart();
    }

    public int getDayInMapSimulation() {
        return dayInMapSimulation;
    }

    private void addAnimalsOnStart() {
        //TODO Gens can be generated randomly
        int[] genSeq = {0, 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 5, 5, 6, 6, 7, 7, 7, 7};
        int[] genSeq2 = {0, 0, 7, 1, 1, 7, 2, 0, 2, 0, 2, 3, 7, 7, 7, 4, 4, 4, 4, 0, 7, 0, 7, 0, 5, 5, 6, 6, 7, 7, 7, 7};

        for (int i = 0; i < ANIMALS_ON_START; i++) {
            rectangularMap.addObject(new Animal(RandomGenerator.getRandomPosition(WIDTH, HEIGHT), 200, new Gens(genSeq)));
        }
    }

    public LinkedList<Animal> deleteDead() {
        LinkedList<Animal> listOfAnimals = new LinkedList<>();
        LinkedList<MapCell> listOfObjects = rectangularMap.getListOfOccupiedCells();
        for (MapCell currCell : listOfObjects) {
            listOfAnimals.addAll(currCell.getAllAnimalsAndRemoveDead(mapStatistics));
            if (currCell.isEmpty()) {
                rectangularMap.removeCellFromHashMap(currCell.getPosition());
            }
        }
        return listOfAnimals;
    }

    public void moveAllAnimals(LinkedList<Animal> listOfAnimals) {
        for (Animal animal : listOfAnimals) {
            animal.moveAnimal();
        }
    }

    public void eatByAnimals() {
        LinkedList<MapCell> listOfObjects = rectangularMap.getListOfOccupiedCells();
        for (MapCell mapCell : listOfObjects) {
            mapCell.eatGrassByStrongestAnimal(mapStatistics);
        }
    }


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

    public void generateGrass() {
        if (GenerateGrass.generateGrasInJungles(rectangularMap))
            mapStatistics.addGrassToStatistics();
        if (GenerateGrass.generateGrassOutOfJungles(rectangularMap))
            mapStatistics.addGrassToStatistics();
    }

    public void subtractEnergy(LinkedList<Animal> listOfAnimals) {
        for (Animal animal : listOfAnimals) {
            animal.setEnergy(animal.getEnergy() - MOVE_ENERGY);
        }
    }

    public void increaseDay() {
        dayInMapSimulation += 1;
        mapStatistics.decreaseSumEnergyByDayPrice();
    }

    public MapStatisticsGetter getMapStatisticsGetter() {
        return mapStatisticsGetter;
    }

}
