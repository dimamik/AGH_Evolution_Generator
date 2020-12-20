package logic.simulation;

import logic.objects.animal.Animal;
import logic.statistics.MapStatistics;
import visual.map.cells.cellsViewModel.CellsWrapper;

import java.util.LinkedList;

public class UniverseSimulation {


    final LinkedList<MapSimulation> listOfMapSimulations;

    public UniverseSimulation() {
        listOfMapSimulations = new LinkedList<>();
    }

    public void addNewMap() {
        listOfMapSimulations.add(new MapSimulation());
    }

    public void removeLastMap() {
        listOfMapSimulations.pollLast();
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

    public void newDayInNthSimulation(int n) {
        n -= 1;
        if (listOfMapSimulations.size() <= n) throw new IllegalArgumentException("No such listOfMapSimulations");
        newDay(listOfMapSimulations.get(n));
    }


    public LinkedList<MapStatistics> getMapStatistics() {
        LinkedList<MapStatistics> listToRet = new LinkedList<>();
        for (MapSimulation mapSimulation : listOfMapSimulations) {
            listToRet.add(mapSimulation.getMapStatistics());
        }
        return listToRet;
    }

    public LinkedList<MapSimulation> getMapSimulations() {
        return listOfMapSimulations;
    }

    public void addNewViewObserver(MapSimulation mapSimulation, CellsWrapper cellsWrapper) {
        mapSimulation.addNewViewObserver(cellsWrapper);
    }

}
