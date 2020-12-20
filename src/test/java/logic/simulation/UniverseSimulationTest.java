package logic.simulation;

import config.Config;
import logic.maps.RectangularMap;
import logic.objects.animal.Animal;
import logic.objects.animal.Gens;
import logic.position.Vector2d;
import logic.statistics.MapStatistics;
import logic.statistics.MapStatisticsPropertyGetter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UniverseSimulationTest {
    MapSimulation mapSimulation;
    MapStatistics mapStatistics;
    RectangularMap rectangularMap;
    MapStatisticsPropertyGetter mapStatisticsPropertyGetter;
    UniverseSimulation universeSimulation;
    int[] genSeq2;
    Animal animal1;
    Animal animal2;
    Animal animal3;
    Animal animal4;

    @BeforeAll
    static void init() {
        Config.ANIMALS_ON_START = 0;
    }

    @BeforeEach
    void setUp() {
        mapSimulation = new MapSimulation();
        mapStatistics = mapSimulation.getMapStatistics();
        rectangularMap = mapSimulation.getRectangularMap();
        mapStatisticsPropertyGetter = mapSimulation.getMapStatisticsGetter();
        universeSimulation = new UniverseSimulation();
        genSeq2 = new int[]{0, 0, 7, 1, 1, 7, 2, 0, 2, 0, 2, 3, 7, 7, 7, 4, 4, 4, 4, 0, 7, 0, 7, 0, 5, 5, 6, 6, 7, 7, 7, 7};
        animal1 = new Animal(new Vector2d(2, 2), 10, new Gens(genSeq2));
        animal2 = new Animal(new Vector2d(2, 3), 0, new Gens(genSeq2));
        animal3 = new Animal(new Vector2d(2, 2), 20, new Gens(genSeq2));
        animal4 = new Animal(new Vector2d(3, 3), 15, new Gens(genSeq2));
    }

    @Test
    void addNewMap() {
        universeSimulation.addNewMap();

    }

    @Test
    void newDayInAllSimulations() {
        universeSimulation.addNewMap();

    }
}