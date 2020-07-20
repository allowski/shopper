package com.shopper.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This model will be serialized to json, not to be persisted database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

    String postalCode;

    String city;

    String state;

    String district;

    String address;

    String number;

    String complement;

}
