package org.com.productservice.kafka;


import org.com.productservice.event.ProductAvailabilityEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class ProductAvailabilityProducer {

    private static final String TOPIC = "product-event";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void send(ProductAvailabilityEvent event) {
        try {
            kafkaTemplate.send(TOPIC, event).get(); // Use .get() to handle exceptions
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error sending product availability event: " + e.getMessage());
        }
    }
}