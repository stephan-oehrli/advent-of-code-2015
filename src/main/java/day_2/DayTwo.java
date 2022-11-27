package day_2;

import utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class DayTwo {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> dimensions = FileUtil.readToList("day_2.txt");
        int totalArea = 0;
        int totalRibbon = 0;
        for (String dimension : dimensions) {
            totalArea += calculateRequiredArea(dimension);
            totalRibbon += calculateRequiredRibbon(dimension);
        }
        System.out.println("total area: " + totalArea);
        System.out.println("total ribbon: " + totalRibbon);
    }

    public static int calculateRequiredArea(String dimension) {
        String[] split = dimension.split("x");
        int a = Integer.parseInt(split[0]) * Integer.parseInt(split[1]);
        int b = Integer.parseInt(split[0]) * Integer.parseInt(split[2]);
        int c = Integer.parseInt(split[1]) * Integer.parseInt(split[2]);
        return 2 * (a + b + c) + Math.min(a, Math.min(b, c));
    }

    public static int calculateRequiredRibbon(String dimension) {
        String[] split = dimension.split("x");
        int[] dim = {
                Integer.parseInt(split[0]),
                Integer.parseInt(split[1]),
                Integer.parseInt(split[2])
        };
        Arrays.sort(dim);
        return 2 * (dim[0] + dim[1]) + dim[0] * dim[1] * dim[2];
    }
}
