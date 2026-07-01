package logger.service.logger;

import logger.config.LoggerConfig;
import logger.pojo.LogMessage;

// 6. Synchronous Logger
public class SyncLogger extends Logger {
    public SyncLogger(LoggerConfig config) {
        super(config);
    }

    @Override
    protected void publish(LogMessage logMessage) {
        writeToAppenders(logMessage); // Writes immediately on the calling thread
    }

    @Override
    public void shutdown() {
        // Nothing to shut down for Sync
    }
}
