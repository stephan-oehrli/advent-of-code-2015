package day_6;

import day_6.DaySix.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;

class DaySixTest {

    @ParameterizedTest(name = "{0}")
    @CsvSource(value = {
            "turn on 0,0 through 999,999     ;ON    ;  0;  0;999;999",
            "toggle 0,0 through 999,0        ;TOGGLE;  0;  0;999;  0",
            "turn off 499,499 through 500,500;OFF   ;499;499;500;500"
    }, delimiter = ';')
    void should_parse_command(String commandString, Instruction instruction,
                              int startX, int startY, int endX, int endY) {

        Command command = CommandParser.parse(commandString);

        assertThat(command)
                .returns(instruction, from(Command::instruction))
                .returns(Coordinates.of(startX, startY), from(Command::startCoordinates))
                .returns(Coordinates.of(endX, endY), from(Command::endCoordinates));
    }

    @ParameterizedTest(name = "{0}")
    @CsvSource(value = {
            "turn on 0,0 through 999,999     ;1000000",
            "toggle 0,0 through 999,0        ;   1000",
            "turn off 499,499 through 500,500;      0"
    }, delimiter = ';')
    void should_run_command(String commandString, Long lightsOn) {
        LightGrid lightGrid = new LightGrid();

        lightGrid.runCommand(commandString);

        assertThat(lightGrid.countLightsOn()).isEqualTo(lightsOn);
    }

    @ParameterizedTest(name = "{0}")
    @CsvSource(value = {
            "turn on 0,0 through 0,0     ;      1",
            "toggle 0,0 through 999,999  ;2000000"
    }, delimiter = ';')
    void should_get_brightness(String commandString, Long brightness) {
        BrightnessGrid brightnessGrid = new BrightnessGrid();

        brightnessGrid.runCommand(commandString);

        assertThat(brightnessGrid.getBrightness()).isEqualTo(brightness);
    }

}