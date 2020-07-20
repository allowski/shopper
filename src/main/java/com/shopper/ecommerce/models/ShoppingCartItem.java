package com.shopper.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * This model is the sale item it self, it contains all the fields
 * of product model, but includes the quantity and an optional comment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCartItem{

    Long id;

    String name;

    String description;

    String pictureUrl;

    BigDecimal price;

    Long qty;

    String comments;

}
