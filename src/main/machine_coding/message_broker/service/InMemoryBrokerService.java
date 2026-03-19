package message_broker.service;

import lombok.extern.slf4j.Slf4j;
import message_broker.config.BrokerConfig;
import message_broker.dto.ConsumerInfo;
import message_broker.dto.TopicInfo;
import message_broker.exception.DuplicateTopicException;
import message_broker.exception.PublisherNotFoundException;
import message_broker.exception.TopicNotFoundException;
import message_broker.pojo.ConsumerState;
import message_broker.pojo.Message;
import message_broker.pojo.Topic;
import message_broker.repository.InMemory;
import message_broker.service.consumer.Consumer;
import message_broker.service.publisher.Publisher;
import message_broker.service.publisher.TopicPublisher;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static message_broker.utils.Util.*;

@Slf4j
public class InMemoryBrokerService implements BrokerService {

    private final InMemory inMemory;

    // Background scheduler for retention eviction
    private final ScheduledExecutorService retentionScheduler;

    private volatile boolean isRunning = true;

    public InMemoryBrokerService(BrokerConfig config) {
        Objects.requireNonNull(config, "config must not be null");
        this.inMemory = new InMemory();
        long interval = config.getEvictionIntervalSeconds();
        retentionScheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "BrokerRetentionScheduler");
            t.setDaemon(true);
            return t;
        });
        retentionScheduler.scheduleAtFixedRate(this::runRetentionEviction, interval, interval, TimeUnit.SECONDS);
    }

    @Override
    public void createTopic(String topicName, Duration retentionPeriod) {
        checkRunning();
        requireNonBlank(topicName, "topicName");
        requirePositive(retentionPeriod, "retentionPeriod");
        if (inMemory.getTopics().putIfAbsent(topicName, new Topic(topicName, retentionPeriod)) != null) {
            throw new DuplicateTopicException(topicName);
        }
        log.info("Topic created: '{}' (retention={})", topicName, retentionPeriod);
    }

    @Override
    public void deleteTopic(String topicName) {
        checkRunning();
        requireNonBlank(topicName, "topicName");
        Topic topic = inMemory.getTopics().remove(topicName);
        if (topic == null) {
            throw new TopicNotFoundException(topicName);
        }

        for (ConsumerState state : topic.getAllConsumerStates()) {
            String consumerId = state.getConsumerId();
            inMemory.getConsumerRegistry().remove(consumerId);
            ExecutorService executor = inMemory.getConsumerExecutors().remove(consumerId);
            if (executor != null) {
                shutdownExecutorGracefully(executor, consumerId);
            }
        }

        inMemory.getPublisherRegistry().entrySet()
                .removeIf(entry -> topicName.equals(entry.getValue().getTopicName()));
        log.info("Topic deleted: '{}'", topicName);
    }

    @Override
    public Publisher registerPublisher(String publisherId, String topicName) {
        checkRunning();
        requireNonBlank(publisherId, "publisherId");
        requireNonBlank(topicName, "topicName");
        getTopicOrThrow(topicName);

        Publisher publisher = new TopicPublisher(publisherId, topicName, this);

        if (inMemory.getPublisherRegistry().putIfAbsent(publisherId, publisher) != null) {
            throw new IllegalStateException("Publisher '" + publisherId + "' is already registered. Use a unique publisherId.");
        }
        log.info("Publisher '{}' registered on topic '{}'", publisherId, topicName);
        return publisher;
    }

    @Override
    public void registerConsumer(String consumerId, String topicName, Consumer consumer) {
        checkRunning();
        requireNonBlank(consumerId, "consumerId");
        requireNonBlank(topicName, "topicName");
        Objects.requireNonNull(consumer, "consumer must not be null");
        Topic topic = getTopicOrThrow(topicName);

        if (!consumerId.equals(consumer.getConsumerId())) {
            throw new IllegalArgumentException("consumerId must match consumer.getConsumerId()");
        }

        if (inMemory.getConsumerRegistry().putIfAbsent(consumerId, consumer) != null) {
            throw new IllegalStateException("Consumer '" + consumerId + "' is already registered. Use a unique consumerId.");
        }

        long initialOffset = topic.getLatestOffset();
        ConsumerState state = new ConsumerState(consumerId, topicName, initialOffset);
        topic.registerConsumerState(state);

        ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
            Thread thread = new Thread(r, "Consumer-" + consumerId);
            thread.setDaemon(true);
            return thread;
        });
        inMemory.getConsumerExecutors().put(consumerId, executor);

        log.info("Consumer '{}' registered on topic '{}' (initialOffset={})", consumerId, topicName, initialOffset);
    }

    @Override
    public void publishByPublisher(String publisherId, String message) {
        checkRunning();
        requireNonBlank(publisherId, "publisherId");
        requireNonNull(message, "message");

        Publisher publisher = inMemory.getPublisherRegistry().get(publisherId);
        if (publisher == null) {
            throw new PublisherNotFoundException(publisherId);
        }
        publishInternal(publisher.getTopicName(), message, publisherId);
    }

    @Override
    public void publish(String topicName, String message) {
        checkRunning();
        requireNonBlank(topicName, "topicName");
        requireNonNull(message, "message");
        publishInternal(topicName, message, "direct");
    }

    private void publishInternal(String topicName, String message, String source) {
        Topic topic = getTopicOrThrow(topicName);

        Message msg = topic.publish(message);
        log.info("Published to '{}' by '{}' -> {}", topicName, source, msg);

        for (ConsumerState state : topic.getAllConsumerStates()) {
            dispatchToConsumer(state, msg);
        }
    }

    @Override
    public void resetOffset(String consumerId, String topicName, long newOffset) {
        checkRunning();
        requireNonBlank(consumerId, "consumerId");
        requireNonBlank(topicName, "topicName");
        Topic topic = getTopicOrThrow(topicName);
        ConsumerState state = getConsumerStateOrThrow(topic, consumerId);

        long effectiveOffset = Math.max(0L, newOffset);
        if (!topic.getMessages().isEmpty()) {
            long firstAvailable = topic.getMessages().firstKey();
            if (effectiveOffset < firstAvailable) {
                log.info("Requested offset {} was evicted. Rewinding consumer '{}' to earliest available {}", effectiveOffset, consumerId, firstAvailable);
                effectiveOffset = firstAvailable;
            }
        }

        state.setOffset(effectiveOffset);
        log.info("Consumer '{}' offset reset to {} on topic '{}'", consumerId, effectiveOffset, topicName);

        List<Message> replayMessages = new ArrayList<>(topic.getMessages().tailMap(effectiveOffset, true).values());
        Consumer consumer = inMemory.getConsumerRegistry().get(consumerId);
        ExecutorService executor = inMemory.getConsumerExecutors().get(consumerId);

        if (consumer == null || executor == null || executor.isShutdown()) {
            throw new IllegalStateException("Consumer '" + consumerId + "' is not active on topic '" + topicName + "'");
        }

        for (Message replayMsg : replayMessages) {
            submitConsumerTask(executor, consumer, state, replayMsg, true);
        }
    }

    @Override
    public ConsumerInfo getConsumerInfo(String consumerId, String topicName) {
        checkRunning();
        requireNonBlank(consumerId, "consumerId");
        requireNonBlank(topicName, "topicName");
        Topic topic = getTopicOrThrow(topicName);
        ConsumerState state = getConsumerStateOrThrow(topic, consumerId);

        long currentOffset = state.getCurrentOffset();
        long latestOffset = topic.getLatestOffset();
        long lag = Math.max(0, latestOffset - currentOffset);
        return new ConsumerInfo(consumerId, topicName, currentOffset, lag);
    }

    @Override
    public List<TopicInfo> listTopics() {
        checkRunning();
        List<TopicInfo> topics = new ArrayList<>();
        for (Topic topic : inMemory.getTopics().values()) {
            topics.add(new TopicInfo(topic.getName(), topic.getRetentionPeriod()));
        }
        return topics;
    }

    @Override
    public void shutdown() {
        isRunning = false;
        log.info("Initiating graceful shutdown");

        retentionScheduler.shutdown();
        try {
            if (!retentionScheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                retentionScheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            retentionScheduler.shutdownNow();
        }

        for (Map.Entry<String, ExecutorService> entry : inMemory.getConsumerExecutors().entrySet()) {
            shutdownExecutorGracefully(entry.getValue(), entry.getKey());
        }
        inMemory.getConsumerExecutors().clear();

        log.info("Shutdown complete");
    }

    private void dispatchToConsumer(ConsumerState state, Message msg) {
        String consumerId = state.getConsumerId();
        Consumer consumer = inMemory.getConsumerRegistry().get(consumerId);
        ExecutorService executor = inMemory.getConsumerExecutors().get(consumerId);

        if (consumer == null || executor == null || executor.isShutdown()) {
            return;
        }
        if (msg.getOffset() < state.getCurrentOffset()) {
            return;
        }
        submitConsumerTask(executor, consumer, state, msg, false);
    }

    private void submitConsumerTask(
            ExecutorService executor,
            Consumer consumer,
            ConsumerState state,
            Message msg,
            boolean replay
    ) {
        executor.submit(() -> {
            if (msg.getOffset() < state.getCurrentOffset()) {
                return;
            }
            try {
                consumer.onMessage(msg);
                state.markConsumed(msg.getOffset());
            } catch (Throwable t) {
                log.error("{} in consumer '{}' at offset {}: {}",
                        replay ? "Replay error" : "Error",
                        consumer.getConsumerId(),
                        msg.getOffset(),
                        t.getMessage(),
                        t);
            }
        });
    }

    private void runRetentionEviction() {
        for (Topic topic : inMemory.getTopics().values()) {
            try {
                topic.evictExpiredMessages();
            } catch (Exception e) {
                log.error("Retention eviction error on topic '{}': {}", topic.getName(), e.getMessage(), e);
            }
        }
    }

    private Topic getTopicOrThrow(String topicName) {
        Topic topic = inMemory.getTopics().get(topicName);
        if (topic == null) {
            throw new TopicNotFoundException(topicName);
        }
        return topic;
    }

    private ConsumerState getConsumerStateOrThrow(Topic topic, String consumerId) {
        ConsumerState state = topic.getConsumerState(consumerId);
        if (state == null) {
            throw new IllegalArgumentException("Consumer '" + consumerId + "' is not registered on topic '" + topic.getName() + "'");
        }
        return state;
    }

    private void checkRunning() {
        if (!isRunning) {
            throw new IllegalStateException("Broker has been shut down.");
        }
    }

    private void shutdownExecutorGracefully(ExecutorService executor, String consumerId) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                log.warn("Consumer '{}' executor did not terminate in time, forcing shutdown", consumerId);
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executor.shutdownNow();
        }
    }
}

