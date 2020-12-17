package logic.objects.animal;

import logic.maps.RectangularMap;
import logic.position.Vector2d;
import logic.simulation.MapSimulation;
import logic.statistics.MapStatistics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AnimalTest {

    MapSimulation mapSimulation;
    MapStatistics mapStatistics;
    RectangularMap rectangularMap;
    int[] genSeq2;
    Animal animal1;
    Animal animal2;
    Animal animal3;
    Animal animal4;

    @BeforeEach
    void setUp() {
        mapSimulation = new MapSimulation();
        mapStatistics = new MapStatistics(mapSimulation, dayOfAnimation);
        rectangularMap = new RectangularMap(mapStatistics);
        genSeq2 = new int[]{0, 0, 7, 1, 1, 7, 2, 0, 2, 0, 2, 3, 7, 7, 7, 4, 4, 4, 4, 0, 7, 0, 7, 0, 5, 5, 6, 6, 7, 7, 7, 7};
        animal1 = new Animal(new Vector2d(2, 2), 10, new Gens(genSeq2));
        animal2 = new Animal(new Vector2d(2, 3), 0, new Gens(genSeq2));
        animal3 = new Animal(new Vector2d(2, 2), 20, new Gens(genSeq2));
        animal4 = new Animal(new Vector2d(3, 3), 15, new Gens(genSeq2));
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void moveAnimal() {
        animal1.moveAnimal();
        animal2.moveAnimal();
        animal3.moveAnimal();
        animal4.moveAnimal();

        assertNotEquals(new Vector2d(2, 2), animal1.getPosition());
        assertNotEquals(new Vector2d(2, 3), animal2.getPosition());
        assertNotEquals(new Vector2d(2, 2), animal3.getPosition());
        assertNotEquals(new Vector2d(3, 3), animal4.getPosition());
    }
}