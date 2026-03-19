package message_broker.service.consumer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import message_broker.pojo.Message;

@Getter
@RequiredArgsConstructor
public class LoggingConsumer implements Consumer {
    private final String consumerId;

    @Override
    public void onMessage(Message message) {
        System.out.printf("%n[%s] Consumed -> %s%n ", consumerId, message);
    }
}
