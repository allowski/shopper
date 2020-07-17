package com.shopper.ecommerce.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String fullName;

    String document;

    String phoneNumber;

    String email;

    ArrayList<Address> addresses = new ArrayList<>();

    @CreationTimestamp
    Date createAt;

    @UpdateTimestamp
    Date updatedAt;

}
