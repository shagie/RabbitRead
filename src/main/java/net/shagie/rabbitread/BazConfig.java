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
@Profile("baz")
@EnableRabbit
public class BazConfig {
    @Bean
    @Qualifier("bazQ")
    Queue bazQueue() {
        return new Queue(Application.bazQueueName, false);
    }

    @Bean
    Binding bazBinding(@Autowired @Qualifier("bazQ") Queue baz, TopicExchange exchange) {
        return BindingBuilder.bind(baz).to(exchange).with("foo.bar.#");
    }

    @Bean
    SimpleMessageListenerContainer bazContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(Application.bazQueueName);
        container.setMessageListener(new BazReceiver());
        return container;
    }
}
