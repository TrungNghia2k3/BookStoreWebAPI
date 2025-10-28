package com.ntn.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.ntn.ecommerce.dotenv.DotenvApplication;

@SpringBootApplication
@EnableFeignClients
public class BookStoreApplication {
    public static void main(String[] args) {
        DotenvApplication.init(); // Load biến môi trường từ .env
        SpringApplication.run(BookStoreApplication.class, args);
    }
}
