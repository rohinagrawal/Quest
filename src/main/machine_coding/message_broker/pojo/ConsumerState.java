package message_broker.pojo;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicLong;

@Getter
public class ConsumerState {

    private final String consumerId;
    private final String topicName;
    private final AtomicLong currentOffset;

    public ConsumerState(String consumerId, String topicName, long initialOffset) {
        this.consumerId = consumerId;
        this.topicName = topicName;
        this.currentOffset = new AtomicLong(initialOffset);
    }

    public long getCurrentOffset() {
        return currentOffset.get();
    }

    public void markConsumed(long offset) {
        currentOffset.updateAndGet(current -> Math.max(current, offset + 1));
    }

    public void setOffset(long offset) {
        currentOffset.set(offset);
    }

    public void advanceOffsetIfBehind(long minOffset) {
        currentOffset.updateAndGet(current -> Math.max(current, minOffset));
    }
}

