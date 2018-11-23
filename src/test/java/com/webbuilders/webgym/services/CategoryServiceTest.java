package com.webbuilders.webgym.services;

import com.webbuilders.webgym.domain.Category;
import com.webbuilders.webgym.domain.Product;
import com.webbuilders.webgym.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    CategoryServiceImpl categoryService;
    Category category;
    Long categoryId = 1L;
    Long categoryIdFail = 2L;

    @Mock
    CategoryRepository categoryRepository;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        categoryService = new CategoryServiceImpl(categoryRepository);
        category = new Category();
    }

    @Test
    public void getCategories() throws Exception {

        HashSet categoryData = new HashSet();
        categoryData.add(category);

        when(categoryService.getCategories()).thenReturn(categoryData);

        Set<Category> categories = categoryService.getCategories();

        assertEquals(categories.size(), 1);
        assertTrue(categories.contains(category));

        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void findCategoryById() throws Exception {

        category.setId(categoryId);
        Optional<Category> categoryOptional = Optional.of(category)
                .filter(e -> e.getId().equals(categoryId));

        when(categoryRepository.findById(categoryId)).thenReturn(categoryOptional);

        Category returned = categoryService.findCategoryById(categoryId);

        verify(categoryRepository, times(1)).findById(categoryId);
        verifyNoMoreInteractions(categoryRepository);

        assertEquals(category, returned);
    }

    @Test(expected = RuntimeException.class)
    public void findCategoryByIdNoCategory() throws Exception {

        category.setId(categoryIdFail);
        Optional<Category> categoryOptional = Optional.of(category)
                .filter(e -> e.getId().equals(categoryId));

        when(categoryRepository.findById(categoryId)).thenReturn(categoryOptional);

        categoryService.findCategoryById(categoryIdFail);
    }
}
