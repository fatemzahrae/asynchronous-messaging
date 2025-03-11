package org.com.lab1.product;

import org.com.lab1.config.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProductConsumer {

    @Autowired
    private ProductService productService;

    @Autowired
    private KafkaProducer kafkaProducer;

    @KafkaListener(topics = "order-topic", groupId = "product-group")
    public void consumeOrderEvent(String message) {
        String[] parts = message.split(",");
        String orderId = parts[0];
        String productId = parts[1];
        int quantity = Integer.parseInt(parts[2]);

        // Check availability
        boolean isAvailable = productService.checkAndUpdateStock(productId, quantity);

        // Publish product event
        String status = isAvailable ? "AVAILABLE" : "OUT_OF_STOCK";
        kafkaProducer.sendMessage("product-topic", orderId + "," + status);
    }
}
