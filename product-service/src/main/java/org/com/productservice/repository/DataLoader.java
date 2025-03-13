package org.com.productservice.repository;

import org.com.productservice.entity.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DataLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if there are no products in the database, then add one.
        if (productRepository.count() == 0) {
            Product product = new Product();
            product.setId("P001");
            product.setName("Product 1");
            product.setPrice(25.5);
            product.setQuantityAvailable(10);
            productRepository.save(product);

            System.out.println("Product added to database.");
        }
    }
}
