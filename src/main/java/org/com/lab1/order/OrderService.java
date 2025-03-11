package org.com.lab1.order;


import net.devh.boot.grpc.client.inject.GrpcClient;
import order.ProductServiceGrpc;
import order.Transaction;
import org.com.lab1.config.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.grpc.client.EnableGrpcClients;
import org.springframework.stereotype.Service;

@Service
@EnableGrpcClients
public class OrderService {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private OrderRepository orderRepository;

    @GrpcClient("product-service")  // ✅ Ensure this matches application.properties
    private ProductServiceGrpc.ProductServiceBlockingStub productServiceStub;

    public Transaction.ProductResponse getProductById(String productId) {
        Transaction.ProductRequest request = Transaction.ProductRequest.newBuilder()
                .setProductId(productId)
                .build();
        return productServiceStub.getProductById(request);
    }

    public Transaction.OrderResponse createOrder(String productId, int quantity) {
        // Fetch product details via gRPC
        Transaction.ProductResponse productResponse = getProductById(productId);

        // Calculate total price
        double totalPrice = productResponse.getPrice() * quantity;

        // Save order as "CREATED"
        Order order = new Order(productId, quantity, totalPrice, "CREATED");
        orderRepository.save(order);

        // Publish order event to Kafka
        kafkaProducer.sendMessage("order-topic",
                String.format("%s,%s,%d", order.getId(), productId, quantity));

        // ✅ Use Builder to return response
        return Transaction.OrderResponse.newBuilder()
                .setOrderId(order.getId().toString()) // Convert UUID to String
                .setStatus(order.getStatus())
                .build();
    }
}
