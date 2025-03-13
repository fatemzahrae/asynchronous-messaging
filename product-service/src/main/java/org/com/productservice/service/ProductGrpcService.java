package org.com.productservice.service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.com.productservice.entity.Product;
import org.com.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class ProductGrpcService extends com.example.product.ProductServiceGrpc.ProductServiceImplBase {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void getProductById(com.example.product.Product.ProductRequest request, StreamObserver<com.example.product.Product.ProductResponse> responseObserver) {
        try {
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> Status.NOT_FOUND
                            .withDescription("Product with ID " + request.getProductId() + " not found")
                            .asRuntimeException());

            com.example.product.Product.ProductResponse response = com.example.product.Product.ProductResponse.newBuilder()
                    .setProductId(product.getId())
                    .setName(product.getName())
                    .setPrice(product.getPrice())
                    .setQuantityAvailable(product.getQuantityAvailable())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription("Error retrieving product: " + e.getMessage())
                            .asRuntimeException());
        }
    }
}