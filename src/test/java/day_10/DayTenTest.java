package day_10;

import day_10.DayTen.LookAndSay;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class DayTenTest {

    @ParameterizedTest
    @CsvSource({
            "1     ,11    ",
            "11    ,21    ",
            "21    ,1211  ",
            "1211  ,111221",
            "111221,312211",
    })
    void should_say_according_to_rules(String look, String say) {
        assertThat(LookAndSay.say(look)).isEqualTo(say);
    }
}