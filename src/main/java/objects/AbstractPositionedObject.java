package objects;

import position.Vector2d;

public abstract class AbstractPositionedObject {


    protected Vector2d position;
    protected ObjectStates state;

    public AbstractPositionedObject(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public ObjectStates getState() {
        return state;
    }
}
