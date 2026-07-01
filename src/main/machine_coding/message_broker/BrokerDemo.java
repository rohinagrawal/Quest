package message_broker;

import message_broker.config.BrokerConfig;
import message_broker.dto.ConsumerInfo;
import message_broker.dto.TopicInfo;
import message_broker.service.BrokerService;
import message_broker.service.InMemoryBrokerService;
import message_broker.service.consumer.LoggingConsumer;
import message_broker.utils.Util;

import java.time.Duration;
import java.util.List;
import java.util.Scanner;

public class BrokerDemo {

    public static void main(String[] args) {
        BrokerConfig config = new BrokerConfig(1);
        BrokerService broker = new InMemoryBrokerService(config);
        printHelp();
        System.out.println("\nBroker is running. Type a command:\n");

        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                try {
                    if (!handleCommand(broker, line)) {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("[Error] Expected a numeric argument: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("[Error] " + e.getMessage());
                }
                System.out.println();
            }
        }
        broker.shutdown();
    }

    /**
     * create-topic <topicName> <retentionSeconds>
     * delete-topic <topicName>
     * list-topics
     * register-publisher <publisherId> <topicName>
     * register-consumer  <consumerId>  <topicName>
     * publish <publisherId> <message text>
     * publish-direct <topicName> <message text>
     * consumer-info <consumerId> <topicName>
     * reset-offset <consumerId> <topicName> <newOffset>
     * @param broker
     * @param line
     * @return
     */
    private static boolean handleCommand(BrokerService broker, String line) {
        String[] parts = line.split("\\s+", 3);
        String command = parts[0].toLowerCase();

        switch (command) {
            case "create-topic" -> {
                if (parts.length < 3) {
                    usage("create-topic <topicName> <retentionSeconds>");
                    return true;
                }
                String topicName = parts[1];
                long retentionSeconds = Long.parseLong(parts[2]);
                broker.createTopic(topicName, Duration.ofSeconds(retentionSeconds));
            }
            case "delete-topic" -> {
                if (parts.length < 2) {
                    usage("delete-topic <topicName>");
                    return true;
                }
                String topicName = parts[1];
                broker.deleteTopic(topicName);
            }
            case "list-topics" -> {
                List<TopicInfo> topics = broker.listTopics();
                if (topics.isEmpty()) {
                    System.out.println("(no topics created yet)");
                } else {
                    topics.forEach(topic -> System.out.println("topic='" + topic.getName() + "' retention=" + Util.formatRetention(topic.getRetentionPeriod())));
                }
            }
            case "register-publisher" -> {
                if (parts.length < 3) {
                    usage("register-publisher <publisherId> <topicName>");
                    return true;
                }
                broker.registerPublisher(parts[1], parts[2]);
            }
            case "register-consumer" -> {
                if (parts.length < 3) {
                    usage("register-consumer <consumerId> <topicName>");
                    return true;
                }
                broker.registerConsumer(parts[1], parts[2], new LoggingConsumer(parts[1]));
            }
            case "publish" -> {
                if (parts.length < 3) {
                    usage("publish <publisherId> <message text>");
                    return true;
                }
                broker.publishByPublisher(parts[1], parts[2]);
            }
            case "publish-direct" -> {
                if (parts.length < 3) {
                    usage("publish-direct <topicName> <message text>");
                    return true;
                }
                broker.publish(parts[1], parts[2]);
            }
            case "consumer-info" -> {
                if (parts.length < 3) {
                    usage("consumer-info <consumerId> <topicName>");
                    return true;
                }
                ConsumerInfo info = broker.getConsumerInfo(parts[1], parts[2]);
                System.out.println(info);
            }
            case "reset-offset" -> {
                String[] full = line.split("\\s+", 4);
                if (full.length < 4) {
                    usage("reset-offset <consumerId> <topicName> <newOffset>");
                    return true;
                }
                broker.resetOffset(full[1], full[2], Long.parseLong(full[3]));
            }
            case "help" -> printHelp();
            case "exit" -> {
                return false;
            }
            default -> System.out.println("Unknown command '" + command + "'. Type 'help'.");
        }
        return true;
    }

    // ── Helpers ─────────────────────────────────────────────────────────────
    private static void printHelp() {
        System.out.println(
                "\n┌─────────────────────────────────────────────────────────────────┐\n" +
                        "│              In-Memory Message Broker — Commands                │\n" +
                        "├────────────────────────────┬────────────────────────────────────┤\n" +
                        "│ create-topic  <n> <secs>   │ Create topic with retention (secs) │\n" +
                        "│ delete-topic  <n>          │ Delete topic (consumers cleaned up) │\n" +
                        "│ list-topics                │ List all active topics              │\n" +
                        "│ register-publisher <id> <t>│ Bind publisher to topic             │\n" +
                        "│ register-consumer  <id> <t>│ Subscribe consumer to topic         │\n" +
                        "│ publish <pubId> <msg…>     │ Publish using registered publisher   │\n" +
                        "│ publish-direct <t> <msg…>  │ Publish directly to topic (admin)   │\n" +
                        "│ consumer-info  <id> <t>    │ Show offset & lag for consumer      │\n" +
                        "│ reset-offset <id> <t> <n>  │ Reset consumer offset & replay      │\n" +
                        "│ help                       │ Show this help                      │\n" +
                        "│ exit                       │ Graceful shutdown                   │\n" +
                        "└────────────────────────────┴────────────────────────────────────┘"
        );
    }

    private static void usage(String syntax) {
        System.err.println("[Usage] " + syntax);
    }
}
