package day_4;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class DayFourTest {

    @ParameterizedTest
    @CsvSource({
            "abcdef, 609043",
            "pqrstuv, 1048970",
    })
    void should_find_the_lowest_hash_starting_with_5_zeros_for_key(String secretKey, int expectedNumber) {
        int lowestNumber = DayFour.findLowestNumberForHashWithDefinedString(secretKey, "00000");

        assertEquals(expectedNumber, lowestNumber);
    }

}
