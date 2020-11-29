package maps;

import objects.AbstractPositionedObject;
import objects.animal.Animal;
import objects.animal.PairedAnimals;
import position.Vector2d;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class RectangularMap {
    int width;
    int height;
    int jungleWidth;
    int jungleHeight;
    int currentDay = 0;
    protected final HashMap<Vector2d, AbstractPositionedObject> objectHashMap;


    public RectangularMap(int width, int height, int jungleWidth, int jungleHeight) {
        this.width = width;
        this.height = height;
        this.jungleWidth = jungleWidth;
        this.jungleHeight = jungleHeight;
        objectHashMap = new HashMap<>();

    }

    /**
     * Runs through All Animals on the map
     * In case of visiting family makes "family" iteration and moves to the next
     * In case of two animals meeting in one cell -> Making Family but in moveAnimal()
     */
    public void runAnimals() {
        LinkedList<AbstractPositionedObject> listOfObjects = new LinkedList<>(objectHashMap.values());
        Iterator<AbstractPositionedObject> objectIterator = listOfObjects.iterator();
        while (objectIterator.hasNext()) {
            AbstractPositionedObject currObject = objectIterator.next();
            if (currObject.isAnimal() && currObject.isPaired()) {
                PairedAnimals pairedAnimals = (PairedAnimals) currObject;
                pairedAnimals.movePairedWithChildren();
                if (pairedAnimals.anybodyLeft())
                    objectHashMap.remove(pairedAnimals.getPosition());
            } else if (currObject.isAnimal()) {
                Animal tmp = (Animal) currObject;
                moveAnimal(tmp);
            }
        }
    }

    /**
     * To validate if move by Animal to position is possible
     *
     * @return true if possible
     */
    public boolean isMovePossible(Vector2d position) {
        if (objectHashMap.containsKey(position)) {
            return !objectHashMap.get(position).isAnimal();
        }
        return true;
    }

    public void placeNewAnimal(Animal animal){
        objectHashMap.put(animal.getPosition(), animal);
    }

    public void putAnimalToHashMap(Animal animal){
        objectHashMap.put(animal.getPosition(), animal);
    }

    /**
     * Moves not Paired animals from some position to some other position
     * @param animal
     * @return
     */
    public boolean moveAnimal(Animal animal) {
        animal.setEnergy(animal.getEnergy()-1);
        Vector2d fromPosition = animal.getPosition();
        Vector2d toPosition = animal.generateNewPosition();
        if (isMovePossible(toPosition)) {
            animal.setPosition(toPosition);
            objectHashMap.remove(fromPosition);
            objectHashMap.put(toPosition, animal);
            return true;
        }
        if (!objectHashMap.get(toPosition).isPaired() && objectHashMap.get(toPosition).isAnimal() && canPair(animal, (Animal) objectHashMap.get(toPosition))) {
            animal.setPosition(toPosition);
            pairAnimals(animal, (Animal) objectHashMap.get(toPosition), toPosition);

        }
        return false;
    }

    private boolean canPair(Animal animal1, Animal animal2) {
        if (animal1.getEnergy() >= 4 && animal2.getEnergy() >= 4) {
            return true;
        }
        return false;
    }

    private void pairAnimals(Animal animal1, Animal animal2, Vector2d pairingPosition) {
        objectHashMap.remove(animal1.getPosition());
        objectHashMap.remove(animal2.getPosition());
        PairedAnimals pairedAnimals = new PairedAnimals(pairingPosition, animal1, animal2, this, currentDay);
        objectHashMap.put(pairingPosition,pairedAnimals);
    }


    /**
     * Returns whether a position on the map is occupied (Either by Animal/Paired Animal or Grass)
     *
     * @param position
     * @return
     */
    public boolean isOccupied(Vector2d position) {
        return objectHashMap.containsKey(position);
    }

    public void nextDay() {
        currentDay += 1;
        //TODO
    }
}
