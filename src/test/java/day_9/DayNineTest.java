package day_9;

import day_9.DayNine.Connection;
import day_9.DayNine.Location;
import day_9.DayNine.LocationGraph;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class DayNineTest {

    private static final LocationGraph TEST_LOCATION_GRAPH = LocationGraph.of(Arrays.asList(
            "London to Dublin = 464",
            "London to Belfast = 518",
            "Dublin to Belfast = 141"
    ));

    @Test
    void should_create_location_list() {
        assertThat(TEST_LOCATION_GRAPH.getLocations()).containsOnly(
                new Location("London"),
                new Location("Dublin"),
                new Location("Belfast")
        );
    }

    @Test
    void should_create_location_graph() {
        assertThat(TEST_LOCATION_GRAPH.getGraph()).hasSize(3);
        assertThat(TEST_LOCATION_GRAPH.getGraph().get(new Location("London"))).containsOnly(
                new Connection(new Location("Dublin"), 464),
                new Connection(new Location("Belfast"), 518)
        );
        assertThat(TEST_LOCATION_GRAPH.getGraph().get(new Location("Dublin"))).containsOnly(
                new Connection(new Location("London"), 464),
                new Connection(new Location("Belfast"), 141)
        );
        assertThat(TEST_LOCATION_GRAPH.getGraph().get(new Location("Belfast"))).containsOnly(
                new Connection(new Location("Dublin"), 141),
                new Connection(new Location("London"), 518)
        );
    }
    
    @Test
    void should_calculate_shortest_distance() {
        assertThat(TEST_LOCATION_GRAPH.calculateShortestDistance()).isEqualTo(605);
    }

    @Test
    void should_calculate_longest_distance() {
        assertThat(TEST_LOCATION_GRAPH.calculateLongestDistance()).isEqualTo(982);
    }
}