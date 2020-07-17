package com.shopper.ecommerce.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Address {

    String postalCode;

    String city;

    String state;

    String district;

    String address;

    String number;

    String complement;

}
