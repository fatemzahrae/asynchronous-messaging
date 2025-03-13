package org.com.orderservice.kafka;



import org.com.orderservice.entity.Order;
import org.com.orderservice.entity.OrderState;
import org.com.orderservice.event.ProductAvailabilityEvent;
import org.com.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProductAvailabilityConsumer {

    @Autowired
    private OrderRepository orderRepository;

    @KafkaListener(topics = "product-event", groupId = "order-group")
    public void handleProductAvailability(ProductAvailabilityEvent event) {
        Order order = orderRepository.findById(event.getOrderId()).orElseThrow();
        if (event.getStatus().equals("AVAILABLE")) {
            order.setState(OrderState.PROCESSING);
        } else {
            order.setState(OrderState.FAILED);
        }
        orderRepository.save(order);
    }
}