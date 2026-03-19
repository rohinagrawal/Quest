package message_broker.exception;

public class DuplicateTopicException extends RuntimeException {
    public DuplicateTopicException(String topicName) {
        super("Topic already exists: '" + topicName + "'");
    }
}

