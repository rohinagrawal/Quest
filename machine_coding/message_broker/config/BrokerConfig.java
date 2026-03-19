package message_broker.config;

import lombok.Getter;

@Getter
public class BrokerConfig {

    private final long evictionIntervalSeconds;

    public BrokerConfig(long evictionIntervalSeconds) {
        if (evictionIntervalSeconds <= 0) {
            throw new IllegalArgumentException("evictionIntervalSeconds must be positive");
        }
        this.evictionIntervalSeconds = evictionIntervalSeconds;
    }
}

