package logic.statistics;

import config.Config;
import logic.maps.RectangularMap;
import logic.objects.animal.Animal;
import logic.objects.animal.Gens;
import logic.position.Vector2d;
import logic.simulation.MapSimulation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnimalStatisticsTest {
    MapSimulation mapSimulation;
    MapStatistics mapStatistics;
    RectangularMap rectangularMap;
    MapStatisticsPropertyGetter mapStatisticsPropertyGetter;
    int[] genSeq2;
    Animal animal1;
    Animal animal2;
    Animal animal3;
    Animal animal4;
    Animal animal5;
    Animal animal6;
    Animal animal7;
    Animal animal8;

    @BeforeAll
    static void init() {
        Config.initialize();
        Config.ANIMALS_ON_START = 0;
    }

    @BeforeEach
    void setUp() {
        mapSimulation = new MapSimulation();
        mapStatistics = mapSimulation.getMapStatistics();
        rectangularMap = mapSimulation.getRectangularMap();
        mapStatisticsPropertyGetter = mapSimulation.getMapStatisticsGetter();
        genSeq2 = new int[]{0, 0, 7, 1, 1, 7, 2, 0, 2, 0, 2, 3, 7, 7, 7, 4, 4, 4, 4, 0, 7, 0, 7, 0, 5, 5, 6, 6, 7, 7, 7, 7};
        animal1 = new Animal(new Vector2d(2, 2), 10, new Gens(genSeq2));
        animal2 = new Animal(new Vector2d(2, 3), 0, new Gens(genSeq2));
        animal3 = new Animal(new Vector2d(2, 2), 20, new Gens(genSeq2));
        animal4 = new Animal(new Vector2d(3, 3), 15, new Gens(genSeq2));
        animal5 = new Animal(new Vector2d(2, 2), 10, new Gens(genSeq2));
        animal6 = new Animal(new Vector2d(2, 3), 0, new Gens(genSeq2));
        animal7 = new Animal(new Vector2d(2, 2), 20, new Gens(genSeq2));
        animal8 = new Animal(new Vector2d(3, 3), 15, new Gens(genSeq2));
    }


    @Test
    void getChildrenNumber_andAddToChildrenList() {
        AnimalStatistics statistics1 = animal1.getAnimalStatistics();
        AnimalStatistics statistics2 = animal2.getAnimalStatistics();
        AnimalStatistics statistics3 = animal3.getAnimalStatistics();
        AnimalStatistics statistics4 = animal4.getAnimalStatistics();
        statistics1.addToChildrenList(animal2, 3);
        statistics1.addToChildrenList(animal3, 10);
        statistics1.addToChildrenList(animal4, 15);
        assertEquals(2, statistics1.getChildrenNumber(5, 15));
        assertEquals(3, statistics1.getChildrenNumber(15, 15));
        assertEquals(0, statistics1.getChildrenNumber(1, 50));
        assertEquals(1, statistics1.getChildrenNumber(0, 15));
    }

    @Test
    void getAncestorsNumber() {
        AnimalStatistics statistics1 = animal1.getAnimalStatistics();
        AnimalStatistics statistics2 = animal2.getAnimalStatistics();
        AnimalStatistics statistics3 = animal3.getAnimalStatistics();
        AnimalStatistics statistics6 = animal6.getAnimalStatistics();
        statistics1.addToChildrenList(animal2, 3);
        statistics2.addToChildrenList(animal3, 10);
        statistics3.addToChildrenList(animal4, 15);
        statistics3.addToChildrenList(animal5, 17);
        statistics3.addToChildrenList(animal6, 20);
        statistics6.addToChildrenList(animal7, 21);
        statistics6.addToChildrenList(animal8, 22);
        assertEquals(2, statistics1.getAncestorsNumberWrapper(22, 22));
        assertEquals(5, statistics3.getAncestorsNumberWrapper(22, 22));
        assertEquals(2, statistics6.getAncestorsNumberWrapper(1, 22));
    }


    @Test
    void testMytest() {

    }
}