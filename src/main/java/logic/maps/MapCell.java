package logic.maps;

import logic.objects.AbstractPositionedObject;
import logic.objects.ObjectStates;
import logic.objects.animal.Animal;
import logic.objects.animal.Family;
import logic.objects.animal.PairAnimals;
import logic.position.Vector2d;
import logic.statistics.MapStatistics;

import java.util.LinkedList;
import java.util.Optional;

import static config.Config.PLANT_ENERGY;
import static logic.objects.ObjectStates.ANIMAL;
import static logic.objects.ObjectStates.GRASS;

/**
 * Class representing Map Cell, containing AbstractPositionedObjects
 */
public class MapCell {
    private final LinkedList<AbstractPositionedObject> listOfObjects;
    private final Vector2d position;

    public MapCell(Vector2d position) {
        this.listOfObjects = new LinkedList<>();
        this.position = position;
    }

    /**
     * removes Dead Animals and returns all alive animals
     * Optimizes work of newDay() in single MapSimulation
     * Notifies statistics to remove forever animal
     *
     * @param mapStatistics - statistics bounded to MapSimulation where this cell is positioned
     * @return - Linked list of Alive animals
     * <p>
     * ! Can be replaced with two separate methods
     * ! Statistics can be placed to observers but than it breaks the rule:
     * Use the pattern when some objects in your app must observe others,
     * but only for a limited time or in specific cases.
     */
    public LinkedList<Animal> getAllAnimalsAndRemoveDead(MapStatistics mapStatistics) {
        LinkedList<Animal> listOfAnimals = new LinkedList<>();
        LinkedList<AbstractPositionedObject> listOfObjectsTmp = new LinkedList<>(listOfObjects);
        for (AbstractPositionedObject object : listOfObjectsTmp) {
            if (object.getState() == ObjectStates.ANIMAL) {
                Animal animal = (Animal) object;
                if (animal.getEnergy() <= 0) {
                    listOfObjects.remove(animal);
                    mapStatistics.removeAnimalForever(animal);
                } else
                    listOfAnimals.add(animal);
            }
        }
        return listOfAnimals;
    }

    /**
     * @return list of all animals on map
     */
    public LinkedList<Animal> getAllAnimals() {
        LinkedList<Animal> listOfAnimals = new LinkedList<>();

        for (AbstractPositionedObject object : listOfObjects) {
            if (object.getState() == ObjectStates.ANIMAL) {
                Animal animal = (Animal) object;
                listOfAnimals.add(animal);
            }
        }
        return listOfAnimals;
    }

    /**
     * @return - Returns Strongest Animal or Optional.empty() if there is only grass
     */
    private Optional<Animal> findStrongestAnimal() {
        int maxEnergy = Integer.MIN_VALUE;
        Animal animalToReturn = null;
        for (AbstractPositionedObject object : listOfObjects) {
            if (object.getState() == ANIMAL) {
                Animal animal = (Animal) object;
                if (animal.getEnergy() > maxEnergy) {
                    animalToReturn = animal;
                    maxEnergy = animal.getEnergy();
                }
            }
        }
        return Optional.ofNullable(animalToReturn);
    }

    /**
     * @param firstStrongestAnimal - first strongest animal
     * @return - Second strongest animal  or Optional.empty() if there is no
     */
    private Optional<Animal> findSecondStrongestAnimal(Animal firstStrongestAnimal) {

        int maxEnergy = Integer.MIN_VALUE;
        Animal animalToReturn = null;
        for (AbstractPositionedObject object : listOfObjects) {
            if (object.getState() == ANIMAL) {

                Animal animal = (Animal) object;
                if (animal.getEnergy() > maxEnergy && !animal.equals(firstStrongestAnimal)) {
                    animalToReturn = animal;
                    maxEnergy = animal.getEnergy();
                }
            }
        }
        return Optional.ofNullable(animalToReturn);
    }


    /**
     * Returns Family if pairing of objects is Possible
     *
     * @return - Optional of Family
     */
    public Optional<Family> pairAnimalsIfPossible() {
        Family family = null;
        if (findStrongestAnimal().isPresent()) {
            Animal firstAnimal = findStrongestAnimal().get();
            if (findSecondStrongestAnimal(firstAnimal).isPresent()) {
                Animal secondAnimal = findSecondStrongestAnimal(firstAnimal).get();
                if (PairAnimals.canPair(firstAnimal, secondAnimal)) {
                    family = new Family(firstAnimal, secondAnimal);
                }
            }
        }
        return Optional.ofNullable(family);
    }

    /**
     * Eat grass by strongest animal method
     *
     * @param mapStatistics - statistics (can be replaced with observer)
     */
    public void eatGrassByStrongestAnimal(MapStatistics mapStatistics) {
        for (AbstractPositionedObject object : listOfObjects) {
            if (object.getState() == GRASS) {
                if (findStrongestAnimal().isPresent()) {
                    Animal animal = findStrongestAnimal().get();
                    animal.setEnergy(animal.getEnergy() + PLANT_ENERGY);
                    mapStatistics.addEnergy(PLANT_ENERGY);
                    listOfObjects.remove(object);
                    mapStatistics.removeGrassFromStatistics();
                    return;
                }
            }
        }
    }

    /**
     * @return -  Strongest Animal if there is animal in the cell
     * Otherwise returns grass
     */
    public Optional<AbstractPositionedObject> getBestObject() {
        if (findStrongestAnimal().isPresent()) {
            return Optional.of(findStrongestAnimal().get());
        } else {
            return Optional.ofNullable(listOfObjects.get(0));
        }
    }

    @Override
    public String toString() {
        return "MapCell{" +
                "listOfObjects=" + listOfObjects +
                '}';
    }

    public Vector2d getPosition() {
        return position;
    }

    public void addObject(AbstractPositionedObject object) {
        listOfObjects.add(object);
    }

    public boolean isEmpty() {
        return listOfObjects.isEmpty();
    }

    public void removeObject(AbstractPositionedObject object) {
        listOfObjects.remove(object);
    }
}
