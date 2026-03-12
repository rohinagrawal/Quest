package logger.service.formatter;

import logger.pojo.LogMessage;

import java.time.format.DateTimeFormatter;

public class DefaultLogFormatter implements LogFormatter {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public String format(LogMessage msg) {
        return String.format("[%s] [%s] [%s] - %s",
                dtf.format(msg.getTimestamp()),
                msg.getThreadName(),
                msg.getLevel().name(),
                msg.getMessage());
    }
}
