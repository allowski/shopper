package com.shopper.ecommerce.controllers;

import com.shopper.ecommerce.exceptions.CheckoutException;
import com.shopper.ecommerce.models.Checkout;
import com.shopper.ecommerce.models.Sale;
import com.shopper.ecommerce.repositories.CustomerRepository;
import com.shopper.ecommerce.repositories.ProductRepository;
import com.shopper.ecommerce.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class CheckoutController {

    @Autowired
    ProductRepository products;

    @Autowired
    CustomerRepository customers;

    @Autowired
    SaleRepository sales;

    @Transactional
    public Sale processCheckout(Checkout checkout) throws CheckoutException {

        Sale newSale = new Sale();

        return newSale;
    }
}
