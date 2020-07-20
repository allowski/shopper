package com.shopper.ecommerce.repositories;

import com.shopper.ecommerce.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    Page<Product> findAllByActiveEquals(Pageable pageable, Boolean active);

}
