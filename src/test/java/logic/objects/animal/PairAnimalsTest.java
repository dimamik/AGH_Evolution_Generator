package logic.objects.animal;

import logic.maps.MapCell;
import logic.maps.RectangularMap;
import logic.position.Vector2d;
import logic.simulation.MapSimulation;
import logic.statistics.MapStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PairAnimalsTest {
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
        animal2 = new Animal(new Vector2d(2, 3), 100, new Gens(genSeq2));
        animal3 = new Animal(new Vector2d(2, 2), 20, new Gens(genSeq2));
        animal4 = new Animal(new Vector2d(3, 3), 15, new Gens(genSeq2));
    }

    @Test
    void pairAnimalsFromFamilyGroup() {
        rectangularMap.addObject(animal1);
        rectangularMap.addObject(animal2);
        rectangularMap.addObject(animal3);
        rectangularMap.addObject(animal4);
        PairAnimals.pairAnimalsFromFamilyGroup(
                new Family(animal1, animal3),
                rectangularMap,
                4
        );
        LinkedList<MapCell> listOfOccupiedCells = rectangularMap.getListOfOccupiedCells();
        int sum = 0;
        for (MapCell cell : listOfOccupiedCells) {
            sum += (cell.getAllAnimalsAndRemoveDead(mapStatistics).size());
        }
        assertEquals(5, sum);
    }

    @Test
    void canPair() {

        assertTrue(
                PairAnimals.canPair(
                        animal1, animal2
                )
        );
    }
}