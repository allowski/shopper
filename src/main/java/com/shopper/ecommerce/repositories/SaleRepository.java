package com.shopper.ecommerce.repositories;

import com.shopper.ecommerce.models.Sale;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends PagingAndSortingRepository<Sale, Long> {
}
