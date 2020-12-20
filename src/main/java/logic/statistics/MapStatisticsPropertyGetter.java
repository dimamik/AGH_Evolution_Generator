package logic.statistics;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.objects.animal.Animal;

import java.util.LinkedList;

public class MapStatisticsPropertyGetter {
    private final LongProperty sumAnimalsAlive;
    private final LongProperty sumGrass;
    private final ListProperty<Double> averageGenomeTypesList;
    private final DoubleProperty averageEnergyForAliveAnimals;
    private final DoubleProperty averageLiveDurationForDeadAnimals;
    private final DoubleProperty averageKidsNumberForAliveAnimals;
    private MapStatistics mapStatistics;

    public MapStatisticsPropertyGetter(MapStatistics mapStatistics) {
        this.mapStatistics = mapStatistics;
        this.sumAnimalsAlive = new SimpleLongProperty(0);
        this.sumGrass = new SimpleLongProperty(0);

        this.averageEnergyForAliveAnimals = new SimpleDoubleProperty(0);
        this.averageKidsNumberForAliveAnimals = new SimpleDoubleProperty(0);
        this.averageLiveDurationForDeadAnimals = new SimpleDoubleProperty(0);
        this.averageGenomeTypesList = new SimpleListProperty<>(FXCollections.observableArrayList(createLinkedListFromArray(new double[]{0, 0, 0, 0, 0, 0, 0, 0})));
        addListeners();
    }

    public double getAverageEnergyForAliveAnimals() {
        return averageEnergyForAliveAnimals.get();
    }

    public void setAverageEnergyForAliveAnimals(double averageEnergyForAliveAnimals) {
        this.averageEnergyForAliveAnimals.set(averageEnergyForAliveAnimals);
    }

    public double getAverageKidsNumberForAliveAnimals() {
        return averageKidsNumberForAliveAnimals.get();
    }

    public void setAverageKidsNumberForAliveAnimals(double averageKidsNumberForAliveAnimals) {
        this.averageKidsNumberForAliveAnimals.set(averageKidsNumberForAliveAnimals);
    }

    public MapStatistics getMapStatistics() {
        return mapStatistics;
    }

    public void setMapStatistics(MapStatistics mapStatistics) {
        this.mapStatistics = mapStatistics;
    }

    public long getSumAnimalsAlive() {
        return sumAnimalsAlive.get();
    }

    public void setSumAnimalsAlive(long sumAnimalsAlive) {
        this.sumAnimalsAlive.set(sumAnimalsAlive);
    }

    public LongProperty sumAnimalsAliveProperty() {
        return sumAnimalsAlive;
    }

    public long getSumGrass() {
        return sumGrass.get();
    }

    public void setSumGrass(long sumGrass) {
        this.sumGrass.set(sumGrass);
    }

    public LongProperty sumGrassProperty() {
        return sumGrass;
    }

    public ObservableList<Double> getAverageGenomeTypesList() {
        return averageGenomeTypesList.get();
    }

    public void setAverageGenomeTypesList(ObservableList<Double> averageGenomeTypesList) {
        this.averageGenomeTypesList.set(averageGenomeTypesList);
    }

    public ListProperty<Double> averageGenomeTypesListProperty() {
        return averageGenomeTypesList;
    }

    public DoubleProperty averageEnergyForAliveAnimalsProperty() {
        return averageEnergyForAliveAnimals;
    }

    public double getAverageLiveDurationForDeadAnimals() {
        return averageLiveDurationForDeadAnimals.get();
    }

    public void setAverageLiveDurationForDeadAnimals(double averageLiveDurationForDeadAnimals) {
        this.averageLiveDurationForDeadAnimals.set(averageLiveDurationForDeadAnimals);
    }

    public DoubleProperty averageLiveDurationForDeadAnimalsProperty() {
        return averageLiveDurationForDeadAnimals;
    }

    public DoubleProperty averageKidsNumberForAliveAnimalsProperty() {
        return averageKidsNumberForAliveAnimals;
    }

    private LinkedList<Double> createLinkedListFromArray(double[] array) {
        LinkedList<Double> newList = new LinkedList<>();
        for (double el : array) {
            newList.add(el);
        }
        return newList;
    }

    private double roundValue(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private void addAverageEnergyListeners() {
        getMapStatistics().sumEnergyProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> setAverageEnergyForAliveAnimals(
                roundValue(
                        (double) getMapStatistics().getSumEnergy() / (double) getMapStatistics().getAliveAnimalsCount())
        ));

        getMapStatistics().aliveAnimalsCountProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> setAverageEnergyForAliveAnimals(
                roundValue(
                        (double) getMapStatistics().getSumEnergy() / (double) getMapStatistics().getAliveAnimalsCount())
        ));
    }

    private void addAverageKidsNumberListener() {
        getMapStatistics().sumChildOfAliveAnimalProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> setAverageKidsNumberForAliveAnimals(
                roundValue(
                        (double) getMapStatistics().getSumChildOfAliveAnimal() / (double) getMapStatistics().getAliveAnimalsCount())
        ));
        getMapStatistics().aliveAnimalsCountProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> setAverageKidsNumberForAliveAnimals(
                roundValue(
                        (double) getMapStatistics().getSumChildOfAliveAnimal() / (double) getMapStatistics().getAliveAnimalsCount())
        ));
    }

    private void addAverageLiveDurationListener() {
        getMapStatistics().sumDaysDeadAnimalsLivedProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> setAverageLiveDurationForDeadAnimals(
                roundValue(
                        (double) getMapStatistics().getSumDaysDeadAnimalsLived() / (double) getMapStatistics().getSumAnimalsDead())
        ));
        getMapStatistics().sumAnimalsDeadProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> setAverageLiveDurationForDeadAnimals(
                roundValue(
                        (double) getMapStatistics().getSumDaysDeadAnimalsLived() / (double) getMapStatistics().getSumAnimalsDead())
        ));
    }

    private void addGenomesTypeListener() {
        getMapStatistics().genomeTypesSumProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<Double> list = getMapStatistics().getGenomeTypesSum();
            LinkedList<Double> tmp = new LinkedList<>();
            for (Double doubleValue : list) {

                tmp.add(roundValue(doubleValue / (double) getMapStatistics().getAliveAnimalsCount()));
            }
            ObservableList<Double> observableList = FXCollections.observableList(tmp);
            setAverageGenomeTypesList(observableList);

        });
        getMapStatistics().aliveAnimalsCountProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<Double> list = getMapStatistics().getGenomeTypesSum();
            LinkedList<Double> tmp = new LinkedList<>();
            for (Double doubleValue : list) {

                tmp.add(roundValue(doubleValue / (double) getMapStatistics().getAliveAnimalsCount()));
            }
            ObservableList<Double> observableList = FXCollections.observableList(tmp);
            setAverageGenomeTypesList(observableList);
        });
    }

    private void addListeners() {
        sumAnimalsAliveProperty().bind(getMapStatistics().aliveAnimalsCountProperty());
        sumGrassProperty().bind(getMapStatistics().grassCountProperty());
        addAverageEnergyListeners();
        addAverageKidsNumberListener();
        addAverageLiveDurationListener();
        addGenomesTypeListener();
    }


    public double[] getAverageGenomeList() {
        double[] genomeList = new double[8];
        for (int i = 0; i < 8; i++) {
            genomeList[i] = roundValue(mapStatistics.getGenomeTypesSum().get(i) / mapStatistics.getAliveAnimalsCount());
        }
        return genomeList;
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
