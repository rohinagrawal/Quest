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
}
