package message_broker.publisher;

import message_broker.service.BrokerService;
import message_broker.service.publisher.TopicPublisher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@DisplayName("TopicPublisher Tests")
class TopicPublisherTest {

    @Test
    @DisplayName("publish should delegate to broker service with configured topic")
    void publishDelegatesToBroker() {
        BrokerService broker = Mockito.mock(BrokerService.class);
        TopicPublisher publisher = new TopicPublisher("pub-1", "orders", broker);

        publisher.publish("hello");

        verify(broker).publishByPublisher("pub-1", "hello");
        verifyNoMoreInteractions(broker);
    }

    @Test
    @DisplayName("publisher retains its identifiers and allows multiple publishes")
    void publishMultipleMessages() {
        BrokerService broker = Mockito.mock(BrokerService.class);
        TopicPublisher publisher = new TopicPublisher("pub-9", "payments", broker);

        publisher.publish("m1");
        publisher.publish("m2");

        verify(broker).publishByPublisher("pub-9", "m1");
        verify(broker).publishByPublisher("pub-9", "m2");
        assertThat(publisher.getPublisherId()).isEqualTo("pub-9");
        assertThat(publisher.getTopicName()).isEqualTo("payments");
    }
}

