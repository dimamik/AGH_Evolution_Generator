package objects.grass;

import maps.RectangularMap;
import position.Vector2d;
import random.RandomGenerator;

public class GenerateGrass {
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
