package message_broker.utils;

import java.time.Duration;

public class Util {
    public static String formatRetention(Duration retention) {
        long seconds = retention.getSeconds();
        if (seconds % 3600 == 0) {
            return (seconds / 3600) + "h";
        }
        if (seconds % 60 == 0) {
            return (seconds / 60) + "m";
        }
        return seconds + "s";
    }

    public static void requireNonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
    }

    public static void requireNonNull(Object value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " must not be null");
        }
    }

    public static void requirePositive(Duration value, String fieldName) {
        if (value == null || value.isZero() || value.isNegative()) {
            throw new IllegalArgumentException(fieldName + " must be positive");
        }
    }
}
