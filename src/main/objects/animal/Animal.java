package objects.animal;

import maps.RectangularMap;
import objects.AbstractPositionedObject;
import position.Vector2d;

import java.util.LinkedList;
import java.util.Objects;

public class Animal extends AbstractPositionedObject {


    private int energy;
    private final Gens gens;




    private final RectangularMap map;

    private final LinkedList<FamilyMember> childrenAnimalList;
    private final LinkedList<FamilyMember> parentsAnimalList;

    @Override
    public String toString() {
        return "A";
    }

    public Animal(Vector2d position, int energy, Gens gens, RectangularMap map) {
        super(position);
        this.energy = energy;
        this.gens = gens;

        this.map = map;


        childrenAnimalList = new LinkedList<>();
        parentsAnimalList = new LinkedList<>();
    }
    @Override
    public boolean isAnimal() {
        return true;
    }




    public void addToChildrenList(Animal child, int day) {
        childrenAnimalList.add(new FamilyMember(child, day));
    }

    public void addToParentsList(Animal parent1, Animal parent2, int day) {
        parentsAnimalList.add(new FamilyMember(parent1, day));
        parentsAnimalList.add(new FamilyMember(parent2, day));
    }

    /**
     * Generates new position,
     * In case of mirroring situation Animal gets mirrored
     * @return new position
     */
    public Vector2d generateNewPosition() {
        return position.nextByNumber(gens.generateMove())
                .mirrorVectorIfOut(map.getWidth(),map.getHeight());
    }


    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }
    public Gens getGens() {
        return gens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(gens, animal.gens) &&
                Objects.equals(childrenAnimalList, animal.childrenAnimalList) &&
                Objects.equals(parentsAnimalList, animal.parentsAnimalList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gens, childrenAnimalList, parentsAnimalList);
    }
}
