package message_broker.service.publisher;

public interface Publisher {
    String getPublisherId();
    String getTopicName();
    void publish(String message);
}

