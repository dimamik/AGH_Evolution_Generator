package logic.position;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapDirectionTest {
    @Test
    void toUnitVector() {
        assertEquals(new Vector2d(-1, 0), MapDirection.WEST.toUnitVector());
    }

    @Test
    void rotateTo() {

        MapDirection mapDirection = MapDirection.WEST.rotateTo(1);

        assertEquals(MapDirection.NORTHWEST, mapDirection);
    }
}