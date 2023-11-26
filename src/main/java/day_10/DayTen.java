package day_10;

import lombok.experimental.UtilityClass;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

public class DayTen {

    public static void main(String[] args) {
        String number = "3113322113";
        System.out.println("Length after 40 iterations: " + LookAndSay.play(number, 40).length());
        System.out.println("Length after 50 iterations: " + LookAndSay.play(number, 50).length());
    }

    @UtilityClass
    public static class LookAndSay {

        public static String play(String number, int times) {
            for (int i = 0; i < times; i++) {
                number = say(number);
            }
            return number;
        }

        public static String say(String look) {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < look.length(); i++) {
                char number = look.charAt(i);
                int times = 1;
                while (i + times < look.length() && look.charAt(i + times) == number) {
                    times++;
                }
                result.append(times).append(parseInt(valueOf(number)));
                i += times - 1;
            }
            return result.toString();
        }
    }
}
