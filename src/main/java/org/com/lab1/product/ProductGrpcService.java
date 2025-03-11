package org.com.lab1.product;

import io.grpc.stub.StreamObserver;
import order.ProductServiceGrpc;
import order.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.grpc.server.service.GrpcService;

import java.util.Optional;

@GrpcService
public class ProductGrpcService extends ProductServiceGrpc.ProductServiceImplBase {

    @Autowired
    private ProductService productService;

    @Override
    public void getProductById(Transaction.ProductRequest request, StreamObserver<Transaction.ProductResponse> responseObserver) {
        String productId = request.getProductId();

        Optional<Product> productOpt = productService.getProductById(productId);

        if (productOpt.isPresent()) {
            Product product = productOpt.get();

            // Build and send gRPC response
            Transaction.ProductResponse response = Transaction.ProductResponse.newBuilder()
                    .setProductId(product.getProductId())
                    .setName(product.getName())
                    .setPrice(product.getPrice())
                    .setAvailableQuantity(product.getAvailableQuantity())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else {

            responseObserver.onError(new RuntimeException("Product not found: " + productId));
        }
    }
}
