package org.com.orderservice.client;



import com.example.product.Product;
import com.example.product.ProductServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProductClient {
    private final ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9091).usePlaintext().build();
    private final ProductServiceGrpc.ProductServiceBlockingStub blockingStub = ProductServiceGrpc.newBlockingStub(channel);

    public Product.ProductResponse getProductById(String productId) {
        return blockingStub.getProductById(
                Product.ProductRequest.newBuilder().setProductId(productId).build()
        );
    }
}