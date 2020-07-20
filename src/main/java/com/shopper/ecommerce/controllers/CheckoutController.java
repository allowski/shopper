package com.shopper.ecommerce.controllers;

import com.shopper.ecommerce.exceptions.CheckoutException;
import com.shopper.ecommerce.exceptions.InvalidItemQuantityException;
import com.shopper.ecommerce.models.*;
import com.shopper.ecommerce.repositories.ProductRepository;
import com.shopper.ecommerce.repositories.SaleRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static java.math.BigDecimal.ZERO;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
public class CheckoutController {

    @Autowired
    ProductRepository products;

    @Autowired
    CustomerController customers;

    @Autowired
    AddressController addressController;

    @Autowired
    SaleRepository sales;

    /**
     * This method handles checkout processing, it includes serveral phases:
     * - Validate shipping address
     * - Check if items are ok, and not tampered by the user.
     * - Create a customer account if it does not exits.
     * - Finally persists the order to the database.
     * @param checkout
     * @return
     */
    @SneakyThrows
    @Transactional
    public Sale processCheckout(Checkout checkout) {

        Sale newSale = new Sale();

        // check if address is valid
        addressController.validate(checkout.getAddress());

        // check items
        checkItems(checkout);

        checkout.getCustomer().setAddress(checkout.getAddress());

        Customer customer = customers.createCustomerIfNotExists(checkout.getCustomer());

        newSale.setCustomer(customer);

        newSale.setItemCount((long) checkout.getItems().size());

        newSale.setItems(checkout.getItems());

        sales.save(newSale);

        return newSale;
    }


    /**
     * Check data integrity from client. Never trust client data.
     * Check if items are active or deleted.
     * Check if prices are ok.
     * @param checkout
     */
    @SneakyThrows
    public void checkItems(Checkout checkout) {

        Long itemCount = 0L;
        BigDecimal total = ZERO;

        if(checkout.getItems() == null)
            throw new CheckoutException("Items required");

        if(checkout.getItems().isEmpty())
            throw new CheckoutException("Select at least one item");

        for(ShoppingCartItem item: checkout.getItems()){

            Optional<Product> productOpt = products.findById(item.getId());

            if(productOpt.isPresent()) {

                Product product = productOpt.get();

                if (product.getPrice().setScale(2).compareTo(item.getPrice().setScale(2)) != 0)

                    throw new CheckoutException("Item price changed", "0400", BAD_REQUEST);

                if (product.isActive())

                    throw new CheckoutException("Item '" + product.getName() + "' has been deleted", "0404", 404);

                else // data is safe, proceed
                {
                    item.setName(product.getName());

                    item.setDescription(product.getDescription());
                }
            }else{

                throw new CheckoutException("Item with id " + item.getId() + " not found", "0404", NOT_FOUND);

            }


            itemCount += item.getQty();

            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQty())));

        }

        if(itemCount.compareTo(0L) <= 0 || total.compareTo(ZERO) <= 0){

            throw new InvalidItemQuantityException("Invalid item count or price");

        }


    }
}
