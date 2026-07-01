package message_broker.exception;

public class PublisherNotFoundException extends RuntimeException {
    public PublisherNotFoundException(String publisherId) {
        super("Publisher not found: '" + publisherId + "'");
    }
}

