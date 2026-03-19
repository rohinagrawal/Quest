package message_broker.pojo;

import lombok.Getter;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

@Getter
public class Topic {

    private final String name;
    private final Duration retentionPeriod;

    private final ConcurrentSkipListMap<Long, Message> messages;
    private final AtomicLong offsetCounter;
    private final Map<String, ConsumerState> consumerStates;

    public Topic(String name, Duration retentionPeriod) {
        this.name = name;
        this.retentionPeriod = retentionPeriod;
        this.messages = new ConcurrentSkipListMap<>();
        this.offsetCounter = new AtomicLong(0);
        this.consumerStates = new ConcurrentHashMap<>();
    }

    public Message publish(String content) {
        long offset = offsetCounter.getAndIncrement();
        Message message = new Message(offset, content, name);
        messages.put(offset, message);
        return message;
    }

    public void evictExpiredMessages() {
        Instant expiryThreshold = Instant.now().minus(retentionPeriod);
        messages.entrySet().removeIf(entry -> entry.getValue().getPublishedAt().isBefore(expiryThreshold));

        if (!messages.isEmpty()) {
            long firstAvailable = messages.firstKey();
            for (ConsumerState state : consumerStates.values()) {
                state.advanceOffsetIfBehind(firstAvailable);
            }
        }
    }

    public void registerConsumerState(ConsumerState state) {
        consumerStates.put(state.getConsumerId(), state);
    }

    public void removeConsumerState(String consumerId) {
        consumerStates.remove(consumerId);
    }

    public ConsumerState getConsumerState(String consumerId) {
        return consumerStates.get(consumerId);
    }

    public List<ConsumerState> getAllConsumerStates() {
        return new ArrayList<>(consumerStates.values());
    }

    public long getLatestOffset() {
        return offsetCounter.get();
    }
}

