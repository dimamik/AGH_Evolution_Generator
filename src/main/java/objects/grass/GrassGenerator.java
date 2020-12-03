package objects.grass;

import objects.maps.RectangularMap;
import objects.random.RandomGenerator;
import position.Vector2d;

public class GrassGenerator {
    public static void generateGrasInJungles(RectangularMap rectangularMap) {
        if (rectangularMap.isJungleFull()) {
            return;
        }
        Vector2d position = RandomGenerator.getRandomPositionInsideJungles();


        while (rectangularMap.isOccupied(position)) {
            position = RandomGenerator.getRandomPositionInsideJungles();
        }

        rectangularMap.addObject(new Grass(position));
    }

    public static void generateGrassOutOfJungles(RectangularMap rectangularMap) {
        if (rectangularMap.isMapFull()) {
            return;
        }
        Vector2d position = RandomGenerator.getRandomPositionInsideMapOutSideOfJungles();

        while (rectangularMap.isOccupied(position)) {
            position = RandomGenerator.getRandomPositionInsideMapOutSideOfJungles();
        }
        rectangularMap.addObject(new Grass(position));
    }
}
