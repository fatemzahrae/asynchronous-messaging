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
        if (productRepository.count() == 0) {
            // Product 1
            Product product1 = new Product();
            product1.setId("123");
            product1.setName("Product 1");
            product1.setPrice(100.0f);
            product1.setQuantityAvailable(10);
            productRepository.save(product1);

            // Product 2
            Product product2 = new Product();
            product2.setId("456");
            product2.setName("Product 2");
            product2.setPrice(200.0f);
            product2.setQuantityAvailable(5);
            productRepository.save(product2);

            // Product 3
            Product product3 = new Product();
            product3.setId("789");
            product3.setName("Product 3");
            product3.setPrice(50.0f);
            product3.setQuantityAvailable(20);
            productRepository.save(product3);

            System.out.println("Sample products added to the database.");
        } else {
            System.out.println("Database already contains products. Skipping data loading.");
        }
    }
}