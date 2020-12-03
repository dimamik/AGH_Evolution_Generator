package objects.random;

import static config.Config.*;
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

    public static Vector2d getRandomPositionInsideJungles(){
        int x = getRandomNumberInRange(WIDTH/JUNGLE_RATIO, WIDTH -WIDTH/JUNGLE_RATIO );
        int y = getRandomNumberInRange(HEIGHT/JUNGLE_RATIO, HEIGHT -HEIGHT/JUNGLE_RATIO );
        return new Vector2d(x,y);
    }
    public static Vector2d getRandomPositionInsideMapOutSideOfJungles(){
        int firstHalf = getRandomNumberInRange(1,2);
        int secondHalf = getRandomNumberInRange(1,2);
        int x;
        int y;
        if (firstHalf == 1){
            x = getRandomNumberInRange(0,WIDTH/JUNGLE_RATIO-1);
        }
        else{
            x = getRandomNumberInRange(WIDTH - WIDTH/JUNGLE_RATIO+1,WIDTH);
        }
        if (secondHalf == 1){
            y = getRandomNumberInRange(0,HEIGHT/JUNGLE_RATIO-1);
        }
        else{
            y = getRandomNumberInRange(HEIGHT - HEIGHT/JUNGLE_RATIO+1,HEIGHT);
        }
        return new Vector2d(x,y);
    }

}
