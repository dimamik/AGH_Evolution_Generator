package statistics;

import objects.animal.Animal;
import objects.maps.RectangularMap;

import static config.Config.ANIMALS_ON_START;


public class Statistics {


    private long aliveAnimalsCount;
    private long grassCount;
    private final long[] genomeTypesSum;
    private long sumEnergy;
    private long sumDaysDeadAnimalsLived;
    private final RectangularMap rectangularMap;
    private long sumAnimalsDead;
    private double sumChildOfAliveAnimal;

    public Statistics(RectangularMap rectangularMap) {
        genomeTypesSum = new long[8];
        sumEnergy = 0;
        this.rectangularMap = rectangularMap;
        sumDaysDeadAnimalsLived = 0;
        sumAnimalsDead = 0;
        sumChildOfAliveAnimal = ANIMALS_ON_START;
    }

    public void addNewChildToStatistics() {
        sumChildOfAliveAnimal += 1;
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

    private void addEnergy(int toAdd) {
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
        sumChildOfAliveAnimal -= 0.5 * (animal.getChildrenAnimalList().size());
    }

    public void addGrassToStatistics() {
        increaseGrassCount();
    }

    public void removeGrassFromStatistics() {
        decreaseGrassCount();
    }

    //Averages

    public double getAverageEnergyForAliveAnimals() {
        return (double) sumEnergy / aliveAnimalsCount;
    }

    public double getAverageKidsNumberForAliveAnimals() {
        return sumChildOfAliveAnimal / aliveAnimalsCount;
    }

    public double getAverageLiveDuration() {
        return (double) sumDaysDeadAnimalsLived / sumAnimalsDead;
    }

    public double[] getAverageGenomeList() {
        double[] genomeList = new double[8];
        for (int i = 0; i < 8; i++) {
            genomeList[i] = (double) genomeTypesSum[i] / aliveAnimalsCount;
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
