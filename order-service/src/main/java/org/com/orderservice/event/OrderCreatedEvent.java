package org.com.orderservice.event;

import lombok.Data;

@Data
public class OrderCreatedEvent {
    private Long orderId;       // ID of the newly created order
    private String productId;   // ID of the product in the order
    private int quantity;       // Quantity requested for the product
}