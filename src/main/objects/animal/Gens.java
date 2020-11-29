package objects.animal;

import java.util.LinkedList;
import java.util.SplittableRandom;
import java.util.function.Supplier;
public class Gens {

    int [] genSequence;
    int [] weightsDirections;



    public Gens(int[] genSequence) {
        this.genSequence = genSequence;
    }

    public int generateMove(){
        return genSequence[(int) (Math.random() * (8 + 1))];
    }

}
