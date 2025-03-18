package org.com.productservice.event;

public class ProductAvailabilityEvent {
    private String orderId;
    private String status; // "AVAILABLE" or "OUT_OF_STOCK"

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}