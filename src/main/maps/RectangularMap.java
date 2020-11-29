package maps;

import objects.AbstractPositionedObject;
import objects.animal.Animal;
import objects.animal.PairedAnimals;
import objects.grass.Grass;
import position.Vector2d;
import visual.MapVisualiser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;

import static objects.random.RandomGenerator.getRandomNumberInRange;
import static objects.random.RandomGenerator.getRandomPosition;

public class RectangularMap {
    public int MIN_GRASS_ENERGY = 0;
    public int MAX_GRASS_ENERGY = 1;


    int width;
    int height;
    int jungleWidth;
    int jungleHeight;
    int currentDay = 0;
    private final HashMap<Vector2d, AbstractPositionedObject> objectHashMap;
    MapVisualiser mapVisualiser;

    /**
     * Width and Height are getting restructured and now starting from 0
     *
     * @param width        Width of the map from 1
     * @param height       height of the map from 1
     * @param jungleWidth  - width of jungle area where plants grow faster
     * @param jungleHeight - height of jungle area where plants grow faster
     */
    public RectangularMap(int width, int height, int jungleWidth, int jungleHeight) {
        this.width = width - 1;
        this.height = height - 1;
        this.jungleWidth = jungleWidth;
        this.jungleHeight = jungleHeight;
        objectHashMap = new HashMap<>();
        mapVisualiser = new MapVisualiser(this);

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
            } else if (currObject.isAnimal() && currObject.isCanMove()) {
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


    public void putAnimalToHashMap(Animal animal) {
        objectHashMap.put(animal.getPosition(), animal);
    }

    /**
     * Moves not Paired animals from some position to some other position
     */
    public boolean moveAnimal(Animal animal) {
        if (animal.getEnergy() <= 0) {
            objectHashMap.remove(animal.getPosition());
            System.out.println("REMOVED ANIMAL");
            return false;
        }
        animal.setEnergy(animal.getEnergy() - 1);
        Vector2d fromPosition = animal.getPosition();
        Vector2d toPosition = animal.generateNewPosition();

        if (isMovePossible(toPosition)) {
            animal.setPosition(toPosition);
            if (objectHashMap.get(fromPosition) != null
                    && !objectHashMap.get(fromPosition).isPaired())
                objectHashMap.remove(fromPosition);
            if (objectHashMap.containsKey(toPosition)
                    && !objectHashMap.get(toPosition).isPaired()
                    && !objectHashMap.get(toPosition).isAnimal()) {
                Grass grass = (Grass) objectHashMap.get(toPosition);
                int amountOfEnergy = grass.getEnergy();
                animal.setEnergy(animal.getEnergy() + amountOfEnergy);
                objectHashMap.remove(toPosition);
            }

            objectHashMap.put(toPosition, animal);
            return true;
        } else if (!objectHashMap.get(toPosition).isPaired()
                && objectHashMap.get(toPosition).isAnimal()
                && canPair(animal, (Animal) objectHashMap.get(toPosition))) {
            pairAnimals(animal, (Animal) objectHashMap.get(toPosition), toPosition, fromPosition);

            return true;
        }
        return false;
    }

    private boolean canPair(Animal animal1, Animal animal2) {
        return animal1.getEnergy() >= 4 && animal2.getEnergy() >= 4;
    }

    private void pairAnimals(Animal animal1, Animal animal2,
                             Vector2d pairingPosition,
                             Vector2d prevPosition) {
        objectHashMap.remove(pairingPosition);
        objectHashMap.remove(prevPosition);
        animal1.setPosition(pairingPosition);
        animal1.setCanMove(false);
        animal2.setCanMove(false);
        PairedAnimals pairedAnimals = new PairedAnimals(pairingPosition, animal1, animal2, this, currentDay);
        objectHashMap.put(pairingPosition, pairedAnimals);
    }


    public int getObjectHashMapSize() {
        return objectHashMap.size();
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


    public Optional<Object> objectAt(Vector2d position) {
        return Optional.ofNullable(objectHashMap.get(position));
    }

    public void nextDay() {
        currentDay += 1;
        generateGrass();
        runAnimals();
        //TODO
    }

    public boolean isMapFull() {
        return objectHashMap.size() >= (width * height);
    }

    public boolean isJungleFull() {
        int count = 0;
        LinkedList<AbstractPositionedObject> listOfObjects = new LinkedList<>(objectHashMap.values());
        Iterator<AbstractPositionedObject> objectIterator = listOfObjects.iterator();
        while (objectIterator.hasNext()) {
            AbstractPositionedObject currObject = objectIterator.next();
            if (currObject.getPosition().isInsideTheJungle(width, height, jungleWidth, jungleHeight)) {
                count++;
            }
        }
        return count >= jungleWidth * jungleHeight;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public void generateGrass() {
        if (!isMapFull()) {
            Vector2d first_grass = getRandomPosition(width, height);
            while (!isMapFull() && first_grass.isInsideTheJungle(width, height, jungleWidth, jungleHeight) || objectHashMap.containsKey(first_grass)) {
                first_grass = getRandomPosition(width, height);
            }
            if (!isMapFull())
                objectHashMap.put(first_grass, new Grass(first_grass, getRandomNumberInRange(MIN_GRASS_ENERGY, MAX_GRASS_ENERGY)));
        }

        if (!isJungleFull()) {
            Vector2d second_grass = getRandomPosition(width, height);
            while (!isJungleFull() && !second_grass.isInsideTheJungle(width, height, jungleWidth, jungleHeight) || objectHashMap.containsKey(second_grass)) {
                second_grass = getRandomPosition(width, height);
            }
            if (!isJungleFull())
                objectHashMap.put(second_grass, new Grass(second_grass, getRandomNumberInRange(MIN_GRASS_ENERGY, MAX_GRASS_ENERGY)));
        }
    }

    @Override
    public String toString() {
        return mapVisualiser.draw(new Vector2d(0, 0), new Vector2d(width, height));
    }

    public HashMap<Vector2d, AbstractPositionedObject> getObjectHashMap() {
        //TODO Delete
        return objectHashMap;
    }
}

