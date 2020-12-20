package logic.statistics;

import logic.simulation.MapSimulation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MapStatisticsWriterTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void updateStatistics() {
        MapSimulation mapSimulation = new MapSimulation();
        MapStatistics mapStatistics = new MapStatistics(mapSimulation);
        MapStatisticsPropertyGetter mapStatisticsPropertyGetter = new MapStatisticsPropertyGetter(mapStatistics);
        new MapStatisticsWriter(mapStatisticsPropertyGetter).writeStatistics();
    }
}