package message_broker.repository;

import lombok.Data;
import message_broker.pojo.Topic;
import message_broker.service.consumer.Consumer;
import message_broker.service.publisher.Publisher;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

@Data
public class InMemory {

    // topic name  →  Topic
    private final Map<String, Topic> topics = new ConcurrentHashMap<>();

    // consumerId  →  Consumer callback
    private final Map<String, Consumer> consumerRegistry = new ConcurrentHashMap<>();

    // consumerId  →  single-threaded executor (guarantees per-consumer ordering)
    private final Map<String, ExecutorService> consumerExecutors = new ConcurrentHashMap<>();

    // publisherId → Publisher
    private final Map<String, Publisher> publisherRegistry = new ConcurrentHashMap<>();
}
