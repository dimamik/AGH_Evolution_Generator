package objects.maps;

import objects.animal.Animal;
import objects.animal.Gens;
import objects.grass.Grass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import position.Vector2d;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {

    static RectangularMap rectangularMap;
    static int[] genSeq = {0, 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 5, 5, 6, 6, 7, 7, 7, 7};
    static int[] genSeq2 = {0, 0, 7, 1, 1, 7, 2, 0, 2, 0, 2, 3, 7, 7, 7, 4, 4, 4, 4, 0, 7, 0, 7, 0, 5, 5, 6, 6, 7, 7, 7, 7};
    static Animal animal1;
    static Animal animal2;
    static Animal animal3;
    static Animal animal4;
    static Animal animal5;
    static Animal animal6;
    static Animal animal7;

    @BeforeAll
    public static void setup() {
        rectangularMap = new RectangularMap();
        animal1 = new Animal(new Vector2d(1, 1), 10, new Gens(genSeq));
        animal2 = new Animal(new Vector2d(1, 1), 20, new Gens(genSeq));
        animal3 = new Animal(new Vector2d(2, 2), 20, new Gens(genSeq2));
        animal4 = new Animal(new Vector2d(2, 2), 20, new Gens(genSeq2));
        animal5 = new Animal(new Vector2d(2, 2), 0, new Gens(genSeq2));
        animal6 = new Animal(new Vector2d(2, 2), 0, new Gens(genSeq2));
        animal7 = new Animal(new Vector2d(1, 1), 0, new Gens(genSeq2));
    }

    @Test
    void UnitTestOfObjectManipulation() {

        rectangularMap.addObject(animal1);
        rectangularMap.addObject(animal2);
        rectangularMap.removeObject(animal1);
        rectangularMap.removeObject(animal2);
        assertFalse(rectangularMap.isOccupied(new Vector2d(1, 1)));
        assertFalse(rectangularMap.isOccupied(new Vector2d(3, 3)));
        rectangularMap.addObject(animal3);
        rectangularMap.addObject(new Grass(new Vector2d(2, 2)));
        assertTrue(rectangularMap.isOccupied(new Vector2d(2, 2)));
        rectangularMap.addObject(animal1);
        rectangularMap.addObject(animal2);
    }

    /**
     * Can not work because of random xD -> Too hard to make some random ld
     */
    @Test
    void moveAnimal() {

        rectangularMap.addObject(animal1);
        rectangularMap.addObject(animal2);
        rectangularMap.addObject(animal3);
        rectangularMap.addObject(animal4);
        rectangularMap.addObject(animal5);
        rectangularMap.addObject(animal6);
        rectangularMap.addObject(animal7);
        Vector2d prev_position = animal1.getPosition();
        rectangularMap.moveAnimal(animal1);
        rectangularMap.moveAnimal(animal2);
        rectangularMap.moveAnimal(animal3);
        rectangularMap.moveAnimal(animal4);
        rectangularMap.moveAnimal(animal5);
        rectangularMap.moveAnimal(animal6);
        rectangularMap.moveAnimal(animal7);
        assertNotEquals(animal1.getPosition(), prev_position);
    }


    @Test
    void deleteDead() {
        rectangularMap.addObject(animal4);
        rectangularMap.addObject(animal5);
        rectangularMap.addObject(animal6);

        LinkedList<Animal> linkedList = rectangularMap.deleteDead();
        assertEquals(linkedList.size(), 1);

        rectangularMap.removeObject(animal4);
        rectangularMap.addObject(animal7);
        LinkedList<Animal> linkedList1 = rectangularMap.deleteDead();
        assertEquals(linkedList1.size(), 0);

        rectangularMap.addObject(animal4);
        rectangularMap.addObject(animal5);
        rectangularMap.addObject(animal6);
        rectangularMap.addObject(animal7);

        LinkedList<Animal> linkedList2 = rectangularMap.deleteDead();
        assertEquals(linkedList2.size(), 1);

    }

    @Test
    void moveAllAnimals() {
        rectangularMap.addObject(new Animal(new Vector2d(1, 1), 10, new Gens(genSeq)));
        rectangularMap.addObject(new Animal(new Vector2d(1, 1), 20, new Gens(genSeq)));
        rectangularMap.addObject(new Animal(new Vector2d(2, 2), 20, new Gens(genSeq2)));
        rectangularMap.addObject(new Animal(new Vector2d(2, 2), 20, new Gens(genSeq2)));
        LinkedList<Animal> linkedList = rectangularMap.deleteDead();
        rectangularMap.moveAllAnimals(linkedList);
        System.out.println("RUN ALL ANIMALS");
    }

    @Test
    void eatByAnimals() {
        rectangularMap.addObject(new Animal(new Vector2d(1, 1), 10, new Gens(genSeq)));
        rectangularMap.addObject(new Animal(new Vector2d(1, 1), 20, new Gens(genSeq)));
        rectangularMap.addObject(new Animal(new Vector2d(2, 2), 20, new Gens(genSeq2)));
        rectangularMap.addObject(new Animal(new Vector2d(2, 2), 20, new Gens(genSeq2)));
        rectangularMap.addObject(new Grass(new Vector2d(0, 0)));
        rectangularMap.addObject(new Grass(new Vector2d(3, 3)));
        LinkedList<Animal> linkedList = rectangularMap.deleteDead();
//        while (true) {
//            rectangularMap.moveAllAnimals(linkedList);
//            rectangularMap.eatByAnimals();
//        }


    }

    @Test
    void pairAnimals() {
        rectangularMap.addObject(new Animal(new Vector2d(1, 1), 100, new Gens(genSeq)));
        rectangularMap.addObject(new Animal(new Vector2d(1, 1), 20, new Gens(genSeq)));
        rectangularMap.addObject(new Animal(new Vector2d(2, 2), 20, new Gens(genSeq2)));
        rectangularMap.addObject(new Animal(new Vector2d(2, 2), 20, new Gens(genSeq2)));
        rectangularMap.addObject(new Grass(new Vector2d(0, 0)));
        rectangularMap.addObject(new Grass(new Vector2d(3, 3)));
        rectangularMap.pairAnimals();
        LinkedList<Animal> linkedList = rectangularMap.deleteDead();
        assertTrue(linkedList.size() == 6);
    }

    @Test
    void generateGrass() {
//
//        while (true) {
//            rectangularMap.generateGrass();
//        }
    }

    @Test
    void newDay() {

        rectangularMap.addObject(new Animal(new Vector2d(1, 1), 10, new Gens(genSeq)));
        rectangularMap.addObject(new Animal(new Vector2d(1, 1), 20, new Gens(genSeq)));
        rectangularMap.addObject(new Animal(new Vector2d(2, 2), 30, new Gens(genSeq2)));
        rectangularMap.addObject(new Animal(new Vector2d(2, 2), 40, new Gens(genSeq2)));
//        while (true) {
//            rectangularMap.newDay();
//        }
    }
}