package maps;

import objects.AbstractPositionedObject;
import objects.ObjectStates;
import objects.animal.Animal;
import objects.animal.Family;
import position.Vector2d;
import statistics.MapStatistics;

import java.util.LinkedList;
import java.util.Optional;

import static config.Config.PLANT_ENERGY;
import static config.Config.START_ENERGY;
import static objects.ObjectStates.ANIMAL;
import static objects.ObjectStates.GRASS;

public class MapCell {
    //TODO listOfObjects can be replaced with HashTable but there is problem with hash()
    private final LinkedList<AbstractPositionedObject> listOfObjects;
    private final Vector2d position;

    public MapCell(Vector2d position) {
        this.listOfObjects = new LinkedList<>();
        this.position = position;
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

    public LinkedList<Animal> getAllAnimalsAndRemoveDead(MapStatistics mapStatistics) {
        LinkedList<Animal> listOfAnimals = new LinkedList<>();
        LinkedList<AbstractPositionedObject> listOfObjectsTmp = new LinkedList<>(listOfObjects);
        for (AbstractPositionedObject object : listOfObjectsTmp) {
            if (object.getState() == ObjectStates.ANIMAL) {
                Animal animal = (Animal) object;
                if (animal.getEnergy() <= 0) {
                    listOfObjects.remove(animal);
                    //TODO statistics. can be replaced with observer notifications
                    mapStatistics.removeAnimalForever(animal);
                } else
                    listOfAnimals.add(animal);
            }
        }
        return listOfAnimals;
    }

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

    private boolean canPair(Animal first, Animal second) {
        return first.getEnergy() >= START_ENERGY && second.getEnergy() >= START_ENERGY;
    }

    public Optional<Family> pairAnimalsIfPossible() {
        Family family = null;
        if (findStrongestAnimal().isPresent()) {
            Animal firstAnimal = findStrongestAnimal().get();
            if (findSecondStrongestAnimal(firstAnimal).isPresent()) {
                Animal secondAnimal = findSecondStrongestAnimal(firstAnimal).get();
                if (canPair(firstAnimal, secondAnimal)) {
                    family = new Family(firstAnimal, secondAnimal);

                }
            }
        }
        return Optional.ofNullable(family);

    }

    @Override
    public String toString() {
        return "MapCell{" +
                "listOfObjects=" + listOfObjects +
                '}';
    }

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


    public Optional<AbstractPositionedObject> getBestObject() {
        if (findStrongestAnimal().isPresent()) {
            return Optional.of(findStrongestAnimal().get());
        } else {
            //Case when we have only grass
            return Optional.ofNullable(listOfObjects.get(0));
        }
    }
}
