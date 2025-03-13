package org.com.orderservice.service;


import com.example.product.Product;
import org.com.orderservice.client.ProductClient;
import org.com.orderservice.entity.Order;
import org.com.orderservice.entity.OrderState;
import org.com.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired private ProductClient productClient;
    @Autowired private OrderRepository orderRepository;

    public Order createOrder(String productId, int quantity) {
        Product.ProductResponse product = productClient.getProductById(productId);
        Order order = new Order();
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setPrice(product.getPrice() * quantity);
        order.setState(OrderState.CREATED);
        return orderRepository.save(order);
    }
}