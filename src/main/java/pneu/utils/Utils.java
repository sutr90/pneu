package pneu.utils;

public class Utils {
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str.trim());
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
