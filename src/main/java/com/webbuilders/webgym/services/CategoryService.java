package com.webbuilders.webgym.services;

import com.webbuilders.webgym.domain.Category;
import com.webbuilders.webgym.domain.Product;

import java.util.Set;

public interface CategoryService {

    /**
     * Get every {@link com.webbuilders.webgym.domain.Category}
     * from the database.
     *
     * @return the categories
     */
    Set<Category> getCategories();

    /**
     * Find a {@link com.webbuilders.webgym.domain.Category}
     * by id.
     *
     * @param id - a category id
     * @return the found category
     */
    Category findCategoryById(Long id);

}
