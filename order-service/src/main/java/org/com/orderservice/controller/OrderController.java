package org.com.orderservice.controller;

import org.com.orderservice.entity.Order;
import org.com.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestParam String productId, @RequestParam int quantity) {
        return orderService.createOrder(productId, quantity);
    }

    @GetMapping("/test")
    public String test() {
        return "Order Controller is working!";
    }

}