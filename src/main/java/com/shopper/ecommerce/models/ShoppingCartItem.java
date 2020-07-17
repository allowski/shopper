package com.shopper.ecommerce.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShoppingCartItem extends Product{

    Long qty;

    String comments;

}
