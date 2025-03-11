package org.com.lab1.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Optional<Product> getProductById(String productId) {
        return productRepository.findByProductId(productId);
    }

    public boolean checkAndUpdateStock(String productId, int quantity) {
        Optional<Product> productOpt = productRepository.findByProductId(productId);
        if (productOpt.isPresent() && productOpt.get().getAvailableQuantity() >= quantity) {
            Product product = productOpt.get();
            product.setAvailableQuantity(product.getAvailableQuantity() - quantity);
            productRepository.save(product);
            return true;
        }
        return false;
    }
}
