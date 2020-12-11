package simulation;

import objects.animal.Animal;
import objects.maps.RectangularMap;

import java.util.LinkedList;

public class Simulation {

    RectangularMap rectangularMap;

    public Simulation() {
        rectangularMap = new RectangularMap();
    }

    public void newDay() {
        rectangularMap.increaseDay();
        LinkedList<Animal> listOfAnimals = rectangularMap.deleteDead();
        rectangularMap.moveAllAnimals(listOfAnimals);
        rectangularMap.eatByAnimals();
        rectangularMap.pairAnimals();
        rectangularMap.subtractEnergy(listOfAnimals);
        rectangularMap.generateGrass();
    }
}
