package com.shopper.ecommerce.repositories;

import com.shopper.ecommerce.models.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

}
