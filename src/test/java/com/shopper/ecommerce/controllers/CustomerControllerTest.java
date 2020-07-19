package com.shopper.ecommerce.controllers;

import com.shopper.ecommerce.exceptions.CustomerValidationException;
import com.shopper.ecommerce.exceptions.EmailInUseException;
import com.shopper.ecommerce.models.Address;
import com.shopper.ecommerce.models.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CustomerControllerTest {

    @Autowired
    CustomerController customerController;

    @Test
    void test_Should_Create_Customer_When_Valid_Data () {

        Customer customer = Customer.builder()
                .fullName("")
                .document("333.333.333-33")
                .phoneNumber("(11)94529-6701")
                .email("test@email.com")
                .address(Address.builder().build())
                .build();

        assertThrows(CustomerValidationException.class, () -> {

            customerController.createCustomerIfNotExists(customer);

        });

        customer.setFullName("Alberto Miranda Dencowski");

        Customer createdCustomer = customerController.createCustomerIfNotExists(customer);

        Customer duplicateCustomer = customerController.createCustomerIfNotExists(createdCustomer);

        assertEquals(createdCustomer.getId(), duplicateCustomer.getId());

    }

    @Test
    void test_Should_Register_A_Customer_When_Valid_Data () {

        Customer customer = Customer.builder()
                .fullName("")
                .document("333.333.333-33")
                .phoneNumber("(11)94529-6701")
                .email("test_register_customer@email.com")
                .address(Address.builder().build())
                .build();

        assertThrows(CustomerValidationException.class, () -> {

            customerController.registerCustomer(customer);

        });

        customer.setFullName("Alberto Miranda Dencowski");

        assertDoesNotThrow(() -> {

            Customer createdCustomer = customerController.registerCustomer(customer);

        });


        //Customer duplicateCustomer = customerController.registerCustomer(createdCustomer);

    }

    @Test
    void test_Should_Throw_Exception_When_Customer_Email_Is_In_Use () {

        Customer customer = Customer.builder()
                .fullName("Alberto Miranda Dencowski")
                .document("333.333.333-33")
                .phoneNumber("(11)94529-6701")
                .email("allowski@email.com")
                .address(Address.builder().build())
                .build();

        Customer createdCustomer = customerController.registerCustomer(customer);

        assertThrows(EmailInUseException.class, () -> {

            createdCustomer.setId(null);

            Customer duplicateCustomer = customerController.registerCustomer(createdCustomer);

        });

    }



}
