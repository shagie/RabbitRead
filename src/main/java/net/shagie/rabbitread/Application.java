package net.shagie.rabbitread;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
@EnableRabbit
public class Application {
    static final String topicExchangeName = "test.exchange";
    static final String barQueueName = "test.bar";
    static final String bazQueueName = "test.baz";

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(DefaultConfig.class, BarConfig.class, BazConfig.class, Runner.class)
                .profiles("default", "bar", "baz")
                .run(args).close();
    }
}
