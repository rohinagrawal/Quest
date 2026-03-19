package message_broker.service.consumer;

import message_broker.pojo.Message;

public interface Consumer {
    String getConsumerId();
    void onMessage(Message message);
}

