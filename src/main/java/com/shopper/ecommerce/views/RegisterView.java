package com.shopper.ecommerce.views;

import com.shopper.ecommerce.controllers.CustomerController;
import com.shopper.ecommerce.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class RegisterView {

    @Autowired
    CustomerController customerController;

    @RequestMapping(method = POST, path = "/api/register")
    Customer registerCustomer(@RequestBody Customer customerData){

        return customerController.registerCustomer(customerData);

    }

}
