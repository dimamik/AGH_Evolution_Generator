package objects.animal;

import org.junit.jupiter.api.Test;
import position.Vector2d;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void moveAnimal() {
        int[] genSeq = {0, 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 5, 5, 6, 6, 7, 7, 7, 7};
        int[] genSeq2 = {0, 0, 7, 1, 1, 7, 2, 0, 2, 0, 2, 3, 7, 7, 7, 4, 4, 4, 4, 0, 7, 0, 7, 0, 5, 5, 6, 6, 7, 7, 7, 7};
        Animal animal = new Animal(new Vector2d(1,1),10,new Gens(genSeq));

        animal.moveAnimal();
        animal.moveAnimal();
        animal.moveAnimal();
        animal.moveAnimal();
        assertNotEquals(new Vector2d(1, 1), animal.getPosition());
    }
}