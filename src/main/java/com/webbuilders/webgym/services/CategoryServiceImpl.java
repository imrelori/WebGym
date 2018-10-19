package com.webbuilders.webgym.services;

import com.webbuilders.webgym.domain.Category;
import com.webbuilders.webgym.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Set<Category> getCategories() {

        Set<Category> categorySet = new HashSet<>();
        categoryRepository.findAll().iterator().forEachRemaining(categorySet::add);

        return categorySet;
    }

    @Override
    public Category findCategoryById(Long id) {

        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if (!categoryOptional.isPresent()) {
            throw new RuntimeException("Category not found with id:" + id);
        }

        return categoryOptional.get();
    }
}
