package logger.service.logger;

import logger.config.LoggerConfig;
import logger.enums.LogLevel;
import logger.pojo.LogMessage;
import logger.service.appender.LogAppender;

// 5. Core Logger Hierarchy
public abstract class Logger {
    protected final LoggerConfig config;

    public Logger(LoggerConfig config) {
        this.config = config;
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public void fatal(String message) {
        log(LogLevel.FATAL, message);
    }

    protected void log(LogLevel level, String message) {
        if (level.getLevel() >= config.getMinimumLevel().getLevel()) {
            LogMessage logMessage = new LogMessage(level, message);
            publish(logMessage);
        }
    }

    protected abstract void publish(LogMessage logMessage);

    // Helper to format and write to all appenders
    protected void writeToAppenders(LogMessage logMessage) {
        String formattedOutput = config.getFormatter().format(logMessage);
        for (LogAppender appender : config.getAppenders()) {
            appender.append(formattedOutput);
        }
    }

    // For graceful shutdown of async loggers
    public abstract void shutdown();
}
