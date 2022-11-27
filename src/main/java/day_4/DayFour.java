package day_4;

import org.apache.commons.codec.digest.DigestUtils;
import utils.FileUtil;

import java.io.FileNotFoundException;

public class DayFour {

    public static void main(String[] args) throws FileNotFoundException {
        String secretKey = FileUtil.readToString("day_4.txt");
        
        int lowestNumberForHashWithFiveZeros = findLowestNumberForHashWithDefinedString(secretKey, "00000");
        int lowestNumberForHashWithSixZeros = findLowestNumberForHashWithDefinedString(secretKey, "000000");

        System.out.println("First task result: " + lowestNumberForHashWithFiveZeros);
        System.out.println("Second task result: " + lowestNumberForHashWithSixZeros);
    }

    public static int findLowestNumberForHashWithDefinedString(String secretKey, String start) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String hash = DigestUtils.md5Hex(secretKey + i);
            if (hash.startsWith(start)) {
                return i;
            }
        }
        return 0;
    }
}
