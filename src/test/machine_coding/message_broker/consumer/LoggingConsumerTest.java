package message_broker.consumer;

import message_broker.pojo.Message;
import message_broker.service.consumer.LoggingConsumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("LoggingConsumer Tests")
class LoggingConsumerTest {

    @Test
    @DisplayName("onMessage should print consumer id and message content")
    void onMessagePrintsConsumerIdAndContent() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        try {
            LoggingConsumer consumer = new LoggingConsumer("c1");
            consumer.onMessage(new Message(5L, "hello", "orders"));

            String output = out.toString();
            assertThat(output).contains("[c1] Consumed ->");
            assertThat(output).contains("content=hello");
            assertThat(output).contains("topicName=orders");
        } finally {
            System.setOut(original);
        }
    }

    @Test
    @DisplayName("onMessage prints each message it receives")
    void onMessagePrintsEveryMessage() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        try {
            LoggingConsumer consumer = new LoggingConsumer("logger");
            consumer.onMessage(new Message(0L, "first", "events"));
            consumer.onMessage(new Message(1L, "second", "events"));

            String output = out.toString();
            assertThat(output).contains("first");
            assertThat(output).contains("second");
            assertThat(output).contains("[logger]");
        } finally {
            System.setOut(original);
        }
    }
}

