package com.shopper.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Builder
public class Checkout {

    Customer customer;

    Address address;

    List<ShoppingCartItem> items;

    public Checkout() {

    }
}
