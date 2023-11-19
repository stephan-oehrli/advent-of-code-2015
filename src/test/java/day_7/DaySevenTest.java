package day_7;

import day_7.DaySeven.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static day_7.DaySeven.ConnectionType.AND;
import static day_7.DaySeven.ConnectionType.OR;
import static day_7.DaySeven.OperationType.*;
import static org.assertj.core.api.Assertions.assertThat;

class DaySevenTest {

    private static final Circuit TEST_CIRCUIT = Circuit.of(Arrays.asList(
            "123 -> x",
            "456 -> y",
            "x AND y -> d",
            "x OR y -> e",
            "x LSHIFT 2 -> f",
            "y RSHIFT 2 -> g",
            "NOT x -> h",
            "NOT y -> i",
            "x -> z",
            "1 AND x -> j"
    ));

    @ParameterizedTest(name = "{0}")
    @CsvSource({
            "123 -> x ,123",
            "456 -> y ,456",
            "123 -> az,123",
    })
    void should_parse_signal(String inputString, int value) {
        Input input = InputParser.parse(inputString);

        assertThat(input).isInstanceOf(Signal.class);
        assertThat(((Signal) input).getValue()).isEqualTo(value);
    }

    @ParameterizedTest(name = "{0}")
    @CsvSource({
            "xy -> x,xy",
            "a -> b , a"
    })
    void should_parse_link(String inputString, String wire) {
        Input input = InputParser.parse(inputString);

        assertThat(input).isInstanceOf(Link.class);
        assertThat(((Link) input).getWire()).isEqualTo(wire);
    }

    @ParameterizedTest(name = "{0}")
    @CsvSource({
            "x AND y -> d  ,AND,x,  y",
            "x OR y -> e   ,OR ,x,  y",
            "x OR abc -> e ,OR ,x,abc",
    })
    void should_parse_connection(String inputString, ConnectionType connectionType, String wireA, String wireB) {
        Input input = InputParser.parse(inputString);

        assertThat(input).isInstanceOf(Connection.class);
        assertThat(((Connection) input).getType()).isEqualTo(connectionType);
        assertThat(((Connection) input).getWireA()).isEqualTo(wireA);
        assertThat(((Connection) input).getWireB()).isEqualTo(wireB);
    }

    @ParameterizedTest(name = "{0}")
    @CsvSource({
            "x LSHIFT 2 -> f,LSHIFT,x,2",
            "y RSHIFT 2 -> g,RSHIFT,y,2",
            "NOT x -> h     ,NOT   ,x,0",
            "NOT y -> i     ,NOT   ,y,0"
    })
    void should_parse_operation(String inputString, OperationType connectionType, String wire, int shiftByValue) {
        Input input = InputParser.parse(inputString);

        assertThat(input).isInstanceOf(Operation.class);
        assertThat(((Operation) input).getType()).isEqualTo(connectionType);
        assertThat(((Operation) input).getWire()).isEqualTo(wire);
        assertThat(((Operation) input).getShiftByValue()).isEqualTo(shiftByValue);
    }

    @Test
    void should_set_up_wires_correctly() {
        assertThat(TEST_CIRCUIT.getWires()).extractingByKeys(
                "x",
                "y",
                "d",
                "e",
                "f",
                "g",
                "h",
                "i",
                "j"
        ).containsExactly(
                new Signal(123),
                new Signal(456),
                new Connection("x", "y", AND),
                new Connection("x", "y", OR),
                new Operation("x", LSHIFT, 2),
                new Operation("y", RSHIFT, 2),
                new Operation("x", NOT, 0),
                new Operation("y", NOT, 0),
                new Connection("1", "x", AND)
        );
    }

    @ParameterizedTest
    @CsvSource({
            "d,   72",
            "e,  507",
            "f,  492",
            "g,  114",
            "h,65412",
            "i,65079",
            "x,  123",
            "y,  456",
            "z,  123",
            "j,  1"
    })
    void should_retrieve_signal(String wire, int signalValue) {
        assertThat(TEST_CIRCUIT.getSignalOf(wire)).isEqualTo(signalValue);
    }
}