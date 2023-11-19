package day_7;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

import static day_7.DaySeven.ConnectionType.AND;
import static day_7.DaySeven.OperationType.LSHIFT;
import static day_7.DaySeven.OperationType.NOT;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class DaySeven {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> inputList = FileUtil.readToList("day_7.txt");
        Circuit circuit = Circuit.of(inputList);
        int firstRun = circuit.getSignalOf("a");
        System.out.println("First run: " + firstRun);
        circuit.getOutputs().clear();
        circuit.getOutputs().put("b", firstRun);
        int secondRun = circuit.getSignalOf("a");
        System.out.println("Second run: " + secondRun);
    }

    private static final String INPUT_DELIMITER = " -> ";

    @Getter
    public static final class Circuit {
        private final HashMap<String, Input> wires = new HashMap<>();
        private final HashMap<String, Integer> outputs = new HashMap<>();

        private Circuit() {
        }

        public static Circuit of(List<String> inputs) {
            Circuit circuit = new Circuit();
            for (String input : inputs) {
                String target = input.split(INPUT_DELIMITER)[1];
                circuit.wires.put(target, InputParser.parse(input));
            }
            return circuit;
        }

        public int getSignalOf(String wire) {
            Input input = wires.get(wire);
            if (outputs.containsKey(wire)) {
                return outputs.get(wire);
            }
            if (input instanceof Signal signal) {
                outputs.put(wire, signal.getValue());
                return signal.getValue();
            }
            if (input instanceof Connection connection) {
                int signalA = isNumeric(connection.wireA) ? parseInt(connection.wireA) : getSignalOf(connection.wireA);
                int signalB = isNumeric(connection.wireB) ? parseInt(connection.wireB) : getSignalOf(connection.wireB);
                int value = connection.getType() == AND ? signalA & signalB : signalA | signalB;
                outputs.put(wire, value);
                return value;
            }
            if (input instanceof Operation operation && operation.getType() != NOT) {
                int signal = getSignalOf(operation.getWire());
                int shiftByValue = operation.getShiftByValue();
                int value = operation.getType() == LSHIFT ? signal << shiftByValue : signal >> shiftByValue;
                outputs.put(wire, value);
                return value;
            }
            if (input instanceof Operation operation) {
                int signal = getSignalOf(operation.getWire());
                int value = ~signal & 0xffff;
                outputs.put(wire, value);
                return value;
            }
            if (input instanceof Link link) {
                int value = getSignalOf(link.getWire());
                outputs.put(wire, value);
                return value;
            }
            throw new IllegalStateException("No signal found for wire: " + wire);
        }
    }

    @UtilityClass
    public static final class InputParser {

        public static Input parse(String input) {
            String[] split = input.split(INPUT_DELIMITER);
            if (isNumeric(split[0])) {
                return new Signal(parseInt(split[0]));
            }
            if (StringUtils.containsAny(split[0], "AND", "OR")) {
                String[] connectionSplit = StringUtils.split(split[0]);
                ConnectionType type = ConnectionType.valueOf(connectionSplit[1]);
                return new Connection(connectionSplit[0], connectionSplit[2], type);
            }
            if (StringUtils.contains(split[0], "SHIFT")) {
                String[] operationSplit = StringUtils.split(split[0]);
                OperationType type = OperationType.valueOf(operationSplit[1]);
                int shiftByValue = parseInt(operationSplit[2]);
                return new Operation(operationSplit[0], type, shiftByValue);
            }
            if (StringUtils.contains(split[0], "NOT")) {
                return new Operation(StringUtils.split(split[0])[1], NOT, 0);
            }
            if (!StringUtils.contains(split[0], " ") && !StringUtils.contains(split[1], " ")) {
                return new Link(split[0]);
            }
            throw new IllegalStateException("Can not parse input: " + input);
        }
    }

    public static abstract class Input {
    }

    @Getter
    @EqualsAndHashCode(callSuper = false)
    @RequiredArgsConstructor
    public static class Signal extends Input {
        private final int value;
    }

    @Getter
    @EqualsAndHashCode(callSuper = false)
    @RequiredArgsConstructor
    public static class Link extends Input {
        private final String wire;
    }

    @Getter
    @EqualsAndHashCode(callSuper = false)
    @RequiredArgsConstructor
    public static class Connection extends Input {
        private final String wireA;
        private final String wireB;
        private final ConnectionType type;
    }

    @Getter
    @EqualsAndHashCode(callSuper = false)
    @RequiredArgsConstructor
    public static class Operation extends Input {
        private final String wire;
        private final OperationType type;
        private final int shiftByValue;
    }

    public enum ConnectionType {
        AND, OR
    }

    public enum OperationType {
        NOT, LSHIFT, RSHIFT
    }
}
