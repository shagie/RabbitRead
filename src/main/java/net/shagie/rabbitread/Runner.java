package net.shagie.rabbitread;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@EnableRabbit
public class Runner implements CommandLineRunner {
    private final ConnectionFactory factory;

    Runner(ConnectionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        try (Connection conn = factory.createConnection(); Channel channel = conn.createChannel(false)) {
            String message = "Hello World!";
            channel.basicPublish(
                    Application.topicExchangeName,
                    "foo.bar.baz",
                    null,
                    message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }

        Thread.sleep(2_000);
        System.out.println("Done...");
    }

}
