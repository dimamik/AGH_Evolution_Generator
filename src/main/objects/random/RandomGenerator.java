package objects.random;

import position.Vector2d;

import java.util.Random;

public class RandomGenerator {
    /**
     * Returns random number from range [min,max]
     *
     * @param min - min value
     * @param max - max value
     * @return
     */
    public static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static Vector2d getRandomPosition(int width, int height) {
        return new Vector2d(getRandomNumberInRange(0, width), getRandomNumberInRange(0, height));
    }

}
