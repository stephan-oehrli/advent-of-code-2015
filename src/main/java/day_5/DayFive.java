package day_5;

import utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.List;

public class DayFive {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> inputList = FileUtil.readToList("day_5.txt");
        int niceStrings = 0;
        int alsoNiceStrings = 0;

        for (String input : inputList) {
            if (isNiceString(input)) {
                niceStrings++;
            }
            if (isAlsoNiceString(input)) {
                alsoNiceStrings++;
            }
        }

        System.out.println("Nice strings: " + niceStrings);
        System.out.println("Also nice strings: " + alsoNiceStrings);
    }

    public static boolean isNiceString(String input) {
        if (hasDisallowedSubstring(input)) {
            return false;
        }
        if (!hasDoubleLetter(input)) {
            return false;
        }
        return hasThreeVowels(input);
    }

    public static boolean isAlsoNiceString(String input) {
        if (!hasPairOfTwoChars(input)) {
            return false;
        }
        return hasRepeatingLetterWithOneLetterBetween(input);
    }

    private static boolean hasRepeatingLetterWithOneLetterBetween(String input) {
        for (int i = 2; i < input.length(); i++) {
            if (input.charAt(i) == input.charAt(i - 2)
                    && input.charAt(i) != input.charAt(i - 1)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasPairOfTwoChars(String input) {
        for (int i = 1; i < input.length() - 2; i++) {
            String part = "" + input.charAt(i - 1) + input.charAt(i);
            if (input.substring(i + 1).contains(part)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasThreeVowels(String input) {
        int vowelsCount = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                vowelsCount++;
            }
        }
        return vowelsCount > 2;
    }

    private static boolean hasDoubleLetter(String input) {
        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i - 1) == input.charAt(i)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasDisallowedSubstring(String input) {
        return input.contains("ab") || input.contains("cd") ||
                input.contains("pq") || input.contains("xy");
    }
}
