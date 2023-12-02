package day_11;

import day_11.DayEleven.PasswordGenerator;
import day_11.DayEleven.PasswordValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class DayElevenTest {

    @ParameterizedTest
    @CsvSource({
            "xx,xy",
            "xy,xz",
            "xz,ya",
            "ya,yb",
    })
    void should_generate_password(String input, String expectedOutput) {
        assertThat(PasswordGenerator.generateFrom(input)).isEqualTo(expectedOutput);
    }

    @ParameterizedTest
    @CsvSource({
            "hijklmmn,false",
            "abbceffg,false",
            "abbcegjk,false",
            "abcdffab,false",
            "abcdffaa,true ",
            "ghjaabcc,true "
    })
    void should_validate_password(String password, boolean expected) {
        assertThat(PasswordValidator.isValid(password)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "abcdefgh,abcdffaa",
            "ghijklmn,ghjaabcc"
    })
    void should_generate_valid_password(String input, String expectedOutput) {
        assertThat(PasswordGenerator.generateValidFrom(input)).isEqualTo(expectedOutput);
    }
}