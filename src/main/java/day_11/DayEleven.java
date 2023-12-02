package day_11;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import utils.FileUtil;

import java.io.FileNotFoundException;

public class DayEleven {

    public static void main(String[] args) throws FileNotFoundException {
        String secret = FileUtil.readToString("day_11.txt");
        String newPassword = PasswordGenerator.generateValidFrom(secret);
        System.out.println("Next valid password is: " + newPassword);
        System.out.println("Next valid password is: " + PasswordGenerator.generateValidFrom(newPassword));
    }

    @UtilityClass
    protected static class PasswordGenerator {

        public String generateFrom(String previousPassword) {
            char[] chars = previousPassword.toCharArray();
            boolean isTransfer = true;
            for (int i = chars.length - 1; i >= 0; i--) {
                if (isTransfer) {
                    int asciiValue = chars[i];
                    isTransfer = asciiValue == 122;
                    chars[i] = isTransfer ? (char) 97 : (char) (asciiValue + 1);
                }
            }
            return String.valueOf(chars);
        }

        public String generateValidFrom(String previousPassword) {
            String newPassword = generateFrom(previousPassword);
            while (!PasswordValidator.isValid(newPassword)) {
                newPassword = generateFrom(newPassword);
            }
            return newPassword;
        }
    }

    @UtilityClass
    protected static class PasswordValidator {

        public boolean isValid(String password) {
            if (StringUtils.containsAny(password, "i", "o", "l")) {
                return false;
            }
            char[] chars = password.toCharArray();
            if (!hasIncreasingStraight(chars)) {
                return false;
            }
            return hasTwoDifferentPairs(chars);
        }

        private static boolean hasIncreasingStraight(char[] chars) {
            for (int i = 0; i < chars.length - 2; i++) {
                if (chars[i] - chars[i + 1] == -1 && chars[i + 1] - chars[i + 2] == -1) {
                    return true;
                }
            }
            return false;
        }

        private static boolean hasTwoDifferentPairs(char[] chars) {
            Character firstPairCharacter = null;
            for (int i = 0; i < chars.length - 1; i++) {
                if (chars[i] == chars[i + 1]) {
                    if (firstPairCharacter == null) {
                        firstPairCharacter = chars[i];
                    } else if (!firstPairCharacter.equals(chars[i])) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
