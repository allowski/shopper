package com.shopper.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shopper.ecommerce.utils.AddressConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    String fullName;

    String document;

    String phoneNumber;

    String email;

    @Convert(converter = AddressConverter.class)
    @Column(columnDefinition = "varchar(2048)")
    Address address;

    @CreationTimestamp
    Date createAt;

    @UpdateTimestamp
    Date updatedAt;

}
