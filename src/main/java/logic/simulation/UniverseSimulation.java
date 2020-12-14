package logic.simulation;

import logic.objects.animal.Animal;
import logic.statistics.MapStatistics;

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

    public LinkedList<MapStatistics> getMapStatistics() {
        LinkedList<MapStatistics> listToRet = new LinkedList<>();
        for (MapSimulation mapSimulation : listOfMapSimulations) {
            listToRet.add(mapSimulation.getMapStatistics());
        }
        return listToRet;
    }
}
