package logger.service.appender;

public class ConsoleAppender implements LogAppender {
    @Override
    public void append(String formattedMessage) {
        // System.out.println is thread-safe, but synchronized prevents output interleaving
        // from different appenders if complex streams are used.
        synchronized (System.out) {
            System.out.println(formattedMessage);
        }
    }
}
