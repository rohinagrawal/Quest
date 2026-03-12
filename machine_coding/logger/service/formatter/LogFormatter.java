package logger.service.formatter;

import logger.pojo.LogMessage;

// 2. Formatter (Strategy Pattern)
public interface LogFormatter {
    String format(LogMessage logMessage);
}
