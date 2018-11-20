package com.webbuilders.webgym.converters;

import com.webbuilders.webgym.commands.CategoryCommand;
import com.webbuilders.webgym.commands.ProductCommand;
import com.webbuilders.webgym.domain.Category;
import com.webbuilders.webgym.domain.Product;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class CategoryCommandToCategoryTest {

    CategoryCommandToCategory categoryCommandToCategory;
    CategoryCommand categoryCommand;
    Long id = 1L;
    String description = "desc";

    @Before
    public void setUp() throws Exception {
        categoryCommandToCategory = new CategoryCommandToCategory();
        categoryCommand = new CategoryCommand();
    }

    @Test
    public void convertNull() {

        categoryCommand = null;
        assertEquals(categoryCommandToCategory.convert(categoryCommand), null);
    }

    @Test
    public void convert() {

        categoryCommand.setId(id);
        categoryCommand.setDescription(description);

        Category result = categoryCommandToCategory.convert(categoryCommand);

        assertEquals(result.getId(), id);
        assertEquals(result.getDescription(), description);
    }
}