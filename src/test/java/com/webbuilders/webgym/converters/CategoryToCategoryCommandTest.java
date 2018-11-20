package com.webbuilders.webgym.converters;

import com.webbuilders.webgym.commands.CategoryCommand;
import com.webbuilders.webgym.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryToCategoryCommandTest {

    CategoryToCategoryCommand categoryToCategoryCommand;
    Category category;
    Long id = 1L;
    String description = "desc";

    @Before
    public void setUp() throws Exception {
        categoryToCategoryCommand = new CategoryToCategoryCommand();
        category = new Category();
    }

    @Test
    public void convertNull() {

        category = null;
        assertEquals(categoryToCategoryCommand.convert(category), null);
    }

    @Test
    public void convert() {

        category.setId(id);
        category.setDescription(description);
        CategoryCommand result = categoryToCategoryCommand.convert(category);

        assertEquals(result.getId(), id);
        assertEquals(result.getDescription(), description);
    }
}