import maps.RectangularMap;
import objects.animal.Animal;
import objects.animal.Gens;
import position.Vector2d;

public class Simulation {
    public static void main(String[] args) {
        RectangularMap rectangularMap = new RectangularMap(100,30,10,10);
        int [] genSeq = {0,0,0,0,0,0,0,0,1,1,2,2,2,2,2,2,3,3,4,4,4,4,4,4,5,5,6,6,7,7,7,7};
        Gens gens = new Gens(genSeq);
        Animal animal = new Animal(new Vector2d(1,1),5,gens,rectangularMap);
        rectangularMap.placeNewAnimal(animal);
        rectangularMap.moveAnimal(animal);
    }
}
