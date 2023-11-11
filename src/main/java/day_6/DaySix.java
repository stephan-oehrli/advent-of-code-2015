package day_6;

import lombok.Data;
import lombok.experimental.UtilityClass;
import utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static day_6.DaySix.Instruction.*;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.StringUtils.remove;
import static org.apache.commons.lang3.StringUtils.split;

public class DaySix {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> inputList = FileUtil.readToList("day_6.txt");
        LightGrid lightGrid = new LightGrid();
        BrightnessGrid brightnessGrid = new BrightnessGrid();

        inputList.forEach((command) -> {
            lightGrid.runCommand(command);
            brightnessGrid.runCommand(command);
        });
        System.out.println("Lights on count: " + lightGrid.countLightsOn());
        System.out.println("Brightness: " + brightnessGrid.getBrightness());
    }

    public static class LightGrid {

        protected final List<List<Light>> grid;

        public LightGrid() {
            grid = new ArrayList<>();
            for (int y = 0; y < 1000; y++) {
                grid.add(new ArrayList<>());
                for (int x = 0; x < 1000; x++) {
                    grid.get(y).add(new Light());
                }
            }
        }

        public void runCommand(String commandString) {
            Command command = CommandParser.parse(commandString);
            for (int y = command.startCoordinates().y(); y <= command.endCoordinates().y(); y++) {
                for (int x = command.startCoordinates().x(); x <= command.endCoordinates().x(); x++) {
                    apply(command, grid.get(y).get(x));
                }
            }
        }

        protected void apply(Command command, Light light) {
            switch (command.instruction) {
                case ON -> light.setOn(true);
                case OFF -> light.setOn(false);
                case TOGGLE -> light.setOn(!light.isOn());
            }
        }

        public Long countLightsOn() {
            Long count = 0L;
            for (int y = 0; y < 1000; y++) {
                for (int x = 0; x < 1000; x++) {
                    if (grid.get(y).get(x).isOn) {
                        count++;
                    }
                }
            }
            return count;
        }
    }

    public static class BrightnessGrid extends LightGrid {

        @Override
        protected void apply(Command command, Light light) {
            switch (command.instruction) {
                case ON -> light.setBrightness(light.getBrightness() + 1);
                case OFF -> light.setBrightness(light.getBrightness() > 0 ? light.getBrightness() - 1 : 0);
                case TOGGLE -> light.setBrightness(light.getBrightness() + 2);
            }
        }

        public Long getBrightness() {
            Long count = 0L;
            for (int y = 0; y < 1000; y++) {
                for (int x = 0; x < 1000; x++) {
                    count += grid.get(y).get(x).getBrightness();
                }
            }
            return count;
        }
    }

    @UtilityClass
    public static class CommandParser {

        public static Command parse(String command) {
            if (command.startsWith("turn on")) {
                return parse(command, "turn on ", ON);
            }
            if (command.startsWith("turn off")) {
                return parse(command, "turn off ", OFF);
            }
            if (command.startsWith("toggle")) {
                return parse(command, "toggle ", TOGGLE);
            }
            throw new IllegalStateException("Can not parse command: " + command);
        }

        private static Command parse(String command, String removeString, Instruction instruction) {
            command = remove(command, removeString);
            String[] split = split(command, " through ");
            return new Command(instruction, Coordinates.of(split[0]), Coordinates.of(split[1]));
        }
    }

    @Data
    public static class Light {
        
        private boolean isOn = false;
        private Long brightness = 0L;
    }

    public record Coordinates(int x, int y) {

        public static Coordinates of(int x, int y) {
            return new Coordinates(x, y);
        }

        public static Coordinates of(String coordinates) {
            String[] split = split(coordinates, ',');
            return new Coordinates(parseInt(split[0]), parseInt(split[1]));
        }
    }

    public record Command(Instruction instruction, Coordinates startCoordinates, Coordinates endCoordinates) {
    }

    public enum Instruction {
        ON, OFF, TOGGLE
    }
}
