package org.com.productservice.kafka;


import org.com.productservice.entity.Product;
import org.com.productservice.event.OrderCreatedEvent;
import org.com.productservice.event.ProductAvailabilityEvent;
import org.com.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedConsumer {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "order-event", groupId = "product-group")
    public void handleOrderCreated(@Payload org.com.productservice.event.OrderCreatedEvent  event) {
        try {
            System.out.println("Received order event: " + event);
            Product product = productRepository.findById(event.getProductId()).orElseThrow();
            ProductAvailabilityEvent availabilityEvent = new ProductAvailabilityEvent();
            availabilityEvent.setOrderId(event.getOrderId());

            if (product.getQuantityAvailable() >= event.getQuantity()) {
                product.setQuantityAvailable(product.getQuantityAvailable() - event.getQuantity());
                productRepository.save(product);
                availabilityEvent.setStatus("AVAILABLE");
            } else {
                availabilityEvent.setStatus("OUT_OF_STOCK");
            }

            kafkaTemplate.send("product-event", availabilityEvent);
        } catch (Exception e) {
            System.err.println("Error processing order event: " + e.getMessage());
            e.printStackTrace();
        }
    }
}