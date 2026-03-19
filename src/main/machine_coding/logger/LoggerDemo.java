package logger;

import logger.config.LoggerConfig;
import logger.enums.LogLevel;
import logger.service.appender.ConsoleAppender;
import logger.service.formatter.DefaultLogFormatter;
import logger.service.logger.AsyncLogger;
import logger.service.logger.Logger;
import logger.service.logger.SyncLogger;

// 8. Main execution and testing
public class LoggerDemo {
    public static void main(String[] args) {
        // Setup Configuration
        LoggerConfig config = new LoggerConfig(LogLevel.INFO, new DefaultLogFormatter());
        config.addAppender(new ConsoleAppender()); // Can easily add FileAppender here

        System.out.println("--- Testing Synchronous Logger ---");
        Logger syncLogger = new SyncLogger(config);
        syncLogger.debug("This is a sync debug message (should be ignored due to log level).");
        syncLogger.info("This is a sync info message.");
        syncLogger.error("This is a sync error message.");

        System.out.println("\n--- Testing Asynchronous Logger (Multi-threaded) ---");
        // Buffer size 10
        Logger asyncLogger = new AsyncLogger(config, 10);

        // Simulating multiple threads logging concurrently
        Runnable loggingTask = () -> {
            for (int i = 0; i < 3; i++) {
                asyncLogger.info("Task processing step " + i);
            }
        };

        Thread t1 = new Thread(loggingTask, "Worker-1");
        Thread t2 = new Thread(loggingTask, "Worker-2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Gracefully shut down the async logger so the program exits properly
        asyncLogger.shutdown();
        System.out.println("Async logger shutdown successfully.");
    }
}
