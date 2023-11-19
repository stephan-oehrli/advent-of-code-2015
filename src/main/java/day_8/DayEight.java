package day_8;

import lombok.experimental.UtilityClass;
import utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.List;

public class DayEight {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> inputList = FileUtil.readToList("day_8.txt");
        System.out.println("String difference: " + StringSizeCounter.calculateStringDifference(inputList));
        System.out.println("Encoded difference: " + StringSizeCounter.calculateEncodedDifference(inputList));
    }

    @UtilityClass
    public static class StringSizeCounter {

        public static int countCodeSize(String str) {
            return str.length();
        }

        public static int countStringSize(String str) {
            str = str.replace("\\\"", "r"); // ( \" => r )
            str = str.replace("\\\\", "r"); // ( \\ => r )
            str = str.replaceAll("\\\\x[0-9a-f][0-9a-f]", "r"); // ( \x00 => r )
            return str.length() - 2;
        }

        public static int countEncodedSize(String str) {
            str = str.replace("\\", "\\\\"); // ( \ => \\ )
            str = str.replace("\"", "\\\""); // ( " => \" )
            return str.length() + 2;
        }

        public static long calculateStringDifference(List<String> strings) {
            long result = 0;
            for (String str : strings) {
                result += StringSizeCounter.countCodeSize(str);
                result -= StringSizeCounter.countStringSize(str);
            }
            return result;
        }

        public static long calculateEncodedDifference(List<String> strings) {
            long result = 0;
            for (String str : strings) {
                result += StringSizeCounter.countEncodedSize(str);
                result -= StringSizeCounter.countCodeSize(str);
            }
            return result;
        }
    }
}
