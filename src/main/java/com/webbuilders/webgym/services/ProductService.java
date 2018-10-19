package com.webbuilders.webgym.services;

import com.webbuilders.webgym.domain.Product;
import java.util.Set;

public interface ProductService {

    /**
     * Get every {@link com.webbuilders.webgym.domain.Product}
     * from the database.
     *
     * @return the products
     */
    Set<Product> getProducts();

    /**
     * Find a {@link com.webbuilders.webgym.domain.Product}
     * by id.
     *
     * @param id - a product id
     * @return the found product
     */
    Product findProductById(Long id);

}
