package message_broker.service.publisher;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import message_broker.service.BrokerService;

@Getter
@RequiredArgsConstructor
public class TopicPublisher implements Publisher {

    private final String publisherId;
    private final String topicName;
    private final BrokerService brokerService;


    @Override
    public void publish(String message) {
        brokerService.publish(topicName, message);
    }
}

