package objects.animal;

import maps.RectangularMap;
import objects.AbstractPositionedObject;
import position.Vector2d;

public class PairedAnimals extends AbstractPositionedObject {

    Animal firstAnimal;
    Animal secondAnimal;
    Animal childAnimal;
    Gens childGens;
    RectangularMap map;
    int dayOfPairing;
    /**
     * Position
     * @param position - Initial position of Parents, than to be used as a checker
     * @param firstAnimal
     * @param secondAnimal
     */
    public PairedAnimals(Vector2d position, Animal firstAnimal, Animal secondAnimal, RectangularMap map, int dayOfPairing) {
        super(position);
        this.firstAnimal = firstAnimal;
        this.secondAnimal = secondAnimal;
        this.map = map;
        this.dayOfPairing = dayOfPairing;

        this.childAnimal = generateAnimal();

    }

    private Gens calculateGens(){
        //TODO
        return null;
    }

    private Animal generateAnimal() {
        this.childGens = calculateGens();
        Animal tmpAnimal = new Animal(position, (int) (firstAnimal.getEnergy()/4 + secondAnimal.getEnergy()/4),childGens,map);
        //TODO To write down to family history
        firstAnimal.addToChildrenList(tmpAnimal,dayOfPairing);
        secondAnimal.addToChildrenList(tmpAnimal,dayOfPairing);
        tmpAnimal.addToParentsList(firstAnimal,secondAnimal,dayOfPairing);
        return tmpAnimal;

    }

    @Override
    public boolean isPaired() {
        return true;
    }

    @Override
    public boolean isAnimal() {
        return true;
    }

    /**
     * Shows if it is time to remove this cell
     * @return
     */
    public boolean anybodyLeft(){
        if (! firstAnimal.getPosition().equals(position)
        && ! secondAnimal.getPosition().equals(position)
        && ! childAnimal.getPosition().equals(position))
            return false;
        return true;
    }

    public void movePairedWithChildren(){

        if (firstAnimal.getPosition().equals(position)){
            if (map.moveAnimal(firstAnimal))
                map.putAnimalToHashMap(firstAnimal);

        }
        if (secondAnimal.getPosition().equals(position)){
            if (map.moveAnimal(secondAnimal))
                map.putAnimalToHashMap(secondAnimal);
        }
        if (childAnimal.getPosition().equals(position)){
            if (map.moveAnimal(childAnimal))
                map.putAnimalToHashMap(childAnimal);
        }

    }

    public PairedAnimals(Vector2d position) {
        super(position);
    }
}
