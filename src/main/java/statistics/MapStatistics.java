package statistics;

import maps.RectangularMap;
import objects.animal.Animal;


public class MapStatistics {

    private final long[] genomeTypesSum;
    private final RectangularMap rectangularMap;
    private long aliveAnimalsCount;
    private long grassCount;
    private long sumEnergy;
    private long sumDaysDeadAnimalsLived;
    private long sumAnimalsDead;
    private double sumChildOfAliveAnimal;

    public MapStatistics(RectangularMap rectangularMap) {
        genomeTypesSum = new long[8];
        sumEnergy = 0;
        this.rectangularMap = rectangularMap;
        sumDaysDeadAnimalsLived = 0;
        sumAnimalsDead = 0;
        sumChildOfAliveAnimal = 0;
    }

    public void addNewChildToStatistics() {
        sumChildOfAliveAnimal += 1;
    }

    private double roundValue(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private void increaseAnimalCount() {
        aliveAnimalsCount += 1;
    }

    private void increaseGrassCount() {
        grassCount += 1;
    }

    private void decreaseAnimalCount() {
        aliveAnimalsCount -= 1;
    }

    private void decreaseGrassCount() {
        grassCount -= 1;
    }

    public void decreaseSumEnergyByDayPrice() {
        sumEnergy -= aliveAnimalsCount;
        if (sumEnergy < 0)
            sumEnergy = 0;
    }

    private void addGenomeType(Animal animal) {
        int[] types = animal.getGens().GetGenomeTypes();
        for (int i = 0; i < 8; i++) {
            genomeTypesSum[i] += types[i];
        }
    }

    private void removeGenomeType(Animal animal) {
        int[] types = animal.getGens().GetGenomeTypes();
        for (int i = 0; i < 8; i++) {
            genomeTypesSum[i] -= types[i];
        }
    }

    public void addEnergy(int toAdd) {
        sumEnergy += toAdd;
    }

    private void removeEnergy(int toRemove) {
        sumEnergy -= toRemove;
    }

    public void addAnimalToStatistics(Animal animal) {
        increaseAnimalCount();
        addGenomeType(animal);
        addEnergy(animal.getEnergy());
    }

    public void removeAnimalFromStatistics(Animal animal) {
        decreaseAnimalCount();
        removeGenomeType(animal);
        removeEnergy(animal.getEnergy());

    }

    public void removeAnimalForever(Animal animal) {
        sumAnimalsDead += 1;
        decreaseAnimalCount();
        removeEnergy(animal.getEnergy());
        removeGenomeType(animal);
        sumDaysDeadAnimalsLived += rectangularMap.getCurrentDay();
        sumChildOfAliveAnimal -= 0.5 * (animal.getChildren().size());
    }

    public void addGrassToStatistics() {
        increaseGrassCount();
    }

    public void removeGrassFromStatistics() {
        decreaseGrassCount();
    }

    //Averages

    public double getAverageEnergyForAliveAnimals() {
        return roundValue((double) sumEnergy / aliveAnimalsCount);
    }

    public double getAverageKidsNumberForAliveAnimals() {
        return roundValue(sumChildOfAliveAnimal / aliveAnimalsCount);
    }

    public double getAverageLiveDuration() {
        return roundValue((double) sumDaysDeadAnimalsLived / sumAnimalsDead);
    }

    public double[] getAverageGenomeList() {
        double[] genomeList = new double[8];
        for (int i = 0; i < 8; i++) {
            genomeList[i] = roundValue((double) genomeTypesSum[i] / aliveAnimalsCount);
        }
        return genomeList;
    }

    public long getAnimalCount() {
        return aliveAnimalsCount;
    }

    public long getGrassCount() {
        return grassCount;
    }

}
