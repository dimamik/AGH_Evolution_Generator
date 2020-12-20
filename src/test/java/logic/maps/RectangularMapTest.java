package logic.maps;

import logic.objects.animal.Animal;
import logic.objects.animal.Gens;
import logic.position.Vector2d;
import logic.random.RandomGenerator;
import logic.simulation.MapSimulation;
import logic.statistics.MapStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {

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
        mapStatistics = new MapStatistics(mapSimulation);
        rectangularMap = new RectangularMap(mapStatistics);
        genSeq2 = new int[]{0, 0, 7, 1, 1, 7, 2, 0, 2, 0, 2, 3, 7, 7, 7, 4, 4, 4, 4, 0, 7, 0, 7, 0, 5, 5, 6, 6, 7, 7, 7, 7};
        animal1 = new Animal(new Vector2d(2, 2), 10, new Gens(genSeq2));
        animal2 = new Animal(new Vector2d(2, 3), 0, new Gens(genSeq2));
        animal3 = new Animal(new Vector2d(2, 2), 20, new Gens(genSeq2));
        animal4 = new Animal(new Vector2d(3, 3), 15, new Gens(genSeq2));
    }

    @Test
    void addObjectAndIsOccupied() {
        rectangularMap.addObject(animal1);
        rectangularMap.addObject(animal2);
        rectangularMap.addObject(animal3);
        rectangularMap.addObject(animal4);

        assertTrue(rectangularMap.isOccupied(new Vector2d(2, 2)));
        assertTrue(rectangularMap.isOccupied(new Vector2d(2, 3)));
        assertTrue(rectangularMap.isOccupied(new Vector2d(2, 2)));
        assertTrue(rectangularMap.isOccupied(new Vector2d(3, 3)));
    }

    @Test
    void removeObject() {
        rectangularMap.addObject(animal1);
        rectangularMap.addObject(animal2);
        rectangularMap.addObject(animal3);
        rectangularMap.addObject(animal4);

        rectangularMap.removeObject(animal3.getPosition(), animal3);
        assertTrue(rectangularMap.isOccupied(new Vector2d(2, 2)));
        rectangularMap.removeObject(animal1.getPosition(), animal1);
        assertFalse(rectangularMap.isOccupied(new Vector2d(2, 2)));
        assertTrue(rectangularMap.isOccupied(new Vector2d(2, 3)));
        assertTrue(rectangularMap.isOccupied(new Vector2d(3, 3)));
    }

    @Test
    void isMapFull() {
        do {
            rectangularMap.addObject(new Animal(RandomGenerator.getRandomPositionInsideMapOutSideOfJungles(), 20, new Gens(genSeq2)));
        } while (!rectangularMap.isMapFull());

    }

    @Test
    void isJungleFull() {
        do {
            rectangularMap.addObject(new Animal(RandomGenerator.getRandomPositionInsideJungles(), 20, new Gens(genSeq2)));
        } while (!rectangularMap.isJungleFull());
    }

    @Test
    void getObjectFromCell_ReturnsEmptyIfCellIsEmpty() {
        rectangularMap.addObject(animal1);
        rectangularMap.addObject(animal2);
        rectangularMap.addObject(animal3);
        rectangularMap.addObject(animal4);

        assertEquals(animal3, rectangularMap.getObjectFromCell(new Vector2d(2, 2)).get());
        assertEquals(animal4, rectangularMap.getObjectFromCell(new Vector2d(3, 3)).get());
        assertEquals(Optional.empty(), rectangularMap.getObjectFromCell(new Vector2d(5, 5)));
    }

    @Test
    void positionChanged() {
        rectangularMap.addObject(animal1);
        Vector2d prevPosition = animal1.getPosition();
        animal1.moveAnimal();
        assertFalse(rectangularMap.isOccupied(prevPosition));
    }
}