package org.com.productservice.event;

public class OrderCreatedEvent {
    private String orderId;
    private String productId;
    private int quantity;

    public OrderCreatedEvent(String productId, String orderId, int quantity) {
        this.productId = productId;
        this.orderId = orderId;
        this.quantity = quantity;
    }

    public OrderCreatedEvent() {}

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return "OrderCreatedEvent{" +
                "orderId='" + orderId + '\'' +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}