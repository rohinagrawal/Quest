package message_broker.service;

import message_broker.config.BrokerConfig;
import message_broker.dto.TopicInfo;
import message_broker.pojo.Message;
import message_broker.service.consumer.Consumer;
import message_broker.service.publisher.Publisher;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("InMemoryBrokerService Functional Flow Tests")
class InMemoryBrokerServiceTest {

    private InMemoryBrokerService broker;

    @BeforeEach
    void setUp() {
        broker = new InMemoryBrokerService(new BrokerConfig(60));
    }

    @AfterEach
    void tearDown() {
        broker.shutdown();
    }

    @Test
    @DisplayName("Full flow: publisher -> broker -> consumer delivers ordered messages with metadata")
    void fullFlowDeliversOrderedMessages() throws InterruptedException {
        broker.createTopic("orders", Duration.ofHours(1));
        Publisher publisher = broker.registerPublisher("p1", "orders");

        CountDownLatch latch = new CountDownLatch(3);
        List<Message> received = Collections.synchronizedList(new ArrayList<>());
        broker.registerConsumer("c1", "orders", consumer("c1", msg -> {
            received.add(msg);
            latch.countDown();
        }));

        publisher.publish("a");
        publisher.publish("b");
        publisher.publish("c");

        assertThat(latch.await(3, TimeUnit.SECONDS)).isTrue();
        assertThat(received).hasSize(3);
        assertThat(received).extracting(Message::getContent).containsExactly("a", "b", "c");
        assertThat(received).extracting(Message::getOffset).containsExactly(0L, 1L, 2L);
        assertThat(received).allMatch(msg -> msg.getTopicName().equals("orders"));
    }

    @Test
    @DisplayName("Multiple consumers each receive the full stream independently")
    void multipleConsumersReceiveIndependently() throws InterruptedException {
        broker.createTopic("orders", Duration.ofHours(1));
        Publisher publisher = broker.registerPublisher("p1", "orders");

        CountDownLatch latch = new CountDownLatch(6);
        List<String> delivered = Collections.synchronizedList(new ArrayList<>());
        broker.registerConsumer("c1", "orders", consumer("c1", msg -> {
            delivered.add("c1-" + msg.getContent());
            latch.countDown();
        }));
        broker.registerConsumer("c2", "orders", consumer("c2", msg -> {
            delivered.add("c2-" + msg.getContent());
            latch.countDown();
        }));

        publisher.publish("m0");
        publisher.publish("m1");
        publisher.publish("m2");

        assertThat(latch.await(3, TimeUnit.SECONDS)).isTrue();
        assertThat(delivered).containsExactlyInAnyOrder(
                "c1-m0", "c1-m1", "c1-m2",
                "c2-m0", "c2-m1", "c2-m2");
    }

    @Test
    @DisplayName("Late consumer can replay the full topic from offset 0 using resetOffset")
    void lateConsumerCanReplayFromStart() throws InterruptedException {
        broker.createTopic("orders", Duration.ofHours(1));
        Publisher publisher = broker.registerPublisher("p1", "orders");

        CountDownLatch firstLatch = new CountDownLatch(2);
        broker.registerConsumer("c1", "orders", consumer("c1", msg -> firstLatch.countDown()));

        publisher.publish("old-0");
        publisher.publish("old-1");

        assertThat(firstLatch.await(3, TimeUnit.SECONDS)).isTrue();

        CountDownLatch replayLatch = new CountDownLatch(2);
        List<Long> offsets = Collections.synchronizedList(new ArrayList<>());
        broker.registerConsumer("c2", "orders", consumer("c2", msg -> {
            offsets.add(msg.getOffset());
            replayLatch.countDown();
        }));

        broker.resetOffset("c2", "orders", 0L);

        assertThat(replayLatch.await(3, TimeUnit.SECONDS)).isTrue();
        assertThat(offsets).containsExactly(0L, 1L);
    }

    @Test
    @DisplayName("Expired messages are evicted but fresh ones continue to flow")
    void retentionEvictionKeepsFreshMessages() throws InterruptedException {
        InMemoryBrokerService retentionBroker = new InMemoryBrokerService(new BrokerConfig(1));
        try {
            retentionBroker.createTopic("events", Duration.ofMillis(100));
            Publisher publisher = retentionBroker.registerPublisher("pub", "events");
            publisher.publish("expired");

            Thread.sleep(1_500);

            CountDownLatch replayLatch = new CountDownLatch(1);
            List<Message> replayed = Collections.synchronizedList(new ArrayList<>());
            retentionBroker.registerConsumer("c1", "events", consumer("c1", msg -> {
                replayed.add(msg);
                replayLatch.countDown();
            }));

            retentionBroker.resetOffset("c1", "events", 0L);
            assertThat(replayLatch.await(500, TimeUnit.MILLISECONDS)).isFalse();
            assertThat(replayed).isEmpty();

            CountDownLatch freshLatch = new CountDownLatch(1);
            List<Message> fresh = Collections.synchronizedList(new ArrayList<>());
            retentionBroker.registerConsumer("c2", "events", consumer("c2", msg -> {
                fresh.add(msg);
                freshLatch.countDown();
            }));
            publisher.publish("fresh");

            assertThat(freshLatch.await(3, TimeUnit.SECONDS)).isTrue();
            assertThat(fresh).hasSize(1);
            assertThat(fresh.get(0).getContent()).isEqualTo("fresh");
        } finally {
            retentionBroker.shutdown();
        }
    }

    @Test
    @DisplayName("listTopics returns names with retention periods")
    void listTopicsReturnsRetention() {
        broker.createTopic("orders", Duration.ofSeconds(30));
        broker.createTopic("payments", Duration.ofMinutes(2));

        List<TopicInfo> topics = broker.listTopics();

        assertThat(topics).extracting(TopicInfo::getName)
                .containsExactlyInAnyOrder("orders", "payments");
        assertThat(topics).filteredOn(t -> t.getName().equals("orders"))
                .first()
                .extracting(TopicInfo::getRetentionPeriod)
                .isEqualTo(Duration.ofSeconds(30));
        assertThat(topics).filteredOn(t -> t.getName().equals("payments"))
                .first()
                .extracting(TopicInfo::getRetentionPeriod)
                .isEqualTo(Duration.ofMinutes(2));
    }

    @Test
    @DisplayName("publishByPublisher fails when publisher is not registered")
    void publishByPublisherFailsForUnknownPublisher() {
        broker.createTopic("orders", Duration.ofHours(1));

        assertThatThrownBy(() -> broker.publishByPublisher("missing", "payload"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Publisher not found");
    }

    @Test
    @DisplayName("deleteTopic unregisters publishers bound to that topic")
    void deleteTopicRemovesPublishersForTopic() {
        broker.createTopic("orders", Duration.ofHours(1));
        Publisher publisher = broker.registerPublisher("p1", "orders");

        broker.deleteTopic("orders");

        assertThatThrownBy(() -> publisher.publish("after-delete"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Publisher not found");
    }

    private Consumer consumer(String id, java.util.function.Consumer<Message> handler) {
        return new Consumer() {
            @Override
            public String getConsumerId() {
                return id;
            }

            @Override
            public void onMessage(Message message) {
                handler.accept(message);
            }
        };
    }
}
