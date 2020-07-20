package com.shopper.ecommerce.controllers;

import com.shopper.ecommerce.models.Product;
import com.shopper.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import static java.lang.Boolean.TRUE;

@Component
public class ProductController {

    @Autowired
    ProductRepository products;

    public Page<Product> findAll(Pageable pageable) {

        return products.findAllByActiveEquals(pageable, TRUE);

    }

}
