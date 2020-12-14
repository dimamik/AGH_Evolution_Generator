package logic.statistics;

import logic.objects.animal.Animal;

import java.util.LinkedList;

public class MapStatisticsGetter {
    //TODO Rebuild to return properties
    MapStatistics mapStatistics;

    public MapStatisticsGetter(MapStatistics mapStatistics) {
        this.mapStatistics = mapStatistics;
    }

    private double roundValue(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public double getAverageEnergyForAliveAnimals() {
        return roundValue((double) mapStatistics.getSumEnergy() / mapStatistics.getAliveAnimalsCount());
    }

    public double getAverageKidsNumberForAliveAnimals() {
        return roundValue(mapStatistics.getSumChildOfAliveAnimal() / mapStatistics.getAliveAnimalsCount());
    }

    public double getAverageLiveDuration() {
        return roundValue((double) mapStatistics.getSumDaysDeadAnimalsLived() / mapStatistics.getSumAnimalsDead());
    }

    public double[] getAverageGenomeList() {
        double[] genomeList = new double[8];
        for (int i = 0; i < 8; i++) {
            genomeList[i] = roundValue((double) mapStatistics.getGenomeTypesSum()[i] / mapStatistics.getAliveAnimalsCount());
        }
        return genomeList;
    }

    public long getAnimalCount() {
        return mapStatistics.getAliveAnimalsCount();
    }

    public long getGrassCount() {
        return mapStatistics.getGrassCount();
    }

    public LinkedList<Animal> getAnimalsWithDominantGenome(LinkedList<Animal> listOfAnimals) {
        double[] averGenomeList = getAverageGenomeList();
        double max_size = Double.MIN_VALUE;
        int dominant = 0;
        for (int i = 0; i < averGenomeList.length; i++) {
            if (max_size < averGenomeList[i]) {
                max_size = averGenomeList[i];
                dominant = i;
            }
        }

        LinkedList<Animal> listToReturn = new LinkedList<>();
        for (Animal animal : listOfAnimals) {
            if (animal.getDominantGenome() == dominant) {
                listToReturn.add(animal);
            }
        }
        return listToReturn;
    }

}
