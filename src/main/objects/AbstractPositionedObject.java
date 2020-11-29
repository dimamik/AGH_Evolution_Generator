package objects;

import position.Vector2d;

public abstract class AbstractPositionedObject {
    public Vector2d getPosition() {
        return position;
    }

    /**
     * Position of Positioned object
     */
    protected Vector2d position;
    protected  boolean isAnimal;
    protected boolean isPaired;

    public boolean isPaired() {
        return false;
    }

    public boolean isAnimal() {
        return isAnimal;
    }



    public AbstractPositionedObject(Vector2d position) {
        this.position = position;
    }
}
