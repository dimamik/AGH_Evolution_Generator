package logic.objects.grass;

import logic.objects.AbstractPositionedObject;
import logic.objects.ObjectStates;
import logic.position.Vector2d;

public class Grass extends AbstractPositionedObject {

    public Grass(Vector2d position) {
        super(position);
        this.state = ObjectStates.GRASS;
    }

}
