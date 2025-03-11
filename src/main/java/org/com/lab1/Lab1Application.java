package org.com.lab1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.grpc.client.EnableGrpcClients;

@SpringBootApplication
@EnableGrpcClients
public class Lab1Application {  // ✅ Ensure this class exists

    public static void main(String[] args) {
        SpringApplication.run(Lab1Application.class, args);
    }
}
