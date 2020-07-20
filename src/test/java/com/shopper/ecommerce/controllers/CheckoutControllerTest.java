package com.shopper.ecommerce.controllers;

import com.shopper.ecommerce.exceptions.CheckoutException;
import com.shopper.ecommerce.exceptions.InvalidItemQuantityException;
import com.shopper.ecommerce.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration
public class CheckoutControllerTest {

    @Autowired
    CheckoutController checkoutController;

    @Test
    void test_Should_Throw_Exception_When_Invalid_Item_Count () {

        assertNotNull(checkoutController);

        assertThrows(InvalidItemQuantityException.class, () -> {
            checkoutController.checkItems(Checkout.builder()
                .customer(Customer.builder()
                        .fullName("Alberto Miranda Dencowski")
                        .email("allowski@gmail.com")
                        .phoneNumber("11-94529-6701")
                        .id(1L)
                        .address(Address.builder().build())
                        .build()
                )
                .items(
                    Arrays.asList(
                        ShoppingCartItem.builder().qty(0L).id(1L).price(BigDecimal.valueOf(6)).build(),
                        ShoppingCartItem.builder().qty(0L).id(1L).price(BigDecimal.valueOf(6)).build()
                    )
                ).build());
        });
    }

    @Test
    void test_Should_Throw_Exception_When_Item_Price_Tampered() {

        assertThrows(CheckoutException.class, () -> {
            checkoutController.checkItems(Checkout.builder()
                    .customer(Customer.builder()
                            .fullName("Alberto Miranda Dencowski")
                            .email("allowski@gmail.com")
                            .phoneNumber("11-94529-6701")
                            .id(1L)
                            .address(Address.builder().build())
                            .build()
                    )
                    .items(
                            Arrays.asList(
                                    ShoppingCartItem.builder().qty(1L).id(1L).price(BigDecimal.valueOf(6)).build(),
                                    ShoppingCartItem.builder().qty(1L).id(2L).price(BigDecimal.valueOf(4)).build()
                            )
                    ).build());
        });
    }

    @Test
    void test_Should_Create_Sale_When_Payload_Is_Valid() {

        AtomicReference<Sale> sale = new AtomicReference<>();

        assertDoesNotThrow(() -> {


            try {
                sale.set(checkoutController.processCheckout(Checkout.builder()
                        .customer(Customer.builder()
                                .fullName("Alberto Miranda Dencowski")
                                .email("allowski@gmail.com")
                                .phoneNumber("11-94529-6701")
                                .document("333.333.333-33")
                                .id(1L)
                                .build()
                        )
                        .address(
                                Address.builder()
                                        .postalCode("06404-326 ")
                                        .state("SP")
                                        .city("Barueri")
                                        .district("Bethaville")
                                        .address("Avda Trindade")
                                        .number("219")
                                        .complement("Sala 109")
                                        .build()
                        )
                        .items(
                                Arrays.asList(
                                        ShoppingCartItem.builder().qty(1L).id(1L).price(BigDecimal.valueOf(6)).build(),
                                        ShoppingCartItem.builder().qty(1L).id(2L).price(BigDecimal.valueOf(5)).build()
                                )
                        ).build()));
            }catch (Exception e){

                e.printStackTrace();

            }

        });

        assertNotNull(sale.get());

        assert(sale.get().getId() > 0);
    }
}
