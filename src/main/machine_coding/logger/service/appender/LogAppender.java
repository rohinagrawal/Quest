package logger.service.appender;

// 3. Appender / Destination (Strategy / Observer Pattern)
public interface LogAppender {
    void append(String formattedMessage);
}
