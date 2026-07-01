package message_broker.pojo;

import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Getter
@ToString
public class Message {
    private final long offset;
    private final String content;
    private final String topicName;
    private final Instant publishedAt;

    public Message(long offset, String content, String topicName) {
        this.offset = offset;
        this.content = content;
        this.topicName = topicName;
        this.publishedAt = Instant.now();
    }
}

