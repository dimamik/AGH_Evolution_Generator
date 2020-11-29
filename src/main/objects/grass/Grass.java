package objects.grass;

import objects.AbstractPositionedObject;
import position.Vector2d;

public class Grass extends AbstractPositionedObject {

    int energy;

    public Grass(Vector2d position) {
        super(position);
        this.isAnimal = false;
    }
}
