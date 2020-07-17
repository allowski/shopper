package com.shopper.ecommerce.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

import static java.lang.Boolean.TRUE;
import static java.math.BigDecimal.ZERO;

@Entity
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;

    String description;

    String pictureUrl;

    BigDecimal price = ZERO;

    Boolean active = TRUE;

}
