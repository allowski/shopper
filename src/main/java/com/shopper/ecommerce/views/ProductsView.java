package com.shopper.ecommerce.views;

import com.shopper.ecommerce.controllers.ProductController;
import com.shopper.ecommerce.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductsView {

    @Autowired
    ProductController products;

    @CrossOrigin
    @RequestMapping(path = "/api/products")
    Page<Product> listProducts(Pageable pageable) {
        return products.findAll(pageable);
    }

}
