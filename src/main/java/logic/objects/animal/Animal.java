package logic.objects.animal;

import logic.maps.PositionChangedObserver;
import logic.objects.AbstractPositionedObject;
import logic.objects.ObjectStates;
import logic.objects.PositionChangedPublisher;
import logic.position.MapDirection;
import logic.position.Vector2d;
import logic.random.RandomGenerator;
import logic.statistics.AnimalStatistics;

import java.util.LinkedList;
import java.util.Objects;

import static config.Config.START_ENERGY;
import static logic.objects.animal.HealthState.*;

public class Animal extends AbstractPositionedObject implements PositionChangedPublisher {


    private final Gens gens;
    private final AnimalStatistics animalStatistics;
    private final LinkedList<PositionChangedObserver> listOfObservers;

    private int energy;
    private MapDirection orientation;

    public Animal(Vector2d position, int energy, Gens gens) {
        super(position);
        this.energy = energy;
        this.gens = gens;
        this.orientation = MapDirection.values()[RandomGenerator.getRandomNumberInRange(0, 7)];
        this.state = ObjectStates.ANIMAL;
        animalStatistics = new AnimalStatistics(this);
        listOfObservers = new LinkedList<>();
    }

    /**
     * Adds child to parent list
     *
     * @param child - child to add
     * @param day   - day when relationship started
     */
    public void addChild(Animal child, int day) {
        animalStatistics.addToChildrenList(child, day);
    }

    /**
     * Takes two parents and children and adds parents to child's list
     *
     * @param parent1 - first parent
     * @param parent2 - second parent
     * @param day     - day when relationship started
     */
    public void addToParents(Animal parent1, Animal parent2, int day) {
        animalStatistics.addToParentsList(parent1, parent2, day);
    }

    /**
     * Rotates animal and moves animal to randomly generated position
     */
    public void moveAnimal() {
        int genMove = gens.generateMove();
        orientation = orientation.rotateTo(genMove);
        Vector2d prevPosition = position;
        position = position.addMirrored(orientation.toUnitVector());
        updatePositionChangedObservers(prevPosition, position);
    }

    @Override
    public void addObserver(PositionChangedObserver observer) {
        listOfObservers.add(observer);
    }

    @Override
    public void removeObserver(PositionChangedObserver observer) {
        listOfObservers.remove(observer);
    }

    @Override
    public void updatePositionChangedObservers(Vector2d previousPosition, Vector2d newPosition) {
        for (PositionChangedObserver observer : listOfObservers) {
            observer.positionChanged(previousPosition, this);
        }
    }


    // Accessors and mutators
    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public LinkedList<FamilyMember> getChildren() {
        return animalStatistics.getChildrenAnimalList();
    }

    public Gens getGens() {
        return gens;
    }

    public HealthState getHealthState() {
        if (energy >= START_ENERGY) return GREEN;
        if (energy > 2) return YELLOW;
        else return RED;
    }


    public int getDominantGenome() {
        return gens.getDominantGenome();
    }

    public AnimalStatistics getAnimalStatistics() {
        return animalStatistics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return energy == animal.energy &&
                Objects.equals(gens, animal.gens) &&
                orientation == animal.orientation &&
                Objects.equals(animalStatistics, animal.animalStatistics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gens, energy, orientation, animalStatistics);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "orientation=" + orientation +
                ", gens=" + gens +
                ", energy=" + energy +
                '}';
    }
}
