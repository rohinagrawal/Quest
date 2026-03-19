package logger.config;

import logger.enums.LogLevel;
import logger.service.appender.LogAppender;
import logger.service.formatter.LogFormatter;

import java.util.ArrayList;
import java.util.List;

// 4. Configuration
public class LoggerConfig {
    private LogLevel minimumLevel;
    private LogFormatter formatter;
    private final List<LogAppender> appenders;

    public LoggerConfig(LogLevel minimumLevel, LogFormatter formatter) {
        this.minimumLevel = minimumLevel;
        this.formatter = formatter;
        this.appenders = new ArrayList<>();
    }

    public void addAppender(LogAppender appender) {
        appenders.add(appender);
    }

    public LogLevel getMinimumLevel() {
        return minimumLevel;
    }

    public LogFormatter getFormatter() {
        return formatter;
    }

    public List<LogAppender> getAppenders() {
        return appenders;
    }
}
