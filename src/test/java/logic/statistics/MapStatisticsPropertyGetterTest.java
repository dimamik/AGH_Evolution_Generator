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

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapStatisticsPropertyGetterTest {
    MapSimulation mapSimulation;
    MapStatistics mapStatistics;
    RectangularMap rectangularMap;
    MapStatisticsPropertyGetter mapStatisticsPropertyGetter;
    int[] genSeq2;
    Animal animal1;
    Animal animal2;
    Animal animal3;
    Animal animal4;

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
    }

    @Test
    void getAnimalsWithDominantGenome() {
        genSeq2 = new int[]{0, 0, 7, 1, 1, 7, 2, 0, 2, 0, 2, 3, 7, 7, 7, 4, 4, 4, 4, 0, 7, 0, 7, 0, 5, 5, 6, 6, 7, 7, 7, 7};
        int[] genSeq1 = new int[]{0, 0, 7, 1, 1, 7, 2, 7, 2, 0, 2, 3, 0, 0, 0, 4, 4, 0, 0, 0, 7, 0, 7, 0, 5, 0, 6, 6, 0, 0, 7, 0};
        int[] genSeq3 = new int[]{0, 7, 7, 1, 1, 7, 2, 7, 2, 0, 2, 3, 0, 0, 0, 4, 4, 0, 0, 0, 7, 0, 7, 0, 5, 0, 6, 6, 0, 0, 7, 0};
        animal1 = new Animal(new Vector2d(2, 2), 10, new Gens(genSeq2));
        animal2 = new Animal(new Vector2d(2, 3), 0, new Gens(genSeq1));
        animal3 = new Animal(new Vector2d(2, 2), 20, new Gens(genSeq3));
        animal4 = new Animal(new Vector2d(3, 3), 15, new Gens(genSeq1));

        rectangularMap.addObject(animal1);
        rectangularMap.addObject(animal2);
        rectangularMap.addObject(animal3);
        rectangularMap.addObject(animal4);

        LinkedList<Animal> listOfAnimals = mapStatisticsPropertyGetter.getAnimalsWithDominantGenome(rectangularMap.getAllAnimals());
        assertEquals(3, listOfAnimals.size());
    }
}