package com.shopper.ecommerce.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ZERO;

@Entity
@Data
@NoArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @OneToOne
    Customer customer;

    Address address;

    List<ShoppingCartItem> items = new ArrayList<>();

    BigDecimal total = ZERO;

    Long itemCount = 0L;

}
