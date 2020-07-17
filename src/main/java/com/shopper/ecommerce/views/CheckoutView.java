package com.shopper.ecommerce.views;

import com.shopper.ecommerce.controllers.CheckoutController;
import com.shopper.ecommerce.exceptions.CheckoutException;
import com.shopper.ecommerce.models.Checkout;
import com.shopper.ecommerce.models.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

public class CheckoutView {

    @Autowired
    CheckoutController checkoutController;

    @RequestMapping(method = POST, path = "/checkout")
    Sale checkout(@RequestBody Checkout checkout) throws CheckoutException {
        return checkoutController.processCheckout(checkout);
    }

}
