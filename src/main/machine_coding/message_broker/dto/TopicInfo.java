package message_broker.dto;

import lombok.Value;

import java.time.Duration;

@Value
public class TopicInfo {
    String name;
    Duration retentionPeriod;
}

