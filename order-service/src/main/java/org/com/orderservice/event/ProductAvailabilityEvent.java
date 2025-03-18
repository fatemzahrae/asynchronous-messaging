package org.com.orderservice.event;


import lombok.Data;

@Data // Lombok generates getters/setters automatically
public class ProductAvailabilityEvent {
    private String orderId;
    private String productId;
    private String status; // "AVAILABLE" or "OUT_OF_STOCK"


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

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