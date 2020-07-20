package com.shopper.ecommerce.controllers;

import com.shopper.ecommerce.exceptions.CustomerValidationException;
import com.shopper.ecommerce.models.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerControllerUnitTest {

    CustomerController customerController = new CustomerController();

    @Test
    void test_Should_Clean_Up_Dirty_Customer_Data() {

        Customer customer = Customer.builder()
                .fullName("Alberto Miranda Dencowski")
                .document("333.333.333-33")
                .phoneNumber("(11)94529-6701")
                .email("test@email.com")
                .build();

        Customer cleanCustomer = customerController.cleanUp(customer);

        assertEquals(cleanCustomer.getDocument(), "33333333333");

        assertEquals(cleanCustomer.getPhoneNumber(), "11945296701");

    }

    @Test
    void test_Should_Throw_Exception_For_Invalid_Customer_Data() {

        Customer customer = Customer.builder()
                .fullName("")
                .document("333.333.333-33")
                .phoneNumber("(11)94529-6701")
                .email("test@email.com")
                .build();

        assertThrows(CustomerValidationException.class, () -> {
           customerController.validate(customer);
        });

        customer.setEmail("invalidEmail");

        assertThrows(CustomerValidationException.class, () -> {
            customerController.validate(customer);
        });

    }

    @Test
    void test_Should_Not_Throw_Exception_Valid_Customer_Data() {

        Customer customer = Customer.builder()
                .fullName("Alberto Miranda Dencowski")
                .document("33333333333")
                .phoneNumber("11945296701")
                .email("test@email.com")
                .build();

        assertDoesNotThrow(() -> {
            customerController.validate(customer);
        });

    }

    @Test
    void test_Should_Throw_Exception_If_Null_Fields() {

        assertThrows(CustomerValidationException.class, () -> {
            customerController.checkForNull(new Customer());
        });

    }

}
