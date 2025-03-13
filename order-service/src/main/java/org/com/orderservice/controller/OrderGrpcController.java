package org.com.orderservice.controller;




import com.example.order.OrderServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.com.orderservice.entity.Order;
import org.com.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class OrderGrpcController extends OrderServiceGrpc.OrderServiceImplBase {
    @Autowired private OrderService orderService;

    @Override
    public void createOrder(com.example.order.Order.CreateOrderRequest request, StreamObserver<com.example.order.Order.CreateOrderResponse> responseObserver) {
        Order order = orderService.createOrder(request.getProductId(), request.getQuantity());
        com.example.order.Order.CreateOrderResponse response = com.example.order.Order.CreateOrderResponse.newBuilder()
                .setOrderId(order.getId().toString())
                .setStatus("CREATED")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}