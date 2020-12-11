package config;

public class Config {

    public static int WIDTH = 15;
    public static int HEIGHT = 20;
    public static int START_ENERGY = 1;
    public static int JUNGLE_RATIO = 3;
    public static int MOVE_ENERGY = 1;
    public static int PLANT_ENERGY = 10;
    public static int ANIMALS_ON_START = 4;

    //TODO Change to apply in constructor
    public static int MAX_SIZE = Math.max(WIDTH, HEIGHT);

    //TODO Add Jungle Parameters

    public Config() {
        //TODO Add Read From JSON Feature

    }
}
