package net.shagie.rabbitread;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("default")
@EnableRabbit
public class DefaultConfig {
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(Application.topicExchangeName);
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("rabbitmq");
        connectionFactory.setPassword("rabbitmq");

        return connectionFactory;
    }
}
