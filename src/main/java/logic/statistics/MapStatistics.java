package logic.statistics;

import logic.objects.animal.Animal;
import logic.simulation.MapSimulation;

import static config.Config.MOVE_ENERGY;


public class MapStatistics {

    private final long[] genomeTypesSum;
    private final MapSimulation mapSimulation;
    private long aliveAnimalsCount;
    private long grassCount;
    private long sumEnergy;
    private long sumDaysDeadAnimalsLived;
    private long sumAnimalsDead;
    private double sumChildOfAliveAnimal;

    public MapStatistics(MapSimulation mapSimulation) {
        genomeTypesSum = new long[8];
        sumEnergy = 0;
        this.mapSimulation = mapSimulation;
        sumDaysDeadAnimalsLived = 0;
        sumAnimalsDead = 0;
        sumChildOfAliveAnimal = 0;
    }

    public long[] getGenomeTypesSum() {
        return genomeTypesSum;
    }

    public long getAliveAnimalsCount() {
        return aliveAnimalsCount;
    }

    public long getSumEnergy() {
        return sumEnergy;
    }

    public long getSumDaysDeadAnimalsLived() {
        return sumDaysDeadAnimalsLived;
    }

    public long getSumAnimalsDead() {
        return sumAnimalsDead;
    }

    public double getSumChildOfAliveAnimal() {
        return sumChildOfAliveAnimal;
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
        sumEnergy -= (aliveAnimalsCount * MOVE_ENERGY);
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
        sumDaysDeadAnimalsLived += mapSimulation.getDayInMapSimulation();
        sumChildOfAliveAnimal -= 0.5 * (animal.getChildren().size());
    }

    public void addGrassToStatistics() {
        increaseGrassCount();
    }

    public void removeGrassFromStatistics() {
        decreaseGrassCount();
    }


    public long getGrassCount() {
        return grassCount;
    }

}
