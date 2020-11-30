package objects;

import position.Vector2d;

public abstract class AbstractPositionedObject {

    /**
     * Position of Positioned object
     */
    protected Vector2d position;



    protected ObjectStates state;
    public AbstractPositionedObject(Vector2d position) {
        this.position = position;

    }

    public Vector2d getPosition() {
        return position;
    }

    public ObjectStates getState() {
        return state;
    }

    public void setState(ObjectStates state) {
        this.state = state;
    }

//    public boolean isPaired() {
//        return false;
//    }
//    protected boolean canMove = true;
//    public void setCanMove(boolean canMove) {
//        this.canMove = canMove;
//    }
//
//    public boolean isCanMove() {
//        return canMove;
//    }
//    public abstract boolean isAnimal();


}
