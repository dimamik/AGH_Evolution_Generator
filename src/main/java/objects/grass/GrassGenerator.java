package objects.grass;

import objects.maps.RectangularMap;
import random.RandomGenerator;
import position.Vector2d;

public class GrassGenerator {
    public static boolean generateGrasInJungles(RectangularMap rectangularMap) {
        if (rectangularMap.isJungleFull()) {
            return false;
        }
        Vector2d position = RandomGenerator.getRandomPositionInsideJungles();


        while (rectangularMap.isOccupied(position)) {
            position = RandomGenerator.getRandomPositionInsideJungles();
        }

        rectangularMap.addObject(new Grass(position));
        return true;
    }

    public static boolean generateGrassOutOfJungles(RectangularMap rectangularMap) {
        if (rectangularMap.isMapFull()) {
            return false;
        }
        Vector2d position = RandomGenerator.getRandomPositionInsideMapOutSideOfJungles();

        while (rectangularMap.isOccupied(position)) {
            position = RandomGenerator.getRandomPositionInsideMapOutSideOfJungles();
        }
        rectangularMap.addObject(new Grass(position));
        return true;
    }
}
