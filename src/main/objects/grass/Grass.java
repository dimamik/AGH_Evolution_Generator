package objects.grass;

import objects.AbstractPositionedObject;
import position.Vector2d;

public class Grass extends AbstractPositionedObject {


    int energy;

    public Grass(Vector2d position, int energy) {
        super(position);
        this.energy = energy;
    }

    @Override
    public boolean isAnimal() {
        return false;
    }

    @Override
    public String toString() {
        return "*" ;
    }
    public int getEnergy() {
        return energy;
    }

}
