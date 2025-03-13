package org.com.orderservice.event;


import lombok.Data;

@Data // Lombok generates getters/setters automatically
public class ProductAvailabilityEvent {
    private Long orderId;
    private String status; // "AVAILABLE" or "OUT_OF_STOCK"

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}