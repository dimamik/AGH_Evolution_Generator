package maps;

public class MapStatistics {
    int movableCounter;
    int immovableCounter;
    int grassCounter;
    int pairedAnimalsCounter;

    public MapStatistics(int movableCounter, int immovableCounter, int grassCounter, int pairedAnimalsCounter) {
        this.movableCounter = movableCounter;
        this.immovableCounter = immovableCounter;
        this.grassCounter = grassCounter;
        this.pairedAnimalsCounter = pairedAnimalsCounter;
    }

    @Override
    public String toString() {
        return "MapStatistics{" + "movableCounter=" + movableCounter + ", immovableCounter=" + immovableCounter
                + ", grassCounter=" + grassCounter + ", pairedAnimalsCounter=" + pairedAnimalsCounter + '}';
    }
}