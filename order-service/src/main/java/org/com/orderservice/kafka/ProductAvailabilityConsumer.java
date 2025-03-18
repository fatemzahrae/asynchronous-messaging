package org.com.orderservice.kafka;



import org.com.orderservice.entity.Order;
import org.com.orderservice.entity.OrderState;
import org.com.orderservice.event.ProductAvailabilityEvent;
import org.com.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ProductAvailabilityConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ProductAvailabilityConsumer.class);

    @Autowired
    private OrderRepository orderRepository;

    @KafkaListener(topics = "product-event", groupId = "order-group")
    public void handleProductAvailability(ProductAvailabilityEvent event) {
        try {
            logger.info("Consumed product-event: {}", event);
            Order order = orderRepository.findById(event.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found: " + event.getOrderId()));

            if (event.getStatus().equals("AVAILABLE")) {
                order.setState(OrderState.PROCESSING);
            } else {
                order.setState(OrderState.FAILED);
            }

            orderRepository.save(order);
            logger.info("Updated order state for orderId={}. New state: {}", event.getOrderId(), order.getState());
        } catch (Exception e) {
            logger.error("Error processing product-event: {}", event, e);
        }
    }
}