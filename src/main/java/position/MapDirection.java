package position;

public enum MapDirection {
    NORTH(0),
    NORTHEAST(1),
    EAST(2),
    SOUTHEAST(3),
    SOUTH(4),
    SOUTHWEST(5),
    WEST(6),
    NORTHWEST(7);

    private final int numVal;

    MapDirection(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }


    public MapDirection rotateTo(int value) {
        int num = (this.getNumVal() + value) % 8;
        return MapDirection.values()[num];
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case SOUTH -> new Vector2d(0, -1);
            case WEST -> new Vector2d(-1, 0);
            case EAST -> new Vector2d(1, 0);
            case NORTHWEST -> new Vector2d(-1, 1);
            case SOUTHWEST -> new Vector2d(-1, -1);
            case NORTHEAST -> new Vector2d(1, 1);
            case SOUTHEAST -> new Vector2d(1, -1);
        };
    }
}
