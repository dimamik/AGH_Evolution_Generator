package objects.maps;

import objects.AbstractPositionedObject;
import objects.ObjectStates;
import objects.animal.Animal;
import objects.animal.FamilyGroup;
import position.Vector2d;

import java.util.LinkedList;
import java.util.Optional;

import static config.Config.*;
import static objects.ObjectStates.ANIMAL;
import static objects.ObjectStates.GRASS;

public class MapCell {
    LinkedList<AbstractPositionedObject> listOfObjects;

    public Vector2d getPosition() {
        return position;
    }

    Vector2d position;

    public MapCell(Vector2d position) {
        this.listOfObjects = new LinkedList<>();
        this.position = position;
    }

    public MapCell(Animal animal, Vector2d position) {
        this.listOfObjects = new LinkedList<>();
        listOfObjects.add(animal);
        this.position = position;
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


    public LinkedList<Animal> getAllAnimalsAndRemoveDead() {
        LinkedList<Animal> listOfAnimals = new LinkedList<>();
        LinkedList<AbstractPositionedObject> listOfObjectsTmp = new LinkedList<>();
        listOfObjectsTmp.addAll(listOfObjects);
        for (AbstractPositionedObject object : listOfObjectsTmp) {
            if (object.getState() == ObjectStates.ANIMAL) {
                Animal animal = (Animal) object;
                if (animal.getEnergy() <= 0)
                    listOfObjects.remove(animal);
                else
                    listOfAnimals.add(animal);
            }
        }
        return listOfAnimals;
    }

    private Optional<Animal> findStrongestAnimal(){
        int maxEnergy = Integer.MIN_VALUE;
        Animal animalToReturn = null;
        for (AbstractPositionedObject object : listOfObjects) {
            if (object.getState() == ANIMAL){
                Animal animal = (Animal) object;
                if (animal.getEnergy() > maxEnergy){
                    animalToReturn = animal;
                    maxEnergy = animal.getEnergy();
                }
            }
        }
        return Optional.ofNullable(animalToReturn);
    }
    private Optional<Animal> findSecondStrongestAnimal(Animal firstStrongestAnimal){

        int maxEnergy = Integer.MIN_VALUE;
        Animal animalToReturn = null;
        for (AbstractPositionedObject object : listOfObjects) {
            if (object.getState() == ANIMAL){

                Animal animal = (Animal) object;
                if (animal.getEnergy() > maxEnergy && ! animal.equals(firstStrongestAnimal)){
                    animalToReturn = animal;
                    maxEnergy = animal.getEnergy();
                }
            }
        }
        return Optional.ofNullable(animalToReturn);
    }

    private boolean canPair(Animal first, Animal second){
        return first.getEnergy() >= START_ENERGY && second.getEnergy() >= START_ENERGY;
    }

    public Optional<FamilyGroup> pairAnimalsIfPossible(){
        FamilyGroup familyGroup = null;
        if (findStrongestAnimal().isPresent() ){
            Animal firstAnimal = findStrongestAnimal().get();
            if (findSecondStrongestAnimal(firstAnimal).isPresent()){
                Animal secondAnimal = findSecondStrongestAnimal(firstAnimal).get();
                if (canPair(firstAnimal,secondAnimal)){
                    familyGroup = new FamilyGroup(firstAnimal,secondAnimal);

                }
            }
        }
        return Optional.ofNullable(familyGroup);

    }

    @Override
    public String toString() {
        return "MapCell{" +
                "listOfObjects=" + listOfObjects +
                '}';
    }

    public void eatGrassByStrongestAnimal() {
        for (AbstractPositionedObject object : listOfObjects) {
            if (object.getState() == GRASS) {
                if (findStrongestAnimal().isPresent()) {
                    Animal animal = findStrongestAnimal().get();
                    animal.setEnergy(animal.getEnergy() + PLANT_ENERGY);
                    listOfObjects.remove(object);
                    return;
                }
            }
        }
    }

}
