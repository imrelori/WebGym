package com.webbuilders.webgym.repositories;

import com.webbuilders.webgym.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
