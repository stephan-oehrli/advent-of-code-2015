package day_3;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DayThreeTest {

    @ParameterizedTest
    @CsvSource({
            ">, 2",
            "^>v<, 4",
            "^v^v^v^v^v, 2",
    })
    void should_count_delivered_houses(String instructions, int expectedDeliveredHouses) {
        int deliveredHouses = DayThree.countDeliveredHouses(instructions);

        assertEquals(expectedDeliveredHouses, deliveredHouses);
    }

    @ParameterizedTest
    @CsvSource({
            "^v, 3",
            "^>v<, 3",
            "^v^v^v^v^v, 11",
    })
    void should_count_delivered_houses_with_robot_santa(String instructions, int expectedDeliveredHouses) {
        int deliveredHouses = DayThree.countDeliveredHousesWithRobotSanta(instructions);

        assertEquals(expectedDeliveredHouses, deliveredHouses);
    }

}
