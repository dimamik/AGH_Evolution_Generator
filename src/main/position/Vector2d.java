package position;

import java.util.Objects;

public class Vector2d {
    private final int x;
    private final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x &&
                y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Vector2d{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Vector2d nextByNumber(int num){
        return switch (num) {
            case 0 -> this.add(new Vector2d(0,1));
            case 1 -> this.add(new Vector2d(1,1));
            case 2 -> this.add(new Vector2d(1,0));
            case 3 -> this.add(new Vector2d(1,-1));
            case 4 -> this.add(new Vector2d(0,-1));
            case 5 -> this.add(new Vector2d(-1,-1));
            case 6 -> this.add(new Vector2d(-1,0));
            case 7 -> this.add(new Vector2d(-1,1));
            default -> this;
        };
    }

    public boolean precedes(Vector2d other) {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d opposite() {
        return new Vector2d(-1 * this.x, -1 * this.y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
