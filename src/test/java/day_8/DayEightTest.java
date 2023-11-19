package day_8;

import day_8.DayEight.StringSizeCounter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class DayEightTest {

    @ParameterizedTest
    @CsvSource({
            "\"\"                 , 2",
            "\"abc\"              , 5",
            "\"aaa\\\"aaa\"       ,10",
            "\"\\x27\"            , 6",
            "\"\\\\ab\\\"ab\\xaa\",14" // ( "\\ab\"ab\xaa" )
    })
    void should_count_code_size(String input, int size) {
        assertThat(StringSizeCounter.countCodeSize(input)).isEqualTo(size);
    }

    @ParameterizedTest
    @CsvSource({
            "\"\"                 ,0",
            "\"abc\"              ,3",
            "\"aaa\\\"aaa\"       ,7",
            "\"\\x27\"            ,1",
            "\"\\\\ab\\\"ab\\xaa\",7"
    })
    void should_count_string_size(String input, int size) {
        assertThat(StringSizeCounter.countStringSize(input)).isEqualTo(size);
    }

    @ParameterizedTest
    @CsvSource({
            "\"\"                 , 6",
            "\"abc\"              , 9",
            "\"aaa\\\"aaa\"       ,16",
            "\"\\x27\"            ,11"
    })
    void should_count_encoded_size(String input, int size) {
        assertThat(StringSizeCounter.countEncodedSize(input)).isEqualTo(size);
    }

    @Test
    void should_calculate_difference() {
        assertThat(StringSizeCounter.calculateStringDifference(Arrays.asList(
                "\"\"",
                "\"abc\"",
                "\"aaa\\\"aaa\"",
                "\"\\x27\"",
                "\"\\\\ab\\\"ab\\xaa\""
        ))).isEqualTo(19);
    }

    @Test
    void should_calculate_encoded_difference() {
        assertThat(StringSizeCounter.calculateEncodedDifference(Arrays.asList(
                "\"\"",
                "\"abc\"",
                "\"aaa\\\"aaa\"",
                "\"\\x27\""
        ))).isEqualTo(19);
    }
}