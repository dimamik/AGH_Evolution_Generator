package objects.grass;

import objects.AbstractPositionedObject;
import objects.ObjectStates;
import position.Vector2d;

public class Grass extends AbstractPositionedObject {


    int energy;

    public Grass(Vector2d position, int energy) {
        super(position);
        this.energy = energy;
        this.state = ObjectStates.GRASS;
    }

    @Override
    public String toString() {
        return "*" ;
    }
    public int getEnergy() {
        return energy;
    }

}
