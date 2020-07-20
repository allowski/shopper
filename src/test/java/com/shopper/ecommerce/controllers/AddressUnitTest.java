package com.shopper.ecommerce.controllers;

import com.shopper.ecommerce.exceptions.AddressValidationException;
import com.shopper.ecommerce.models.Address;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddressUnitTest {

    AddressController addressController = new AddressController();

    @Test
    void test_Should_Throw_Exception_When_Invalid_Address() {

        assertThrows(AddressValidationException.class, () -> {

            addressController.validate(Address.builder().build());

        });

        assertThrows(AddressValidationException.class, () -> {

            addressController.validate(Address.builder().postalCode("12345-678").build());

        });

    }

    @Test
    void test_Should_Not_Throw_Exception_When_Invalid_Address() {

        assertDoesNotThrow(() -> {

            addressController.validate(Address.builder()
                    .postalCode("06404-326 ")
                    .state("SP")
                    .city("Barueri")
                    .district("Bethaville")
                    .address("Avda Trindade")
                    .number("219")
                    .complement("Sala 109")
                    .build());

        });

        assertDoesNotThrow(() -> {

            addressController.validate(Address.builder()
                    .postalCode("-$06404-326 ")
                    .state("SP")
                    .city("Barueri")
                    .district("Bethaville")
                    .address("Avda Trindade")
                    .number("219")
                    .complement("Sala 109")
                    .build());

        });


    }

}
