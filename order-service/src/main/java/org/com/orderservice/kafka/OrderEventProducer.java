package org.com.orderservice.kafka;


import org.com.orderservice.event.OrderCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventProducer {

    private static final String TOPIC = "order-event";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void send(OrderCreatedEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}