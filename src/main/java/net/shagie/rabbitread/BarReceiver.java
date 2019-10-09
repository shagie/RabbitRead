package net.shagie.rabbitread;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class BarReceiver implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("Bar Received <" + message + ">");
    }

    @Override
    public void containerAckMode(AcknowledgeMode mode) {

    }
}
