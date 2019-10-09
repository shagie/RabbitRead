package net.shagie.rabbitread;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("bar")
@EnableRabbit
public class BarConfig {
    @Bean
    @Qualifier("barQ")
    Queue barQueue() {
        return new Queue(Application.barQueueName, false);
    }

    @Bean
    Binding barBinding(@Autowired @Qualifier("barQ") Queue bar, TopicExchange exchange) {
        return BindingBuilder.bind(bar).to(exchange).with("foo.bar.#");
    }

    @Bean
    BarReceiver barReceiver() {
        return new BarReceiver();
    }

    @Bean
    SimpleMessageListenerContainer barContainer(ConnectionFactory connectionFactory, BarReceiver barReceiver) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(Application.barQueueName);
        container.setMessageListener(barReceiver);
        return container;
    }
}
