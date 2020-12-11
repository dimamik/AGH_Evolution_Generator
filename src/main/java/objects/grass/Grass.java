package objects.grass;

import objects.AbstractPositionedObject;
import objects.ObjectStates;
import position.Vector2d;

public class Grass extends AbstractPositionedObject {

    public Grass(Vector2d position) {
        super(position);
        this.state = ObjectStates.GRASS;
    }

}
