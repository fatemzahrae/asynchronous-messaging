package org.com.lab1.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderConsumer {

    @Autowired
    private OrderRepository orderRepository;

    @KafkaListener(topics = "product-topic", groupId = "order-group")
    public void consumeProductEvent(String message) {
        String[] parts = message.split(",");
        String orderIdStr = parts[0]; // This is a String
        String status = parts[1];

        try {
            // Convert String to UUID
            UUID orderId = UUID.fromString(orderIdStr);

            // Fetch the order
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

            // Update order state
            order.setStatus(status.equals("AVAILABLE") ? "PROCESSING" : "FAILED");
            orderRepository.save(order);

        } catch (IllegalArgumentException e) {
            System.err.println("Invalid orderId format: " + orderIdStr);
        }
    }
}
