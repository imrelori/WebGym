package com.webbuilders.webgym.converters;

import com.webbuilders.webgym.commands.ProductCommand;
import com.webbuilders.webgym.domain.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ProductToProductCommandTest {

    DetailToDetailCommand detailToDetailCommand;
    CategoryToCategoryCommand categoryToCategoryCommand;
    ComponentToComponentCommand componentToComponentCommand;

    ProductToProductCommand productToProductCommand;
    Product product;

    Long id = 1L;
    String name = "product";
    String url = "https://product.hu";
    Integer servings = 2;
    String pack = "Big";
    String description = "description";
    Integer price = 100;
    CartLine cartLine = new CartLine();
    Level level = Level.ADVANCED;

    @Before
    public void setUp() throws Exception {

        componentToComponentCommand = new ComponentToComponentCommand();
        detailToDetailCommand = new DetailToDetailCommand(componentToComponentCommand);
        categoryToCategoryCommand = new CategoryToCategoryCommand();
        productToProductCommand = new ProductToProductCommand(detailToDetailCommand, categoryToCategoryCommand);

        product = new Product();
        product.setId(id);
        product.setCartLine(cartLine);
        product.setDescription(description);
        product.setLevel(level);
        product.setName(name);
        product.setPack(pack);
        product.setPrice(price);
        product.setServings(servings);
        product.setUrl(url);
    }

    @Test
    public void convertProductCommandIsNull() {

        product = null;

        assertEquals(productToProductCommand.convert(product), null);
    }

    @Test
    public void convertAllFalse() {

        ProductCommand result = productToProductCommand.convert(product);

        assertEquals(result.getId(), id);
        assertTrue(result.getDetails().isEmpty());
        assertTrue(result.getCategories().isEmpty());
        assertEquals(result.getCartLine(), cartLine);
        assertEquals(result.getDescription(), description);
        assertEquals(result.getLevel(), level);
        assertEquals(result.getName(), name);
        assertEquals(result.getPack(), pack);
        assertEquals(result.getPrice(), price);
        assertEquals(result.getServings(), servings);
        assertEquals(result.getUrl(), url);
    }

    @Test
    public void convert() {

        Long categoryId = 100L;
        Category category = new Category();
        category.setId(categoryId);
        Set<Category> categories = new HashSet<>();
        categories.add(category);
        product.setCategories(categories);

        Long detailId = 200L;
        Details detail = new Details();
        detail.setId(detailId);
        Set<Details> details = new HashSet<>();
        details.add(detail);
        product.setDetails(details);

        ProductCommand result = productToProductCommand.convert(product);

        assertEquals(result.getId(), id);
        assertEquals(result.getDetails().size(), 1);
        assertEquals(result.getDetails().stream().findFirst().get().getId(), detailId);
        assertEquals(result.getCategories().size(), 1);
        assertEquals(result.getCategories().stream().findFirst().get().getId(), categoryId);
        assertEquals(result.getCartLine(), cartLine);
        assertEquals(result.getDescription(), description);
        assertEquals(result.getLevel(), level);
        assertEquals(result.getName(), name);
        assertEquals(result.getPack(), pack);
        assertEquals(result.getPrice(), price);
        assertEquals(result.getServings(), servings);
        assertEquals(result.getUrl(), url);
    }
}