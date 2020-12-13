package logic.maps;

import config.Config;
import logic.objects.animal.Animal;
import logic.objects.animal.Family;
import logic.objects.animal.Gens;
import logic.objects.grass.Grass;
import logic.position.Vector2d;
import logic.simulation.MapSimulation;
import logic.statistics.MapStatistics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Optional;

import static config.Config.PLANT_ENERGY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class MapCellTest {

    static MapCell mapCell1;
    static MapCell mapCell2;
    static MapCell mapCell3;
    static MapCell mapCell4;

    static Animal animal1;
    static Animal animal2;
    static Animal animal3;
    static Animal animal4;
    static int[] genSeq2;

    @BeforeAll
    public static void init() {
        Config.initialize();
    }

    @BeforeEach
    void setUp() {
        mapCell1 = new MapCell(new Vector2d(2, 2));
        mapCell2 = new MapCell(new Vector2d(3, 3));
        mapCell3 = new MapCell(new Vector2d(4, 4));
        mapCell4 = new MapCell(new Vector2d(5, 5));
        genSeq2 = new int[]{0, 0, 7, 1, 1, 7, 2, 0, 2, 0, 2, 3, 7, 7, 7, 4, 4, 4, 4, 0, 7, 0, 7, 0, 5, 5, 6, 6, 7, 7, 7, 7};
        animal1 = new Animal(new Vector2d(2, 2), 10, new Gens(genSeq2));
        animal2 = new Animal(new Vector2d(2, 2), 0, new Gens(genSeq2));
        animal3 = new Animal(new Vector2d(2, 2), 20, new Gens(genSeq2));
        animal4 = new Animal(new Vector2d(2, 2), 0, new Gens(genSeq2));
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getPosition() {
        MapCell mapCell1 = new MapCell(new Vector2d(2, 2));
        assertEquals(new Vector2d(2, 2), mapCell1.getPosition());
    }

    @Test
    void getAllAnimalsAndRemoveDead_ShouldUpdateStatisticsRemoveDeadAndReturn() {
        MapSimulation mapSimulation = new MapSimulation();
        MapStatistics mapStatistics = new MapStatistics(mapSimulation);
        mapStatistics.addAnimalToStatistics(animal1);
        mapStatistics.addAnimalToStatistics(animal2);
        mapStatistics.addAnimalToStatistics(animal3);
        mapStatistics.addAnimalToStatistics(animal4);
        mapCell1.addObject(animal1);
        mapCell1.addObject(animal2);
        mapCell1.addObject(animal3);
        mapCell1.addObject(animal4);
        LinkedList<Animal> linkedList = mapCell1.getAllAnimalsAndRemoveDead(mapStatistics);
        assertEquals(2, linkedList.size());

    }

    @Test
    void pairAnimalsIfPossible() {
        MapSimulation mapSimulation = new MapSimulation();
        MapStatistics mapStatistics = new MapStatistics(mapSimulation);
        mapStatistics.addAnimalToStatistics(animal1);
        mapStatistics.addAnimalToStatistics(animal2);
        mapStatistics.addAnimalToStatistics(animal3);
        mapStatistics.addAnimalToStatistics(animal4);
        mapCell1.addObject(animal1);
        mapCell1.addObject(animal2);
        mapCell1.addObject(animal3);
        mapCell1.addObject(animal4);
        Optional<Family> optionalFamily = mapCell1.pairAnimalsIfPossible();
        assertFalse(optionalFamily.isEmpty());
    }


    @Test
    void eatGrassByStrongestAnimal() {
        MapSimulation mapSimulation = new MapSimulation();
        MapStatistics mapStatistics = new MapStatistics(mapSimulation);
        mapStatistics.addAnimalToStatistics(animal1);
        mapStatistics.addAnimalToStatistics(animal2);
        mapStatistics.addAnimalToStatistics(animal3);
        mapStatistics.addAnimalToStatistics(animal4);
        mapCell1.addObject(animal1);
        mapCell1.addObject(animal2);
        mapCell1.addObject(animal3);
        mapCell1.addObject(animal4);
        mapCell1.addObject(new Grass(new Vector2d(2, 2)));
        mapCell1.eatGrassByStrongestAnimal(mapStatistics);
        assertEquals(20 + PLANT_ENERGY, animal3.getEnergy());
    }

    @Test
    void getBestObject() {
        MapSimulation mapSimulation = new MapSimulation();
        MapStatistics mapStatistics = new MapStatistics(mapSimulation);
        mapStatistics.addAnimalToStatistics(animal1);
        mapStatistics.addAnimalToStatistics(animal2);
        mapStatistics.addAnimalToStatistics(animal3);
        mapStatistics.addAnimalToStatistics(animal4);
        mapCell1.addObject(animal1);
        mapCell1.addObject(animal2);
        mapCell1.addObject(animal3);
        mapCell1.addObject(animal4);
        mapCell1.addObject(new Grass(new Vector2d(2, 2)));

        assertEquals(animal3, mapCell1.getBestObject().get());
    }
}