package day_2;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class DayTwoTest {

    @ParameterizedTest
    @CsvSource({
            "2x3x4, 58",
            "1x1x10, 43"
    })
    void should_calculate_required_paper(String dimension, int expectedArea) {
        int area = DayTwo.calculateRequiredArea(dimension);

        assertEquals(expectedArea, area);
    }

    @ParameterizedTest
    @CsvSource({
            "2x3x4, 34",
            "1x1x10, 14"
    })
    void should_calculate_required_ribbon(String dimension, int expectedLength) {
        int ribbonLength = DayTwo.calculateRequiredRibbon(dimension);

        assertEquals(expectedLength, ribbonLength);
    }

}