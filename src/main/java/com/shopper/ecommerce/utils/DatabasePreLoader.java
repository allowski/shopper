package com.shopper.ecommerce.utils;

import com.shopper.ecommerce.models.Product;
import com.shopper.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Component
public class DatabasePreLoader {

    ProductRepository productRepository;

    @Autowired
    public DatabasePreLoader(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void fill() {

        productRepository.save(
                Product.builder()
                        .id(1L)
                        .name("Coca-cola")
                        .price(BigDecimal.valueOf(6))
                        .description("500ML")
                        .active(true)
                        .build()
        );

        productRepository.save(
                Product.builder()
                        .id(2L)
                        .name("Guaraná antártica")
                        .price(BigDecimal.valueOf(5))
                        .description("500ML")
                        .active(true)
                        .build()
        );

        productRepository.save(
                Product.builder()
                        .id(3L)
                        .name("Ice tea leão")
                        .price(BigDecimal.valueOf(10))
                        .description("1.5L")
                        .active(true)
                        .build()
        );

        productRepository.save(
                Product.builder()
                        .id(4L)
                        .name("Sukita")
                        .price(BigDecimal.valueOf(10))
                        .description("1.5L")
                        .active(true)
                        .build()
        );

        productRepository.save(
                Product.builder()
                        .id(5L)
                        .name("Pepsi")
                        .price(BigDecimal.valueOf(10))
                        .description("1.5L")
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
                        .build()
        );

    }

}
