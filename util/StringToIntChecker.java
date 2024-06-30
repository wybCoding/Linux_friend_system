package util;

public class StringToIntChecker {
    public static boolean isInteger(String a) {
        try {
            Integer.parseInt(a);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
