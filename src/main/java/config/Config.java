package config;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class to initialize start parameters,
 * reads config.json and writes values to static ints
 */
public class Config {

    public static final int LEFT_PADDING = 20;
    public static int WIDTH = 12;
    public static int HEIGHT = 12;
    public static int START_ENERGY = 10;
    public static int JUNGLE_RATIO = 4;
    public static int MOVE_ENERGY = 1;
    public static int PLANT_ENERGY = 10;
    public static int ANIMALS_ON_START = 4;
    public static int TIMER_DURATION = 10;
    public static int MAX_SIZE = HEIGHT;
    public static int JUNGLE_X_START = WIDTH / JUNGLE_RATIO;
    public static int JUNGLE_Y_START = HEIGHT / JUNGLE_RATIO;

    public static void initialize() {
        Gson gson = new Gson();
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get("src/main/java/config/config.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert reader != null;
        Parameters parameters = gson.fromJson(reader, Parameters.class);
        setParameters(parameters);
    }

    private static void setParameters(Parameters parameters) {
        WIDTH = parameters.WIDTH;
        HEIGHT = parameters.HEIGHT;
        START_ENERGY = parameters.START_ENERGY;
        JUNGLE_RATIO = parameters.JUNGLE_RATIO;
        MOVE_ENERGY = parameters.MOVE_ENERGY;
        PLANT_ENERGY = parameters.PLANT_ENERGY;
        ANIMALS_ON_START = parameters.ANIMALS_ON_START;
        TIMER_DURATION = parameters.TIMER_DURATION;
        MAX_SIZE = Math.max(WIDTH, HEIGHT);
        JUNGLE_X_START = WIDTH / JUNGLE_RATIO;
        JUNGLE_Y_START = HEIGHT / JUNGLE_RATIO;
    }

    private static class Parameters {
        public int WIDTH;
        public int HEIGHT;
        public int START_ENERGY;
        public int JUNGLE_RATIO;
        public int MOVE_ENERGY;
        public int PLANT_ENERGY;
        public int ANIMALS_ON_START;
        public int TIMER_DURATION;
    }
}
