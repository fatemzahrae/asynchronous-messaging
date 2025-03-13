package org.com.productservice.kafka;


import org.com.productservice.event.ProductAvailabilityEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProductAvailabilityProducer {

    private static final String TOPIC = "product-event";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void send(ProductAvailabilityEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}