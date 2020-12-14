package logic.statistics;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import logic.objects.animal.Animal;
import logic.simulation.MapSimulation;

import static config.Config.MOVE_ENERGY;


public class MapStatistics {

    private final long[] genomeTypesSum;
    private final MapSimulation mapSimulation;
    private final LongProperty aliveAnimalsCount;
    private final LongProperty grassCount;
    private final LongProperty sumEnergy;
    private final LongProperty sumDaysDeadAnimalsLived;
    private final LongProperty sumAnimalsDead;
    private final LongProperty sumChildOfAliveAnimal;

    public MapStatistics(MapSimulation mapSimulation) {
        //TODO Add to handle genomeTypesSumProperty
        genomeTypesSum = new long[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        sumEnergy = new SimpleLongProperty(0);
        grassCount = new SimpleLongProperty(0);
        this.mapSimulation = mapSimulation;
        aliveAnimalsCount = new SimpleLongProperty(0);
        sumDaysDeadAnimalsLived = new SimpleLongProperty(0);
        sumAnimalsDead = new SimpleLongProperty(0);
        sumChildOfAliveAnimal = new SimpleLongProperty(0);
    }

    public long[] getGenomeTypesSum() {
        return genomeTypesSum;
    }

    public MapSimulation getMapSimulation() {
        return mapSimulation;
    }

    public long getAliveAnimalsCount() {
        return aliveAnimalsCount.get();
    }

    public void setAliveAnimalsCount(long aliveAnimalsCount) {
        this.aliveAnimalsCount.set(aliveAnimalsCount);
    }

    public LongProperty aliveAnimalsCountProperty() {
        return aliveAnimalsCount;
    }

    public LongProperty grassCountProperty() {
        return grassCount;
    }

    public long getSumEnergy() {
        return sumEnergy.get();
    }

    public void setSumEnergy(long sumEnergy) {
        this.sumEnergy.set(sumEnergy);
    }

    public LongProperty sumEnergyProperty() {
        return sumEnergy;
    }

    public long getSumDaysDeadAnimalsLived() {
        return sumDaysDeadAnimalsLived.get();
    }

    public void setSumDaysDeadAnimalsLived(long sumDaysDeadAnimalsLived) {
        this.sumDaysDeadAnimalsLived.set(sumDaysDeadAnimalsLived);
    }

    public LongProperty sumDaysDeadAnimalsLivedProperty() {
        return sumDaysDeadAnimalsLived;
    }

    public long getSumAnimalsDead() {
        return sumAnimalsDead.get();
    }

    public void setSumAnimalsDead(long sumAnimalsDead) {
        this.sumAnimalsDead.set(sumAnimalsDead);
    }

    public LongProperty sumAnimalsDeadProperty() {
        return sumAnimalsDead;
    }

    public long getSumChildOfAliveAnimal() {
        return sumChildOfAliveAnimal.get();
    }

    public void setSumChildOfAliveAnimal(long sumChildOfAliveAnimal) {
        this.sumChildOfAliveAnimal.set(sumChildOfAliveAnimal);
    }

    public LongProperty sumChildOfAliveAnimalProperty() {
        return sumChildOfAliveAnimal;
    }

    public void addNewChildToStatistics() {

        setSumChildOfAliveAnimal(getSumChildOfAliveAnimal() + 1);

    }

    private void increaseAnimalCount() {
        setAliveAnimalsCount(getAliveAnimalsCount() + 1);
    }

    private void increaseGrassCount() {
        setGrassCount(getGrassCount() + 1);

    }

    private void decreaseAnimalCount() {
        setAliveAnimalsCount(getAliveAnimalsCount() - 1);

    }

    private void decreaseGrassCount() {
        setGrassCount(getGrassCount() - 1);
    }

    public void decreaseSumEnergyByDayPrice() {
        setSumEnergy(getSumEnergy() - (getAliveAnimalsCount() * MOVE_ENERGY));
        if (getSumEnergy() < 0)
            setSumEnergy(0);
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
        setSumEnergy(getSumEnergy() + toAdd);
    }

    private void removeEnergy(int toRemove) {
        setSumEnergy(getSumEnergy() - toRemove);
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
        setSumAnimalsDead(getSumAnimalsDead() + 1);
        decreaseAnimalCount();
        removeEnergy(animal.getEnergy());
        removeGenomeType(animal);

        setSumDaysDeadAnimalsLived(getSumDaysDeadAnimalsLived() + mapSimulation.getDayInMapSimulation());
        setSumChildOfAliveAnimal((long) (getSumChildOfAliveAnimal() - (0.5 * (animal.getChildren().size()))));
    }

    public void addGrassToStatistics() {
        increaseGrassCount();
    }

    public void removeGrassFromStatistics() {
        decreaseGrassCount();
    }

    public long getGrassCount() {
        return grassCount.get();
    }

    public void setGrassCount(long grassCount) {
        this.grassCount.set(grassCount);
    }
}
