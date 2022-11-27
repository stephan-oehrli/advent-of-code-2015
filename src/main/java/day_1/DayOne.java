package day_1;

import utils.FileUtil;

import java.io.FileNotFoundException;

public class DayOne {

    public static void main(String[] args) throws FileNotFoundException {
        String input = FileUtil.readToString("day_1.txt");
        System.out.println(findFloors(input));
        System.out.println(findBasement(input));
    }


    public static int findFloors(String braces) {
        int floor = 0;
        for (char brace : braces.toCharArray()) {
            floor += nextFloor(brace);
        }
        return floor;
    }

    public static int findBasement(String braces) {
        int floor = 0;
        for (int i = 0; i < braces.length(); i++) {
            char brace = braces.charAt(i);
            floor += nextFloor(brace);
            if (floor == -1) {
                return i + 1;
            }
        }
        return 0;
    }

    private static int nextFloor(char brace) {
        if (brace == '(') {
            return 1;
        } else if (brace == ')') {
            return -1;
        }
        return 0;
    }
}
