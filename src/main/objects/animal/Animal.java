package objects.animal;

import maps.RectangularMap;
import movements.Direction;
import objects.AbstractPositionedObject;
import position.Vector2d;

import java.util.LinkedList;

public class Animal extends AbstractPositionedObject {


    private int energy;
    private Gens gens;


    private RectangularMap map;

    private LinkedList<FamilyMember> childrenAnimalList;
    private LinkedList<FamilyMember> parentsAnimalList;

    public Animal(Vector2d position, int energy, Gens gens, RectangularMap map) {
        super(position);
        this.energy = energy;
        this.gens = gens;

        this.map = map;
        this.isAnimal = true;

        childrenAnimalList = new LinkedList<>();
        parentsAnimalList = new LinkedList<>();
    }


    public void addToChildrenList(Animal child, int day) {
        childrenAnimalList.add(new FamilyMember(child, day));
    }

    public void addToParentsList(Animal parent1, Animal parent2, int day) {
        parentsAnimalList.add(new FamilyMember(parent1, day));
        parentsAnimalList.add(new FamilyMember(parent2, day));
    }

    public Vector2d generateNewPosition() {
        return position.nextByNumber(gens.generateMove());
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

}
