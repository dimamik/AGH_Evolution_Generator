package logic.position;

import java.util.Objects;

import static config.Config.*;

public class Vector2d {
    private int x;
    private int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x && y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Vector2d{" + "x=" + x + ", y=" + y + '}';
    }

    private Vector2d mirrorVectorIfOut() {
        if (y >= HEIGHT) {
            y = 0;
        }
        if (y < 0) {
            y = HEIGHT - 1;
        }
        if (x >= WIDTH) {
            x = 0;
        }
        if (x < 0) {
            x = WIDTH - 1;
        }
        return this;
    }

    /**
     * @return true if Vector2d is inside jungles
     */
    public boolean isInsideTheJungle() {

        int starting_x = WIDTH / JUNGLE_RATIO;
        int starting_y = HEIGHT / JUNGLE_RATIO;
        return getX() >= starting_x && getY() >= starting_y
                && getX() <= HEIGHT - starting_x && getY() <= WIDTH - starting_y;
    }

    /**
     * Adds vectors with MIRRORING
     *
     * @param vector vector to mirror add
     * @return mirror sum of given two vectors
     */
    public Vector2d addMirrored(Vector2d vector) {
        Vector2d toReturn;
        toReturn = new Vector2d(x + vector.getX(), y + vector.getY());
        return toReturn.mirrorVectorIfOut();

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
