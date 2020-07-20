package com.shopper.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shopper.ecommerce.utils.AddressConverter;
import com.shopper.ecommerce.utils.ShoppingCartItemListConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ZERO;

/**
 * This whole model is a sale, containing customer, address for shipping, items, total and so on.
 */
@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    Customer customer;

    /**
     * Our address will be stored as JSON to the database.
     */
    @Convert(converter = AddressConverter.class)
    @Column(columnDefinition = "varchar(2048)")
    Address address;

    /**
     * Our items will be stored as JSON to the database.
     */
    @Convert(converter = ShoppingCartItemListConverter.class)
    @Column(columnDefinition = "varchar(2048)")
    List<ShoppingCartItem> items = new ArrayList<>();

    BigDecimal total = ZERO;

    Long itemCount = 0L;

}
