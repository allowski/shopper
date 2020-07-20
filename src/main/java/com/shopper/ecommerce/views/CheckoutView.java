package com.shopper.ecommerce.views;

import com.shopper.ecommerce.controllers.CheckoutController;
import com.shopper.ecommerce.exceptions.CheckoutException;
import com.shopper.ecommerce.exceptions.ShopperException;
import com.shopper.ecommerce.models.Checkout;
import com.shopper.ecommerce.models.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(method = POST)
public class CheckoutView {

    @Autowired
    CheckoutController checkoutController;

    @PostMapping("/api/checkout" ) //, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    Sale checkout(@RequestBody Checkout checkout) throws CheckoutException {
        return checkoutController.processCheckout(checkout);
    }

    @ExceptionHandler(value = {ShopperException.class})
    @ResponseBody
    Object errorHandler(ShopperException ex, HttpServletResponse response) {
        ex.printStackTrace(System.err);
        HashMap<String, String> error = new HashMap<>();
        error.put("error", ex.getErrorMessage());
        error.put("code", ex.getErrorCode());
        response.setStatus(ex.getStatus().value());
        return error;
    }

}
