package message_broker.service;

import message_broker.dto.ConsumerInfo;
import message_broker.dto.TopicInfo;
import message_broker.service.consumer.Consumer;
import message_broker.service.publisher.Publisher;

import java.time.Duration;
import java.util.List;

public interface BrokerService {
    void createTopic(String topicName, Duration retentionPeriod);
    void deleteTopic(String topicName);
    Publisher registerPublisher(String publisherId, String topicName);
    void registerConsumer(String consumerId, String topicName, Consumer consumer);
    void publishByPublisher(String publisherId, String message);
    void publish(String topicName, String message);
    void resetOffset(String consumerId, String topicName, long newOffset);
    ConsumerInfo getConsumerInfo(String consumerId, String topicName);
    List<TopicInfo> listTopics();
    void shutdown();
}

