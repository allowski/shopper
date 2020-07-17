package com.shopper.ecommerce.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
public class Checkout {

    Customer customer;

    Address address;

    List<HashMap<Product, Long>> items;

}
