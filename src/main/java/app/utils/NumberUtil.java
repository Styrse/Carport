package app.utils;

public class NumberUtil {
    public static float parseFloat(String input, float fallback) {
        if (input == null || input.isBlank()) return fallback;
        try {
            return Float.parseFloat(input);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }
}
