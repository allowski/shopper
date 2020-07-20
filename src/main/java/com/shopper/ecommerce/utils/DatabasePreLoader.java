package com.shopper.ecommerce.utils;

import com.shopper.ecommerce.models.Product;
import com.shopper.ecommerce.repositories.ProductRepository;
import com.shopper.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Component
public class DatabasePreLoader {

    ProductRepository productRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    public DatabasePreLoader(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void fill() {

        emailService.sendEmail("allowski@gmail.com", "Initial email", "Server is up!");

        productRepository.save(
                Product.builder()
                        .id(1L)
                        .name("Coca-cola")
                        .price(BigDecimal.valueOf(6))
                        .description("500ML")
                        .active(true)
                        .pictureUrl("https://i.ibb.co/r2F3qS7/03da408559.jpg")
                        .build()
        );

        productRepository.save(
                Product.builder()
                        .id(2L)
                        .name("Guaraná antártica")
                        .price(BigDecimal.valueOf(5))
                        .description("500ML")
                        .active(true)
                        .pictureUrl("https://i.ibb.co/DtCVNLQ/Screen-Shot-2020-07-20-at-02-12-34.png")
                        .build()
        );

        productRepository.save(
                Product.builder()
                        .id(3L)
                        .name("Ice tea leão")
                        .price(BigDecimal.valueOf(10))
                        .description("1.5L")
                        .active(true)
                        .pictureUrl("https://i.ibb.co/FbnFsRW/2cabe4f0-d7da-43b0-b8d4-d8a1ca176eca.png")
                        .build()
        );

        productRepository.save(
                Product.builder()
                        .id(4L)
                        .name("Sukita")
                        .price(BigDecimal.valueOf(10))
                        .description("1.5L")
                        .active(true)
                        .pictureUrl("https://i.ibb.co/RT0MR89/1000006979.jpg")
                        .build()
        );

        productRepository.save(
                Product.builder()
                        .id(5L)
                        .name("Pepsi")
                        .price(BigDecimal.valueOf(10))
                        .description("1.5L")
                        .pictureUrl("https://i.ibb.co/RT0MR89/1000006979.jpg")
                        .active(true)
                        .build()
        );

        productRepository.save(
                Product.builder()
                        .id(6L)
                        .name("Pepsi-Zero *deleted*")
                        .price(BigDecimal.valueOf(11))
                        .description("1.5L")
                        .active(false)
                        .pictureUrl("https://i.ibb.co/r2F3qS7/03da408559.jpg")
                        .build()
        );

    }

}
