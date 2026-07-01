package logger.service.logger;

import logger.config.LoggerConfig;
import logger.pojo.LogMessage;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

// 7. Asynchronous Logger (Multi-threaded producer-consumer)
public class AsyncLogger extends Logger {
    private final BlockingQueue<LogMessage> buffer;
    private final Thread workerThread;
    private volatile boolean isRunning;

    public AsyncLogger(LoggerConfig config, int bufferSize) {
        super(config);
        this.buffer = new ArrayBlockingQueue<>(bufferSize);
        this.isRunning = true;

        this.workerThread = new Thread(() -> {
            while (isRunning || !buffer.isEmpty()) {
                try {
                    // take() blocks until a message is available
                    LogMessage msg = buffer.take();
                    // Poison pill check for shutdown
                    if (msg.getLevel() == null && msg.getMessage().equals("POISON_PILL")) {
                        continue;
                    }
                    writeToAppenders(msg);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "AsyncLogger-Worker-Thread");

        this.workerThread.start();
    }

    @Override
    protected void publish(LogMessage logMessage) {
        if (!isRunning) return;
        try {
            // put() blocks if the buffer is full (backpressure)
            buffer.put(logMessage);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Failed to enqueue log message asynchronously.");
        }
    }

    @Override
    public void shutdown() {
        isRunning = false;
        try {
            // Inject a poison pill to wake up the worker thread if it's blocked on take()
            buffer.put(new LogMessage(null, "POISON_PILL"));
            workerThread.join(); // Wait for the worker to finish draining the queue
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
