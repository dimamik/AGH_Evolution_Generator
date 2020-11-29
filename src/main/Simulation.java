import maps.RectangularMap;
import objects.animal.Animal;
import objects.animal.Gens;
import position.Vector2d;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Simulation {

    RectangularMap rectangularMap;

    public Simulation() {


        run();
    }

    public static void main(String[] args) {
        new Simulation();

    }

    public void run() {
        rectangularMap = new RectangularMap(10, 10, 2, 2);
        int[] genSeq = {0, 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 5, 5, 6, 6, 7, 7, 7, 7};
        int[] genSeq2 = {0, 0, 7, 1, 1, 7, 2, 0, 2, 0, 2, 3, 7, 7, 7, 4, 4, 4, 4, 0, 7, 0, 7, 0, 5, 5, 6, 6, 7, 7, 7, 7};
        Gens gen1 = new Gens(genSeq);
        Gens gen2 = new Gens(genSeq2);
        Animal animal1 = new Animal(new Vector2d(1, 1), 10, gen1, rectangularMap);
        Animal animal2 = new Animal(new Vector2d(2, 2), 10, gen2, rectangularMap);
        Animal animal3 = new Animal(new Vector2d(3, 2), 10, gen2, rectangularMap);
        rectangularMap.putAnimalToHashMap(animal1);
        rectangularMap.putAnimalToHashMap(animal2);
        rectangularMap.putAnimalToHashMap(animal3);
        Timer timer = new Timer();
        TimerTask task = new RunGame();

        timer.scheduleAtFixedRate(task, 0, 10);
    }

    class RunGame extends TimerTask {
        public int i = 0;

        public void run() {
            System.out.println(rectangularMap.toString());
            rectangularMap.nextDay();

        }
    }

}
