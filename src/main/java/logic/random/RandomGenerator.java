package logic.random;

import logic.objects.animal.Gens;
import logic.position.MapDirection;
import logic.position.Vector2d;

import java.util.Random;

import static config.Config.*;

public class RandomGenerator {
    /**
     * Returns random number from range [min,max]
     *
     * @param min - min value
     * @param max - max value
     * @return random number in range [min,max]
     */

    public static int getRandomNumberInRange(int min, int max) {

        if (min > max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static Vector2d getRandomPositionInsideBorders(int xs, int xe, int ys, int ye) {
        int x = getRandomNumberInRange(xs, xe);
        int y = getRandomNumberInRange(ys, ye);
        return new Vector2d(x, y);
    }

    public static Vector2d getRandomPosition(int width, int height) {
        return new Vector2d(getRandomNumberInRange(0, width), getRandomNumberInRange(0, height));
    }

    public static Vector2d getRandomPositionInsideJungles() {
        int x = getRandomNumberInRange(WIDTH / JUNGLE_RATIO, WIDTH - (WIDTH / JUNGLE_RATIO) - 1);
        int y = getRandomNumberInRange(HEIGHT / JUNGLE_RATIO, HEIGHT - (HEIGHT / JUNGLE_RATIO) - 1);
        return new Vector2d(x, y);
    }

    public static Vector2d getRandomPositionInsideMapOutSideOfJungles() {
        int zoneNumber;
        int x;
        int y;
        zoneNumber = getRandomNumberInRange(1, 4);
        return switch (zoneNumber) {
            case 1 -> getRandomPositionInsideBorders(0, WIDTH / JUNGLE_RATIO - 1, 0, HEIGHT - 1);

            case 2 -> getRandomPositionInsideBorders(WIDTH / JUNGLE_RATIO, WIDTH - (WIDTH / JUNGLE_RATIO),
                    0, HEIGHT / JUNGLE_RATIO - 1);

            case 3 -> getRandomPositionInsideBorders(WIDTH / JUNGLE_RATIO, WIDTH - (WIDTH / JUNGLE_RATIO),
                    HEIGHT - (HEIGHT / JUNGLE_RATIO), HEIGHT - 1);

            case 4 -> getRandomPositionInsideBorders(WIDTH - (WIDTH / JUNGLE_RATIO), WIDTH - 1, 0, HEIGHT - 1);

            default -> throw new IllegalStateException("Unexpected value: " + zoneNumber);
        };
    }

    public static Gens generateRandomGenome() {
        int[] genSeq = new int[32];
        for (int i = 0; i < 32; i++) {
            genSeq[i] = RandomGenerator.getRandomNumberInRange(0, 7);
        }
        return new Gens(genSeq);
    }

    public static MapDirection generateRandomMapDirection() {
        return MapDirection.values()[RandomGenerator.getRandomNumberInRange(0, 7)];
    }
}