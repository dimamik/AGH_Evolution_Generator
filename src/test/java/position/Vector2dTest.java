package position;

import config.Config;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    static Vector2d vector2d;
    static Vector2d vector2d2;
    static Vector2d vector2d3;
    static Vector2d vector2d4;
    static Vector2d vector2d5;
    static Vector2d vector2d6;
    static Vector2d vector2d7;
    static Vector2d vector2d8;

    @BeforeAll
    public static void setUp() {
        Config.WIDTH = 12;
        Config.HEIGHT = 12;
        Config.JUNGLE_RATIO = 3;
        Config.MOVE_ENERGY = 1;
        Config.PLANT_ENERGY = 10;
        vector2d = new Vector2d(4, 4);
        vector2d2 = new Vector2d(3, 4);
        vector2d3 = new Vector2d(4, 8);
        vector2d4 = new Vector2d(8, 8);
        vector2d5 = new Vector2d(8, 4);
        vector2d6 = new Vector2d(6, 5);
        vector2d7 = new Vector2d(12, 5);
        vector2d8 = new Vector2d(0, 0);
    }

    @Test
    void addMirrored() {
        assertEquals(new Vector2d(0, 5), vector2d7.addMirrored(new Vector2d(1, 0)));
    }


    @Test
    void isInsideTheJungle() {

        assertTrue(vector2d.isInsideTheJungle());
        assertTrue(vector2d3.isInsideTheJungle());
        assertFalse(vector2d2.isInsideTheJungle());
        assertFalse(vector2d7.isInsideTheJungle());
        assertFalse(vector2d8.isInsideTheJungle());
        assertTrue(vector2d4.isInsideTheJungle());
        assertTrue(vector2d5.isInsideTheJungle());
        assertTrue(vector2d6.isInsideTheJungle());
        assertTrue(vector2d6.isInsideTheJungle());
    }


}