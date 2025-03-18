package org.com.orderservice.service;


import com.example.product.Product;
import org.com.orderservice.client.ProductClient;
import org.com.orderservice.entity.Order;
import org.com.orderservice.entity.OrderState;
import org.com.orderservice.event.OrderCreatedEvent;
import org.com.orderservice.kafka.OrderEventProducer;
import org.com.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;


@Service
public class OrderService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate; // Inject KafkaTemplate directly

    public Order createOrder(String productId, int quantity) {
        // Validate input parameters
        if (productId == null || productId.isEmpty()) {
            throw new IllegalArgumentException("Product ID must not be null or empty");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        // Fetch product details
        Product.ProductResponse product = productClient.getProductById(productId);
        if (product == null) {
            throw new RuntimeException("Product not found with ID: " + productId);
        }

        // Create and save the order
        Order order = new Order();
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setPrice((float) (product.getPrice() * quantity));
        order.setState(OrderState.CREATED);
        orderRepository.save(order);

        // Create and send the OrderCreatedEvent
        OrderCreatedEvent event = new OrderCreatedEvent(order.getId(), productId, quantity);
        try {
            kafkaTemplate.send("order-event", event).get(); // Use .get() to handle exceptions
            System.out.println("Successfully sent OrderCreatedEvent: " + event);
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Failed to send OrderCreatedEvent: " + e.getMessage());
            throw new RuntimeException("Failed to send OrderCreatedEvent", e);
        }

        return order;
    }
}