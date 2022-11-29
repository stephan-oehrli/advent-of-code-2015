package day_5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class DayFiveTest {

    @ParameterizedTest
    @CsvSource({
        "ugknbfddgicrmopn, true",
        "aaa, true",
        "jchzalrnumimnmhp, false",
        "haegwjzuvuyypxyu, false",
        "dvszwmarrgswjxmb, false"
    })
    void should_check_if_String_is_nice(String testString, boolean expected) {
        boolean isNice = DayFive.isNiceString(testString);

        assertEquals(expected, isNice);
    }

    @ParameterizedTest
    @CsvSource({
            "qjhvhtzxzqqjkmpb, true",
            "xxyxx, true",
            "uurcxstgmygtbstg, false",
            "ieodomkazucvgmuy, false"
    })
    void should_check_if_String_is_nice_too(String testString, boolean expected) {
        boolean isNice = DayFive.isAlsoNiceString(testString);

        assertEquals(expected, isNice);
    }
}