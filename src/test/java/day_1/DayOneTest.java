package day_1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DayOneTest {

    @ParameterizedTest
    @CsvSource({
            "(()), 0",
            "()(), 0",
            "(()(()(, 3",
            ")())()), -3"
    })
    void should_find_floor(String braces, int expectedFloors) {
        int floors = DayOne.findFloors(braces);

        assertEquals(expectedFloors, floors);
    }

    @ParameterizedTest
    @CsvSource({
            "), 1",
            "()()), 5",
    })
    void should_find_basement(String braces, int expectedCharAt) {
        int charAt = DayOne.findBasement(braces);

        assertEquals(expectedCharAt, charAt);
    }

}