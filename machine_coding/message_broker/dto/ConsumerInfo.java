package message_broker.dto;

import lombok.Value;

@Value
public class ConsumerInfo {
    String consumerId;
    String topicName;
    long currentOffset;
    long lag;
}

