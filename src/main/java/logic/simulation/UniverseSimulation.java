package logic.simulation;

import logic.objects.animal.Animal;

import java.util.LinkedList;

public class UniverseSimulation {


    LinkedList<MapSimulation> listOfMapSimulations;

    public UniverseSimulation() {

        listOfMapSimulations = new LinkedList<>();
    }

    public void addNewMap() {
        listOfMapSimulations.add(new MapSimulation());
    }

    private void newDay(MapSimulation mapSimulation) {
        mapSimulation.increaseDay();
        LinkedList<Animal> listOfAnimals = mapSimulation.deleteDead();
        mapSimulation.moveAllAnimals(listOfAnimals);
        mapSimulation.eatByAnimals();
        mapSimulation.pairAnimals();
        mapSimulation.subtractEnergy(listOfAnimals);
        mapSimulation.generateGrass();
    }

    public void newDayInAllSimulations() {
        for (MapSimulation mapSimulation : listOfMapSimulations) {
            newDay(mapSimulation);
        }
    }
}
