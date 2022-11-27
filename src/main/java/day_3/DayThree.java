package day_3;

import utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

public class DayThree {

    public static void main(String[] args) throws FileNotFoundException {
        String instructions = FileUtil.readToString("day_3.txt");

        int deliveredHousesWithoutRobotSanta = countDeliveredHouses(instructions);
        int deliveredHousesWithRobotSanta = countDeliveredHousesWithRobotSanta(instructions);
        System.out.println("Delivered houses without robot santa: " + deliveredHousesWithoutRobotSanta);
        System.out.println("Delivered houses with robot santa: " + deliveredHousesWithRobotSanta);
    }

    public static int countDeliveredHouses(String instructions) {
        Set<Coordinates> coordinates = new HashSet<>();
        Coordinates position = new Coordinates(0, 0);
        coordinates.add(position);

        for (char instruction : instructions.toCharArray()) {
            position = nexCoordinates(position, instruction);
            coordinates.add(position);
        }

        return coordinates.size();
    }

    public static int countDeliveredHousesWithRobotSanta(String instructions) {
        Set<Coordinates> coordinates = new HashSet<>();
        Coordinates startPosition = new Coordinates(0, 0);
        Coordinates positionSanta = startPosition;
        Coordinates positionRobot = startPosition;
        coordinates.add(startPosition);

        for (int i = 0; i < instructions.length(); i++) {
            char instruction = instructions.charAt(i);
            if (i % 2 == 0) {
                positionRobot = nexCoordinates(positionRobot, instruction);
                coordinates.add(positionRobot);
            } else {
                positionSanta = nexCoordinates(positionSanta, instruction);
                coordinates.add(positionSanta);
            }
        }

        return coordinates.size();
    }

    private static Coordinates nexCoordinates(Coordinates position, char instruction) {
        if (instruction == '^') {
            return new Coordinates(position.x, position.y + 1);
        } else if (instruction == 'v') {
            return new Coordinates(position.x, position.y - 1);
        } else if (instruction == '>') {
            return new Coordinates(position.x + 1, position.y);
        } else if (instruction == '<') {
            return new Coordinates(position.x - 1, position.y);
        }

        return position;
    }

    private static class Coordinates {
        int x;
        int y;

        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Coordinates that = (Coordinates) o;

            if (x != that.x) return false;
            return y == that.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}
